package cn.smallc.footballcollection.extractor.tag;



import cn.smallc.footballcollection.entity.crawler.IMG;
import cn.smallc.footballcollection.entity.crawler.Page;
import cn.smallc.footballcollection.util.GetDoc;
import cn.smallc.footballcollection.util.IMGUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/****
 * 
 * @ClassName: extractorpagewithimgtag_jinritoutiao
 * @Description: TODO jinritoutiao
 * @author zhangjianfu@021.com
 * @date 2016年01月06日
 *
 */
public class extractorpagewithimgtag_toutiaohao {
	private static final String imgtaghead = "$#imgidx=";
	private static final String imgtagtail = "#$";
	private static final String linetag = "!@#!@";
	private static ArrayList<IMG> imglist = new ArrayList<>();
	private static HashSet<String> imgsrcs = new HashSet<String>();
	private static String tmpcontentstr;
	private static int imgidx;
	private static Map<String, String> AllType=InitNewsType();//所有分类
	public static void main(String[] args) throws Exception {
		//checkOnePage("https://m.toutiao.com/i6563796029584441869/");
		checkOnePage("https://www.toutiao.com/item/6447797805015630350/");
//			checkAllPage();
	}
	private static void checkOnePage(String url) {
		Page page = new Page();
		page.setUrl(url);
		page.setUrlFrom("COS联盟");
		System.out.println(page.getUrl());
		Document doc = GetDoc.getdoc4Chrome(page.getUrl(), 3000, 0, 2);
		extractorpagewithimgtag_toutiaohao aa = new extractorpagewithimgtag_toutiaohao();
		page = aa.geturlpage(doc, page, 3000, 0, 2);
		System.out.println("标题：" + page.getTitle());
		System.out.println("分类：" + page.getCategory());
		System.out.println("关键字：" + page.getKeywords());
		System.out.println("来源：" + page.getSource());
		System.out.println("作者：" + page.getAuthor());
		System.out.println("发布日期：" + page.getTimeStr());
		System.out.println("内容：" + page.getContent());
		for (IMG img : page.getIMG()) {
			System.out.println(img.getIdx()+"\t"+img.getSrc());
		}
	}

//	private static void checkAllPage() {
//		Set<UrlBase> linkset = extractorlink_toutiaohao.getlinks("toutiaohao", 3000, 0, 2,"D:/workspace/CrawlerMiniSiteDongfanghao/src/cn/com/sh/crawler/conf/dfhAccount.txt");
//		System.out.println("linkset size:" + linkset.size());
//		for (UrlBase url : linkset) {
//			try{
//			Document doc = GetDoc.getdoc4Chrome(url.getUrl(), 3000, 0, 2);
//			extractorpagewithimgtag_toutiaohao aa = new extractorpagewithimgtag_toutiaohao();
//			PageBase pg = aa.geturlpage(doc, url, 3000, 0, 2);
//			System.out.println("标题：" + pg.getContenttitle());
//			System.out.println("主分类：" + pg.getUrlmaintype());
//			System.out.println("子分类：" + pg.getUrlsubtype());
//			System.out.println("关键字：" + pg.getKeywords());
//			System.out.println("来源：" + pg.getSource());
//			System.out.println("链接积分：" + pg.getBasescore());
//			System.out.println("作者：" + pg.getAuthor());
//			System.out.println("发布日期：" + pg.getPagedate());
//			System.out.println("东方号账号：" + pg.getDfhid());
//			System.out.println("东方号名称：" + pg.getDfhname());
//			System.out.println("东方号头像：" + pg.getDfhheadsrc());
//			System.out.println("内容：" + pg.getContent());
//			for (IMG img : pg.getImgs()) {
//				System.out.println(img.getIdx()+"\t"+img.getSrc());
//			}
//			Check.checkPage(pg, isPageHandled);
//
//		}catch (Exception e){
//				e.printStackTrace();
//			}
//
//		}
//		Check.printErrorInfo();
//	}

