package cn.smallc.footballcollection.entity.crawler;


import cn.smallc.footballcollection.common.ientity.IAggregateRoot;

public class Visited implements IAggregateRoot {

    private int ID;
    private String title;
    private String url;
    private long crawlerTime;

    public Visited() {
    }

    public long getCrawlerTime() {
        return crawlerTime;
    }

    public void setCrawlerTime(long crawlerTime) {
        this.crawlerTime = crawlerTime;
    }

    public Visited(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
