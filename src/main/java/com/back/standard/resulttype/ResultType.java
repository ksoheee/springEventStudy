package com.back.standard.resulttype;

public interface ResultType {
    String getResultCode();
    String getMsg();

    default <T> T getData(){
        return null;
    }
}