	// 初始化
	public extractorpagewithimgtag_toutiaohao() {
		this.imglist = new ArrayList<IMG>();
		this.imgsrcs = new HashSet<String>();
		this.tmpcontentstr = "";
		this.imgidx = 1;
	}

	/**
	 *
	 * @param _doc
	 * @param _u
	 * @param jsouptimeout
	 * @param jsoupwaittime
	 * @param jsoupretrytime
	 * @return
	 */
	public Page geturlpage(Document _doc, Page _u, int jsouptimeout,
			int jsoupwaittime, int jsoupretrytime) {
//		MatchNewsType(_doc,_u);//重新匹配分类
		Page urlpage = new Page();
		int imgidx = 1;// 图片
		int videoidx = 1;// 视频
		// 设置url属性
		// 获取分页信息
		_doc.setBaseUri(_u.getUrl());
		List<String> urlList = new ArrayList<String>();
		urlpage.setUrl(_u.getUrl());
		// 保存html原文档
		urlpage.setTitle(getcontenttitle(_doc));
		urlpage.setKeywords(getcontentkeywords(_doc));
		urlpage.setTimeStr(getcontentdate(_doc));
		urlpage.setUrlFrom(_u.getUrlFrom());
		urlpage.setCategory(_u.getCategory());
		urlpage.setSource("今日头条");
		urlpage.setAuthor(getcontentauthor(_doc));
		urlpage.setContent(getcontent(_doc, _u.getUrl(), 1, imgidx, videoidx));
		urlpage.setIMG(imglist);

		// 追加分页正文
		for (String url : urlList) {
			Document doc = GetDoc.getdoc(url, jsouptimeout, jsoupwaittime,
					jsoupretrytime);
			// 移除首页外的摘要
			if (!doc.select("div[class=new_txt]").select("p[id=fzy]").isEmpty()) {
				doc.select("div[class=new_txt]").select("p[id=fzy]").remove();
			}
			doc.setBaseUri(url);
			String content = getcontent(doc, url, 1,
					urlpage.getIMG().size() + 1, 1);
			urlpage.setContent(urlpage.getContent() + content);
		}
		return urlpage;
	}

