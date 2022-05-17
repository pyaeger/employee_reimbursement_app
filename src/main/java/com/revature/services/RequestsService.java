package com.revature.services;

import java.util.List;

import com.revature.models.Request;
import com.revature.models.User;


public interface RequestsService {

		
	
		public Request findRequById(int requ_id); 
		
		public List<Request> findAllRequByAuthorStatus(int author, int status_id);
		
		public List<Request> findAllRequByStatus(int status_id);
		
		public Request findAllRequByType(int type_id);
		
		public Request findAllRequByAuthor(int author);	
		
		public Request findAllRequByResolver(int resolver);
		
		public List<Request> findAllRequ();
		
		public int submit(Request requ);
		
		public boolean editRequ(Request requ);
		
		public boolean deleteRequById(int requ_id);


	}
