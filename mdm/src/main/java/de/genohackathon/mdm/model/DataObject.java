package de.genohackathon.mdm.model;

import org.bson.Document;

/**
 * Created by Surface Book on 31.05.2017.
 */
public interface DataObject {

    public String getId();

    public Document toDocument();
    public DataObject fromDocument(Document doc);
}
