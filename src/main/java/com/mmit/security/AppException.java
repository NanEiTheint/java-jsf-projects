package com.mmit.security;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class AppException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public AppException(String msg)
	{
		super(msg);
	}

}
