package com.xpoliceservices.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class XServiceManData implements Serializable {

    @SerializedName("exServiceMen")
    @Expose
    private List<XServiceman> exServiceMen = null;

    public List<XServiceman> getExServiceMen() {
        return exServiceMen;
    }

    public void setExServiceMen(List<XServiceman> exServiceMen) {
        this.exServiceMen = exServiceMen;
    }

    public static class XServiceman implements Serializable {

        @SerializedName("firstName")
        @Expose
        public String firstName;
        @SerializedName("lastName")
        @Expose
        public String lastName;
        @SerializedName("mobileNumber")
        @Expose
        public String mobileNumber;
        @SerializedName("exPoliceId")
        @Expose
        public Object exPoliceId;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("password")
        @Expose
        public String password;
        @SerializedName("state")
        @Expose
        public String state;
        @SerializedName("city")
        @Expose
        public String city;
        @SerializedName("area")
        @Expose
        public String area;
        @SerializedName("district")
        @Expose
        public String district;
        @SerializedName("subDivision")
        @Expose
        public String subDivision;
        @SerializedName("divisionPoliceStation")
        @Expose
        public String divisionPoliceStation;
        @SerializedName("reqDocs")
        @Expose
        public String reqDocs;
        @SerializedName("userType")
        @Expose
        public String userType;
        @SerializedName("status")
        @Expose
        public Integer status;
        @SerializedName("services")
        @Expose
        public String services;
        @SerializedName("subservices")
        @Expose
        public String subservices;
        @SerializedName("isActive")
        @Expose
        public Boolean isActive;
        @SerializedName("image")
        @Expose
        public String image;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

        public Object getExPoliceId() {
            return exPoliceId;
        }

        public void setExPoliceId(Object exPoliceId) {
            this.exPoliceId = exPoliceId;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getSubDivision() {
            return subDivision;
        }

        public void setSubDivision(String subDivision) {
            this.subDivision = subDivision;
        }

        public String getDivisionPoliceStation() {
            return divisionPoliceStation;
        }

        public void setDivisionPoliceStation(String divisionPoliceStation) {
            this.divisionPoliceStation = divisionPoliceStation;
        }

        public String getReqDocs() {
            return reqDocs;
        }

        public void setReqDocs(String reqDocs) {
            this.reqDocs = reqDocs;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getServices() {
            return services;
        }

        public void setServices(String services) {
            this.services = services;
        }

        public Boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}






