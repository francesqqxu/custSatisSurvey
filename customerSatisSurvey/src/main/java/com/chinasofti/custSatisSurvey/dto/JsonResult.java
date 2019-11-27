package com.chinasofti.custSatisSurvey.dto;

import java.util.ArrayList;
import java.util.List;

public class JsonResult<T> {
	// 状态
	private boolean success;

	// 错误码
	private String error;

	// 错误信息
	private String errorMessage;

	// 数据
	private List<T> data = new ArrayList<T>();

	// 数据总条数
	private Integer total = data != null ? data.size() : 0;

	public JsonResult() {
		super();
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}
	
	public static <T> JsonResult<T> success() {
		return success(null);
	}
	
	public static <T> JsonResult<T> success(List<T> data) {
		JsonResult<T> jsonResult = new JsonResult<T>();
		jsonResult.success = true;
		jsonResult.error = null;
		jsonResult.errorMessage = null;
		jsonResult.data = data;
		return jsonResult;
	}
	
	public static <T> JsonResult<T> success(T row) {
		JsonResult<T> jsonResult = new JsonResult<T>();
		jsonResult.success = true;
		jsonResult.error = null;
		jsonResult.errorMessage = null;
		jsonResult.data.add(row);
		jsonResult.total = 1;
		return jsonResult;
	}
	
	public static <T> JsonResult<T> error(String error, String errorMessage) {
		JsonResult<T> jsonResult = new JsonResult<T>();
		jsonResult.success = false;
		jsonResult.error = error;
		jsonResult.errorMessage = errorMessage;
		jsonResult.data = null;
		return jsonResult;
	}
}
