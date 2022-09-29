package com.HostelMS.service;

import com.HostelMS.exception.GlobalException;

public interface LoginRegister {

	public void Register()throws GlobalException;
	public void Login() throws GlobalException;
	
}
