package cn.smallc.footballcollection.main;


import cn.smallc.footballcollection.biz.VisitedBiz;
import cn.smallc.footballcollection.entity.crawler.IMG;
import cn.smallc.footballcollection.entity.crawler.Page;
import cn.smallc.footballcollection.entity.crawler.Visited;
import cn.smallc.footballcollection.support.SharedRepositoryFactory;
import cn.smallc.footballcollection.util.ExceptionUtils;
import cn.smallc.footballcollection.util.GetSiteurls;
import cn.smallc.footballcollection.util.MD5;
import cn.smallc.footballcollection.util.PageContentUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainSite_toutiaohao {

    // jsoup连接默认参数
    private final static int jsouptimeout = 3000;
    private final static int jsoupwait = 0;
    private final static int jsoupretry = 2;
    private final static String filter_str_arr[] = { "trs_editor",
            "font-family", "pt;", "style", "class=", "src=", "width=", "p{",
            "div{", "td{", "th{", "span{", "span{", "ul{", "li{", "a{",
            "TRS_Editor", "FONT-FAMILY", "PT;", "STYLE", "CLASS=", "SRC=",
            "WIDTH=", "P{", "DIV{", "TD{", "TH{", "SPAN{", "SPAN{", "UL{",
            "LI{", "A{", };
    public static void main(String[] args) {

		int sleeptime = 100000;// 抓取时间间隔(毫秒)
		String imgsavepath = "D:/myworkspace/mytest1/imgs/";
		String htmlsavepath = "D:/myworkspace/mytest1/htmls/";
		String imgsavepathpic = "D:/myworkspace/mytest1/imgspic/";
		String htmlsavepathpic = "D:/myworkspace/mytest1/htmlspic/";
		String urlfrom = "toutiaohao";
		String statusfile = "D:/myworkspace/mytest1/crawlerstatus_";
		String propertiesName="E:\\smallC\\workspace\\crawlerCollection\\src\\main\\resources\\dfhAccount.txt";
		String logType="new";

//        int sleeptime = Integer.valueOf(args[0]);// 抓取时间间隔(毫秒)
//        String imgsavepath = args[1];// 非图片新闻图片保存路径
//        String htmlsavepath = args[2];// 非图片新闻和html保存路径
//        String imgsavepathpic = args[3];// 图片新闻图片保存路径
//        String htmlsavepathpic = args[4];// 图片新闻和html保存路径
//        String urlfrom = args[5];// 站点"qiumiwu"
//        String statusfile = args[6];// 爬虫状态文件
//        String propertiesName=args[7];
//        String logType=args[8];

        System.setProperty("log4j2Filename", urlfrom +"_"+logType+"_log");
        Logger logger = LogManager.getLogger(MainSite_toutiaohao.class);

        boolean loopflag = true;

        if (!imgsavepath.endsWith("/")) {
            imgsavepath = imgsavepath + "/";
        }

        if (!htmlsavepath.endsWith("/")) {
            htmlsavepath = htmlsavepath + "/";
        }

        if (!imgsavepathpic.endsWith("/")) {
            imgsavepathpic = imgsavepathpic + "/";
        }

        if (!htmlsavepathpic.endsWith("/")) {
            htmlsavepathpic = htmlsavepathpic + "/";
        }

        System.out.println("crawler start");

        // 开始抓取,死循环
        while (loopflag) {

            ArrayList<Page> unvisitedcache = new ArrayList<>();// 未访问队列
            ArrayList<Page> urlPages = new ArrayList<>();// 结果队列
            ArrayList<Page> unvisitedurls = new ArrayList<>();// 新url队列

            // 本次抓取时间
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            String td = df.format(new Date());

            // 获取站点下的新闻链接
            unvisitedcache.addAll(GetSiteurls.getnextlvurls(urlfrom,
                    jsouptimeout, jsoupwait, jsoupretry,propertiesName));

            System.out.println("unvisitedcache:" + unvisitedcache.size());

            // 抓取数据

            //所有已入库数据
            List<Visited> visiteds = SharedRepositoryFactory.getVisitedRepository().getAll();

            for (Page u : unvisitedcache) {
                try {
                    String temp_url = u.getUrl();
                    System.out.println(temp_url);
                    if (temp_url == null || temp_url.length() <= 0) {
                        continue;
                    }else
                    {
                        if (VisitedBiz.isVisited(u,visiteds)) {
                            continue;
                        } else {
                            // 新的url加入队列
                            unvisitedurls.add(u);
                            //加入去重表
                            SharedRepositoryFactory.getVisitedRepository().insert(new Visited(u.getTitle(),u.getUrl()));
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
//                    logger.fatal(ExceptionUtils.getErrorInfoFromException(e));
//                    logger.fatal(u.getUrl() + "\t" + MD5.GetMD5Code(u.getUrl()));
                }

            }

            for (Page url : unvisitedurls) {
                urlPages.clear();
                Page Page_one = null;
                Crawlerminisite_v3_new crawlerminisite = new Crawlerminisite_v3_new(
                        jsouptimeout, jsoupwait, jsoupretry, td);

                try {
                    Page_one = crawlerminisite.startOnePage(url);
                    if(Page_one==null){
                        continue;
                    }
                    /**
                     * 连接超时，404 等 将请求 url  删除url  方便下次 再次爬去
                     */
//                    if(Page_one.getHtmlstr().equals("<html>\n" +
//                            " <head></head>\n" +
//                            " <body></body>\n" +
//                            "</html>")){
//                        LinkDBminiV2.delPagefromhbase(HbaseConnmini.minisitevisited,rowKey);
//                        logger.debug(new Date().getTime()+"\t"+visited_rk);
//                    }
//                    if(Page_one.getContent().trim().equals("")){
//                        Page_one = null;
//                    }

                    if (Page_one != null) {
                        /**
                         * 过滤作者
                         * 黑名单过滤
                         */
                        /**
                         * 正文过滤
                         */
                        if(PageContentUtil.IsPageContentEmpty(Page_one)){
                            continue;
                        }
                        urlPages.add(Page_one);
                    }

                } catch (Exception e) {
                    //中间出错,将去重表数据删除
                    SharedRepositoryFactory.getVisitedRepository().removeByUrlAndTitle(new Visited(url.getUrl(),url.getTitle()));
                    e.printStackTrace();
                    logger.fatal(getFatalLogString(Page_one));
                }

                try {
                    /**
                     * 生成图片名
                     */
                    for (Page p : urlPages) {
                        // 正文图片
                        List<IMG> temp_img = p.getIMG();

                        for (int i = 0; i < temp_img.size(); i++) {
                            String imgsrc = temp_img.get(i).getSrc();
                            if (imgsrc != null && !imgsrc.equals("")) {
                                String tmp_imgname = df.format(p.getCrawlerTime())
                                        + "_"
                                        + temp_img.get(i)
                                        .getPurl()
                                        + "_"
                                        + temp_img.get(i).getIdx();
                                temp_img.get(i).setPname(tmp_imgname);
                                p.setIMG(temp_img);
                            }
                        }
                    }

                    // 存mysql
                    if (urlPages.size() > 0) {
                        //入库page和img
                        for (Page page : urlPages) {
                            SharedRepositoryFactory.getPageRepository().insert(page);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        //中间出错,将去重表数据删除
                        SharedRepositoryFactory.getVisitedRepository().removeByUrlAndTitle(new Visited(url.getUrl(),url.getTitle()));
                    }catch (Exception e1){
                        e1.printStackTrace();
                    }
                    logger.fatal(ExceptionUtils.getErrorInfoFromException(e));
                    logger.fatal(getFatalLogString(Page_one));
                }
                //临时调试
                //			break;
                try {
                    // 防止大量链接
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            try {
                FileWriter fw = new FileWriter(statusfile + urlfrom);
                fw.write(0);
                fw.close();
            } catch (Exception e) {
                e.printStackTrace();
                logger.fatal(ExceptionUtils.getErrorInfoFromException(e));
            }

            // 等待下一次抓取
            if (sleeptime > 0) {
                System.out.println("wait.......");
                try {
                    Thread.sleep(sleeptime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    static String getFatalLogString(Page Page) {
        StringBuffer sb = new StringBuffer();
        if (Page != null && Page.getUrlFrom() != null && Page.getUrl() != null) {
            sb.append(Page.getCrawlerTime());
            sb.append("\t");
            sb.append(Page.getUrlFrom());
            sb.append("\t");
            sb.append(Page.getUrl());
            sb.append("\t");
            sb.append(MD5.GetMD5Code(Page.getUrl()));
        }

        return sb.toString();
    }

}
