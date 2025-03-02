package com.thanglv.broadleafstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BaseResponse <T> {
    private int status;
    private String message;
    private T data;

    public static <T> BaseResponse<T> ok(T data) {
        return new BaseResponse<T>(200, "success", data);
    }

    public static <T> BaseResponse<T> of(int status, String message, T data) {
        return new BaseResponse<T>(status, message, data);
    }
}
