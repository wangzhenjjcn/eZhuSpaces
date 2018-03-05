/*
 * Forge SDK
 * The Forge Platform contains an expanding collection of web service components that can be used with Autodesk cloud-based products or your own technologies. Take advantage of Autodesk’s expertise in design and engineering.
 *
 * OpenAPI spec version: 0.1.0
 * Contact: forge.help@autodesk.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.autodesk.client.api;

import com.sun.jersey.api.client.GenericType;

import com.autodesk.client.ApiException;
import com.autodesk.client.ApiClient;
import com.autodesk.client.Configuration;
import com.autodesk.client.model.*;
import com.autodesk.client.Pair;
import com.autodesk.client.auth.Credentials;
import com.autodesk.client.auth.Authentication;
import com.autodesk.client.ApiResponse;

import java.io.File;

import com.autodesk.client.model.PostBucketsPayload;
import com.autodesk.client.model.Bucket;
import com.autodesk.client.model.Reason;
import com.autodesk.client.model.Buckets;


import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BucketsApi {
  private ApiClient apiClient;

  public BucketsApi() {
    this(Configuration.getDefaultApiClient());
  }

  public BucketsApi(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  public ApiClient getApiClient() {
    return apiClient;
  }

  public void setApiClient(ApiClient apiClient) {
    this.apiClient = apiClient;
  }

  /**
   * 
   * Use this endpoint to create a bucket. Buckets are arbitrary spaces created and owned by applications. Bucket keys are globally unique across all regions, regardless of where they were created, and they cannot be changed. The application creating the bucket is the owner of the bucket. 
   * @param postBuckets Body Structure (required)
   * @param xAdsRegion The region where the bucket resides Acceptable values: &#x60;US&#x60;, &#x60;EMEA&#x60; Default is &#x60;US&#x60;  (optional, default to US)
   * @return Bucket
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Bucket> createBucket(PostBucketsPayload postBuckets, String xAdsRegion,  Authentication oauth2, Credentials credentials) throws ApiException, Exception {

    Object localVarPostBody = postBuckets;
    
    // verify the required parameter 'postBuckets' is set
    if (postBuckets == null) {
      throw new ApiException(400, "Missing the required parameter 'postBuckets' when calling createBucket");
    }
    
    // create path and map variables
    String localVarPath = "/oss/v2/buckets".replaceAll("\\{format\\}","json");

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    if (xAdsRegion != null)
      localVarHeaderParams.put("x-ads-region", apiClient.parameterToString(xAdsRegion));

    
    final String[] localVarAccepts = {
      "application/vnd.api+json", "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    GenericType<Bucket> localVarReturnType = new GenericType<Bucket>() {};
    return apiClient.invokeAPI(oauth2, credentials, localVarPath, "POST", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarReturnType);
      }
  /**
   * 
   * This endpoint will delete a bucket. 
   * @param bucketKey URL-encoded bucket key (required)
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Void> deleteBucket(String bucketKey,  Authentication oauth2, Credentials credentials) throws ApiException, Exception {

    Object localVarPostBody = null;
    
    // verify the required parameter 'bucketKey' is set
    if (bucketKey == null) {
      throw new ApiException(400, "Missing the required parameter 'bucketKey' when calling deleteBucket");
    }
    
    // create path and map variables
    String localVarPath = "/oss/v2/buckets/{bucketKey}".replaceAll("\\{format\\}","json")
      .replaceAll("\\{" + "bucketKey" + "\\}", apiClient.escapeString(bucketKey.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    
    
    final String[] localVarAccepts = {
      "application/vnd.api+json", "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);


    return apiClient.invokeAPI(oauth2, credentials, localVarPath, "DELETE", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, null);
  }
  /**
   * 
   * This endpoint will return the buckets owned by the application. This endpoint supports pagination.
   * @param bucketKey URL-encoded bucket key (required)
   * @return Bucket
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Bucket> getBucketDetails(String bucketKey,  Authentication oauth2, Credentials credentials) throws ApiException, Exception {

    Object localVarPostBody = null;
    
    // verify the required parameter 'bucketKey' is set
    if (bucketKey == null) {
      throw new ApiException(400, "Missing the required parameter 'bucketKey' when calling getBucketDetails");
    }
    
    // create path and map variables
    String localVarPath = "/oss/v2/buckets/{bucketKey}/details".replaceAll("\\{format\\}","json")
      .replaceAll("\\{" + "bucketKey" + "\\}", apiClient.escapeString(bucketKey.toString()));

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();


    
    
    final String[] localVarAccepts = {
      "application/vnd.api+json", "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    GenericType<Bucket> localVarReturnType = new GenericType<Bucket>() {};
    return apiClient.invokeAPI(oauth2, credentials, localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarReturnType);
      }
  /**
   * 
   * This endpoint will return the buckets owned by the application. This endpoint supports pagination. 
   * @param region The region where the bucket resides Acceptable values: &#x60;US&#x60;, &#x60;EMEA&#x60; Default is &#x60;US&#x60;  (optional, default to US)
   * @param limit Limit to the response size, Acceptable values: 1-100 Default &#x3D; 10  (optional, default to 10)
   * @param startAt Key to use as an offset to continue pagination This is typically the last bucket key found in a preceding GET buckets response  (optional)
   * @return Buckets
   * @throws ApiException if fails to make API call
   */
  public ApiResponse<Buckets> getBuckets(String region, Integer limit, String startAt,  Authentication oauth2, Credentials credentials) throws ApiException, Exception {

    Object localVarPostBody = null;
    
    // create path and map variables
    String localVarPath = "/oss/v2/buckets".replaceAll("\\{format\\}","json");

    // query params
    List<Pair> localVarQueryParams = new ArrayList<Pair>();
    Map<String, String> localVarHeaderParams = new HashMap<String, String>();
    Map<String, Object> localVarFormParams = new HashMap<String, Object>();

    localVarQueryParams.addAll(apiClient.parameterToPairs("", "region", region));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "limit", limit));
    localVarQueryParams.addAll(apiClient.parameterToPairs("", "startAt", startAt));

    
    
    final String[] localVarAccepts = {
      "application/vnd.api+json", "application/json"
    };
    final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);

    final String[] localVarContentTypes = {
      "application/json"
    };
    final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);

    GenericType<Buckets> localVarReturnType = new GenericType<Buckets>() {};
    return apiClient.invokeAPI(oauth2, credentials, localVarPath, "GET", localVarQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, localVarAccept, localVarContentType, localVarReturnType);
      }
}