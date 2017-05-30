package de.genohackathon.mdm;

import org.bson.BsonArray;
import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DBConnector {
	
	public static void main(String[] args) {
		DBConnector DBC = new DBConnector();
		DBC.create();
		DBC.read();
	}
	
	private MongoCollection<Document> projects;
	
	public DBConnector(){
		MongoClient mongo = new MongoClient("127.0.0.1");
		MongoDatabase db = mongo.getDatabase("MDM");
		try{
			db.createCollection("Projects");
		}catch(Exception e){
			
		}
		projects = db.getCollection("Projects");
	}
	
	public void create(){
		Document doc = new Document();
		doc.append("Name", new BsonString("Test2"));
		BsonArray skills = new BsonArray();
		skills.add(new BsonString("SkillA"));
		skills.add(new BsonString("SkillB"));
//		skills.add(new BsonString("SkillC"));
		doc.append("Skills", skills);
		projects.insertOne(doc);
	}
	
	public void read(){
		Document filter = new Document();
		filter.append("Skills", "SkillC");
//		filter.append("Skills", "SkillC");
		System.out.println(projects.count());
		FindIterable<Document> found = projects.find(filter);
		for(Document d : found){
			System.out.println(d.toString());
		}
	}
}
