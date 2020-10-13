package com.banco.inter.desafio.endpoint.exception;

import java.io.Serializable;
import java.util.Date;

/**
 * Classe responsavel para conter informações do Erro.
 * 
 * @author arthurmapati@gmail.com
 * 
 */

public class ApiError implements Serializable {

	private static final long serialVersionUID = -8282047648749694959L;
	
	private int code;
	
	private String msg;
	
	private Date date;
	
	public ApiError() {
	}	

	public ApiError(int code, String msg, Date date) {
		super();
		this.code = code;
		this.msg = msg;
		this.date = date;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	

}
