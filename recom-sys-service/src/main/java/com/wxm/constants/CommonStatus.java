package com.wxm.constants;


public enum CommonStatus{
    // text字段为枚举描述
    UNKNOWN(-1, ""),
    
    COMMON_INVALID(0,"无效、未校验、未上线"),
    COMMON_VALID(1,"有效、已校验、已上线"),

    SUCCESS(200,"成功"),
    INVITATION_ERROR(3001,"邀请码校验失败"),
    INVITATION_NOT_EXIST(3002,"邀请码不存在"),
    INVITATION_CANT_USE(3003,"邀请码不可用"),
    SCENE_SCRIPT_NAME_ERROR(3004,"场景脚本更新失败"),
    TOCKEN_NOT_CORRECT(3005,"token不正确"),
    
    
    PARAM_ERROR(400,"参数异常,请检查"),
    SERVER_ERROR(500,"服务器内部错误");
    
    private int    value;
    private String text; 

    private static final KV<Integer, CommonStatus> LOOKUP = new KV<Integer, CommonStatus>();

    static {
        for (CommonStatus status : CommonStatus.values()) {
            LOOKUP.put(status.getValue(), status);
        }
        LOOKUP.putDefault(UNKNOWN);
    }

    private CommonStatus(int value, String text) {
        this.value = value;
        this.text = text;
    }

    public int getValue() {
        return this.value;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    
    public static CommonStatus of(Integer value) {
        return LOOKUP.get(value);
    }

}
