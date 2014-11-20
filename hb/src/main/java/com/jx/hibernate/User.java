package com.jx.hibernate;
 
import java.io.Serializable;
import java.util.Date;
 

import javax.persistence.*;
 
@SuppressWarnings("serial")
@Entity
@Table(name = "USER")
public class User implements Serializable{
 
  @Id @GeneratedValue
  @Column(name = "USER_ID")
  private int userId;
 
  @Column(name = "USERNAME")
  private String userName;
 
  @Column(name = "CREATED_BY")
  private String createdBy;
 
  @Column(name = "CREATED_DATE")
  private Date createdDate;
 
  public User() {}
 
  public int getUserId() {
    return userId;
  }
  
  public void setUserId(int userId) {
    this.userId = userId;
  }
 
  public String getUsername() {
    return userName;
  }
 
  public void setUsername(String username) {
    this.userName = username;
  }
 
  public String getCreatedBy() {
    return createdBy;
  }
 
  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }
 
  public Date getCreatedDate() {
    return createdDate;
  }
  
  public void setCreatedDate(Date createdDate) {
    this.createdDate = createdDate;
  }
  
  public String toString()
  {
    return "user Id:" + userId + ", user name:" + userName + ", createdBy:" + createdBy + ", createdDate:" + createdDate;
  }
}