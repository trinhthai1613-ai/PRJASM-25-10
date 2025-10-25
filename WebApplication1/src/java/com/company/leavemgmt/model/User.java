package com.company.leavemgmt.model;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable {
    private int userId;
    private String username;
    private String fullName;
    private int divisionId;
    private String divisionName;
    private List<String> roles;

    public int getUserId(){return userId;} public void setUserId(int v){userId=v;}
    public String getUsername(){return username;} public void setUsername(String v){username=v;}
    public String getFullName(){return fullName;} public void setFullName(String v){fullName=v;}
    public int getDivisionId(){return divisionId;} public void setDivisionId(int v){divisionId=v;}
    public String getDivisionName(){return divisionName;} public void setDivisionName(String v){divisionName=v;}
    public List<String> getRoles(){return roles;} public void setRoles(List<String> v){roles=v;}

    public boolean hasRole(String r){
        if (roles==null) return false;
        for (String x:roles) if (x.equalsIgnoreCase(r)) return true;
        return false;
    }
}
