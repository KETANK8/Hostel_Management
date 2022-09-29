package com.HostelMS.service;

import com.HostelMS.exception.GlobalException;

public interface userDashboard {

	public void dashboard(int uId) throws GlobalException;
	public void viewRoom();
	public void viewDueAmount();
	public void viewProfile();
	public void changeContactnumber();
	public void changePassword() throws GlobalException;
}
