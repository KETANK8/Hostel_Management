package com.HostelMS.dao;

import com.HostelMS.exception.GlobalException;
import com.HostelMS.model.User;

public interface HostelMSDao {

	public int Registration(User u) throws GlobalException;
	public User Login(String UserName,String password) throws GlobalException;
}
