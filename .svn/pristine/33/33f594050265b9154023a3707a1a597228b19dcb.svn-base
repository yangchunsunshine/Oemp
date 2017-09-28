package com.wb.component.computer.billManage.util;

import java.io.Serializable;

/**
 * RespDto查询结果基础类
 * Created by wyf on 2015/8/29.
 */
@SuppressWarnings("serial")
public class RespDto  implements Serializable{
    private boolean success;
    private Object rows;
    private Object others;
    private Object data;
    static RespDto getSuccess(boolean success, Object data) {
        RespDto respDto = new RespDto();
        respDto.setSuccess(success);
        respDto.setRows(data);
        return respDto;
    }


    static RespDto getSuccess(boolean success, Object data, Object others) {
        RespDto respDto = getSuccess(success, data);
        respDto.setOthers(others);
        return respDto;
    }


    public Object getOthers() {
        return others;
    }

    public void setOthers(Object others) {
        this.others = others;
    }

    public static RespDto getSuccess() {
        return  getSuccess(true, null);
    }

    public static RespDto getSuccess(Object data) {
        return  getSuccess(true, data);
    }

    public static RespDto getSuccess(Object data, Object others) {
        return  getSuccess(true, data, others);
    }

    public static RespDto getFail(Object data) {
        return getSuccess(false, data);
    }
    public static RespDto getFail(Object data,Object others) {
    	RespDto respDto = getSuccess(false, data);
    	respDto.setOthers(others);
        return respDto;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }


	public Object getRows() {
		return rows;
	}


	public void setRows(Object rows) {
		this.rows = rows;
	}


	public Object getData() {
		return data;
	}


	public void setData(Object data) {
		this.data = data;
	}
	

}
