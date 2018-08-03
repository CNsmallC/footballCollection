package cn.smallc.footballcollection.util;


import cn.smallc.footballcollection.entity.crawler.Page;

public class PageContentUtil {
	public static boolean IsPageContentEmpty(Page page){
		if(page==null){
			return true;
		}
		String content=page.getContent().trim().replaceAll("!@#!@","");
		if(("".equals(content.trim())||content.trim().length()<40)&&page.getIMG().size()<2){
			return true;	
		}else {
			return false;
		}
		
	}
	
}
