package com.jx.hibernate;
 
import java.util.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
 
public class Run {
  public static void main(String[] args) {
    Session session = HibernateSessionManager.getSessionFactory().openSession();
//    Session session = HibernateSessionManager.getSessionFactory().getCurrentSession();
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
//    finally
//    {
//      session.close();
//    }
    
    System.out.println("Users are added to database");
    //Read all users
//    session = HibernateSessionManager.getSessionFactory().openSession();
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
      HibernateSessionManager.shutdown();
      System.out.println("Shut down");
    }
  }
}