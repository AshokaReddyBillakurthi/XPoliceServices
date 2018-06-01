package com.xpoliceservices.app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ApplicationData implements Serializable{

    @SerializedName("applicationDatas")
    @Expose
    private List<Application> applicationDatas = null;

    public List<Application> getApplicationDatas() {
        return applicationDatas;
    }

    public void setApplicationDatas(List<Application> applicationDatas) {
        this.applicationDatas = applicationDatas;
    }

    public static class Application implements Serializable {
        @SerializedName("applicationNumber")
        @Expose
        public String applicationNumber;
        @SerializedName("firstName")
        @Expose
        public String firstName;
        @SerializedName("lastName")
        @Expose
        public String lastName;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("mobileNumber")
        @Expose
        public String mobileNumber;
        @SerializedName("state")
        @Expose
        public String state;
        @SerializedName("city")
        @Expose
        public String city;
        @SerializedName("area")
        @Expose
        public String area;
        @SerializedName("userImage")
        @Expose
        public String userImage;
        @SerializedName("applicationType")
        @Expose
        public String applicationType;
        @SerializedName("status")
        @Expose
        public Integer status;
        @SerializedName("payableAmount")
        @Expose
        public Double payableAmount;
        @SerializedName("district")
        @Expose
        public String district;
        @SerializedName("subDivision")
        @Expose
        public String subDivision;
        @SerializedName("circlePolicestation")
        @Expose
        public String circlePolicestation;
        @SerializedName("data")
        @Expose
        public String data;
        @SerializedName("comment")
        @Expose
        public String comment;
        @SerializedName("accepted")
        @Expose
        public Boolean accepted;
        @SerializedName("xServiceManEmail")
        @Expose
        public String xserviceManEmail;

        public String getApplicationNumber() {
            return applicationNumber;
        }

        public void setApplicationNumber(String applicationNumber) {
            this.applicationNumber = applicationNumber;
        }

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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobileNumber() {
            return mobileNumber;
        }

        public void setMobileNumber(String mobileNumber) {
            this.mobileNumber = mobileNumber;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
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

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
        }

        public String getApplicationType() {
            return applicationType;
        }

        public void setApplicationType(String applicationType) {
            this.applicationType = applicationType;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Double getPayableAmount() {
            return payableAmount;
        }

        public void setPayableAmount(Double payableAmount) {
            this.payableAmount = payableAmount;
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

        public String getCirclePolicestation() {
            return circlePolicestation;
        }

        public void setCirclePolicestation(String circlePolicestation) {
            this.circlePolicestation = circlePolicestation;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public Boolean getAccepted() {
            return accepted;
        }

        public void setAccepted(Boolean accepted) {
            this.accepted = accepted;
        }

        public String getXserviceManEmail() {
            return xserviceManEmail;
        }

        public void setXserviceManEmail(String xserviceManEmail) {
            this.xserviceManEmail = xserviceManEmail;
        }
    }
}