package be.maximvdw.placeholderapi.internal.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Map;

public class HtmlUtils {
	/**
	 * User Agent
	 */
	private final static String USER_AGENT = "Mozilla/5.0";

	/**
	 * Get the body contents of an url
	 * 
	 * @param url
	 *            URL Link
	 * @return String with the body
	 * @throws IOException
	 */
	public static String getHtmlSource(String url) throws IOException {
		URL yahoo = new URL(url);
		URLConnection yc = yahoo.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream(), "UTF-8"));
		String inputLine;
		StringBuilder a = new StringBuilder();
		while ((inputLine = in.readLine()) != null)
			a.append(inputLine);
		in.close();

		return a.toString();
	}

	public static HtmlResponse sendGetRequest(String url, String[] inputcookies, int timeout) throws Exception {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setReadTimeout(timeout);
		con.setConnectTimeout(timeout);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		String[] cookies = new String[0];
		if (con.getHeaderField("Set-Cookie") != null)
			cookies = con.getHeaderField("Set-Cookie").split(";");

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return new HtmlResponse(response.toString(), con.getResponseCode(), cookies);
	}

	/**
	 * Send a get request
	 * 
	 * @param url
	 *            Url
	 * @return Response
	 * @throws Exception
	 *             Exception
	 */
	public static HtmlResponse sendGetRequest(String url, String[] inputcookies) throws Exception {
		return sendGetRequest(url, inputcookies, 2500);
	}

	/**
	 * Send post request
	 * 
	 * @param url
	 *            Url
	 * @param params
	 *            Params
	 * @return Response
	 * @throws Exception
	 *             Exception
	 */
	public static HtmlResponse sendPostRequest(String url, Map<String, String> params, String[] inputcookies)
			throws Exception {

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		String urlParameters = "";
		for (String key : params.keySet()) {
			String value = params.get(key);
			urlParameters += key + "=" + value + "&";
		}
		urlParameters = urlParameters.substring(0, urlParameters.length() - 1);

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		String[] cookies = new String[0];
		if (con.getHeaderField("Set-Cookie") != null)
			cookies = con.getHeaderField("Set-Cookie").split(";");

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return new HtmlResponse(response.toString(), con.getResponseCode(), cookies);

	}

	public static String unsetURLStreamHandlerFactory() {
		try {
			Field f = URL.class.getDeclaredField("factory");
			f.setAccessible(true);
			Object curFac = f.get(null);
			f.set(null, null);
			URL.setURLStreamHandlerFactory(null);
			return curFac.getClass().getName();
		} catch (Exception e) {
	
		}
		return null;
	}

	/**
	 * Download a file
	 * 
	 * @param url
	 *            URL
	 * @param location
	 *            Output location
	 * @throws IOException
	 *             Input Output exception
	 */
	public static void downloadFile(String url, String location) throws IOException {
		URL website = new URL(url);
		URLConnection connection = website.openConnection();
		connection.addRequestProperty("User-Agent",USER_AGENT);
		ReadableByteChannel rbc = Channels.newChannel(connection.getInputStream());
		File yourFile = new File(location);
		yourFile.getParentFile().mkdirs();
		if (!yourFile.exists()) {
			yourFile.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(yourFile);
		fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();
	}

	public static String getIPFromHost(String host) {
		try {
			InetAddress address = InetAddress.getByName(host);
			return (address.getHostAddress());
		} catch (Exception ex) {

		}
		return "0.0.0.0";
	}
}
