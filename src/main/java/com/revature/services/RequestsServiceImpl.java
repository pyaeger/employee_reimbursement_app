package com.revature.services;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.revature.dao.RequestsDAO;
import com.revature.dao.RequestsDAOImpl;
import com.revature.dao.UserDAO;
import com.revature.dao.UserDAOImpl;
import com.revature.models.Request;

public class RequestsServiceImpl implements RequestsService {
	
	private RequestsDAO rdao;
	private static Logger log = Logger.getLogger(RequestsServiceImpl.class);
	

	//introducing dependency injection through use of a construction injection
	//so we are not manually injecting what we need for this class; we are handing that control over to the application
	public RequestsServiceImpl(RequestsDAOImpl dao) {
		super();
		this.rdao = dao;
	}

	@Override
	public Request findRequById(int requ_id) {
		log.info("in service layer. searching request by requ_id: " + requ_id);
		return rdao.findRequById(requ_id);
	}
	
	@Override
	public List<Request> findAllRequByAuthorStatus(int author, int status_id) {
		log.info("in the service layer, searching requests by author: " + author + " and status: " + status_id);
		return rdao.findAllRequByAuthorStatus(author, status_id);
	}

	@Override
	public List<Request> findAllRequByStatus(int status_id) {
		log.info("in the service layer, searching requests by status: " + status_id);
		return rdao.findAllRequByStatus(status_id);
	}

	@Override
	public Request findAllRequByType(int type_id) {
		
		return null;
	}

	@Override
	public Request findAllRequByAuthor(int author) {
		
		return null;
	}

	@Override
	public Request findAllRequByResolver(int resolver) {
		
		return null;
	}

	@Override
	public List<Request> findAllRequ() {
		log.info("in service layer. finding all requests...");
		return rdao.findAllRequ();
	}
	
	@Override
	public int submit(Request requ) {
		
		return rdao.submit(requ);
	}

	@Override
	public boolean editRequ(Request requ) {
		
		return false;
	}

	@Override
	public boolean deleteRequById(int requ_id) {
		
		return false;
	}

}
