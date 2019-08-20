package com.java.common.utils;

import net.sf.json.JSONObject;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.*;

/**
 * 用于模拟HTTP请求中GET/POST方式
 * @author landa
 *
 */
public class HttpRequestUtils {
	/**
	 * 发送GET请求
	 *
	 * @param url
	 *            目的地址
	 * @param parameters
	 *            请求参数，Map类型。
	 * @return 远程响应结果
	 */
	public static String sendGet(String url, Map<String, Object> parameters) {
		String result="";
		BufferedReader in = null;// 读取响应输入流
		StringBuffer sb = new StringBuffer();// 存储参数
		String params = "";// 编码之后的参数
		try {
			// 编码请求参数
			if(parameters.size()==1){
				for(String name:parameters.keySet()){
					sb.append(name).append("=").append(parameters.get(name));
				}
				params=sb.toString();
			} else if(parameters.size() > 1){
				for (String name : parameters.keySet()) {
					sb.append(name).append("=").append(parameters.get(name)).append("&");
				}
				String temp_params = sb.toString();
				params = temp_params.substring(0, temp_params.length() - 1);
			}
			String full_url = "";
//			params = java.net.URLEncoder.encode(params,"utf-8");
			if(params == ""){
				full_url = url;
			} else {
				full_url = url + "?" + params;
			}
//			System.out.println("请求路径：" + full_url);
			// 创建URL对象
			java.net.URL connURL = new java.net.URL(full_url);
			// 打开URL连接
			java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL
					.openConnection();
			// 设置通用属性
			httpConn.setRequestProperty("Accept", "*/*");
			httpConn.setRequestProperty("Connection", "Keep-Alive");
			httpConn.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
			// 建立实际的连接
			httpConn.connect();
			// 响应头部获取
//			Map<String, List<String>> headers = httpConn.getHeaderFields();
//			// 遍历所有的响应头字段
//			System.out.println("遍历所有的响应头字段：");
//			for (String key : headers.keySet()) {
//				System.out.println(key + "\t：\t" + headers.get(key));
//			}
			// 定义BufferedReader输入流来读取URL的响应,并设置编码方式
			in = new BufferedReader(new InputStreamReader(httpConn
					.getInputStream(), "UTF-8"));
			String line;
			// 读取返回的内容
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result ;
	}

	/**
	 * 发送POST请求
	 *
	 * @param url
	 *            目的地址
	 * @param parameters
	 *            请求参数，Map类型。
	 * @return 远程响应结果
	 */
	public static String sendPost(String url, Map<String, String> parameters, Map<String, String> headers, Cookie[] cookies) {
		String result = "";// 返回的结果
		BufferedReader in = null;// 读取响应输入流
		PrintWriter out = null;
		StringBuffer sb = new StringBuffer();// 处理请求参数
		String params = "";// 编码之后的参数
		try {
			// 编码请求参数
			if (parameters.size() == 1) {
				for (String name : parameters.keySet()) {
					if("ImageData".equals(name)){
						sb.append(name).append("=").append(
								java.net.URLEncoder.encode(parameters.get(name),
										"UTF-8"));
					}else{
						sb.append(name).append("=").append(Integer.parseInt(parameters.get(name)));
					}

				}
				params = sb.toString();
			} else if (parameters.size() > 1) {
				for (String name : parameters.keySet()) {
					if("ImageData".equals(name)){
						sb.append(name).append("=").append(
								java.net.URLEncoder.encode(parameters.get(name),
										"UTF-8")).append("&");
					}else{
						sb.append(name).append("=").append(Integer.parseInt(parameters.get(name)));
					}
				}
				String temp_params = sb.toString();
				params = temp_params.substring(0, temp_params.length() - 1);
			}
			System.out.println("请求参数"+params);
			// 创建URL对象
			java.net.URL connURL = new java.net.URL(url);
			// 打开URL连接
			java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL
					.openConnection();

			// 设置通用属性
            httpConn.setRequestProperty("Charset", "UTF-8");
            httpConn.setRequestProperty("Accept", "*/*");
            httpConn.setRequestProperty("Connection", "Keep-Alive");
			httpConn.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
            if (headers.size() >= 1) {
                for (String name : headers.keySet()) {
                    httpConn.setRequestProperty(name, headers.get(name));
                }
            }
            if(cookies != null){
            	String cookie = "";
				for (int i = 0; i < cookies.length; i++) {
					// System.out.println(cookies[i].getName() + ":" + cookies[i].getValue());
					cookie += cookies[i].getName() + "=" + cookies[i].getValue() + ";";
				}
				if (cookie != ""){
					cookie = cookie.substring(0, cookie.length() - 1);
					httpConn.setRequestProperty("Cookie", cookie);
				}
			}
			//设置请求方式
			httpConn.setRequestMethod("POST");
			// 发送POST请求必须设置如下两行
			httpConn.setDoOutput(true);
			httpConn.setDoInput(true);
			// 获取HttpURLConnection对象对应的输出流
			out = new PrintWriter(httpConn.getOutputStream());
			// 发送请求参数
			out.write(params);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应，设置编码方式
			in = new BufferedReader(new InputStreamReader(httpConn
					.getInputStream(), "UTF-8"));
			String line;
			// 读取返回的内容
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 上传文件的请求方法
	 *
	 * @param urlStr 请求地址
	 * @return
	 */
	public static String httpUpload(String urlStr, List<InputStream> files, Map<String, String> params) {
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		try {
			httpClient = HttpClients.createDefault();

			// 把一个普通参数和文件上传给下面这个地址 是一个servlet
			HttpPost httpPost = new HttpPost(urlStr);

			// 编码请求参数
			MultipartEntityBuilder multipart = MultipartEntityBuilder.create();

			if(!files.isEmpty() && files.size() > 0){
				for (int i = 0; i<files.size(); i++) {
					multipart.addBinaryBody("attachments", files.get(i));
				}
			}


			for (String name : params.keySet()) {
				StringBody param = new StringBody(params.get(name), ContentType.create(
						"text/plain", Consts.UTF_8));
				multipart.addPart(name, param);
			}

			HttpEntity reqEntity = multipart.build();

			httpPost.setEntity(reqEntity);

			// 发起请求 并返回请求的响应
			response = httpClient.execute(httpPost);

			// 获取响应对象
			HttpEntity resEntity = response.getEntity();

			if (resEntity != null) {
				return EntityUtils.toString(resEntity, Charset.forName("UTF-8"));
			}

			// 销毁
			EntityUtils.consume(resEntity);
		}catch (Exception e){
			e.printStackTrace();
		}finally {
			try {
				if(response != null){
					response.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				if(httpClient != null){
					httpClient.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	/**
	 * 主函数，测试请求
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		Map<String, String> parameters = new HashMap<String, String>();
        JSONObject json = new JSONObject();
        json.put("userName", "Johnny5");
        json.put("companyName", "Johnny4");
        json.put("contactNo", "090912345679");
        json.put("contactPerson", "John doe4");
        json.put("companyAddress", "4th benning");
        parameters.put("Details", json.toString());
        parameters.put("key", "WaRQqEcpnE2A1hTwobHvQA7kklsjC0Fk");
        parameters.put("Address", "");
        parameters.put("Pubkey", "");

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type","application/x-www-form-urlencoded");
//        headers.put("x-mobile-app","true");
//		String result =sendGet("http://18.138.35.246/rs/get_syspars", parameters);
        String result = sendPost("http://18.138.35.246/rs/register_wallet", parameters, headers, null);
        System.out.println("结果---------------->" + result);
        JSONObject obj = JSONObject.fromObject(result);
        System.out.print(obj.get("error"));
	}

	/**
	 * 发送post请求
	 * @param url  路径
	 * @param jsonObject  参数(json类型)
	 * @param encoding 编码格式
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static String sendPost(String url, JSONObject jsonObject,String encoding) throws ParseException, IOException{
		String body = "";

		//创建httpclient对象
		CloseableHttpClient client = HttpClients.createDefault();
		//创建post方式请求对象
		HttpPost httpPost = new HttpPost(url);

		//装填参数
		StringEntity s = new StringEntity(jsonObject.toString(), "utf-8");
		s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
				"application/json"));
		//设置参数到请求对象中
		httpPost.setEntity(s);

		//设置header信息
		//指定报文头【Content-type】、【User-Agent】
		httpPost.setHeader("Content-type", "application/json");
		httpPost.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_0) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11");


		//执行请求操作，并拿到结果（同步阻塞）
		CloseableHttpResponse response = client.execute(httpPost);
		//获取结果实体
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			//按指定编码转换结果实体为String类型
			body = EntityUtils.toString(entity, encoding);
		}
		EntityUtils.consume(entity);
		//释放链接
		response.close();
		return body;
	}

	public static String sendGet1(String url, Map<String, Object> parameters, String outPath, String picName) {
		String result="";
		BufferedImage in = null;// 读取响应输入流
		StringBuffer sb = new StringBuffer();// 存储参数
		String params = "";// 编码之后的参数
		try {
			// 编码请求参数
			if(parameters.size()==1){
				for(String name:parameters.keySet()){
					sb.append(name).append("=").append(parameters.get(name));
				}
				params=sb.toString();
			} else if(parameters.size() > 1){
				for (String name : parameters.keySet()) {
					sb.append(name).append("=").append(parameters.get(name)).append("&");
				}
				String temp_params = sb.toString();
				params = temp_params.substring(0, temp_params.length() - 1);
			}
			String full_url = "";
//			params = java.net.URLEncoder.encode(params,"utf-8");
			if(params == ""){
				full_url = url;
			} else {
				full_url = url + "?" + params;
			}
			System.out.println("请求路径：" + full_url);
			// 创建URL对象
			java.net.URL connURL = new java.net.URL(full_url);
			// 打开URL连接
			java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection) connURL
					.openConnection();
			// 设置通用属性
			httpConn.setRequestProperty("Accept", "*/*");
			httpConn.setRequestProperty("Connection", "Keep-Alive");
			httpConn.setRequestProperty("User-Agent",
					"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.1)");
			// 建立实际的连接
			httpConn.connect();
			// 响应头部获取
			Map<String, List<String>> headers = httpConn.getHeaderFields();
			// 遍历所有的响应头字段
			System.out.println("遍历所有的响应头字段：");
			for (String key : headers.keySet()) {
				System.out.println(key + "\t：\t" + headers.get(key));
			}
			// 定义BufferedImage输入流来读取URL的响应,并设置编码方式
			in = ImageIO.read(httpConn.getInputStream());

//			String path = "E:\\image\\"+DateUtil.getDateYMD()+"\\";
			File file = new File(outPath);
			if (!file.exists()) {//如果文件夹不存在
				file.mkdirs();//创建文件夹
			}
			// 创建输出流对象
			ImageIO.write(in, "jpg", new File(outPath + picName));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result ;
	}

	/**
	 * 通过form-data进行post提交
	 * @param urlStr
	 * @param textMap
	 * contentType非空采用filename匹配默认的图片类型
	 * @return 返回response数据
	 */
	public static String sendPost(String urlStr, Map<String, String> textMap) {
		String res = "";
		HttpURLConnection conn = null;
		// boundary就是request头和上传文件内容的分隔符
		String BOUNDARY = "---------------------------123821742118716";
		try {
			URL url = new URL(urlStr);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(5000);
			conn.setReadTimeout(30000);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(false);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			// conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows; U; Windows NT 6.1; zh-CN; rv:1.9.2.6)");
			conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
			OutputStream out = new DataOutputStream(conn.getOutputStream());
			// text
			if (textMap != null) {
				StringBuffer strBuf = new StringBuffer();
				Iterator iter = textMap.entrySet().iterator();
				while (iter.hasNext()) {
					Map.Entry entry = (Map.Entry) iter.next();
					String inputName = (String) entry.getKey();
					String inputValue = (String) entry.getValue();
					if (inputValue == null) {
						continue;
					}
//					strBuf.append("\r\n").append("--").append(BOUNDARY).append("\r\n");
					strBuf.append("Content-Disposition: form-data; name=\"" + inputName + "\"\r\n\r\n");
					strBuf.append(inputValue);
				}
				out.write(strBuf.toString().getBytes());
			}

			byte[] endData = ("\r\n--" + BOUNDARY + "--\r\n").getBytes();
			out.write(endData);
			out.flush();
			out.close();
			// 读取返回数据
			StringBuffer strBuf = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				strBuf.append(line).append("\n");
			}
			res = strBuf.toString();
			reader.close();
			reader = null;
		} catch (Exception e) {
			System.out.println("发送POST请求出错。" + urlStr);
			e.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
				conn = null;
			}
		}
		return res;
	}

	public static String POST_FORM(String url, Map<String,String> map, String encoding) throws ParseException, IOException{
		String body = "";
		//创建httpclient对象
		CloseableHttpClient client = HttpClients.createDefault();
		//创建post方式请求对象
		HttpPost httpPost = new HttpPost(url);

		List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>();
		if(map!=null){
			for (Map.Entry<String, String> entry : map.entrySet()) {
				nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		//设置参数到请求对象中
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));

		//设置连接超时时间 为3秒
		RequestConfig config = RequestConfig.custom().setConnectTimeout(3000).setConnectionRequestTimeout(3000).setSocketTimeout(5000).build();
		httpPost.setConfig(config);
//		System.out.println("请求地址："+url);
//		System.out.println("请求参数："+nvps.toString());

		//1.表单方式提交数据   简单举例，上面给出的是map参数
//        List<BasicNameValuePair> pairList = new ArrayList<BasicNameValuePair>();
//        pairList.add(new BasicNameValuePair("name", "admin"));
//        pairList.add(new BasicNameValuePair("pass", "123456"));
//        httpPost.setEntity(new UrlEncodedFormEntity(pairList, "utf-8"));

		//2.json方式传值提交
//        JSONObject jsonParam = new JSONObject();
//        jsonParam.put("name", "admin");
//        jsonParam.put("pass", "123456");
//        StringEntity entity = new StringEntity(jsonParam.toString(), "utf-8");// 解决中文乱码问题
//        entity.setContentEncoding("UTF-8");
//        entity.setContentType("application/json");
//        httpPost.setEntity(entity);

		//可以设置header信息 此方法不设置
		//指定报文头【Content-type】、【User-Agent】
//        httpPost.setHeader("Content-type", "application/json");
//        httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

		//执行请求操作，并拿到结果（同步阻塞）
		CloseableHttpResponse response = client.execute(httpPost);
		//获取结果实体
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			//按指定编码转换结果实体为String类型
			body = EntityUtils.toString(entity, encoding);
		}
		EntityUtils.consume(entity);
		//释放链接
		response.close();
		return body;
	}

}