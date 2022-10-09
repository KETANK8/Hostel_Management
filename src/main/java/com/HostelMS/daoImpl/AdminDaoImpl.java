/**
 * HOSTEL   MANAGEMENT    SYSTEM
 * @author Ketan Kumar
 * Illustrating USE OF LOMBOK,LOGGER AND GLOBAL EXCEPTION IN HOSTEL MANAGEMENT SYSTEM 
 * TO CREATE USER,ROOM ADD ROOM AND USER TO DATABASE USING LOMBOK INHRITANCE IN HIBERNATE 
 * ALLOTING ROOM TO USER
 * THERE ARE TWO TYPES OF USER
 * ->ADMIN
 * ->END USER
 * AND PRINT DATA OF ONE OR ALL USER USING LOGGER, DELETE USER AND ROOM USING DATA ACCESS OBJECT AND HQL 
 * CREATING AND USING GLOBAL EXCEPTION
 * ILLUSTRATING OBJECT RELATION MAPPING IN ENTITY USING HIBERNATE
 * ONE ROOM CAN HAVE MANY USER
 */
package com.HostelMS.daoImpl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import com.HostelMS.config.HibernateUtil;
import com.HostelMS.dao.AdminDao;
import com.HostelMS.exception.GlobalException;
import com.HostelMS.model.Room;
import com.HostelMS.model.User;

import org.hibernate.Session;

public class AdminDaoImpl implements AdminDao{

	// 	METHOD 1
	// METHOD TO CREATE ROOM
	@Override
	public int createRoom(Room r) throws GlobalException {
		// TODO Auto-generated method stub
		try(Session ses=HibernateUtil.getSession()){
			
			ses.beginTransaction();
			String Name = r.getRoomName();
			Room r2=null;
			
			// COMPARING GIVEN ROOM NAME IN THE DATABASE
			r2=(Room)ses.createQuery("from Room where roomName =: Name").setParameter("Name", Name).uniqueResult();
			
			// SAVING ROOM
			// IF GIVEN ROOM NAME IS NOT PRESENT IN DATABASE
			if(r2==null)
			{
				ses.save(r);
				ses.getTransaction().commit(); // ADDING ROOM INTO DATABASE
				return 1;
			}
			else {
				
				// THROWS EXCEPTION IF GIVEN ROOM NAME IS ALREADY EXIST IN DATABASE
				throw new GlobalException("Room Name is already Taken!!!");
			}
			
		}
	}
	
	// METHOD 2
	// METHOD TO ALLOT A ROOM TO USER
	// ALLOT ROOM TO USER BASED ON USER ID AND ROOM ID
	// ONE ROOM CAN HAVE MANY STUDENT
	@Override
	public int allotRoom(int uId, int rId) throws GlobalException {
		// TODO Auto-generated method stub
		
		// CREATING AUTO CLOSABLE SESSION OBJECT
		try(Session ses=HibernateUtil.getSession()){
			int count =0;
			
			ses.beginTransaction();
			
			// FETCHING LIST OF ALL USER 
			// USER THAT ALREADY HAVE ROOM ACCESS OF GIVEN ROOM ID
			List<User> userList = ses.createQuery("from User where userRoom_roomId =: id ").setParameter("id", rId).getResultList();
			
			// COUNTING NO OF USER HAVE GIVEN ROOM ACCESS
			for(User u : userList)
				count ++;
			
			Room r = ses.get(Room.class, rId);
			
			// IF GIVEN ROOM IS NOT PRESENT IN DATABASE
			if(r==null){
				throw new GlobalException("Room Not Found !!!");
			}
			// ONE ROOM CAN ONLY ALLOTED TO FOUR USER
			// IF ROOM IS NOT ALLOTED TO FOUR USER
			// THEN ROOM HAVE SPACE AND ROOM GET ALLOTED TO STUDENT
			else if(count<4) {
			int res = ses.createQuery("Update User set userRoom_roomId =: rId where userId =: id ").setParameter("rId",rId).setParameter("id", uId).executeUpdate();
			ses.getTransaction().commit();
			return res;
			}
			// IF ROOM ALREADY ALLOTED TO FOUR USER
			// THEN ROOM CANNOT BE ALLOTED TO GIVEN USER
			else
				throw new GlobalException("Room is Already Full !!!"); // THROWING EXCEPTION IF ROOM ALREADY HAVE 4 USER
		}
	}

