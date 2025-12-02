package com.ljy.quiz_backend.model;

public class Result{
    private Integer code; //1是成功，0是失败；
    private String msg; //成功消息或者失败消息；
    private Object data; //放数据；
    //...get,set,toString
    //构造方法；
    //静态success方法和error方法；


    public Result() {
    }

    public Result(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }
    // 在 com.cyd.quiz_backend.model.Result 类中添加以下方法：
    public static Result success(Object data) {
        Result result = new Result();
        result.setCode(200); // 假设200是成功状态码
        result.setMsg("操作成功");
        result.setData(data);
        return result;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public static Result success(String  msg){
        return new Result(1, msg, null);
    }

    public static Result error(String msg){
        return new Result(0, msg, null);
    }


}
