package com.jx.hibernate;
 
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.jx.hibernate.onetomany.Department;
import com.jx.hibernate.onetomany.OneToManyEmployee;
import com.jx.hibernate.onetoone.OneToOneEmployee;
import com.jx.hibernate.onetoone.EmployeeDetail;
 
public class Run {
  public static void main(String[] args) {
    WriteAndReadOneUser();
    OneToOneMapping();
    OneToManyMapping();
    cleanup();
  }

  private static void OneToManyMapping() {
    System.out.println("*****OneToManyMapping*****");
    
    Session session = HibernateSessionManager.getSessionFactory().openSession();
    session.beginTransaction();

    Department department = new Department();
    department.setDepartmentName("Sales");
    session.save(department);
    com.jx.hibernate.onetomany.OneToManyEmployee emp1 = new com.jx.hibernate.onetomany.OneToManyEmployee("Nina", "Mayers", "111");
    com.jx.hibernate.onetomany.OneToManyEmployee emp2 = new com.jx.hibernate.onetomany.OneToManyEmployee("Tony", "Almeida", "222");

    emp1.setDepartment(department);
    emp2.setDepartment(department);
     
    session.save(emp1);
    session.save(emp2);

    session.getTransaction().commit();
    
    //Read them from DB and print them
    {
      System.out.println("results after adding employees");
      List<OneToManyEmployee> employees = session.createQuery("from OneToManyEmployee").list();
      for (OneToManyEmployee employee1 : employees) {
        System.out.println(employee1.getFirstname() + " , " + 
            employee1.getLastname() + ", " + 
            employee1.getDepartment().getDepartmentName() + " , " +
            employee1.getDepartment().getDepartmentId());
      }
      List<Department> dp = session.createQuery("from Department").list();
      for (Department d : dp)
      {
        System.out.println(d.getDepartmentName() + " , " + d.getDepartmentId());
      }
    }
    session.close();
  }

  private static void cleanup() {
    HibernateSessionManager.shutdown();
  }

  private static void OneToOneMapping() {
    System.out.println("Hibernate One-To-One example");
    
    Session session = HibernateSessionManager.getSessionFactory().openSession();
    session.beginTransaction();

    EmployeeDetail employeeDetail = new EmployeeDetail("10th Street", "LA", "San Francisco", "U.S.");
    OneToOneEmployee employee = new OneToOneEmployee("Nina", "Mayers", new java.sql.Date(121212), "114-857-965");
    employee.setEmployeeDetail(employeeDetail);
    employeeDetail.setEmployee(employee);
    session.save(employee);
    
    employeeDetail = new EmployeeDetail("9th Street", "LA", "San Francisco", "U.S.");
    employee = new OneToOneEmployee("Sofia", "Xie", new java.sql.Date(141414), "0731245789");
    employee.setEmployeeDetail(employeeDetail);
    employeeDetail.setEmployee(employee);
    session.save(employee);

    {
      System.out.println("results after adding employees");
      List<OneToOneEmployee> employees = session.createQuery("from OneToOneEmployee").list();
      for (OneToOneEmployee employee1 : employees) {
        System.out.println(employee1.getFirstname() + " , " + 
            employee1.getLastname() + ", " + 
            employee1.getEmployeeDetail().getState());
      }
      List<EmployeeDetail> details = session.createQuery("from EmployeeDetail").list();
      for (EmployeeDetail ed : details)
      {
        System.out.println(ed.getEmployeeId() + " , " + ed.getCountry() + ", " + 
            ed.getState() + " , " + ed.getCity() + " , " + ed.getStreet());
      }
    }

    session.getTransaction().commit();
    
    //Now let's delete the employee 2 from employee table, since cascading is enabled, we expect the
    //corresponding row in the employeedetail tabel to be removed too.
    session.beginTransaction();
    session.delete(employee);
    session.getTransaction().commit();
    
    {
      System.out.println("results after deleting the last employee");
      List<OneToOneEmployee> employees = session.createQuery("from OneToOneEmployee").list();
      for (OneToOneEmployee employee1 : employees) {
        System.out.println(employee1.getFirstname() + " , " + 
            employee1.getLastname() + ", " + 
            employee1.getEmployeeDetail().getState());
      }
      List<EmployeeDetail> details = session.createQuery("from EmployeeDetail").list();
      for (EmployeeDetail ed : details)
      {
        System.out.println(ed.getEmployeeId() + " , " + ed.getCountry() + ", " + 
            ed.getState() + " , " + ed.getCity() + " , " + ed.getStreet());
      }
    }
    
    session.close();
  }

  private static void WriteAndReadOneUser() {
    Session session = HibernateSessionManager.getSessionFactory().openSession();
  //  Session session = HibernateSessionManager.getSessionFactory().getCurrentSession();
    Transaction t = null;
   
    //Write some users into the table
    try
    {
      t = session.beginTransaction();
      User user = new User();
      
      user.setUserId(1);
      user.setUsername("James");
      user.setCreatedBy("Application");
      user.setCreatedDate(new Date());
      
      session.save(user);
      t.commit();
    }
    catch (HibernateException e)
    {
      if (null != t)
      {
        t.rollback();
      }
      e.printStackTrace();
    }
    //We should not close the session(conversation) here, as we have more transactions coming later.
  //  finally
  //  {
  //    session.close();
  //  }
    
    System.out.println("Users are added to database");
    //Read all users
  //  session = HibernateSessionManager.getSessionFactory().openSession();
    try {
      t = session.beginTransaction();
      //Using HQL (Hibernate Query Language) to read data from the database
      List<User> userList = session.createQuery("from User").list();
      for (User u : userList)
      {
        System.out.println(u.toString());
      }
      t.commit();
    } catch (HibernateException e) {
      e.printStackTrace();
    }
    finally {
      session.close();
      System.out.println("session closed");
    }    
  }
}