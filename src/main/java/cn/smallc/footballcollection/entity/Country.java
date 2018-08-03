package cn.smallc.footballcollection.entity;

import cn.smallc.footballcollection.common.ientity.IAggregateRoot;
import cn.smallc.footballcollection.entity.enums.Em_ContinentType;

public class Country  implements IAggregateRoot {

    private int ID;
    private String name_C;
    private String name_E;
    private Em_ContinentType continent;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName_C() {
        return name_C;
    }

    public void setName_C(String name_C) {
        this.name_C = name_C;
    }

    public String getName_E() {
        return name_E;
    }

    public void setName_E(String name_E) {
        this.name_E = name_E;
    }

    public Em_ContinentType getContinent() {
        return continent;
    }

    public void setContinent(Em_ContinentType continent) {
        this.continent = continent;
    }
}
