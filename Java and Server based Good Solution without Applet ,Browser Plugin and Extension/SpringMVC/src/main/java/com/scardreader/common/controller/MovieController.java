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
@RequestMapping("/movie")
public class MovieController {
	
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

	@RequestMapping(value="/{name}", method = RequestMethod.GET)
	public String getMovie(@PathVariable String name, ModelMap model) {
		configureSessionFactory();
		Session session = null;
		Transaction tx=null;
		User user = null;
		try {
			
		
		user = new User();
		String[] ipAndStatus = name.split("\\s+");
		user.setId(Long.parseLong(ipAndStatus[0]));
		System.out.println(Long.parseLong(ipAndStatus[0]));
		user.setName(ipAndStatus[1]);
		user.setPassword(ipAndStatus[1]);
		session = sessionFactory.openSession();
		tx = session.beginTransaction();
		Query query = session.createQuery("from User u where u.id = '" + ipAndStatus[0] + "'");
		List users = query.list();
		if(users != null && users.size() > 0)
			session.saveOrUpdate(user);
		else
			session.saveOrUpdate(user);
		tx.commit();
		session.flush();
		model.addAttribute("movie", name);
		return "list";
		}catch (Exception ex) {
			ex.printStackTrace();
			
			// Rolling back the changes to make the data consistent in case of any failure 
			// in between multiple database write operations.
			tx.rollback();
			model.addAttribute("movie", "There is a problem in the communication");
			return "list";
		} finally{
			if(session != null) {
				session.evict(user);
				session.close();
			}
		}

	}
	
}