	// METHOD 3
	// METHOD TO DELETE USER
	// DELETE USER BASED ON USER ID
	@Override
	public int deleteUser(int uId) throws GlobalException {
		// TODO Auto-generated method stub
		try(Session ses=HibernateUtil.getSession()){
			ses.beginTransaction();
			
			// DELETE USER FROM DATABASE
			int res = ses.createQuery("delete from User where userId =:  id ").setParameter("id", uId).executeUpdate();
			if (res != 1)
				throw new GlobalException("User not found!!");// THROWING GLOBAL EXCEPTION IF USER IS NOT PRESENT IN DATABASE
			ses.getTransaction().commit();
			return res;
		}
	}

	// METHOD 4
	// METHOD TO SET FEES
	// SET HOW MUCH AMOUNT NEED TO PAY FOR ROOM
	@Override
	public int generateRent(int uId, int amount) throws GlobalException {
		// TODO Auto-generated method stub
		try(Session ses=HibernateUtil.getSession()){
			ses.beginTransaction();
			User u = ses.get(User.class,uId);
			if(amount <0)
				// THROWING EXCEPTION IF ADMIN INPUT INVALID AMOUNT
				throw new GlobalException("Amount Cannot be negative !!!");
			if(u == null)
				// THROWING EXCEPTION IF USER NOT PRESENT IN DATABASE
				throw new GlobalException("User not found !!!");
			int fee = u.getUserRent();
			fee += amount;
			// SETTING FEES AMOUNT
			int res = ses.createQuery("update User set userRent =: fee  where userId =: id").setParameter("fee", fee).setParameter("id", uId).executeUpdate();
			ses.getTransaction().commit();
			return res;
		}
	}

	// METHOD 5
	// METHOD TO PAY FEES AMOUNT
	// REDUCE THE DUE AMOUNT BASED ON HOW MUCH AMOUNT IS PAID
	@Override
	public int rentPayment(int uId, int amount) throws GlobalException {
		// TODO Auto-generated method stub
		try(Session ses=HibernateUtil.getSession()){
			ses.beginTransaction();
			User u = ses.get(User.class,uId);
			
			if(amount <0)
				// THROWING EXCEPTION IF AMOUNT IS INVALID
				throw new GlobalException("Amount Cannot be negative !!!");
			if(u == null)
				// THROWING EXCEPTION IF USER NOT PRESENT IN DATABASE
				throw new GlobalException("User not found !!!");
			int fee = u.getUserRent();
			fee = fee - amount;
			// UPDATING DUE AMOUNT BASED ON AMOUNT IS PAID
			int res = ses.createQuery("update User set userRent =: fee where userId =: id").setParameter("fee", fee).setParameter("id", uId).executeUpdate();
			ses.getTransaction().commit();
			return fee;
		}
	}

	// METHOD 6
	// METHOD TO FETCH LIST OF USER PRESENT IN A ROOM
	// FETCH USER BASED ON ROOM ID
	@Override
	public List<User> userInARoom(int rId) {
		// TODO Auto-generated method stub
		try(Session ses=HibernateUtil.getSession()){
			
			// FETCHING ALL USER PRESENT IN A ROOM
			Query qu = ses.createQuery("from User where userRoom_roomId =: rId").setParameter("rId",rId);
			List<User> userList=qu.getResultList();
			
			// RETURNING USER LIST
			return userList;
		}
	}

	// METHOD 7
	// METHOD TO FETCH LIST OF ALL ROOM
	@Override
	public List<Room> AllRooms() {
		// TODO Auto-generated method stub
		try(Session ses=HibernateUtil.getSession()){
			
			// FETCHING LIST OF ROOM
			Query qu=ses.createQuery("from Room");
			List<Room> roomList=qu.getResultList();
			
			// RETURNING ROOM LIST
			return roomList;
		}
	}

