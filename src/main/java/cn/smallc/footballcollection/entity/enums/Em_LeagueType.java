package cn.smallc.footballcollection.entity.enums;

public enum Em_LeagueType {
    FA_Premier_League("英超"),
    Serie_A("意甲"),
    La_Liga("西甲"),
    Bundesliga("德甲"),
    Ligue_1("法甲");

    private String value;

    Em_LeagueType(String value){this.value = value;}

    public String getValue() {
        return this.value;
    }
}
