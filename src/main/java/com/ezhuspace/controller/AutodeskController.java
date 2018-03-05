package com.ezhuspace.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

/**
 * @author Wang Zhen <A.Hleb.King wangzhenjjcn@gmail.com>
 * @since 2018年1月20日 上午10:59:28
 */
@Controller
public class AutodeskController {
	private static final Logger LOG = LoggerFactory.getLogger(AutodeskController.class);
	
	public AutodeskController() {
		
	}
	
	@RequestMapping(path = "/send", method = RequestMethod.POST)
	public void send(HttpServletRequest request, HttpServletResponse response) throws IOException {
		LOG.debug("send...........................................................2222");
		response.getWriter().append("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFUVK");
		response.getWriter().flush();
	}
	
	@RequestMapping(value = "/buy", method = RequestMethod.POST)
	public void buy(HttpServletRequest request, HttpServletResponse response) throws IOException {
		LOG.debug("buy...........................................................2222");
		
	}
	
	@RequestMapping(path = "/check", method = RequestMethod.GET)
	public void check(HttpServletRequest request, HttpServletResponse response) throws IOException {
		LOG.debug("check...........................................................2222");
		
	}
	
	@RequestMapping(path = "/sendFile", method = RequestMethod.POST)
	public void sendFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		LOG.debug("sendFile...........................................................2222");
		
	}
	
	@RequestMapping(value = "/3d/upload", method = RequestMethod.POST, path = "/3d/upload")
	public void uploadFile2Html(HttpServletRequest request, HttpServletResponse response) throws IOException {
		LOG.debug("22222222...........................................................2222");
		// 得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
		String savePath = request.getServletContext().getRealPath("/upload");
		
		// 上传时生成的临时文件保存目录
		LOG.debug("savePPPPPPPPPPPPPPPath");
		LOG.debug(savePath);
		String tempPath = request.getServletContext().getRealPath("/upload/tmp");
		LOG.debug("tempPPPPPPPPPPPPPPPPath");
		LOG.debug(tempPath);
		File tmpFile = new File(tempPath);
		if (!tmpFile.exists()) {
			// 创建临时目录
			tmpFile.mkdir();
		}
		// 消息提示
		String message = "";
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			factory.setSizeThreshold(1024 * 100);// 设置缓冲区的大小为100KB，如果不指定，那么缓冲区的大小默认是10KB
			factory.setRepository(tmpFile);
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setProgressListener(new ProgressListener() {
				public void update(long pBytesRead, long pContentLength, int arg2) {
					LOG.debug("文件大小为：" + pContentLength + ",当前已处理：" + pBytesRead);
					LOG.debug("00000000000000000000000000066600000000000");
				}
			});
			upload.setHeaderEncoding("UTF-8");
			if (!ServletFileUpload.isMultipartContent(request)) {
				return;
			}
			upload.setFileSizeMax(1024 * 1024 * 1024l);
			upload.setSizeMax(1024 * 1024 * 100 * 1024l);
			List<FileItem> list = upload.parseRequest(request);
			LOG.debug("----request.getContentType-" + request.getContentType() + "----request.getContentLength-" + request.getContentLength()
			        + "------.getPathInfo--" + request.getPathInfo());
			LOG.debug("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\" + list.size());
			list = upload.parseRequest(request);
			LOG.debug("----request.getContentType-" + request.getContentType() + "----request.getContentLength-" + request.getContentLength()
			        + "------.getPathInfo--" + request.getPathInfo());
			LOG.debug("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\" + list.size());
			try {
	            StandardMultipartHttpServletRequest req = (StandardMultipartHttpServletRequest) request;
	            Iterator<String> iterator = req.getFileNames();
	            while (iterator.hasNext()) {
	                MultipartFile file = req.getFile(iterator.next());
	                	String filename = file.getName().substring(file.getName().lastIndexOf("\\") + 1);
						String saveFilename = makeFileName(filename);
						String realSavePath = makePath(saveFilename, savePath);
	                    File desFile = new File(realSavePath);
	                    if(!desFile.getParentFile().exists()){
	                        desFile.mkdirs();
	                    }
	                    try {
	                        file.transferTo(desFile);
	                        LOG.debug(desFile.getPath());
	                        LOG.debug(desFile.getName());
	                        LOG.debug(desFile.getAbsolutePath());
	                    } catch (IllegalStateException | IOException e) {
	                        e.printStackTrace();
	                        LOG.debug("msg", "处理失败");
	                    }
	                }
	        }catch (Exception e){
	            e.printStackTrace();
	        }
		} catch (FileUploadBase.FileSizeLimitExceededException e) {
			e.printStackTrace();
			request.setAttribute("message", "单个文件超出最大值！！！");
			return;
		} catch (FileUploadBase.SizeLimitExceededException e) {
			e.printStackTrace();
			request.setAttribute("message", "上传文件的总的大小超出限制的最大值！！！");
			return;
		} catch (Exception e) {
			message = "文件上传失败！";
			e.printStackTrace();
		}
		request.setAttribute("message", message);
		response.getWriter().append(message);
		response.getWriter().flush();
		// try {
		// AutodeskConf.initializeOAuth();
		//
		// try {
		// AutodeskConf.createBucket();
		// } catch (ApiException e) {
		// System.err.println("Error creating bucket : " + e.getResponseBody());
		// } catch (Exception e) {
		// System.err.println("Error creating bucket : ");
		// }
		// try {
		// ObjectDetails uploadedObject = AutodeskConf.uploadFile(new File(""));
		// try {
		// Job job = AutodeskConf.translateToSVF(uploadedObject.getObjectId());
		//
		// if (job.getResult().equals("success")) {
		// String base64Urn = job.getUrn();
		//
		// Manifest manifest = AutodeskConf.verifyJobComplete(base64Urn);
		// if (manifest.getStatus().equals("success")) {
		// AutodeskConf.openViewer(manifest.getUrn());
		// }
		// }
		// } catch (ApiException e) {
		// System.err.println("Error translating file : " +
		// e.getResponseBody());
		// } catch (Exception e) {
		// System.err.println("Error translating file : ");
		// }
		// } catch (ApiException e) {
		// System.err.println("Error uploading file : " + e.getResponseBody());
		// } catch (Exception e) {
		// System.err.println("Error uploading file : ");
		// }
		// } catch (ApiException e) {
		// System.err.println("Error Initializing OAuth client : " +
		// e.getResponseBody());
		// } catch (Exception e) {
		// System.err.println("Error Initializing OAuth client : ");
		// }
		
		return;
	}
	
	@RequestMapping(path = "/3d", method = { RequestMethod.GET })
	public void get3DHtml(HttpServletRequest request, HttpServletResponse response) throws IOException {
		uploadFile2Html(request, response);
	}
	
	/**
	 * @Method: makeFileName
	 * @Description: 生成上传文件的文件名，文件名以：uuid+"_"+文件的原始名称
	 * @param filename
	 *            文件的原始名称
	 * @return uuid+"_"+文件的原始名称
	 */
	private String makeFileName(String filename) { // 2.jpg
		// 为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
		return UUID.randomUUID().toString() + "_" + filename;
	}
	
	/**
	 * 为防止一个目录下面出现太多文件，要使用hash算法打散存储
	 * 
	 * @Method: makePath
	 * @Description:
	 * @param filename
	 *            文件名，要根据文件名生成存储目录
	 * @param savePath
	 *            文件存储路径
	 * @return 新的存储目录
	 */
	private String makePath(String filename, String savePath) {
		// 得到文件名的hashCode的值，得到的就是filename这个字符串对象在内存中的地址
		int hashcode = filename.hashCode();
		int dir1 = hashcode & 0xf; // 0--15
		int dir2 = (hashcode & 0xf0) >> 4; // 0-15
		// 构造新的保存目录
		String dir = savePath + "\\" + dir1 + "\\" + dir2; // upload\2\3
		                                                   // upload\3\5
		// File既可以代表文件也可以代表目录
		File file = new File(dir);
		// 如果目录不存在
		if (!file.exists()) {
			// 创建目录
			file.mkdirs();
		}
		return dir;
	}
}
