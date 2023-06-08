package biz;

public enum FromTypeEnum {
    LIAN_JIA("lianjia","链家网");

    private String code;

    private String desc;

    FromTypeEnum(String code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public String getCode(){
        return code;
    }

    public String getDesc(){
        return desc;
    }


}
