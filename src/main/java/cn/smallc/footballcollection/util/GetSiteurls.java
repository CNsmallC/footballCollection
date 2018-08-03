package cn.smallc.footballcollection.util;


import cn.smallc.footballcollection.entity.crawler.Page;

import java.util.HashSet;
import java.util.Set;

public class GetSiteurls {
	
	public static Set<Page> getnextlvurls(String _urlfrom,
										  int _jsouptimeout, int _jsoupwait, int _jsoupretry, String propertiesName) {
		Set<Page> netxlvurls = new HashSet<Page>();
		switch (_urlfrom) {
//			case "toutiaohao":
//				netxlvurls= extractorlink_toutiaohao.getlinks(_urlfrom, 3000, 0, 2,propertiesName);
//				break;
		}
		return netxlvurls;
	}
}
