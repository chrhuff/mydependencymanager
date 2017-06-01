package de.genohackathon.mdm.dao;

import com.mongodb.MongoClient;
import de.genohackathon.mdm.model.Project;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

public class DBConnector {

    private Datastore db = null;
    
    private static DBConnector instance = null;

    public static Datastore getDB() {
        if (instance == null) {
            Morphia morphia = new Morphia();
            morphia.mapPackage("de.genohackathon.mdm.model");
            MongoClient mongo = new MongoClient("127.0.0.1");
            Datastore datastore = morphia.createDatastore(mongo, "MDM");
            datastore.ensureCaps();
            morphia.map();
            try {
                datastore.ensureIndexes();
            }catch(Exception e){
                //every time, but works anyways
            }
            
            instance = new DBConnector(datastore);
        }
        return instance.db;
    }

    private DBConnector(Datastore db) {
        this.db = db;
    }
}
