package cn.smallc.footballcollection.util;


import cn.smallc.footballcollection.entity.crawler.IMG;
import org.jsoup.nodes.Element;

//import cn.com.sh.crawler.bean.IMG;

public class IMGUtils {

	public static IMG getvalidIMG(Element _imgele, int _imgtypefilter,
								  String _purl, int _imgidx) {
		IMG img = new IMG();
		// 获取图片的url
		String imgsrc = UrlFilter.geturls(_imgele.absUrl("src"));
		
		if(imgsrc==null || imgsrc.length()<=0)  {
			imgsrc = UrlFilter.geturls(_imgele.absUrl("data-src"));
		}
		if(imgsrc==null || imgsrc.length()<=0)  {
			imgsrc = UrlFilter.geturls(_imgele.absUrl("alt_src"));
		}
		
		//针对微看点做特殊处理
		if(imgsrc.contains("http://read.html5.qq.com/image?src=fav&imgflag=2&imageUrl=")){
			imgsrc=imgsrc.replace("http://read.html5.qq.com/image?src=fav&imgflag=2&imageUrl=", "");
		}

		// 根据图片名后缀获得图片的类型
		String pictype = "";
		int idx = imgsrc.lastIndexOf(".");
		if (idx > 0) {
			pictype = imgsrc.substring(idx + 1);
		}

		// 过滤非正常的图片url
		if (imgsrc.endsWith(".html") || imgsrc.endsWith(".htm")
				|| imgsrc.endsWith(".shtml") || imgsrc.contains(".dll?")
				|| imgsrc.contains(".jsp?") || imgsrc.contains(".aspx?")
		//		|| imgsrc.contains(".php?")
				) 
		{

		} else {
			
			//过滤搜狐有问题图片
			if(imgsrc.endsWith("!article.foil")){
				imgsrc = imgsrc.replace("!article.foil", "");
			}

			
			// 图片过滤类型为0,仅过滤gif图片,否则过滤gif和png图片
			if (_imgtypefilter == 1) {
				if (pictype.equals("gif") || pictype.equals("png")) {
				} else {
					if (!_imgele.attr("alt").isEmpty()) {
						img.setAlt(_imgele.attr("alt"));
					}

					if (imgsrc != null && imgsrc.length() > 0) {
						img.setSrc(imgsrc);
					}

					img.setPurl(_purl);
					img.setIdx(_imgidx);
				}
			}
			
			//不进行任何过滤
			else if (_imgtypefilter == 2) {
					if (!_imgele.attr("alt").isEmpty()) {
						img.setAlt(_imgele.attr("alt"));
					}

					if (imgsrc != null && imgsrc.length() > 0) {
						img.setSrc(imgsrc);
					}

					img.setPurl(_purl);
					img.setIdx(_imgidx);

			}
			
			else {
				if (pictype.equals("gif")) {
				} else {

					if (!_imgele.attr("alt").isEmpty()) {
						img.setAlt(_imgele.attr("alt"));
					}

					if (imgsrc != null && imgsrc.length() > 0) {
						img.setSrc(imgsrc);
					}

					img.setPurl(_purl);
					img.setIdx(_imgidx);
				}
			}

		}
		return img;
	}

}
