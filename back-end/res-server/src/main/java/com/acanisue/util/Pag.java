package com.acanisue.util;


import cn.hutool.core.io.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

/**
 * 编写者：Acanisue
 * 日期；2023/3/23 17:43
 */
@Slf4j
public class Pag {

		public static void renderString(HttpServletResponse response, String string) {
				try {
						response.setStatus(200);
						response.setContentType("application/json");
						response.setCharacterEncoding("utf-8");
						response.getWriter().print(string);
				} catch (IOException e) {
						e.printStackTrace();
				}
		}


		/**
		 * @param response 返回的主机
		 * @param file 文件
		 * @param type 根据上一步的获取的文件头信息
		 * @see FileUtil#getType(File)
		 */
		public static void renderFile(HttpServletResponse response, File file, String type) {
				response.setHeader("Cache-Control", "max-age=60");
				response.setDateHeader("Expires", System.currentTimeMillis()+1000*60);
				response.setContentType(switchType(type));
				WritableByteChannel writableByteChannel = null;
				FileChannel fileChannel = null;
				try {
						writableByteChannel = Channels.newChannel(response.getOutputStream());
						fileChannel = new FileInputStream(file.getAbsolutePath()).getChannel();
						fileChannel.transferTo(0, fileChannel.size(), writableByteChannel);
				} catch (IOException e) {
						log.warn(e.getMessage(), e);
				}finally {
						try {
								if (!ObjectUtils.isEmpty(fileChannel)) fileChannel.close();
								if (!ObjectUtils.isEmpty(writableByteChannel))writableByteChannel.close();
						}catch (IOException e){
								log.warn("文件管道流关闭错误:",e);
						}

				}

		}

		/**
		 * @param userAgent
		 * @param response
		 * @param file
		 */

		public static void downloadFile(String userAgent,HttpServletResponse response,File file) {
				response.setContentType(MediaType.APPLICATION_OCTET_STREAM.toString());
				String filename = FileUtil.getName(file);
				String[] split = filename.split("\\$");
				filename=split[split.length-1];
				WritableByteChannel writableByteChannel = null;
				FileChannel fileChannel = null;
				String fileType = FileUtil.getType(file);
				try{
						//设置头
						response.addHeader("Content-Type", URLEncoder.encode(fileType, "UTF8"));
						response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename,"UTF8"));
						// 设置响应内容的长度
						response.setContentLength((int) file.length());
						writableByteChannel = Channels.newChannel(response.getOutputStream());
						fileChannel = new FileInputStream(file.getAbsolutePath()).getChannel();
						fileChannel.transferTo(0, fileChannel.size(), writableByteChannel);
				}catch (IOException e){
						log.error("文件传输异常", e);
				}finally {
						try {
								if (!ObjectUtils.isEmpty(fileChannel)) fileChannel.close();
								if (!ObjectUtils.isEmpty(writableByteChannel))writableByteChannel.close();
						}catch (IOException e){
								log.warn("文件管道流关闭错误:",e);
						}

				}
		}


		public static String switchType(String type){
				switch(type) {
						case "jpg":
						case "jpeg":
								return "image/jpeg";
						case "svg":
								return  "image/svg+xml";
						case "mp4":
								return "video/mp4";
								// todo 待测试格式
						case "avi":
								return "video/avi";
						// todo 待测试格式
						case "wmv":
								return "video/wmv";
						// todo 待测试格式
						case "mov":
								return "video/mov";
						case "png":
						default:
								return "image/png";
				}
		}
}
