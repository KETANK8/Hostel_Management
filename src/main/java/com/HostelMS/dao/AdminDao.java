package com.HostelMS.dao;

import java.util.List;

import com.HostelMS.exception.GlobalException;
import com.HostelMS.model.*;

public interface AdminDao {

	public List<Room> AllRooms();
	public List<User> AllUsers();
	public int createRoom(Room r) throws GlobalException;
	public int allotRoom(int uId,int rId) throws GlobalException;
	public int deleteUser(int uId) throws GlobalException;
	public List<User> userInARoom(int rId);
	public int setDueAmount(int uId,int amount) throws GlobalException;
	public int depositFeeAmount(int uId,int amount) throws GlobalException;
	public User fetchUserProfile(int uId)throws GlobalException;
}
