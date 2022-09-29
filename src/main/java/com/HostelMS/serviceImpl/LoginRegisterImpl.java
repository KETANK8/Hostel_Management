package com.HostelMS.serviceImpl;

import java.util.Scanner;
import java.util.regex.Pattern;

import com.HostelMS.App;
import com.HostelMS.dao.HostelMSDao;
import com.HostelMS.daoImpl.HostelMSDaoImpl;
import com.HostelMS.exception.GlobalException;
import com.HostelMS.model.User;
import com.HostelMS.service.LoginRegister;

import org.apache.log4j.Logger;

public class LoginRegisterImpl implements LoginRegister{

	static Logger log = Logger.getLogger(App.class);
	static Scanner scan = new Scanner(System.in);
	static HostelMSDao dao = new HostelMSDaoImpl();
	
	@Override
	public void Register() throws GlobalException {
		// TODO Auto-generated method stub
		User u = new User();
		log.info("REGISTRATION");
		log.info("Enter Your First Name : ");
		String fName = scan.next();
		u.setFirstName(fName);
		log.info("Enter Your Last Name : ");
		String lName = scan.next();
		u.setLastName(lName);
		log.info("Create Unique Username");
		String uname=scan.next();
		u.setUserName(uname);
		log.info("Create Password");
		String upwd=scan.next();
		u.setUserPassword(upwd);
		log.info("Enter Contact number");
		String uphone=scan.next();
		u.setUserContact(uphone);
		log.info("Enter Your Address");
		String uaddress=scan.next();
		u.setUserAddress(uaddress);
		u.setUserRole("student");
		u.setUserRoom(null);
		u.setUserFee(0);
		
		// Using Regular Expression
		// RegEx To Check Data Entry
		// Giving Condition to Set Unique UserName
		// Giving Condition to Set Unique Password
		// Giving Condition to Set Contact Number
		if(Pattern.matches("[a-zA-Z]{5,}", uname)&&Pattern.matches("[a-zA-Z0-9@#]{6,}",upwd)&&Pattern.matches("[0-9]{9,}", uphone))
		{
			//saving the user details
			int status=dao.Registration(u);
			log.info(status);
			if(status==1) {
				log.info("Registration success");
			}
			else {
				throw new GlobalException("Something went wrong");
			}
		}
		else {
			throw new GlobalException("\nUserName should Atleast have Five Characters. \nPassword must be AlphaNumeric and Atleast have 8 Characters. \nContact No should Atleast have 10 digits.");
		}
		
	}

	@Override
	public void Login() {
		// TODO Auto-generated method stub
		log.info("Profile Login");
		log.info("Enter Your Username");
		String username=scan.next();
		log.info("Enter Your Password");
		String password=scan.next();
		User u = null;
		try {
			u = dao.Login(username, password);
			log.info("\nLogin Successfull"+"\n\nWelcome  "+u.getUserName());
			
			// CREATING OBJECT OF ADMIN AND USER DASHBOARD
			adminDashboardImpl adimpl = new adminDashboardImpl();
			userDashboardImpl udimpl=new userDashboardImpl();
			
			// CALLING ADMIN OR USER DASHBOARD 
			// BASED ON USER ROLE
			// IF USER ROLE IS ADMIN THEN ONLY ADMIN DASHBOARD IS CALLED
			if(u.getUserRole().equals("admin")) {
				adimpl.dashboard();
			}
			else {
				udimpl.dashboard(u.getUserId());
			}
		} catch (GlobalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}	
}
