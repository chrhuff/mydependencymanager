package de.genohackathon.mdm.dao;

import org.bson.BsonArray;
import org.bson.BsonString;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class DBConnector {

	private static MongoDatabase db = null;

	public static MongoDatabase getDB(){
		if(db==null){
			MongoClient mongo = new MongoClient("127.0.0.1");
			db = mongo.getDatabase("MDM");
		}
		return db;
	}

	public static void main(String[] args) {
		DBConnector DBC = new DBConnector();
		DBC.create();
		DBC.read();
	}
	
	private MongoCollection<Document> projects;
	
	public DBConnector(){


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
