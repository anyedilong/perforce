package org.sdblt.utils.encode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//请求地址下载txt文件并获取里面的东西
public class FileOperations {
	public static String LOCAL_PATH = "";

	public void InputFile(String fileName, String fileUrl) {
		// 待下载文件地址
		InputStream in = null;
		OutputStream out = null;
		HttpURLConnection conn = null;
		try {
			// 初始化连接
			URL url = new URL(fileUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			// 读取数据
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				File directory = new File("");// 设定为当前文件夹
				byte[] buffer = new byte[2048];
				in = conn.getInputStream();
				LOCAL_PATH = directory.getCanonicalPath();
				out = new FileOutputStream(new File(LOCAL_PATH, fileName));
				int count = 0;
				int finished = 0;
				while ((count = in.read(buffer)) != -1) {
					if (count != 0) {
						out.write(buffer, 0, count);
						finished += count;
					} else {
						break;
					}
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
				in.close();
				conn.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public String readTxtFile(String filePath) {
		String ideString = null;
		try {
			String encoding = "GB2312";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(file), encoding);// 考虑到编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					String string = lineTxt.split("=")[0];
					if (string.equals("终端主密钥密文")) {
						ideString = lineTxt.split("=")[1];
					}
				}
				read.close();
			} else {
				String nc = "找不到指定的文件";
				return nc;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ideString;
	}
}
