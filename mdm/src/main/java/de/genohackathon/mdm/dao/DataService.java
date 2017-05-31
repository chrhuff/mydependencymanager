package de.genohackathon.mdm.dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import de.genohackathon.mdm.model.DataObject;
import de.genohackathon.mdm.model.Project;
import org.apache.commons.lang3.StringUtils;
import org.bson.BsonDocument;
import org.bson.BsonObjectId;
import org.bson.BsonString;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by chuff on 30.05.2017.
 */
public class DataService<T extends DataObject> {

    private MongoCollection<Document> collection;

    private Class<T> typeClass;

    public DataService(Class<T> type) {
        typeClass = type;
        MongoDatabase db = DBConnector.getDB();
        try {
            db.createCollection(type.getName());
        } catch (Exception e) {

        }
        collection = db.getCollection(type.getName());
    }

    public List<T> findAll() {
        try {
            T obj = typeClass.newInstance();
            ArrayList list = new ArrayList<T>();
            for (Document doc : collection.find()) {
                list.add(obj.fromDocument(doc));
            }
            return list;
        }catch (InstantiationException|IllegalAccessException e){
            return Collections.emptyList();
        }
    }

    public void create(T obj) {
        collection.insertOne(obj.toDocument());
    }

    public void update(T obj) {
        collection.findOneAndReplace(getIdFilter(obj.getId()), obj.toDocument());
    }

    public void updateOrCreate(T obj) {
        if (StringUtils.isBlank(obj.getId())) {
            create(obj);
            return;
        }
        if (reload(obj)!=null){
            update(obj);
        }else{
            create(obj);
        }
    }

    public T reload(T obj) {
        return findById(obj.getId());
    }

    public T findById(String id){
        try {
            T obj = typeClass.newInstance();
            return (T) obj.fromDocument(collection.find(getIdFilter(id)).first());
        }catch (InstantiationException|IllegalAccessException e){
            return null;
        }
    }

    public void delete(T obj) {
        collection.findOneAndDelete(getIdFilter(obj.getId()));
    }

//    private Document toDocument(Project project) {
//        if (project == null) return null;
//        Document doc = new Document();
//        doc.put("name", new BsonString(project.getName()));
//        if (StringUtils.isNotBlank(project.getId())) {
//            doc.put("_id", new BsonObjectId(new ObjectId(project.getId())));
//        }
//        return doc;
//    }
//
//    private Project toProject(Document document) {
//        if (document == null) {
//            return null;
//        }
//        Project project = new Project();
//        project.setName(document.getString("name"));
//        project.setId(document.getObjectId("_id").toString());
//        return project;
//    }

    private BsonDocument getIdFilter(String id) {
        BsonDocument filter = new BsonDocument();
        BsonObjectId bsonId = new BsonObjectId(new ObjectId(id));
        filter.put("_id", bsonId);
        return filter;
    }
}
