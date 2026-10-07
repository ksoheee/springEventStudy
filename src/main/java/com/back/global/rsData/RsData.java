package com.back.global.rsData;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RsData<T> {
    private String resultType;
    private String msg;
    private T data;

    public RsData(String resultType, String msg) {
        this.resultType = resultType;
        this.msg = msg;
    }

}
