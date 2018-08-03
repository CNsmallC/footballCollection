package cn.smallc.footballcollection.extractor.link;

import cn.smallc.footballcollection.entity.crawler.Page;
import cn.smallc.footballcollection.util.HttpClientUtilsForAppV2;
import cn.smallc.footballcollection.util.MD5;
import cn.smallc.footballcollection.util.UrlFilter;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class extractorlink_toutiaohao {
	private static Set<String> allUrls;// 全局链接去重，首页和滚动链接不去参与去重
	private static String as;
	private static String cp;

	public static void main(String[] args) {
		Set<Page> set = getlinks("toutiaohao", 3000, 0, 2,
				"E:\\smallC\\workspace\\crawlerCollection\\src\\main\\resources\\dfhAccount.txt");
		for(Page url:set){
            System.out.println(url.getCategory() + "\t" + url.getUrl() + "\t" + url.getTitle() +"\t"+url.getUrlFrom());
        }
        System.err.println("获取新闻数量为:"+set.size());
	}
	/**
	 * 获取分类新闻url
	 *
	 * @return
	 */
	public static Set<Page> getlinks_type(Document doc, String _urlfrom, String _mainType) {
		Set<Page> typelinks = new HashSet<Page>();
		Elements els=null;
		//第一种模板
		if(!doc.select("section a").isEmpty()){
			doc.select("section.video-mode").remove();//删除视频
			els=doc.select("section a");
		}
		if(els!=null&&!els.isEmpty()){
			typelinks.addAll(setUrlProp(els, _urlfrom,_mainType));
		}
		return typelinks;
	}

	/**
	 * 通用设置url属性方法
	 *
	 * @param eles
	 * @param urlFrom
	 * @param mainType
	 * @return
	 */
	public static Set<Page> setUrlProp(Elements eles, String urlFrom, String mainType) {
		// boolean urlAllFlag 是否去重标记，true表示去重，除了首页和滚动链接，其余参与去重
		int index = 1;
		Set<Page> typelinks = new HashSet<Page>();
		for (Element temp : eles) {
			Page u = new Page();
			String topic = temp.select("h3").text();
			String url = temp.absUrl("href");
			url = UrlFilter.geturls(url);
			//过滤动态
			if(url.startsWith("http://toutiao.com/dongtai")){
				continue;
			}
			if (allUrls.contains(url)) {
				continue;
			} else {
				allUrls.add(url);
			}
			if(topic.contains("触乐夜话")){
				continue;
			}
			//过滤此新闻
			u.setUrlFrom(urlFrom);//zbah
			u.setTitle(topic);//标题
			u.setUrl(url);//链接
			u.setCategory(mainType);//分类
			index++;
			typelinks.add(u);
		}
		return typelinks;
	}


	public static Set<Page> getlinks(String _urlfrom, int _jsouptimeout, int _jsoupwait, int _jsoupretry, String propertiesName) {
		allUrls=new HashSet<>();
		Set<Page> url=new HashSet<Page>();
		List<String > alldfh=readDfhPropertiesFile(propertiesName);
		for (String dfh : alldfh) {
			try {

				generateAsCp();
				String[] dfhInfo=dfh.split(",");
				String media_id=dfhInfo[0].replace("http://toutiao.com/m", "");
				String ApiUrl="https://www.toutiao.com/pgc/ma_mobile/?page_type=1&max_behot_time=0&aid=&media_id="+media_id+"&count=10&version=2&as="+as+"&cp="+cp+"&timestamp=";
				System.out.println(ApiUrl);
				String str=HttpClientUtilsForAppV2.get(ApiUrl,new String[]{"Cookie=_ba=BA0.2-20180404-51234-wnJWPDqCi2LtLU0R1rhO; tt_webid=6540515597373212167; WEATHER_CITY=%E5%8C%97%E4%BA%AC; UM_distinctid=1628fe33cb942-08f37a45e1d4a1-3f3c5501-1fa400-1628fe33cba8d4; CNZZDATA1259612802=1449536741-1522829383-%7C1522829383; tt_webid=6540515597373212167; __tasessionId=b1d34eawk1522832456971","User-Agent=Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1",
						"X-CSRFToken=",
						"X-Requested-With=XMLHttpRequest"});
				JSONObject jsons=JSONObject.parseObject(str);
				int forIndex=1;
				Thread.sleep(1500);
				while(jsons.getString("html").isEmpty()&&forIndex<5){
					generateAsCp();
					ApiUrl="https://www.toutiao.com/pgc/ma_mobile/?page_type=1&max_behot_time=0&aid=&media_id="+media_id+"&count=10&version=2&as="+as+"&cp="+cp+"&timestamp=";
					System.out.println(ApiUrl);
					str=HttpClientUtilsForAppV2.get(ApiUrl,new String[]{"Cookie=_ba=BA0.2-20180404-51234-wnJWPDqCi2LtLU0R1rhO; tt_webid=6540515597373212167; WEATHER_CITY=%E5%8C%97%E4%BA%AC; UM_distinctid=1628fe33cb942-08f37a45e1d4a1-3f3c5501-1fa400-1628fe33cba8d4; CNZZDATA1259612802=1449536741-1522829383-%7C1522829383; tt_webid=6540515597373212167; __tasessionId=b1d34eawk1522832456971","User-Agent=Mozilla/5.0 (iPhone; CPU iPhone OS 9_1 like Mac OS X) AppleWebKit/601.1.46 (KHTML, like Gecko) Version/9.0 Mobile/13B143 Safari/601.1",
							"X-CSRFToken=",
							"X-Requested-With=XMLHttpRequest"});
					jsons=JSONObject.parseObject(str);
					forIndex++;
					Thread.sleep(1500);
				}
				if(jsons.getString("html").replace(" ", "").isEmpty())continue; //此处如果还是取不到接口数据 则直接退出进行下一个账号
				Document doc=Jsoup.parse(jsons.getString("html"));
				doc.setBaseUri(dfhInfo[0]);
				url.addAll(getlinks_type(doc,_urlfrom,dfhInfo[4])); //doc对象 urlfrom 主分类 子分类
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		return url;
	}

	/**
	 * 读取配置文件拿到全部东方号
	 * @return
	 */
	public static List<String> readDfhPropertiesFile(String propertiesName){
		List<String> list=new ArrayList<String>();
		try
	    {
	        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(propertiesName), "UTF-8"));
	        String str="";
	        while( ( str = br.readLine() ) != null )  {
	        	if(str.startsWith("#")){
					continue;
				}
	        	System.out.println(str);
				list.add(str);
	        }
	        br.close();
	    }
	    catch( FileNotFoundException e )
	    {
	    	e.printStackTrace();
	    }
	    catch( IOException e )
	    {
	    	e.printStackTrace();
	    }
		return list;
	}

	/**
	 * 获取今日头条AS,CP
	 */
	 private static void generateAsCp() {
	        DecimalFormat decimalFormat = new DecimalFormat("###0");//格式化设置
	        String e = decimalFormat.format(Math.floor(new Date().getTime()/1e3));
	        String r = Long.toHexString(Long.valueOf(e)).toUpperCase();
	        char[] r1 = r.toCharArray();
	        String t = MD5.GetMD5Code(String.valueOf(e)).toUpperCase();
	        if(8!=r1.length) {
	            as = "479BB4B7254C150";
	            cp = "7E0AC8874BB0985";
	        }
	        char[] o = t.substring(0, 5).toCharArray(), i = t.substring(t.length()-5).toCharArray();
	        String n = "";
	        for(int s = 0; s < 5; s++) {
	            n += String.valueOf(o[s]) + String.valueOf(r1[s]);
	        }
	        String a = "";
	        for(int c = 0; c < 5; c++) {
	            a += String.valueOf(r1[c+3]) + String.valueOf(i[c]);
	        }
	        as = "A1" + n + r.substring(r1.length-3);
	        cp = r.substring(0, 3) + a + "E1";
	 }
}
