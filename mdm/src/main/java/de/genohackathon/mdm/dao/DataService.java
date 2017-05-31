package de.genohackathon.mdm.dao;

import de.genohackathon.mdm.model.DataObject;
import org.bson.BsonDocument;
import org.bson.BsonObjectId;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import java.util.List;

/**
 * Created by chuff on 30.05.2017.
 */
public class DataService<T extends DataObject> {

    private Class<T> typeClass;

    final Datastore datastore = DBConnector.getDB();

    public DataService(Class<T> type) {
        typeClass = type;
    }

    public List<T> findAll() {
        final Query<T> query = datastore.createQuery(typeClass);
        return query.asList();
    }

    public void create(T obj) {
        datastore.save(obj);
    }

    public void update(T obj) {
        datastore.save(obj);
    }

    public void updateOrCreate(T obj) {
        if (obj.getId() == null) {
            create(obj);
            return;
        }
        if (reload(obj) != null) {
            update(obj);
        } else {
            create(obj);
        }
    }

    public T reload(T obj) {
        return datastore.get(obj);
    }

    public T findById(ObjectId id) {
        return datastore.find(typeClass).field("_id").equal(id).get();
    }

    public List<T> findFullText(String text){
        return datastore.find(typeClass).search(text).order("_id").asList();
    }

    public void delete(T obj) {
        datastore.delete(obj);
    }

    private BsonDocument getIdFilter(String id) {
        BsonDocument filter = new BsonDocument();
        BsonObjectId bsonId = new BsonObjectId(new ObjectId(id));
        filter.put("_id", bsonId);
        return filter;
    }
}