	/***
	 * 获取文章内容
	 * 
	 * @param _doc
	 * @return
	 */
	//TODO Auto-generated catch block
	private String getcontent(Document _doc, String _purl, int _imgtypefilter,
                              int _imgidxstart, int _videoidxstart) {
		StringBuilder ret = new StringBuilder();
		// 获取html正文段
		Elements maincontenteles = null;
		if (!_doc.select("div[class=article-content]").select("div").isEmpty()) {
			Elements contents = _doc.select("div[class=article-content]")
					.select("div");
			// 视频播放
			if (!contents.select("div[class=tt-video-box]").isEmpty()) {
				contents.select("div[class=tt-video-box]").remove();
			}
			//投票提交的表单过滤
			if(!contents.select("div[class=mp-vote-box mp-vote-outer]").select("div[class=mp-vote-check]").isEmpty()){
				String text = contents.select("div[class=mp-vote-box mp-vote-outer]").text();
				contents.select("div[class=mp-vote-box mp-vote-outer]").remove();
	//			logger.info("time:"+StringUtil.getTraceInfo() + ",url:" + _purl+ "处理方式:表单提交过滤; 过滤内容:"+text);
			}
			// js
			if (!contents.select("script").isEmpty()) {
				contents.select("script").remove();
			}
			if(!contents.select("h1:contains(大家还爱看)").isEmpty()){
				contents.select("h1:contains(大家还爱看) ~ *").remove();
				contents.select("h1:contains(大家还爱看)").remove();
			}
			if(!contents.select("p:contains(大家还爱看)").isEmpty()){
				contents.select("p:contains(大家还爱看) ~ *").remove();
				contents.select("p:contains(大家还爱看)").remove();
			}
			if (contents.hasText()) {
				maincontenteles = contents;
			}
		}
		Element element = null;
		if (maincontenteles != null) {
			// int imgidx = _imgidxstart;// 图片
			if (maincontenteles.size() > 1) {
				element = maincontenteles.get(1);
			} else {
				element = maincontenteles.get(0);
			}
		}else{
			Pattern pattern =Pattern.compile("content: '(.*)',");
			Matcher matcher = pattern.matcher(_doc.select("script").toString());
			if (matcher.find()){
				String text  = matcher.group(1);
				text = StringEscapeUtils.unescapeHtml(text);
				element = new Element(Tag.valueOf("div"), _purl).html(text);
			}
		}

		String rt = getcontent(element, _purl, _imgtypefilter, _imgidxstart,
				_videoidxstart);


		/*************/
		String ret_str = rt;
		if (ret_str.startsWith(" ") || ret_str.endsWith(" ")) {
			ret_str = ret_str.trim();
		}
		if (ret_str.endsWith("!@#!@")) {
			ret_str = ret_str.substring(0, ret_str.length() - 5);
		}
		String[] strings = ret_str.split("!@#!@");
		StringBuffer content_s = new StringBuffer();

		if (strings != null) {
			for (int i = 0; i < strings.length; i++) {
				if (i == strings.length - 3 || i == strings.length - 2 || i == strings.length - 1) {
					if (contaitsFiltStr(strings[i])) {
						continue;
					}
				}
				content_s.append(strings[i]).append(linetag);
			}
		}
		ret_str = content_s.toString();
		try {
			if (ret_str.endsWith(linetag)) {
				ret_str = ret_str.substring(0, ret_str.lastIndexOf(linetag));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		while (ret_str.indexOf(linetag + linetag) != -1) {
			ret_str = ret_str.replaceAll(linetag + linetag, linetag);
		}

		ret_str = ret_str.replaceAll("\n", "").replaceAll("\r", "");
		ret_str=ret_str.replace("来源YouTube:FashionGuide", "");
		ret_str=ret_str.replace("【下载】汽车之家App，买车能省好几万", "");
		ret_str=ret_str.replace("请点击下面”了解更多“四个字购买！","");
		ret_str=ret_str.replace("详情请点击下面”了解更多“", "");
		ret_str=ret_str.replace("（微信号：nbdnews）", "");
		ret_str=ret_str.replace("（微信ID：meijingyingshi）", "");
		ret_str=ret_str.replace("（微信公众号：吴晓波频道）", "");
		ret_str=ret_str.replace("（微信账号：asklicai）", "");
		ret_str=ret_str.replace("（公众号：icaibei）", "");
		ret_str=ret_str.replace("追踪个股策略？点击“了解更多”，查看彩贝智选机器人的最强选择。", "");
		ret_str=ret_str.replace("受篇幅字数所限，想看完整版，请点击下方“了解更多”", "");
		ret_str=ret_str.replace("雷帝触网由资深媒体人雷建平创办，其为头条签约作者，若转载请写明来源。", "");
		ret_str=ret_str.replace("（微信公号：ymcj8686）", "");
		ret_str=ret_str.replace("（www.NewSeed.cn）", "");
		ret_str=ret_str.replace("（图片署名： 东方IC） \"", "");
		ret_str=ret_str.replace("（图片署名： 东方IC/ 东方IC 周俊祥） \"", "");
		ret_str=ret_str.replace("（图片署名： 东方IC）", "");
		ret_str=ret_str.replace("，欢迎关注我！点击[了解更多]有福利", "");
		ret_str=ret_str.replace("(中新经纬APP)", "");
		ret_str=ret_str.replace("全国双创媒体联盟：18813091903（微信注明身份）", "");
		ret_str=ret_str.replace("*本文由i黑马（ID：iheima）和园区界(ID：yuanqujie)联合出品，争做创投圈最有价值的园区日报。", "");
		ret_str=ret_str.replace("i黑马，让创业者不再孤独。", "");
		ret_str=ret_str.replace("↓↓↓ 阅读原文，加入黑马百城计划！", "");
		ret_str=ret_str.replace("想学习更多玩机技巧，赶快关注IT168手机频道~", "");
		ret_str=ret_str.replace("————END—————", "");
		ret_str=ret_str.replace("运营商世界网（官方微信公众号tel_world）—— TMT行业知名新锐媒体，一家专注通信、互联网、家电、手机、数码的原创资讯网站。", "");
		ret_str=ret_str.replace("以上文章为本人原创，特此声明", "");
		ret_str=ret_str.replace("如果你想和艳姐聊聊，可以加艳姐个人微信", "");
		ret_str=ret_str.replace("我们关注：企业战略、新盘速递、买房故事、产品推介、高管离职、土地市场。", "");
		ret_str=ret_str.replace("地产人言 言尽地产人与事", "");
		ret_str=ret_str.replace("欢迎投稿至727869321@qq.com", "");
		ret_str=ret_str.replace("如果您喜欢本文，可以：", "");
		ret_str=ret_str.replace("长按，识别二维码，关注聊城房地产", "");
		ret_str=ret_str.replace("（关注微信公众号：NJHFHHH，在旅行中感触历史）", "");
		return ret_str;

	}

	/*********************/

	/**
	 *
	 * @param element
	 * @param _purl
	 * @param _imgtypefilter
	 * @param _imgidxstart
	 * @param _videoidxstart
	 * @return
	 */
	String getcontent(Element element, String _purl, int _imgtypefilter,
                      int _imgidxstart, int _videoidxstart) {
		StringBuilder ret = new StringBuilder();
		if (element != null) {
			List<Node> nodes = element.childNodes();
			int nodesize = nodes.size();
			for (int i = 0; i < nodesize; i++) {
				Node n = nodes.get(i);
				// System.out.println(n.nodeName());
				if (n.nodeName().equals("br") || n.nodeName().equals("p")) {
					if (n.toString().indexOf("视频加载中...") != -1) {
						continue;
					}
					if (!ret.toString().endsWith(linetag)) {
						ret.append(linetag);
					}
					// System.out.println("-----"+ret.toString());
				}
				if (n.childNodes().size() > 1) {
					ret.append(getcontent((Element) n, _purl, _imgtypefilter,
							_imgidxstart, _videoidxstart));
				} else {
					// 将node转换为element,报错则跳过
					Element tmp = null;
					try {
						tmp = (Element) nodes.get(i);
					} catch (Exception e) {
						if (e instanceof ClassCastException) {
							String nodeStr = nodes.get(i).toString();
							if (nodeStr.indexOf("<!--") != -1) {
								continue;
							}
							tmpcontentstr = nodeStr;
							if (!tmpcontentstr.equals("")) {
								ret.append(tmpcontentstr);
							}

						}
					}
					if (tmp != null) {
						// 获取图片
						if (!tmp.select("img").isEmpty()) {
							Elements imgeles = tmp.select("img");
							for (Element tmpimgele : imgeles) {
								IMG tmpimg = IMGUtils.getvalidIMG(tmpimgele,
										_imgtypefilter, _purl, imgidx);
								if (tmpimg.getSrc() != null
										&& tmpimg.getSrc().length() > 0
										&& !imgsrcs.contains(tmpimg.getSrc())) {
									imglist.add(tmpimg);
									imgsrcs.add(tmpimg.getSrc());
									if (!ret.toString().endsWith(linetag)) {
										ret.append(linetag);
									}
									ret.append(imgtaghead);
									ret.append(String.format("%04d", imgidx));
									ret.append(imgtagtail);
									ret.append(linetag);
									imgidx++;
								}
							}
						} else {
							// 文本信息
							tmpcontentstr = tmp.text();
							if(tmpcontentstr.startsWith("<embed") && tmpcontentstr.endsWith("</embed>")){
								continue;
							}
							if (!tmpcontentstr.equals("")) {
								ret.append(tmp.text());
							}

						}
					}

				}

			}
		}
		// return ret.toString().replaceFirst(linetag, "");
		return ret.toString();
	}
	
	private  boolean contaitsFiltStr(String content_str){
		content_str=content_str.replaceAll(" ", "");
		if((content_str.contains("公号")||content_str.contains("微号")||content_str.contains("微信号")
		||content_str.contains("公众号")||content_str.contains("订阅号")||content_str.contains("微信平台")
		||content_str.contains("公众平台")||content_str.contains("微信服务号")||content_str.contains("头条号")
		||content_str.contains("今日头条号")||content_str.contains("订阅关注")||content_str.contains("QQ/微信")
		||content_str.contains("添加本人")||content_str.contains("加我微信")||content_str.contains("欢迎订阅")
		||content_str.contains("长按可复制")||content_str.contains("今日头条订阅")||content_str.contains("微信搜索")
		||content_str.contains("微信关注")||content_str.contains("打开微信查找")||content_str.contains("加微信")
		||content_str.contains("长按二维码")||content_str.contains("长按图片")||content_str.contains("识别二维码")
		||content_str.contains("扫描下方二维码")||content_str.contains("广告投放请加")||content_str.contains("点击下方订阅")
		||content_str.contains("淘宝搜索店铺")||content_str.contains("原油投资")||content_str.contains("下载微看点")
		||content_str.contains("微信:")||content_str.contains("微信：")||content_str.contains("评论都来说说"))||content_str.contains("点赞↓↓↓")
		&&((content_str.getBytes()).length<250)){
			return true;
		}
		else{
			return false;
		}
	}

	/**
	 * 获取文章发表时间
	 * 
	 * @param _doc
	 * @return
	 */
	private String getcontentdate(Document _doc) {
		String coontentdate = "";
		Elements es = _doc.select("div[class=subtitle clearfix]").select(
				"span[class=time]");
		if (!es.isEmpty()) {
			String str = es.text();
			coontentdate = str;
		}
		else if(!_doc.select("div[class=minibar clearfix]").select("span[class=time]").isEmpty()){
			String str = _doc.select("div[class=minibar clearfix]").select("span[class=time]").text();
			coontentdate = str;
		} else if(_doc.select("div#article-main").select("div.articleInfo").select("span.time").hasText()) {
			coontentdate = _doc.select("div#article-main").select("div.articleInfo").select("span.time").text();
		}
		Pattern pattern =Pattern.compile("time:\\s*'(.*)'");
		Matcher matcher = pattern.matcher(_doc.select("script").toString());
		if (matcher.find()){
			coontentdate = matcher.group(1);
		}

//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//
//		Date date = null;
//		try {
//			date = df.parse(coontentdate);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}

		return coontentdate;
	}

	/**
	 * 获取文章内容作者
	 * 
	 * @param _doc
	 * @return
	 */
	public String getcontentauthor(Document _doc) {
		String contentauthor = "";
		return contentauthor;
	}

	/**
	 * 获取正文关键词
	 * 
	 * @param _doc
	 * @return
	 */
	public static String getcontentkeywords(Document _doc) {
		String keywords = "";
		StringBuffer sb = new StringBuffer();
		Elements els = _doc.select("div[class=clearfix]")
				.select("ul[class=tag-list]").select("li");
		if (!els.isEmpty()) {
			for (Element e : els) {
				sb.append(e.text());
				sb.append(",");
			}
		}
		keywords = sb.toString();

		if (keywords.endsWith(",")) {
			keywords = keywords.substring(0, keywords.lastIndexOf(","));
		}
		if(keywords==null ||keywords.length()<=1){
			if(!_doc.select(".label-list").isEmpty()){
			Elements elements	= _doc.select(".label-list .label-item");
				for(Element e: elements){
					sb.append(e.text()).append(",");
				}
				keywords =sb.toString();
				if (keywords.endsWith(",")) {
					keywords = keywords.substring(0, keywords.lastIndexOf(","));
				}
			}
		}

		return keywords;
	}

	/**
	 * 获取正文标题
	 * 
	 * @param _doc
	 * @return
	 */
	public static String getcontenttitle(Document _doc) {
		String contenttitle = "";

		if (!_doc.select("div[class=title]").select("h1").isEmpty()) {
			contenttitle = _doc.select("div[class=title]").select("h1").text();
		}
		else if(!_doc.select("div[class=article-header]").select("h1[class=title]").isEmpty()){
			contenttitle = _doc.select("div[class=article-header]").select("h1[class=title]").text();
		} else if(!_doc.select("h1.article-title").hasText()) {
			contenttitle = _doc.select("h1.article-title").text();
		}
		Pattern pattern =Pattern.compile("title:\\s*'(.*)'\\s*,");
		Matcher matcher = pattern.matcher(_doc.select("script").toString());
		if (matcher.find()){
			contenttitle = matcher.group(1);
		}
		// 找不到标题的时候取默认标题
		else {
		     if(_doc.title().indexOf("-")!=-1){
		    	 contenttitle = _doc.title().substring(0,_doc.title().lastIndexOf("-"));
					if(contenttitle!=null){
						contenttitle=contenttitle.trim();
					}
		     }
		     else{
		    	 contenttitle= _doc.title();
		     }
		}
		return contenttitle;
	}
	/**
	 * 匹配分类
	 * @param doc
	 * @param page
	 */
	public static void MatchNewsType(Document doc,Page page){
		Pattern pa=Pattern.compile("chineseTag: '(.*)',");
		Matcher ma=pa.matcher(doc.select("script").toString());
		if(ma.find()){
			String SourcesType=ma.group(1);
			String str=AllType.get(SourcesType);
			String mainType=page.getCategory();//主分类 子分类 赋值为我们事先设置的分类
			//判断头条号页面上的分类是否存在东方号分类中
			//存在的话继续往下判断事先设置的分类是否有误
			//如果头条号页面上的分类不存在我们东方号分类中则不进行修改 继续使用事先设置的分类
			if(null!=str||SourcesType.equals("美食")||SourcesType.equals("搞笑")){
				//如果头条号页面 存在我们东方号分类中！则:继续判断分类是否设置正确
				if(SourcesType.equals("美食")){//美食分类 对应 健康：饮食
					mainType="健康";
					//如果头条号页面分类与我们设置的分类不一致
					//并且头条号页面分类存在东方号分类中 则修改为头条号页面分类
				}else if(SourcesType.equals("搞笑")){
					mainType="笑话";
				}
				page.setCategory(mainType);//主分类
			}
		}
	}
	
	/**
	 * 东方号新闻全部分类 
	 * key=主分类
	 * Value=子分类
	 * @return
	 */
	public static Map<String, String> InitNewsType(){
		HashMap<String, String> map=new HashMap<>();
		map.put("娱乐","电影|电视|八卦|综艺|音乐");
		map.put("社会","");
		map.put("财经","外汇|保险|不动产|黄金|新三板|信托|股票|期货|基金|理财");
		map.put("军事","");
		map.put("体育","NBA|CBA|德甲|意甲|网球|中超|西甲|英超|棋牌|田径|高尔夫|排球|羽毛球|台球");
		map.put("游戏","");
		map.put("科技","科学|互联网|数码");
		map.put("国内","");
		map.put("国际","");
		map.put("汽车","");
		map.put("教育","");
		map.put("房产","");
		map.put("时尚","珠宝|腕表|美容护肤|时装搭配");
		map.put("笑话","");
		map.put("历史","");
		map.put("健康","保健|心理|减肥|健身|饮食");
		map.put("星座","");
		map.put("家居","");
		map.put("情感","");
		map.put("旅游","");
		map.put("历史","");
		map.put("教育","");
		map.put("社会","");
		map.put("动漫","");
		map.put("育儿","");
		map.put("宠物","");
		return map;
	}
}