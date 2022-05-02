package no.hvl.dat110.aciotdevice.client;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.JsonParser;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RestClient {

	public RestClient() {
		// TODO Auto-generated constructor stub
	}

	private static String logpath = "/accessdevice/log";

	public void doPostAccessEntry(String message) {

		// TODO: implement a HTTP POST on the service to post the message
		OkHttpClient okClient = new OkHttpClient();
		Gson gson = new Gson();
		AccessMessage msg = new AccessMessage(message);
		
		MediaType JSON = MediaType.parse("application/json; charset=utf-8");
		RequestBody reqBody = RequestBody.create(JSON, gson.toJson(msg));
		
		Request request = new Request.Builder()
				.url("http://localhost:8080" + logpath).
				post(reqBody).
				build();
		try (Response response = okClient.newCall(request).execute()){
			System.out.println(response.body().string());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private static String codepath = "/accessdevice/code";
	
	public AccessCode doGetAccessCode() {

		AccessCode code = null;
		
		// TODO: implement a HTTP GET on the service to get current access code
		OkHttpClient okClient = new OkHttpClient();
		Gson gson = new Gson();
		
		Request request = new Request.Builder()
				.url("http://localhost:8080" + codepath)
				.get()
				.build();
		
		System.out.println("doGetAccessCode request: ");
		System.out.println(request);
		
		try(Response response = okClient.newCall(request).execute()){
			System.out.println("response data: ");
			System.out.println(response.body());
			String responseBody = response.body().string();
			System.out.println("ResponseBody: ");
			System.out.println(responseBody);
			
			code = gson.fromJson(JsonParser.parseString(responseBody), AccessCode.class);
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		return code;
	}
}
