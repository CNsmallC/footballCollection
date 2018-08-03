package cn.smallc.footballcollection.entity.enums;

public enum Em_ContinentType {
    Europe("欧洲"),
    South_America("南美洲"),
    Africa("非洲"),
    North_America("北美洲"),
    Asia("亚洲"),
    Oceania("大洋洲");

    private String value;

    Em_ContinentType(String value){this.value = value;}

    public String getValue() {
        return this.value;
    }
}
