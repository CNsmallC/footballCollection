package cn.smallc.footballcollection.main;


import cn.smallc.footballcollection.entity.crawler.Page;
import cn.smallc.footballcollection.extractor.tag.extractorpagewithimgtag_toutiaohao;
import cn.smallc.footballcollection.util.HttpClientUtilsForAppV2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Crawlerminisite_v3_new {
    ArrayList<Page> urls = new ArrayList<>();
    private int jsouptimeout = 3000;
    private int jsoupwaittime = 0;
    private int jsoupretrytime = 2;
    private String td;
    static String[] urlFroms = {
            "chunyuyisheng"
    };
    List<String> urlFromList = Arrays.asList(urlFroms);

    public Crawlerminisite_v3_new(ArrayList<Page> _unvisitedurls, int _jsouptimeout,
                                  int _jsoupwait, int _jsoupretry, String _td) {
        this.urls = _unvisitedurls;
        this.jsouptimeout = _jsouptimeout;
        this.jsoupwaittime = _jsoupwait;
        this.jsoupretrytime = _jsoupretry;
        this.td = _td;
    }

    public Crawlerminisite_v3_new(int _jsouptimeout,
                                  int _jsoupwait, int _jsoupretry, String _td) {
        this.jsouptimeout = _jsouptimeout;
        this.jsoupwaittime = _jsoupwait;
        this.jsoupretrytime = _jsoupretry;
        this.td = _td;
    }


    public Page startOnePage(Page u) {
        String urlFrom = u.getUrlFrom();
        //修正bug 同一站点同一个url会出现在好几个分类里面
        //如果抓取过,continue
        /**
         * 2015-11-03
         * */
        try {
            //头条号格式:https://www.toutiao.com/item/6510439889358553608/
            //去重
//            if(VisitedBiz.isVisited(u, SharedRepositoryFactory.getVisitedRepository().getAll())){
//                return null;
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("crawling:" + u.getUrl());
        Document doc = null;

//		if(!urlFromList.contains(urlFrom)&&!u.getUrlFrom().equals("dongfanghao")){
//			doc = GetDoc.getdoc(u.getUrl(), jsouptimeout, jsoupwaittime, jsoupretrytime);
//			String flag = doc.title();
//			if ("Error".equals(flag) || "您未被授权查看该页".equals(flag) || "无法找到该页".equals(flag)) {
//				System.out.println("问题页面");
//				return null;
//			}
//		}

        if (u.getUrlFrom().equals("toutiaohao")) {
            String Content = HttpClientUtilsForAppV2.get(u.getUrl(), new String[]{
                    "user-agent=Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36",
                    "Cookie=tt_webid=6540550048668911118; tt_webid=6540550048668911118; UM_distinctid=162905da13c6d2-0277e799c8832d-3f3c5501-1fa400-162905da13da32; CNZZDATA1259612802=525653624-1522838037-%7C1522842582; __tasessionId=ki15bwi9q1522847058692; _ga=GA1.2.591978139.1522847060; _gid=GA1.2.371951946.1522847060"
            });
            doc = Jsoup.parse(Content);
        }
        if (!urlFromList.contains(urlFrom) && ((doc == null) || doc.body() == null)) {
            return null;
        } else {
            Page page = new Page();
            System.err.println(u.getUrlFrom());
            switch (u.getUrlFrom()) {
                case "toutiaohao":
                    extractorpagewithimgtag_toutiaohao toutiaohao = new extractorpagewithimgtag_toutiaohao();
                    page = toutiaohao.geturlpage(doc, u, jsouptimeout, jsoupwaittime, jsoupretrytime);
                    break;
//                case "baijiahao":
//                    extractorpagewithimgtag_baijiahao baijiahao = new extractorpagewithimgtag_baijiahao();
//                    page = baijiahao.geturlpage(doc, u, jsouptimeout, jsoupwaittime, jsoupretrytime);
//                    if ("".equals(page.getContent().trim())) {
//                        _5daysBeforeFlag = true;
//                        break;
//                    }
//                    try {
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//                        Date pubDate = sdf.parse(page.getPagedate());
//                        if (new Date().getTime() - pubDate.getTime() > 1000 * 3600 * 24 * 5) {
//                            //	page.setPassreview(1);
//                            _5daysBeforeFlag = true;
//                            return null;
//                        } else {
//                            page.setPassreview(0);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    break;
//                case "weibo":
//                    extractorpagewithimgtag_weibo weibo = new extractorpagewithimgtag_weibo();
//                    page = weibo.geturlpage(doc, u, jsouptimeout, jsoupwaittime, jsoupretrytime);
//                    if ("".equals(page.getContent().trim())) {
//                        _5daysBeforeFlag = true;
//                        break;
//                    }
//                    try {
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//                        Date pubDate = sdf.parse(page.getPagedate());
//                        if (new Date().getTime() - pubDate.getTime() > 1000 * 3600 * 24 * 5) {
//                            //	page.setPassreview(1);
//                            _5daysBeforeFlag = true;
//                            return null;
//                        } else {
//                            page.setPassreview(0);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    break;
                default:
                    // 正文提取
                    break;
            }
            /**
             * 2015-11-03 新的put方法
             * */

//            String rowkey = "";
//            if ("weixinsogou".equals(u.getUrlFrom())) {
//                String url = u.getUrl();
//                if (url.indexOf("&id=") != -1) {
//                    rowkey = u.getUrlFrom() + "_" + url.substring(url.lastIndexOf("&id=") + 4);
//                }
//            } else {
//                rowkey = u.getUrlFrom() + "_" + MD5.GetMD5Code(u.getUrl());
//            }
//            try {
//                if(VisitedBiz.isVisited(u, SharedRepositoryFactory.getVisitedRepository().getAll())){
//                    return null;
//                } else {
//                    SharedRepositoryFactory.getVisitedRepository().insert(new Visited(page.getUrl(),page.getTitle()));
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
            //网页的抓取时间
            Date date = new Date();
            // 加入结果集
            page.setCrawlerTime(date);

            return page;
        }
    }
}
