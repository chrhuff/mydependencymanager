package de.genohackathon.mdm.dao;

import de.genohackathon.mdm.model.Project;

/**
 * Created by chuff on 30.05.2017.
 */
public class ProjectService {
    
    public Project create(String projectName) {
        Project project = new Project();
        project.setId("-42");
        project.setName(projectName);
        return project;
    }
    
    public void update(Project project) {
        //do nothing
    }
    
    public Project findById(String id) {
        Project project = new Project();
        project.setId(id);
        project.setName("Project="+id);
        return project;
    }
    
    public void delete(Project project) {
        //do nothing
    }
}
