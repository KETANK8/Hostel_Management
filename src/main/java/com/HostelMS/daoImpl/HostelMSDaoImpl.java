package com.HostelMS.daoImpl;

import com.HostelMS.config.HibernateUtil;
import com.HostelMS.dao.HostelMSDao;
import com.HostelMS.exception.GlobalException;
import com.HostelMS.model.User;

import org.hibernate.Session;

public class HostelMSDaoImpl implements HostelMSDao{

	@Override
	public int Registration(User u) throws GlobalException {
		// TODO Auto-generated method stub
			try(Session ses=HibernateUtil.getSession())
			{
				
				String name=u.getUserName();
				User u2=null;
				u2= (User) ses.createQuery("from User where userName=: Name").setParameter("Name", name).uniqueResult();
				if(u2==null)
				{
					ses.beginTransaction();
					ses.save(u);
					ses.getTransaction().commit();
					return 1;	
				}
				else {
					throw new GlobalException("User Name alreay taken!!!");
				}
				
			}
	}

	@Override
	public User Login(String UserName, String password) throws GlobalException {
		// TODO Auto-generated method stub
		try(Session ses=HibernateUtil.getSession()){
			ses.beginTransaction();
			
			User u2=null;
			u2=(User)ses.createQuery("from User where userName=:username").setParameter("username", UserName).uniqueResult();
			if(u2!=null)
			{
				if(u2.getUserPassword().equals(password)) {
					return u2;
				}
				else {
					throw new GlobalException("Wrong Username or Password!!!");
				}
			}
			else {
				throw new GlobalException("User does not exist!!!");
			}
			
		}
	}

}
