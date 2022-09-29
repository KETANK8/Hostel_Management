package com.HostelMS;
import org.apache.log4j.Logger;

import java.util.Scanner;

import com.HostelMS.exception.GlobalException;
import com.HostelMS.serviceImpl.LoginRegisterImpl;


public class App 
{
	static Logger log=Logger.getLogger(App.class);
    public static void main( String[] args )
    {
    		// Creating Scanner Object
         	Scanner scan=new Scanner(System.in);
        	log.info("Hostel Management System");
        	LoginRegisterImpl loginReg=new LoginRegisterImpl();
        	int choice =0;
        	while(choice<3) {
	        	log.info("\nPress 1 - Register New Profile \nPress 2 - Profile Login \nPress 3 - Exit \nEnter Your Choice : ");
	        	choice =scan.nextInt();
	        	
	        	switch(choice) {
		        	
		        	// First Case
		        	// TO Register New Profile
		        	case 1->{
					        	try {
					        		loginReg.Register();
					        	} 
					        	catch (GlobalException e){
					        		// TODO Auto-generated catch block
					        		e.printStackTrace();
					        	}
		        			}
		        	
		        	// Second Case
		        	// TO Login to Existing Profile
		        	case 2->loginReg.Login();
	        	}
        	}
        	scan.close();
    }
}
