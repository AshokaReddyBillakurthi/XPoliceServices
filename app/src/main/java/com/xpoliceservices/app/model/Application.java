package com.xpoliceservices.app.model;

import java.io.Serializable;

public class Application implements Serializable {

    public String applicationNo;
    public String firstName;
    public String lastName;
    public String email;
    public String mobileNo;
    public String state;
    public String city;
    public String area;
    public String userImg;
    public String applicationType;
    public int status = 0;
    public int isAccepted = 0;
    public String xServiceManEmail = "";
    public double payableAmount = 0;
    public String district = "";
    public String subDivision = "";
    public String circlePolicestation = "";
    public String data = "";
}
