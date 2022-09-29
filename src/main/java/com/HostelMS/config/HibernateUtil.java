package com.HostelMS.config;

import java.util.Properties;

import com.HostelMS.model.Room;
import com.HostelMS.model.User;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;


public class HibernateUtil {

	private static SessionFactory sesfac;
	
	public static SessionFactory getSessionFactory() {
		
		if(sesfac==null) {
			
		try {
				Configuration config=new Configuration();
				Properties pro=new Properties();
			
				pro.put(Environment.DRIVER,"com.mysql.cj.jdbc.Driver");
				pro.put(Environment.URL, "jdbc:mysql://localhost:3306/hostel");
				pro.put(Environment.USER,"root" );
				pro.put(Environment.PASS, "142307");
				pro.put(Environment.DIALECT,"org.hibernate.dialect.MySQL5Dialect");
				pro.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
				pro.put(Environment.HBM2DDL_AUTO,"update");
				pro.put(Environment.SHOW_SQL,"false");
				
				config.setProperties(pro);
				config.addAnnotatedClass(User.class);
				config.addAnnotatedClass(Room.class);
				sesfac=config.buildSessionFactory();
				
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		}
		return sesfac;
	}
	
	public static Session getSession() {
		
		return getSessionFactory().openSession();
	}
}
