package de.genohackathon.mdm.model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

import java.util.Date;
import java.util.Set;

@Entity("project")
public class Project implements DataObject {

    @Id
    private ObjectId id;

    private String name;

    @Reference
    private Employee projectLeader;
    
    private Date startDate;
    private Date endDate;
    private String resultType;
    private Integer resources;
    private Integer budget;
    private String businessCase;
    private String channel;
    private String customerType;
    private String targetGroup;
    private Set<String> itSystems;
    private Set<String> values;

    @Override
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;
        return this.id.toString().equals(project.getId());
    }

    public Employee getProjectLeader() {
        return projectLeader;
    }

    public void setProjectLeader(Employee projectLeader) {
        this.projectLeader = projectLeader;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public Integer getResources() {
        return resources;
    }

    public void setResources(Integer resources) {
        this.resources = resources;
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public String getBusinessCase() {
        return businessCase;
    }

    public void setBusinessCase(String businessCase) {
        this.businessCase = businessCase;
    }

    public Set<String> getValues() {
        return values;
    }

    public void setValues(Set<String> values) {
        this.values = values;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getTargetGroup() {
        return targetGroup;
    }

    public void setTargetGroup(String targetGroup) {
        this.targetGroup = targetGroup;
    }

    public Set<String> getItSystems() {
        return itSystems;
    }

    public void setItSystems(Set<String> itSystems) {
        this.itSystems = itSystems;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
