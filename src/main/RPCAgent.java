package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class RPCAgent {
	final URL url = new URL("http://localhost:8332/");
	
	public RPCAgent(String rpcuser, String rpcpwd) throws IOException {
		Authenticator.setDefault(new Authenticator() {
		      protected PasswordAuthentication getPasswordAuthentication() {
		          return new PasswordAuthentication (rpcuser, rpcpwd.toCharArray());
		      }
		  });
	}
	
	public Integer getBlockCount() throws IOException  {
		String result = sendPostRequest("{\"jsonrpc\": \"1.0\", \"id\":\"RPCConnector::getBlockCount\", "
				+ "\"method\": \"getblockcount\", \"params\": [] }");
		JsonParser parser = new JsonParser();
		Integer retVal = Integer.valueOf(parser.parse(result).getAsJsonObject().get("result").getAsString());
		return retVal;
	}
	
	public String getBlockHash(int height) throws IOException {
		String result = sendPostRequest("{\"jsonrpc\": \"1.0\", \"id\":\"RPCConnector::getBlockHash\", "
				+ "\"method\": \"getblockhash\", \"params\": [" + height + "] }");
		JsonParser parser = new JsonParser();
		String retVal = parser.parse(result).getAsJsonObject().get("result").getAsString();
		return retVal;
	}
	
	public String getBlock(int height) throws IOException {
		String hash = getBlockHash(height);
		String result = sendPostRequest("{\"jsonrpc\": \"1.0\", \"id\":\"RPCConnector::getBlock\", "
				+ "\"method\": \"getblock\", \"params\": [\"" + hash + "\"] }");
		JsonParser parser = new JsonParser();
		String retVal = parser.parse(result).getAsJsonObject().get("result").getAsJsonObject().toString();
		return retVal;
	}
	
	public String getTx(String hash) throws IOException {
		String result = sendPostRequest("{\"jsonrpc\": \"1.0\", \"id\":\"RPCConnector::getTx\", "
				+ "\"method\": \"getrawtransaction\", \"params\": [\"" + hash + "\"," + 1 + "] }");
		JsonParser parser = new JsonParser();
		JsonObject txObj = parser.parse(result).getAsJsonObject().get("result").getAsJsonObject();
		txObj.remove("hex");
		return txObj.toString();
	}
	
	private String sendPostRequest(String msg) throws IOException {
		HttpURLConnection http = (HttpURLConnection) url.openConnection();
		http.setRequestMethod("POST"); // PUT is another valid option
		http.setDoOutput(true);
		http.connect();
		OutputStreamWriter osw = new OutputStreamWriter(http.getOutputStream());
		osw.write(msg);
		osw.flush();
		osw.close();
		
		InputStreamReader streamReader = new InputStreamReader(http.getInputStream(),"utf-8");
		BufferedReader reader = new BufferedReader(streamReader);
		
		String line = null;
		StringBuilder result = new StringBuilder();
		while ((line = reader.readLine()) != null)
			result.append(line + "\n");
		reader.close();
		http.disconnect();
		return result.toString();
	}
}

