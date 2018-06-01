package com.xpoliceservices.app.utils;

/**
 * Created by TO-OW109 on 19-02-2018.
 */

public interface ApiServiceConstants {

    //Network Urls
//    String MAIN_URL = "http://192.168.1.29:8080/webservices5";

    String MAIN_URL = "http://192.168.1.29:8080/PoliceService";
    String USER_REGISTRATION = "/registercustomer";
    String X_SERVICEMAN_REGISTRATION = "/registerExServicMan";
    String CUSTOMER_LOGIN = "/customerLogin";
    String SERVICEMAN_LOGIN = "/serviceManLogin";
    String UPDATE_PASSWORD = "/updatePassword";
    String UPDATE_XSERVICEMAN_PASSWORD = "/updateServiceManPassword";
    String SAVE_APPLICATION = "/saveApplication?";
    String GET_APPLICATIONS = "/getApplicataions?";
    String GET_XSERVICEMAN = "/getexservicemen?";
    String CUSTOMER_PROFILE = "/customerprofile?";
    String XSERVICEMAN_PROFILE  = "/servicemanprofile?";
    String UPDATE_XSERVICEMAN_STATUS = "/updateservicemanstatus?";
    String GEO_DATA = "/geodata";
    String GET_ASSIGNABLEEXSERVICEMEN = "/getassignableexservicemen?";
    String ASSIGN_XSERVICEMAN = "/assignxserviceman?";
    String UPDATE_APPLICATIONSTATUS = "/updateapplicationstatus?";
//    String STATES = "/getStates";
//    String DISTRICTS = "/getDistricts";
//    String SUB_DIVISIONS = "/getSubDivisions";
//    String DIVISION = "/getDivisions";
}
