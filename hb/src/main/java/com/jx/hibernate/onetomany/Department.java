package com.jx.hibernate.onetomany;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
 
@Entity
@Table(name="DEPARTMENT")
public class Department {
 
    @Id
    @GeneratedValue
    @Column(name="DEPARTMENT_ID")
    private Long departmentId;
     
    public Long getDepartmentId() {
      return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
      this.departmentId = departmentId;
    }

    public String getDepartmentName() {
      return departmentName;
    }

    public void setDepartmentName(String departmentName) {
      this.departmentName = departmentName;
    }

    public Set<OneToManyEmployee> getEmployees() {
      return employees;
    }

    public void setEmployees(Set<OneToManyEmployee> employees) {
      this.employees = employees;
    }

    @Column(name="DEPT_NAME")
    private String departmentName;
     
    //One department can have many employees.
    //mappedBy specifies the ownership: department "owns" the employees
    @OneToMany(mappedBy="department")
    private Set<OneToManyEmployee> employees;
}
