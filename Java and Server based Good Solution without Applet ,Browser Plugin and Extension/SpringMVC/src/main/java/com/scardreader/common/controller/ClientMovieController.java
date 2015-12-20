package com.scardreader.common.controller;

import java.util.List;
import java.util.Properties;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import model.User;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import util.HibernateUtil;

@Controller
@RequestMapping("/client")
public class ClientMovieController {
	
	private static SessionFactory sessionFactory = null;  
	private static ServiceRegistry serviceRegistry = null;  
	  
	private static SessionFactory configureSessionFactory() throws HibernateException {  
	    Configuration configuration = new Configuration();  
	    configuration.setProperty("hibernate.connection.url", ("jdbc:sqlite:"+System.getenv("SCARDREADER")+"/1.db"));
	    configuration.configure();  
	    
	    Properties properties = configuration.getProperties();
	    
		serviceRegistry = new ServiceRegistryBuilder().applySettings(properties).buildServiceRegistry();          
	    sessionFactory = configuration.buildSessionFactory(serviceRegistry);  
	    
	    return sessionFactory;  
	}

	@RequestMapping(value="/{id}", method = RequestMethod.GET)
	public String getMovie(@PathVariable String id, ModelMap model) {
		configureSessionFactory();
		Session session = null;
		Transaction tx=null;
		try {
			session = sessionFactory.openSession();
			tx = session.beginTransaction();
		Query query = session.createQuery("from User u where u.id = '" + id + "'");
		List users = query.list();
		User user = null;
		if(users!=null && users.size()>0)
			user = (User) users.get(0);
		tx.commit();
		session.flush();
		if(user!=null){
			model.addAttribute("movie", user.getName());
			return "list";
		}
		else {
			model.addAttribute("movie", "There is a problem in the communication");
			return "list";
		}
		}catch (Exception ex) {
				ex.printStackTrace();
				
				// Rolling back the changes to make the data consistent in case of any failure 
				// in between multiple database write operations.
				tx.rollback();
				model.addAttribute("movie", "There is a problem in the communication");
				return "list";
			} finally{
				if(session != null) {
					session.close();
				}
			}


	}
	
}