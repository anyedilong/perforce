package org.sdblt.common.exception;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

@SuppressWarnings("serial")
public class CommonException extends RuntimeException {

	private int code;

//	public CommonException() {
//		super();
//	}

//	public CommonException(String message) {
//		super(message);
//	}

	public CommonException(int code, String message) {
		super(message);
		this.code = code;
	}

//	public CommonException(String message, Throwable cause) {
//		super(message, cause);
//	}

	public int getCode() {
		return code;
	}

}
