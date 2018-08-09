package cn.smallc.footballcollection.entity.enums;

public enum Em_RootPlayType {
    win("胜"),
    draw("平"),
    lose("负"),

    concede_win("让球胜"),
    concede_draw("让球平"),
    concede_lose("让球负"),

    score_1_0("1:0"),
    score_2_0("2:0"),
    score_2_1("2:1"),
    score_3_0("3:0"),
    score_3_1("3:1"),
    score_3_2("3:2"),
    score_4_0("4:0"),
    score_4_1("4:1"),
    score_4_2("4:2"),
    score_5_0("5:0"),
    score_5_1("5:1"),
    score_5_2("5:2"),
    score_win_other("胜其他"),

    score_0_0("0:0"),
    score_1_1("1:1"),
    score_2_2("2:2"),
    score_3_3("3:3"),
    score_draw_other("平其他"),

    score_0_1("0:1"),
    score_0_2("0:2"),
    score_1_2("1:2"),
    score_0_3("0:3"),
    score_1_3("1:3"),
    score_2_3("2:3"),
    score_0_4("0:4"),
    score_1_4("1:4"),
    score_2_4("2:4"),
    score_0_5("0:5"),
    score_1_5("1:5"),
    score_2_5("2:5"),
    score_lose_other("付其他"),

    ;



    private String value;

    Em_RootPlayType(String value){this.value = value;}

    public String getValue() {
        return this.value;
    }
}
