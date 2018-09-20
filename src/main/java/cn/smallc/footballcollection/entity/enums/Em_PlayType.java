package cn.smallc.footballcollection.entity.enums;

/**
 * @Author smallC
 * @Date 2018/9/18
 * @Description
 */
public enum Em_PlayType {

    NORMAL("胜负平"),
    RQ_NORMAL("让球胜负平"),
    SCORE("比分"),
    HALF_ALL("全半场"),
    ALL_GOAL("总进球");


    private String value;

    Em_PlayType(String value){this.value = value;}

    public String getValue() {
        return this.value;
    }
}
