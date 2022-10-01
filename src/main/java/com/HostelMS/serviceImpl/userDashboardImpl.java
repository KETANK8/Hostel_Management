/**
 * HOSTEL   MANAGEMENT    STSTEM
 * @author Ketan Kumar
 * ->END USER
 * AND PRINT DATA OF ONE OR ALL USER USING LOGGER, DELETE USER AND ROOM USING DATA ACCESS OBJECT AND HQL 
 * CREATING AND USING GLOBAL EXCEPTION
 * ILLUSTRATING OBJECT RELATION MAPPING IN ENTITY USING HIBERNATE
 * ONE ROOM CAN HAVE MANY USER
 */
package com.HostelMS.serviceImpl;

import java.util.Scanner;

import com.HostelMS.App;
import com.HostelMS.dao.UserDao;
import com.HostelMS.daoImpl.UserDaoImpl;
import com.HostelMS.exception.GlobalException;
import com.HostelMS.model.User;
import com.HostelMS.service.userDashboard;

import org.apache.log4j.Logger;

public class userDashboardImpl implements userDashboard{

	static Logger log=Logger.getLogger(userDashboardImpl.class);
	
	// CREATING SCANNER OBJECT
	static Scanner scan=new Scanner(System.in);
	
	// CREATING USER DASHBOARD IMPL OBJECT
	static userDashboardImpl udimpl=new userDashboardImpl();
	
	// CREATING USER DAO OBJECT
	static UserDao udao = new UserDaoImpl();
	static int userId;
	
	
	// METHOD 1
	// METHOD TO IMPLEMENT DASHBOARD
	// GIVE USER CHOICE TO PERFORM DIFFERENT ACTION
	@Override
	public void dashboard(int uId) throws GlobalException {
		// TODO Auto-generated method stub
		log.info("USER DASHBOARD");
		int choice=0;
		userId=uId;
		
		// CREATING LOOP
		while(choice<7) {
			
			// USER CAN PERFORM THESE ACTIONS
			log.info("\nPress 1 - View Your Room\nPress 2 - Due Amount \nPress 3 - View  Your Profile\nPress 4 - Change Contact Number \nPress 5 - Change password \nPress 6 - Log Out");
			
			choice=scan.nextInt();
			
			switch(choice) {
		
				case 1->udimpl.viewRoom();
				
				case 2->udimpl.viewDueAmount();
				
				case 3->udimpl.viewProfile();
				
				case 4->udimpl.changeContactnumber();
				
				case 5->udimpl.changePassword();
				
				// DEFAULT CASE TO LOGOUT
				// LOG OUT FROM USER DASHBOARD
				// RETURN TO THE MAIN MENU/LOGIN PAGE
				default ->{ log.info("\nLOGGED OUT\n");
				App.main(null);}
			}
		}
		
	}

	
	// METHOD 2
	// TO SHOW ROOM DETAILS OF USER
	@Override
	public void viewRoom() {
		// TODO Auto-generated method stub
		User u= udao.userRoom(userId);// FETCHING USER ROOM DETAILS
		log.info("Hello "+u.getUserName()+"\nRoom number : "+u.getUserRoom().getRoomId()+"\nRoom Name : "+u.getUserRoom().getRoomName()+"\nRoom Type : "+u.getUserRoom().getRoomType());
	}

	// METHOD 3
	// TO SHOW DUE AMOUNT OF USER
	@Override
	public void viewDueAmount() throws GlobalException {
		// TODO Auto-generated method stub
		int amount= udao.userDueAmount(userId);// FETCH DUE AMOUNT DETAILS
		log.info("Rent Due : "+amount);
	}

	// METHOD 4
	// FETCH AND SHOW PROFILE DETIALS TO USER
	@Override
	public void viewProfile() {
		// TODO Auto-generated method stub
		User u= udao.userProfile(userId);// FETCHING USER DETAILS
		log.info(u);
	}

	
	// METHOD 5
	// TO CHANGE CONTACT NUMBER
	@Override
	public void changeContactnumber() throws GlobalException {
		// TODO Auto-generated method stub
		log.info("Enter New Contact number");
		String contact = scan.next();
		int res=udao.changeContact(userId, contact);// UPDATING CONTACT NUMBER
		if(res==1) {
			log.info("Phone number updated");
		}
	}

	
	// METHOD 6
	// TO CHANGE PASSWORD
	@Override
	public void changePassword() {
		// TODO Auto-generated method stub
		log.info("Enter Current Password");
		String oldpswrd = scan.next();
		log.info("Enter New Password");
		String newpswd = scan.next();
		int res = 0 ;
		try {
			res = udao.changePassWord(userId, oldpswrd, newpswd);
		} catch (GlobalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// CHANGING ACCOUNT PASSWORD
		if(res==1) {
			log.info("password changed");
		}
	}

	
}
