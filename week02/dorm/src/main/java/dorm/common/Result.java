package dorm.common;

import lombok.Data;

@Data
public class Result<T> {
    private Integer code;    // 状态码：200成功，401未登录，403无权限，500失败
    private String message;  // 提示信息
    private T data;          // 数据（可以是对象、列表等）

    // 成功（带数据）
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage("成功");
        result.setData(data);
        return result;
    }

    // 成功（不带数据）
    public static <T> Result<T> success() {
        return success(null);
    }

    // 成功（自定义消息）
    public static <T> Result<T> success(String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(200);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    // 失败
    public static <T> Result<T> error(String message) {
        Result<T> result = new Result<>();
        result.setCode(500);
        result.setMessage(message);
        return result;
    }

    // 自定义状态码失败
    public static <T> Result<T> error(Integer code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }
}