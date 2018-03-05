package com.ezhuspace.api.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author WangZhen <A.Hleb.King wangzhenjjcn@gmail.com>
 * @since  2018年1月19日  上午11:55:18
 */
public class BaseResponse {

    @JsonProperty("errcode")
    private int errorCode;
    @JsonProperty("errmsg")
    private String error;

    public BaseResponse() {

    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isSuccessful() {
        return this.errorCode == 0;
    }
}
