package com.java.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class File2CodeUtil {

	private static Logger logger = LoggerFactory.getLogger(File2CodeUtil.class);
	
	/**
	 * <p>
	 * 将文件转成base64 字符串
	 * </p>
	 * 
	 * @param path
	 *            文件路径
	 * @return
	 * @throws Exception
	 */
	public static String encodeBase64File(String path) throws Exception {
		File file = new File(path);
		FileInputStream inputFile = new FileInputStream(file);
		byte[] buffer = new byte[(int) file.length()];
		inputFile.read(buffer);
		inputFile.close();
//		return "data:image/jpeg;base64," + new BASE64Encoder().encode(buffer);
		return new BASE64Encoder().encode(buffer);
	}

	/**
	 * <p>
	 * 将base64字符解码保存文件
	 * </p>
	 * 
	 * @param base64Code
	 * @param targetPath
	 * @throws Exception
	 */
	public static void decoderBase64File(String base64Code, String targetPath,String filename){
		try{
			if (!new File(targetPath).exists() && !new File(targetPath).isDirectory()){       
			    new File(targetPath).mkdirs();    
			}
			byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
			FileOutputStream out = new FileOutputStream(targetPath+filename);
			out.write(buffer);
			out.close();
		}catch(Exception e){
			logger.error("", e);
		}
	}

	/**
	 * <p>
	 * 将base64字符保存文本文件
	 * </p>
	 * 
	 * @param base64Code
	 * @param targetPath
	 * @throws Exception
	 */
	public static void toFile(String base64Code, String targetPath)
			throws Exception {
		byte[] buffer = base64Code.getBytes();
		FileOutputStream out = new FileOutputStream(targetPath);
		out.write(buffer);
		out.close();
	}

	public static void main(String[] args) throws Exception {
		String result = encodeBase64File("C:\\Users\\admin\\AppData\\Local\\Temp\\backupfile\\beijin20190422142256.jpg");
		System.out.println(result);
	}
	
}