package com.app.example.baidutranslateapp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import com.alibaba.fastjson.JSON;
import com.app.example.baidutranslateapp.bean.JsonData;

import android.util.Log;

public class TranslationUtils {

	private static final String mUrl = "http://api.fanyi.baidu.com/api/trans/vip/translate";
	private static String q = "";
	private static String from = "";
	private static String to = "";
	private static final String appid = "20160110000008779";
	private static String salt = "";
	private static String sign = "";
	private static final String key = "5Yr3sD5g3GVJogWMXfDE";

	public static String translation(String source, String to1) {
		q = source;
		from = "auto";
		to = to1;
		Random r = new Random();
		int a = r.nextInt(2147483647);
		salt = a + "";
		sign = appid + q + salt + key;
		String md5 = computeMD5(sign).toLowerCase();
		if (md5 == null) {
			sign = computeMD5(sign).toLowerCase();
		} else {
			sign = md5.toLowerCase();
		}
		Log.i("info", sign.length() + "");
		try {
			q = URLEncoder.encode(q, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			try {
				q = URLEncoder.encode(q, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		// return dealDatas(doPost(mUrl));
		return dealDatas(doGet(mUrl));
	}

	private static String dealDatas(String doGet) {
		try {
			JsonData jsonData = JSON.parseObject(doGet, JsonData.class);
			return jsonData.getTrans_result().get(0).getDst();
		} catch (Exception e) {
			return null;
		}
	}

	public static String computeMD51(String input) {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");

			md.update(input.getBytes());

			for (byte b : md.digest()) {
				result += Integer.toHexString(b & 0xFF);
			}
		} catch (Exception e) {
			result = null;
		}
		return result;
	}

	// 32位MD5值
	public static String computeMD5(String input) {
		if (input == null)
			return null;
		StringBuilder sb = new StringBuilder();
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		// 生成一组length=16的byte数组
		byte[] bs = digest.digest(input.getBytes());

		for (int i = 0; i < bs.length; i++) {
			int c = bs[i] & 0xFF; // byte转int为了不丢失符号位， 所以&0xFF
			if (c < 16) { // 如果c小于16，就说明，可以只用1位16进制来表示， 那么在前面补一个0
				sb.append("0");
			}
			sb.append(Integer.toHexString(c));
		}
		return sb.toString();
	}

	public static String doGet(final String strurl) {
		FutureTask<String> future = new FutureTask<String>(
				new Callable<String>() {
					@Override
					public String call() throws Exception {
						HttpURLConnection conn = null;
						BufferedReader br = null;
						BufferedWriter bw = null;
						URL url = new URL(strurl
								+ doData(q, from, to, appid, salt, sign));
						conn = (HttpURLConnection) url.openConnection();
						br = new BufferedReader(new InputStreamReader(
								conn.getInputStream()));
						String line = null;
						while ((line = br.readLine()) != null) {
							return line;
						}
						return line;
					}
				});
		new Thread(future).start();
		try {
			return future.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String doPost(final String strurl) {
		FutureTask<String> future = new FutureTask<String>(
				new Callable<String>() {
					@Override
					public String call() throws Exception {
						HttpURLConnection conn = null;
						BufferedReader br = null;
						BufferedWriter bw = null;
						URL url = new URL(strurl);
						conn = (HttpURLConnection) url.openConnection();
						conn.setRequestMethod("POST");
						conn.setDoOutput(true);
						bw = new BufferedWriter(new OutputStreamWriter(
								conn.getOutputStream()));
						bw.write("q=" + q + "from=" + from + "to=" + to
								+ "appid=" + appid + "salt=" + salt + "sign="
								+ sign);
						bw.flush();
						// bw.write("q="+q);
						// bw.flush();
						// bw.write("from="+from);
						// bw.flush();
						// bw.write("to="+to);
						// bw.flush();
						// bw.write("appid="+appid);
						// bw.flush();
						// bw.write("salt="+salt);
						// bw.flush();
						// bw.write("sign="+sign);
						// bw.flush();
						br = new BufferedReader(new InputStreamReader(
								conn.getInputStream()));
						String line = null;
						while ((line = br.readLine()) != null) {
							return line;
						}
						return line;
					}
				});
		new Thread(future).start();
		try {
			return future.get();
		} catch (Exception e) {

			e.printStackTrace();
		}
		return null;
	}

	public static String doData(String q, String from, String to, String appid,
			String salt, String sign) {
		return "?q=" + q + "&from=" + from + "&to=" + to + "&appid=" + appid
				+ "&salt=" + salt + "&sign=" + sign;
	}

}
