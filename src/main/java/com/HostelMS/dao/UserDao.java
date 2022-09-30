package com.HostelMS.dao;

import com.HostelMS.exception.GlobalException;
import com.HostelMS.model.User;

public interface UserDao {

	public User userRoom(int uId);
	public int userDueAmount(int uId) throws GlobalException;
	public User userProfile(int uId);
	public int changeContact(int uId, String newContact) throws GlobalException;
	public int changePassWord(int uId, String oldPswrd, String newPswrd) throws GlobalException;
}
