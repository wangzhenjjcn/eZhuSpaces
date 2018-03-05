package com.ezhuspace.configuration;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.UriBuilder;

import org.apache.commons.codec.binary.Base64;

import com.autodesk.client.ApiException;
import com.autodesk.client.ApiResponse;
import com.autodesk.client.api.BucketsApi;
import com.autodesk.client.api.DerivativesApi;
import com.autodesk.client.api.ObjectsApi;
import com.autodesk.client.auth.Credentials;
import com.autodesk.client.auth.OAuth2TwoLegged;
import com.autodesk.client.model.Bucket;
import com.autodesk.client.model.Job;
import com.autodesk.client.model.JobPayload;
import com.autodesk.client.model.JobPayloadInput;
import com.autodesk.client.model.JobPayloadItem;
import com.autodesk.client.model.JobPayloadOutput;
import com.autodesk.client.model.Manifest;
import com.autodesk.client.model.ObjectDetails;
import com.autodesk.client.model.PostBucketsPayload;

/**
 * @author Wang Zhen <A.Hleb.King wangzhenjjcn@gmail.com>
 * @since 2018年1月20日 上午11:06:30
 */

public class AutodeskConf {
	public static final String CLIENT_ID = " ";
	public static final String CLIENT_SECRET = " ";
	public static final String BUCKET_KEY = "point-" + CLIENT_ID.toLowerCase();
	public static final String FILE_NAME = "point-" + new Date(System.currentTimeMillis()).getMonth() + "-"
	        + new Date(System.currentTimeMillis()).getDate() + "-" + new Date(System.currentTimeMillis()).getHours() + "-"
	        + new Date(System.currentTimeMillis()).getMinutes() + "-" + new Date(System.currentTimeMillis()).getSeconds() + ".skp";
	public static final String FILE_PATH = "data/1234.skp";
	
	public static final BucketsApi bucketsApi = new BucketsApi();
	public static final ObjectsApi objectsApi = new ObjectsApi();
	public static final DerivativesApi derivativesApi = new DerivativesApi();
	
	public static OAuth2TwoLegged oauth2TwoLegged;
	public static Credentials twoLeggedCredentials;
	
	/**
	 * Initialize the 2-legged OAuth 2.0 client, and optionally set specific
	 * scopes.
	 * 
	 * @throws Exception
	 */
	public static void initializeOAuth() throws Exception {
		
		// You must provide at least one valid scope
		List<String> scopes = new ArrayList<String>();
		scopes.add("data:read");
		scopes.add("data:write");
		scopes.add("bucket:create");
		scopes.add("bucket:read");
		
		// Set autoRefresh to `true` to automatically refresh the access token
		// when it expires.
		oauth2TwoLegged = new OAuth2TwoLegged(CLIENT_ID, CLIENT_SECRET, scopes, true);
		twoLeggedCredentials = oauth2TwoLegged.authenticate();
	}
	
	/**
	 * Example of how to create a new bucket using Forge SDK. Uses the
	 * oauth2TwoLegged and twoLeggedCredentials objects that you retrieved
	 * previously.
	 * 
	 * @throws com.autodesk.client.ApiException
	 * @throws Exception
	 */
	public static void createBucket() throws ApiException, Exception {
		System.out.println("***** Sending createBucket request");
		PostBucketsPayload payload = new PostBucketsPayload();
		payload.setBucketKey(BUCKET_KEY);
		payload.setPolicyKey(PostBucketsPayload.PolicyKeyEnum.PERSISTENT);
		ApiResponse<Bucket> response = bucketsApi.createBucket(payload, "US", oauth2TwoLegged, twoLeggedCredentials);
		System.out.println("***** Response for createBucket: " + response.getData());
	}
	
	/**
	 * Example of how to upload a file to the bucket. Uses the oauth2TwoLegged
	 * and twoLeggedCredentials objects that you retrieved previously.
	 * 
	 * @throws java.io.FileNotFoundException
	 * @throws com.autodesk.client.ApiException
	 * @throws Exception
	 */
	public static ObjectDetails uploadFile(File fileToUpload) throws FileNotFoundException, ApiException, Exception {
		System.out.println("***** Sending uploadFile request");
//		File fileToUpload = new File(FILE_PATH);
		ApiResponse<ObjectDetails> response = objectsApi.uploadObject(BUCKET_KEY, FILE_NAME, (int) fileToUpload.length(), fileToUpload, null, null,
		        oauth2TwoLegged, twoLeggedCredentials);
		
		System.out.println("***** Response for uploadFile: ");
		ObjectDetails objectDetails = response.getData();
		System.out.println("Uploaded object Details - Location: " + objectDetails.getLocation() + ", Size:" + objectDetails.getSize());
		return objectDetails;
	}
	
