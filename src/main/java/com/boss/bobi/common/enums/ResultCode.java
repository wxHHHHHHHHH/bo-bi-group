package com.boss.bobi.common.enums;

//  数据返回状态码
public enum ResultCode {
    // 基础状态码
    DEFEATED(100, "失败"),//    失败
    SUCCEED(200, "成功"),//    成功
    // -------------------失败状态码----------------------
    LOSE_EFFECTIVENESS(9000, "失效"),
    NO_LOGIN(9001, "未登录"),
    CHECK_FAIL(9002, "验证失败"),
    //
    //外部云盘状态code
    YSUCCEED(0,"ok");

    public Integer result;
    public String msg;

    ResultCode(Integer result, String msg) {
        this.result = result;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public Integer getResult() {
        return result;
    }

    public static String getContent(Integer result) {
        for (ResultCode resultCode : values()) {
            if (resultCode.getResult() == result) {
                return resultCode.getMsg();
            }
        }
        return "未查询到该状态码定义说明";
    }
}
