package com.revature.dao;

import java.util.List;

import com.revature.models.Request;

public interface RequestsDAO {
	
	
	public Request findRequById(int requ_id); 
	
	public List<Request> findAllRequByAuthorStatus(int author, int status_id);
	
	public List<Request> findAllRequByStatus(int status_id);
	
	public List<Request> findAllRequByType(int type_id);
	
	public List<Request> findAllRequByAuthor(int author);	
	
	public List <Request> findAllRequByResolver(int resolver);
	
	public List<Request> findAllRequ();
	
	public int submit(Request requ);
	
	public boolean editRequ(Request requ);
	
	public boolean deleteRequById(int requ_id);


	

}
