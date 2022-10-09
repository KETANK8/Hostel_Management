/**
 * HOSTEL   MANAGEMENT    SYSTEM
 * @author Ketan Kumar
 * ->ADMIN
 * AND PRINT DATA OF ONE OR ALL USER USING LOGGER, DELETE USER AND ROOM USING DATA ACCESS OBJECT AND HQL 
 * CREATING AND USING GLOBAL EXCEPTION
 * ILLUSTRATING OBJECT RELATION MAPPING IN ENTITY USING HIBERNATE
 * ONE ROOM CAN HAVE MANY USER
 */
package com.HostelMS.serviceImpl;

import java.util.List;
import java.util.Scanner;

import com.HostelMS.App;
import com.HostelMS.dao.AdminDao;
import com.HostelMS.daoImpl.AdminDaoImpl;
import com.HostelMS.exception.GlobalException;
import com.HostelMS.model.Room;
import com.HostelMS.model.User;
import com.HostelMS.service.adminDashboard;

import org.apache.log4j.Logger;

public class adminDashboardImpl implements adminDashboard{

	static Logger log = Logger.getLogger(adminDashboardImpl.class);
	static Scanner scan = new Scanner(System.in);
	static adminDashboard adashl=new adminDashboardImpl();
	static AdminDao adao=new AdminDaoImpl();
	
	// METHOD 1
	// METHOD TO IMPLEMENT DASHBOARD
	// GIVE ADMIN CHOICE TO PERFORM DIFFERENT ACTION
	@Override
	public void dashboard() {
		// TODO Auto-generated method stub
		int choice=0;
		// CREATING A LOOP TO RE ENTER CHOICE
		// RE ENTER CHOICE AFTER CCOMPLETING ONE ACTION
		while(choice<14)
		{
		log.info("\nADMIN DASHBOARD");
		// THESE ARE THE ALL ACTION ADMIN CAN PERFORM
		log.info("\nPress 1 - All Rooms \nPress 2 - All Users \nPress 3 - Create Rooms \nPress 4 - Allot Room \nPress 5 - Room Status \nPress 6 - Fetch User Profile \nPress 7 - Set Due Fees Amount \nPress 8 - Pay Due Fees Amount \nPress 9 - Delete User \nPress 10 - Set User Role \nPress 11 - Vaccant Room List \nPress 12 - Un Alloted User \nPress 13 - Log Out");
		choice = scan.nextInt();
			switch(choice) {
			
				case 1->adashl.fetchAllRooms();
				case 2->adashl.fetchAllUsers();
				case 3->adashl.createRoom();
				case 4->adashl.allotRoom();
				case 5->adashl.userInARoom();
				case 6->adashl.viewUserProfile();
				case 7->adashl.generateRent();
				case 8->adashl.rentPayment();
				case 9->adashl.deleteUser();
				case 10->adashl.setUserRole();
				case 11->adashl.vaccantRoom(); 
				case 12->adashl.unAllotedUser();
				// DEFAULT CASE TO LOGOUT
				// LOG OUT FROM ADMIN DASHBOARD
				// RETURN TO THE MAIN MENU/LOGIN PAGE
				default ->{ log.info("\nLOGGED OUT\n");
					App.main(null);}
				}
		}
	}

	//METHOD 2
	// METHOD TO FETCH LIST OF USER PRESENT IN A ROOM
	// FETCH USER BY CALLING ALL ROOM METHOD
	@Override
	public void fetchAllRooms() {
		// TODO Auto-generated method stub
		
		// CALLING ALL ROOM METHOD USING ADMIN DAO OBJECT
		// FETCHING LIST OF ROOM
		List<Room> roomList = adao.AllRooms();
		for(Room r : roomList)
			// PRINTING DETAIL OF ALL ROOM
			// USING ENHANCED FOR LOOP
			log.info("\nRoom Id : "+r.getRoomId()+"\tRoom Name : "+r.getRoomName()+"\tRoom Type : "+r.getRoomType());
	}

