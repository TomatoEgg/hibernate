package com.jx.hibernate;
 
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
 
public class HibernateSessionManager {
  private static SessionFactory sessionFactory = createSessionFactory();
  private static ServiceRegistry serviceRegistry;

  public static SessionFactory createSessionFactory() 
  {
     Configuration configuration = new Configuration();
     configuration.configure();
     serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
     sessionFactory = configuration.buildSessionFactory(serviceRegistry);
     return sessionFactory;
  }
  
  //This is deprecated
//  private static SessionFactory buildSessionFactory() 
//  {
//    try 
//    {
//	  // Create the SessionFactory from hibernate.cfg.xml
//	  return new Configuration().configure().buildSessionFactory();
//	} 
//    catch (Throwable ex) 
//    {
//	  // Make sure you log the exception, as it might be swallowed
//	  System.err.println("SessionFactory creation failed." + ex);
//	  throw new ExceptionInInitializerError(ex);
//    }
//  }
 
  public static SessionFactory getSessionFactory() 
  {
    return sessionFactory;
  }
 
  public static void shutdown() 
  {
    // Close caches and connection pools
    sessionFactory.close();
  }
}