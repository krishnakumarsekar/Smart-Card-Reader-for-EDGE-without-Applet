package model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import util.HibernateUtil;

public class User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Log log = LogFactory.getLog(User.class);
	
	private long id;
	
	private String name;
	
	private String password;

	public long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@SuppressWarnings("unchecked")
	public String login(){
		Session session = HibernateUtil.currentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("from User u where u.name = '" + name + "'");
		List users = query.list();
		User user = null;
		if(users!=null && users.size()>0)
			user = (User) users.get(0);
		tx.commit();
		HibernateUtil.shutdown();
		if(user!=null){
			if(user.getName().equals(name)&&user.getPassword().equals(password)){
				log.info("someone successfully use username "+name+" to log in.");
				return "success";
			}
		}
		log.info("someone failed to use username "+name+" to log in.");
		return "failure";
	}
	
	@SuppressWarnings("unchecked")
	public String register(){
		try {
			User user = new User();
			user.setName(name);
			user.setPassword(password);
			System.out.println(name);
			Session session = HibernateUtil.currentSession();
			Transaction tx = session.beginTransaction();
			Query query = session.createQuery("from User u where u.name = '" + name + "'");
			List users = query.list();
			if(users != null && users.size() > 0)
				return "exist";
			session.save(user);
			tx.commit();
			HibernateUtil.shutdown();
			log.info("someone successfully use username "+name+" to register.");
			return "success";
		} catch (HibernateException e) {
			log.info("someone failed to use username "+name+" to register.");
			return "failure";
		}
	}
}