	// METHOD 8
	// METHOD TO FETCH LIST OF ALL USER
	// FETCH ALL USER EXCEPT ADMIN
	@Override
	public List<User> AllUsers() {
		// TODO Auto-generated method stub
		try(Session ses=HibernateUtil.getSession()){
			
			// FETCHING LIST OF ALL USER IN DATABASE
			Query qu=ses.createQuery("from User where userRole != 'admin' ");
			List<User> userList=qu.getResultList();
			
			// RETURNING USER LIST
			return userList;
		}
	}

	// METHOD 9
	// METHOD TO FETCH A USER
	// FETCH USER PROFILE BASED ON USER ID/PRIMARY KEY
	@Override
	public User fetchUserProfile(int uId) throws GlobalException {
		// TODO Auto-generated method stub
		try(Session ses=HibernateUtil.getSession()){
			
			// FETCHING USER
			User u = ses.get(User.class,uId);
			if (u==null) {
				
				// THROWING EXCEPTION IF USER IS NOT PRESENT IN DATABASE
				throw new GlobalException("User does not exist!!!");
			}
			else
				return u;
		}
	}

	// METHOD 10
	// METHOD TO SET ROLE OF A USER
	// ONLY ADMIN CAN SET ROLE OF USER
	// ADMIN CAN ADD MORE ADMIN
	@Override
	public int setRole(int uId, String role) throws GlobalException {
		// TODO Auto-generated method stub
		try(Session ses=HibernateUtil.getSession()){
			ses.beginTransaction();
			User u = ses.get(User.class,uId);
			if(u == null)
				// THROWING EXCEPTION IF USER NOT PRESENT IN DATABASE
				throw new GlobalException("User not found !!!");
			// UPDATING USER ROLE BASED ON AMOUNT IS PAID
			int res = ses.createQuery("update User set userRole =: role where userId =: id").setParameter("role", role).setParameter("id", uId).executeUpdate();
			ses.getTransaction().commit();
			return res;
		}
	}

	// METHOD 11
	// TO FETCH THE LIST OF ALL VACANT ROOM
	// VACANT ROOM WITH NO OF AVIALABLE BEDS
	@Override
	public List<Room> VaccantRooms() {
		// TODO Auto-generated method stub
			try(Session ses=HibernateUtil.getSession()){
			
				// FETCHING LIST OF ROOM
				Query qu=ses.createQuery("from Room");
				List<Room> roomList=qu.getResultList();
				List<Room> vaccantRoom = new ArrayList<Room>();
				
				// TRAVERSING ROOM LIST
				// USING ENHANCED FOR LOOP
				for(Room r : roomList) {
					int count = 0;
					
					// FETCHING LIST OF ALL USER 
					// USER THAT ALREADY HAVE ROOM ACCESS OF GIVEN ROOM ID
					int rId = r.getRoomId(); // FETCHING ROOM ID
					List<User> userList= ses.createQuery("from User where userRoom_roomId =: id ").setParameter("id", rId).getResultList();
					
					// COUNTING NO OF USER HAVE ROOM ACCESS OF GIVEN ROOM
					for(User u : userList)
						count ++;
					
					// ONE ROOM CAN ONLY ALLOTED TO FOUR USER
					// IF ROOM IS NOT ALLOTED TO FOUR USER
					// THEN ROOM HAVE SPACE AND ROOM GET ALLOTED TO USER
					// IF ROOM ALREADY ALLOTED TO FOUR USER
					// THEN ROOM CANNOT BE ALLOTED TO GIVEN USER
					if(count<4 && r!=null){
						vaccantRoom.add(r); // ADDING ROOM TO THE VACANT LIST IF BED AVAILABLE
					}	
				}
			// RETURNING ROOM LIST
			return vaccantRoom;
		}
	}

	// METHOD 12
	// TO FETCH LIST OF USER
	// WHO DOES NOT HAVE ACCESS TO ANY ROOM
	@Override
	public List<User> UnAllotedUsers() {
		// TODO Auto-generated method stub
		try(Session ses=HibernateUtil.getSession()){
			
			// FETCHING ALL USER PRESENT IN A ROOM
			Query qu = ses.createQuery("from User where userRoom_roomId = null");
			List<User> userList=qu.getResultList();
			
			// RETURNING USER LIST
			return userList;
		}
	}

}
