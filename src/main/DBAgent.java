package main;


import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class DBAgent {
	
	public DBAgent() {
		try{
			
	         // To connect to mongodb server
	         MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
				
	         // Now connect to your databases
	         MongoDatabase db = mongoClient.getDatabase( "test" );
	         System.out.println("Connect to database successfully");
	         mongoClient.close();
				
	      }catch(Exception e){
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      }
	}
	
}
