package cn.smallc.footballcollection.util;

import cn.smallc.footballcollection.entity.crawler.Page;

import java.util.ArrayList;
import java.util.HashSet;

public class UrlFilter {

	/**
	 * url筛选,去除不需要的url
	 * @param _url
	 * @return
	 */
	public static String geturls(String _url) {

		String url = _url;

		// 如果url包含#，去除#之后的
		if (url.contains("#")) {
			url = url.split("#", 2)[0];
		}

		// 如果url包含.html?或者.htm?或者.shtml?
		if (url.contains(".html?") || url.contains(".htm?")
				|| url.contains(".shtml?") || url.contains("?fr=")
				|| url.contains("?tn=") || url.contains("?qq-pf-to=")) {
			url = url.split("\\?", 2)[0];
		}
		
		//针对百度新闻新增后缀
		if (url.contains("&utm_medium")) {
			url = url.split("&", 2)[0];
		}
		
		//163特殊后缀
		if(url.contains("?utm_campaign=163ad3"))
			url=url.replace("?utm_campaign=163ad3","");
		
		//针对url前面有空格
		if(url.indexOf("http://")>0){
			url=url.substring(url.indexOf("http://"));
		}
		
		//取出url后面换行
		if(url.endsWith("\r") || url.endsWith("\n")){
			url.replace("\r", "").replace("\n", "");
		}
		
		//取出url前后空格
		url=url.trim();
		
		return url;
	}
	
	
	/**
	 * url去重
	 * @param urllist
	 * @return
	 */
	public static ArrayList<Page> getdistincturl(ArrayList<Page> urllist) {
		HashSet<String> urlmd5 = new HashSet<String>();
		ArrayList<Page> disturls = new ArrayList<Page>();

		for (Page url : urllist) {
			if (urlmd5.contains(MD5.GetMD5Code(url.getUrl()))) {
				continue;
			} else {
				disturls.add(url);
				urlmd5.add(MD5.GetMD5Code(url.getUrl()));
			}

		}

		return disturls;

	}
	public static void main(String[] args) {
		System.out.println(geturls("http://v.youku.com/v_show/id_XMTYzNTI3MDUyOA==.html?spm=sadfsad&asdfsadfsd"));
	}
}