	// METHOD 3
	// METHOD TO FETCH LIST OF ALL USER
	// FETCH ALL USER EXCEPT ADMIN
	@Override
	public void fetchAllUsers() {
		// TODO Auto-generated method stub
		// FETCHING LIST OF ALL USER
		// CALLING ALL USER METHOD USING ADMIN DAO OBJECT
		List<User> userList = adao.AllUsers();
		
		// PRINTING DETAIL OF USER
		// USING ENHANCED FOR LOOP
		for(User u : userList) {
			
			if(u.getUserRoom()==null)
				log.info("\nUser Id : "+u.getUserId()+" \tName : "+u.getFirstName()+" "+u.getLastName()+" \tContact No : "+u.getUserContact()+" \tRoom : null");
			else
				log.info("\nUser Id : "+u.getUserId()+" \tName : "+u.getFirstName()+" "+u.getLastName()+" \tContact No : "+u.getUserContact()+" \tRoom : "+u.getUserRoom().getRoomId());
		}
	}
	

	// METHOD 4
	// METHOD TO CREATE A ROOM
	// ADD ROOM TO DATABASE
	@Override
	public void createRoom() {
		// TODO Auto-generated method stub
		Room r=new Room();
		log.info("Enter Room Id");
		int rId = scan.nextInt();
		r.setRoomId(rId);
		log.info("Enter Room Name");
		String rName = scan.next();
		r.setRoomName(rName);
		log.info("Enter Room Type");
		String rType = scan.next();
		r.setRoomType(rType);
		try {
			// CALLING CREATE ROOM METHOD USING ADMIN DAO OBJECT
			int st= adao.createRoom(r); // CREATING ROOM
			if(st==1) {
				log.info("Room added successfully");
			}
		}
		catch(GlobalException e) {
			log.info(e.getMessage());
		}
	}

