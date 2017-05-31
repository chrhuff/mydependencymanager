package de.genohackathon.mdm.model;

import org.apache.commons.lang3.StringUtils;
import org.bson.BsonObjectId;
import org.bson.BsonString;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 * Created by chuff on 30.05.2017.
 */
public class Project implements DataObject{
    
    private String id;
    private String name_test;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name_test;
    }

    public void setName(String name) {
        this.name_test = name;
    }

    public Document toDocument() {
        //if (project == null) return null;
        Document doc = new Document();
        doc.put("name", new BsonString(getName()));
        if (StringUtils.isNotBlank(getId())) {
            doc.put("_id", new BsonObjectId(new ObjectId(getId())));
        }
        return doc;
    }

    public Project fromDocument(Document document) {
        if (document == null) {
            return null;
        }
        Project project = new Project();
        project.setName(document.getString("name"));
        project.setId(document.getObjectId("_id").toString());
        return project;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;
        
        return id.equals(project.id);
    }

    @Override
    public int hashCode() {
        return name_test.hashCode();
    }
}
