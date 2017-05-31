package de.genohackathon.mdm.model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

/**
 * Created by chuff on 30.05.2017.
 */
@Entity("dependency")
public class Dependency implements DataObject {
    
    @Id
    private ObjectId id;
    
    @Reference
    private Project sourceProject;
    
    @Reference
    private Project targetProject;
    
    private String comment;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public Project getSourceProject() {
        return sourceProject;
    }

    public void setSourceProject(Project sourceProject) {
        this.sourceProject = sourceProject;
    }

    public Project getTargetProject() {
        return targetProject;
    }

    public void setTargetProject(Project targetProject) {
        this.targetProject = targetProject;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dependency that = (Dependency) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