	// METHOD 5
	// METHOD TO ALLOT ROOM TO USER
	// ALLOT ROOM  USING PRIMARY KEY (ROOM ID)
	@Override
	public void allotRoom() {
		// TODO Auto-generated method stub
		log.info("Enter user Id");
		int uId =scan.nextInt();
		log.info("Enter room Id");
		int rId =scan.nextInt();
		int res;
		try {
			
			// CALLING ALLOT ROOM METHOD USING ADMIN DAO OBJECT
			// ALLOTING ROOM TO USER
			res = adao.allotRoom(uId, rId);
			if(res==1)
				log.info("Room alloted successfully");
		} 
		catch (GlobalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// METHOD 6
	// METHOD TO DELETE USER FORM  DATABASE
	// DELETE USER USINFF PRIAMRY KEY (USER ID)
	@Override
	public void deleteUser() {
		// TODO Auto-generated method stub
		log.info("Enter user Id to delete");
		int uId = scan.nextInt();
		int res;
		try {
			
			// CALLING DELETE USER METHOD USING ADMIN DOA OBJECT
			// DELETING USER FROM DATABASE
			res = adao.deleteUser(uId);
			if(res == 1)
				log.info("deleted!...");
		} catch (GlobalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// METHOD 7
	// METHOD TO FETCH ALLOTED ROOM USER
	// FETCHING USER OF ROOM USING PRIMARY KEYS(USER ID AND ROOM ID)
	@Override
	public void userInARoom() {
		// TODO Auto-generated method stub
		log.info("Enter Room Id : ");
		int rId = scan.nextInt();
		
		// CALLING USER IN A ROOM METHOD USING ADMIN DAO OBJECT
		// FETCHING USER LIST
		List<User> userList = adao.userInARoom(rId);
		for(User u:userList)
			// PRESENTING USER INFO
			// USING ENHANCED FOR LOOP
			log.info("\nUser Id : "+u.getUserId()+" \tName : "+u.getFirstName()+" "+u.getLastName()+" \tContact : "+u.getUserContact());
		
	}

	// METHOD 8
	// METHOD TO SET DUE AMOUNT FOR AN USER
	// SET DUE AMOUNT USING PRIMARY KEY(USER ID)
	@Override
	public void generateRent() {
		// TODO Auto-generated method stub
		log.info("Enter Amount to Generate Rent");
		int amount = scan.nextInt();
		log.info("Enter User Id");
		int uId = scan.nextInt();
		int res;
		try {
			
			// CALLING SET DUE AMOUNT METHOD USING ADMIN DO OBJECT
			// UPDATING DUE AMOUNT OF AN USER
			res = adao.generateRent(uId, amount);
			if(res==1)
				log.info("Amount Added");
		} catch (GlobalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// METHOD 9
	// METHOD TO PAY DUE AMOUNT
	// PAY DUE AMOUNT USING PRIAMRY KEY(USER ID)
	@Override
	public void rentPayment() {
		// TODO Auto-generated method stub
		log.info("Enter Amount to Pay Rent");
		int amount = scan.nextInt();
		log.info("Enter user Id");
		int uId = scan.nextInt();
		int reviseAmount;
		try {
			
			// CALLING DEPOSIT FEE AMOUNT USING ADMIN DAO OBJECT
			// UPDATING DUE AMOUNT OF A USER AFTER PAYMENT
			reviseAmount = adao.rentPayment(uId, amount);
			if(reviseAmount >= 0)
				log.info("\nPAYMENT SUCCESSFULL \nAmount Paid "+amount+".Rs \nYour Revised Due Rent Amount : "+reviseAmount+".Rs");
		} 
		catch (GlobalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// METHOD 10
	// METHOD TO VIEW PROFILE OF AN USER
	// VIEW USER PROFILE USING PRIMARY KEY(USER ID)
	@Override
	public void viewUserProfile() {
		// TODO Auto-generated method stub
		log.info("Enter User Id : ");
		int uId = scan.nextInt();
		User u;
		try {
			
			// CALLING FETCH USER PROFILE METHOD USING ADMIN DAO OBJECT
			// FETCHING USER PROFILE
			u = adao.fetchUserProfile(uId);
			log.info(u);
		} 
		catch (GlobalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// METHOD 11
	// METHOD TO SET ROLE OF AN USER
	// SET USER ROLE USING PRIMARY KEY(USER ID)
	@Override
	public void setUserRole() {
		// TODO Auto-generated method stub
		log.info("Enter User Id : ");
		int uId = scan.nextInt();
		log.info("Enter User Role : ");
		String role = scan.next();
		int res;
		try {
			
			// CALLING SET ROLW METHOD USING ADMIN DAO OBJECT
			// UPDATING ROLE OF AN USER
			res = adao.setRole(uId, role);
			if(res==1)
				log.info(role+" Added Successfully.");
		} catch (GlobalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// METHOD 12
	// TO CHECK VACANT ROOM IN HOSTEL
	@Override
	public void vaccantRoom() {
		// TODO Auto-generated method stub
		// CALLING VACANT ROOM METHOD USING ADMIN DAO OBJECT
		// FETCHING LIST OF VACANT ROOM
		List<Room> roomList = adao.VaccantRooms();// FETCHING LIST
		log.info("\nVACANT ROOMS ");
		for(Room r : roomList) {
			int bed =0, rId = r.getRoomId();
			
			// USING USER IN ROOM METHOD TO CHECK NO OF USER IN A ROOM
			// TO FIND VACANT BED IN A ROOM
			List<User> user = adao.userInARoom(rId);
			
			// PRINTING DETAIL OF ALL VACCANT ROOM
			// VACANT ROOM WITH VACCANT BED
			// USING ENHANCED FOR LOOP
			log.info("\nRoom Id : "+r.getRoomId()+"\tRoom Name : "+r.getRoomName()+"\tRoom Type : "+r.getRoomType()+"\tAvailable Bed : "+(4-user.size()));
		} 	
	}

	// METHOD 13
	// TO FETCH LIST OF USER WITHOUT ROOM
	@Override
	public void unAllotedUser() {
		// TODO Auto-generated method stub
		// FETCHING LIST OF ALL USER THOSE ARE NOT ALLOTED TO ANY ROOM
		// CALLING UNALLOTED USER METHOD USING ADMIN DAO OBJECT
		List<User> userList = adao.UnAllotedUsers();
		
		// TRAVERSING USER LIST
		for(User u : userList)
			// PRINTING DETAIL OF USER
			// USING ENHANCED FOR LOOP
			log.info("\nUser Id : "+u.getUserId()+" \tName : "+u.getFirstName()+" "+u.getLastName()+"\tContact No : "+u.getUserContact());
	}
}
