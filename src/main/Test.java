package main;

import java.io.IOException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;


public class Test {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		final String rpcuser ="Ulysseys";
		final String rpcpassword ="YourSuperGreatPasswordNumber_DO_NOT_USE_THIS_OR_YOU_WILL_GET_ROBBED_385593";
		 
		RPCAgent conn = new RPCAgent(rpcuser, rpcpassword);
		DBAgent dba = new DBAgent();
		
		String json = "{'database' : 'mkyongDB','table' : 'hosting'," +
				  "'detail' : {'records' : 99, 'index' : 'vps_index1', 'active' : 'true'}}}";

		DBObject dbObject = (DBObject)JSON.parse(json);
		System.out.println(dbObject);
	}
	
	

}
