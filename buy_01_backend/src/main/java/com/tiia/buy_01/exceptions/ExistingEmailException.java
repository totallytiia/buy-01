package com.tiia.buy_01.exceptions;

import javax.management.RuntimeErrorException;

public class ExistingEmailException extends RuntimeErrorException{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public ExistingEmailException(Error e) {
		super(e);
		// TODO Auto-generated constructor stub
	}

}
