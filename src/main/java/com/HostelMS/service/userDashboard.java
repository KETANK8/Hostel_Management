package com.HostelMS.service;

import com.HostelMS.exception.GlobalException;

public interface userDashboard {

	public void dashboard(int uId) throws GlobalException;
	public void viewRoom();
	public void viewDueAmount() throws GlobalException;
	public void viewProfile();
	public void changeContactnumber() throws GlobalException;
	public void changePassword() throws GlobalException;
}