	/**
	 * Example of how to send a translate to SVF job request. Uses the
	 * oauth2TwoLegged and twoLeggedCredentials objects that you retrieved
	 * previously.
	 * 
	 * @param urn
	 *            - the urn of the file to translate
	 * @throws com.autodesk.client.ApiException
	 * @throws Exception
	 */
	public static Job translateToSVF(String urn) throws ApiException, Exception {
		System.out.println("***** Sending Derivative API translate request");
		
		JobPayload job = new JobPayload();
		
		byte[] urnBase64 = Base64.encodeBase64(urn.getBytes());
		
		JobPayloadInput input = new JobPayloadInput();
		input.setUrn(new String(urnBase64));
		
		JobPayloadOutput output = new JobPayloadOutput();
		JobPayloadItem formats = new JobPayloadItem();
		formats.setType(JobPayloadItem.TypeEnum.SVF);
		formats.setViews(Arrays.asList(JobPayloadItem.ViewsEnum._3D));
		output.setFormats(Arrays.asList(formats));
		
		job.setInput(input);
		job.setOutput(output);
		
		ApiResponse<Job> response = derivativesApi.translate(job, true, oauth2TwoLegged, twoLeggedCredentials);
		System.out.println("***** Response for Translating File to SVF: " + response.getData());
		
		return response.getData();
	}
	
	/**
	 * Example of how to query the status of a translate job. Uses the
	 * oauth2TwoLegged and twoLeggedCredentials objects that you retrieved
	 * previously.
	 * 
	 * @param base64Urn
	 *            - the urn of the file to translate in base 64 format
	 * @throws com.autodesk.client.ApiException
	 * @throws Exception
	 */
	public static Manifest verifyJobComplete(String base64Urn) throws ApiException, Exception {
		System.out.println("***** Sending getManifest request");
		boolean isComplete = false;
		ApiResponse<Manifest> response = null;
		
		while (!isComplete) {
			response = derivativesApi.getManifest(base64Urn, null, oauth2TwoLegged, twoLeggedCredentials);
			Manifest manifest = response.getData();
			if (response.getData().getProgress().equals("complete")) {
				isComplete = true;
				System.out.println("***** Finished translating your file to SVF - status: " + manifest.getStatus() + ", progress:"
				        + manifest.getProgress());
			} else {
				System.out.println("***** Haven't finished translating your file to SVF - status: " + manifest.getStatus() + ", progress:"
				        + manifest.getProgress());
				Thread.sleep(1000);
			}
		}
		
		return response.getData();
		
	}
	
	/**
	 * Open translated SVF file in the viewer Uses the twoLeggedCredentials
	 * object that you retrieved previously. Opens the file statically from your
	 * hard drive with url parameters for the accessToken and for the urn of the
	 * file to show.
	 * 
	 * @param base64Urn
	 * @throws IOException
	 */
	public static void openViewer(String base64Urn) throws IOException {
		System.out.println("***** Opening SVF file in viewer with urn:" + base64Urn);
		File htmlFile = new File("data/viewer.html");
		// UriBuilder builder = UriBuilder.fromPath("file:///" +
		// htmlFile.getAbsolutePath()).queryParam("token",twoLeggedCredentials.getAccessToken()).queryParam("urn",base64Urn);
		UriBuilder builder = UriBuilder.fromPath("http://localhost/viewer.html").queryParam("token", twoLeggedCredentials.getAccessToken())
		        .queryParam("urn", base64Urn);
		System.out.println("URI:" + builder.build());
		System.out.println("        S:" + builder.toString());
		Desktop.getDesktop().browse(builder.build());
	}
	
	/**
	 * Example of how to delete a file that was uploaded by the application.
	 * Uses the oauth2TwoLegged and twoLeggedCredentials objects that you
	 * retrieved previously.
	 * 
	 * @throws com.autodesk.client.ApiException
	 * @throws Exception
	 */
	public static void deleteFile() throws ApiException, Exception {
		System.out.println("***** Sending deleteFile request");
		ApiResponse<Void> response = objectsApi.deleteObject(BUCKET_KEY, FILE_NAME, oauth2TwoLegged, twoLeggedCredentials);
		System.out.println("***** Response Code for deleting File: " + response.getStatusCode());
	}
	
}
