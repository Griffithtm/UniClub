/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author hoang
 */
public class RoleRequest {
    private int requestID;
    private int userID;
    private int requestedRoleID;
    private String status;
    private Date requestDate;
    private Integer reviewedBy;
    private Date reviewedDate;

    // dùng khi join để hiển thị
    private String userName;
    private String requestedRoleName;

    public int getRequestID() {
        return requestID;
    }

    public void setRequestID(int requestID) {
        this.requestID = requestID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getRequestedRoleID() {
        return requestedRoleID;
    }

    public void setRequestedRoleID(int requestedRoleID) {
        this.requestedRoleID = requestedRoleID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Integer getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(Integer reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public Date getReviewedDate() {
        return reviewedDate;
    }

    public void setReviewedDate(Date reviewedDate) {
        this.reviewedDate = reviewedDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRequestedRoleName() {
        return requestedRoleName;
    }

    public void setRequestedRoleName(String requestedRoleName) {
        this.requestedRoleName = requestedRoleName;
    }
    
    
}
