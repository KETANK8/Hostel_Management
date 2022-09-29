package com.HostelMS.daoImpl;

import com.HostelMS.config.HibernateUtil;
import com.HostelMS.dao.UserDao;
import com.HostelMS.exception.GlobalException;
import com.HostelMS.model.User;

import org.hibernate.Session;

public class UserDaoImpl implements UserDao{

	// METHOD 1
	// TO SHOW ROOM DETAILS OF USER
	@Override
	public User userRoom(int uId) {
		// TODO Auto-generated method stub
		try(Session ses=HibernateUtil.getSession()){
			
			User u=ses.get(User.class,uId);// FETCHING USER OBJECT
			return u;
		}
	}

	// METHOD 3
	// TO SHOW DUE AMOUNT OF USER
	@Override
	public int userDueAmount(int uId) {
		// TODO Auto-generated method stub
		try(Session ses=HibernateUtil.getSession()){
			
			int amount=(int)ses.createQuery("select userFee from User where userId =: id").setParameter("id", uId).uniqueResult();// FETCHING AMOUNT DETAILS
			return amount;
		}
	}

	// METHOD 4
	// TO SHOW PROFILE DETAILS OF USER
	@Override
	public User userProfile(int uId) {
		// TODO Auto-generated method stub
		try(Session ses=HibernateUtil.getSession()){
			
			User u = ses.get(User.class,uId);// FETCHING USER OBJECT
			return u;
		}
	}

	// METHOD 5
	// TO CHANGE CONTACT INFO OF USER
	@Override
	public int changeContact(int uId, String newContact) {
		// TODO Auto-generated method stub
		try(Session ses=HibernateUtil.getSession()){
			ses.beginTransaction();
			// UPDATING CONTACT INFO
			int res =ses.createQuery("update User set userContact =: contact where userId =: id").setParameter("contact", newContact).setParameter("id",uId).executeUpdate();
			ses.getTransaction().commit();
			return res;	
		}
	}

	// METHOD 6
	// TO CHANGE PASSWORD OF USER PROFILE
	@Override
	public int changePassWord(int uId, String oldPswrd, String newPswrd) throws GlobalException {
		// TODO Auto-generated method stub
		try(Session ses=HibernateUtil.getSession()){
			ses.beginTransaction();
			User u= ses.get(User.class, uId);
			
			// VALIDATE IF INPUT PASSWORD IS MATCHES WITH ACCOUNT PASSWORD
			if(u.getUserPassword().equals(oldPswrd)) {
				
				// COMPARE OLD AND NEW PASSWORD
				// PASSWORD CANNOT CHANGE
				// IF NEW AND OLD PASSWORD IS SAME
				if(oldPswrd.equals(newPswrd)) {
					throw new GlobalException("New Password cannot be same as Old Password");
				}
				
				// NEW AND OLD PASSWORD IS DIFFERENT
				// UPDATING NEW PASSWORD OF USER PROFILE
				else {
					// UPDATING PASSWORD
					int status =ses.createQuery("update User set userPassword =: newPwd where userId =: id").setParameter("newPwd", newPswrd).setParameter("id", uId).executeUpdate();
					ses.getTransaction().commit();
					return status;
				}
			}
			else {
				throw new GlobalException("Wrong Password!!!");
			}
		}
	}

	
	
}
