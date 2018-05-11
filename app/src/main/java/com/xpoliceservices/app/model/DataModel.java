package com.xpoliceservices.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class DataModel implements Serializable{

    @SerializedName("states")
    @Expose
    private List<State> states = null;
    @SerializedName("districts")
    @Expose
    private List<District> districts = null;
    @SerializedName("subDivisions")
    @Expose
    private List<SubDivision> subDivisions = null;
    @SerializedName("divisions")
    @Expose
    private List<Division> divisions = null;
    @SerializedName("services")
    @Expose
    private List<Service> services = null;
    @SerializedName("subServices")
    @Expose
    private List<SubService> subServices = null;

    public List<State> getStates() {
        return states;
    }

    public void setStates(List<State> states) {
        this.states = states;
    }

    public List<District> getDistricts() {
        return districts;
    }

    public void setDistricts(List<District> districts) {
        this.districts = districts;
    }

    public List<SubDivision> getSubDivisions() {
        return subDivisions;
    }

    public void setSubDivisions(List<SubDivision> subDivisions) {
        this.subDivisions = subDivisions;
    }

    public List<Division> getDivisions() {
        return divisions;
    }

    public void setDivisions(List<Division> divisions) {
        this.divisions = divisions;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public List<SubService> getSubServices() {
        return subServices;
    }

    public void setSubServices(List<SubService> subServices) {
        this.subServices = subServices;
    }

    public static class District {

        @SerializedName("districtCode")
        @Expose
        private String districtCode;
        @SerializedName("districtName")
        @Expose
        private String districtName;
        @SerializedName("stateCode")
        @Expose
        private String stateCode;

        public String getDistrictCode() {
            return districtCode;
        }

        public void setDistrictCode(String districtCode) {
            this.districtCode = districtCode;
        }

        public String getDistrictName() {
            return districtName;
        }

        public void setDistrictName(String districtName) {
            this.districtName = districtName;
        }

        public String getStateCode() {
            return stateCode;
        }

        public void setStateCode(String stateCode) {
            this.stateCode = stateCode;
        }

    }

    public static class Division {

        @SerializedName("divisionCode")
        @Expose
        private String divisionCode;
        @SerializedName("divisionName")
        @Expose
        private String divisionName;
        @SerializedName("subDivisionCode")
        @Expose
        private String subDivisionCode;

        public boolean isSelected = false;

        public String getDivisionCode() {
            return divisionCode;
        }

        public void setDivisionCode(String divisionCode) {
            this.divisionCode = divisionCode;
        }

        public String getDivisionName() {
            return divisionName;
        }

        public void setDivisionName(String divisionName) {
            this.divisionName = divisionName;
        }

        public String getSubDivisionCode() {
            return subDivisionCode;
        }

        public void setSubDivisionCode(String subDivisionCode) {
            this.subDivisionCode = subDivisionCode;
        }

    }

    public static class Service {

        @SerializedName("serviceCode")
        @Expose
        private String serviceCode;
        @SerializedName("serviceName")
        @Expose
        private String serviceName;
        @SerializedName("active")
        @Expose
        private Boolean active;

        public String getServiceCode() {
            return serviceCode;
        }

        public void setServiceCode(String serviceCode) {
            this.serviceCode = serviceCode;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public Boolean getActive() {
            return active;
        }

        public void setActive(Boolean active) {
            this.active = active;
        }

    }

    public static class State {

        @SerializedName("stateCode")
        @Expose
        private String stateCode;
        @SerializedName("stateName")
        @Expose
        private String stateName;

        public String getStateCode() {
            return stateCode;
        }

        public void setStateCode(String stateCode) {
            this.stateCode = stateCode;
        }

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

    }

    public static class SubDivision {

        @SerializedName("subDivisionCode")
        @Expose
        private String subDivisionCode;
        @SerializedName("subDivisionName")
        @Expose
        private String subDivisionName;
        @SerializedName("districtCode")
        @Expose
        private String districtCode;

        public String getSubDivisionCode() {
            return subDivisionCode;
        }

        public void setSubDivisionCode(String subDivisionCode) {
            this.subDivisionCode = subDivisionCode;
        }

        public String getSubDivisionName() {
            return subDivisionName;
        }

        public void setSubDivisionName(String subDivisionName) {
            this.subDivisionName = subDivisionName;
        }

        public String getDistrictCode() {
            return districtCode;
        }

        public void setDistrictCode(String districtCode) {
            this.districtCode = districtCode;
        }
    }

    public static class SubService {

        @SerializedName("subServiceCode")
        @Expose
        private String subServiceCode;
        @SerializedName("subServiceName")
        @Expose
        private String subServiceName;
        @SerializedName("serviceCode")
        @Expose
        private String serviceCode;
        @SerializedName("active")
        @Expose
        private Boolean active;

        public String getSubServiceCode() {
            return subServiceCode;
        }

        public void setSubServiceCode(String subServiceCode) {
            this.subServiceCode = subServiceCode;
        }

        public String getSubServiceName() {
            return subServiceName;
        }

        public void setSubServiceName(String subServiceName) {
            this.subServiceName = subServiceName;
        }

        public String getServiceCode() {
            return serviceCode;
        }

        public void setServiceCode(String serviceCode) {
            this.serviceCode = serviceCode;
        }

        public Boolean getActive() {
            return active;
        }

        public void setActive(Boolean active) {
            this.active = active;
        }
    }
}
