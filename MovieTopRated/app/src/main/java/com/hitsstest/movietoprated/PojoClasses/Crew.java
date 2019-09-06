
package com.hitsstest.movietoprated.PojoClasses;

import java.util.HashMap;
import java.util.Map;

public class Crew {

    private String creditId;
    private String department;
    private Integer gender;
    private Integer id;
    private String job;
    private String name;
    private Object profilePath;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(Object profilePath) {
        this.profilePath = profilePath;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
