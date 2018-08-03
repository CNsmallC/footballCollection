package cn.smallc.footballcollection.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
* @ClassName: MKLogBack 
* @Description: 日志记录
* @author xzs
* @date 2016年12月26日 下午4:14:55 
*  
*/
public class MKLogBack {
	static MKLogBack instance = null;
	static Logger logger = LoggerFactory.getLogger(MKLogBack.class);
	
	private MKLogBack() {
	}

	public static MKLogBack getInstance() {
		if (instance == null) {
			synchronized (MKLogBack.class) {
				if (instance == null) {
					instance = new MKLogBack();
				}
			}
		}
		return instance;
	}

	public void trace(Object message) {
		logger.trace(message.toString());
	}

	public void trace(Object message, Throwable t) {
		logger.trace(message.toString(), t);
	}

	public void debug(Object message) {
		logger.debug(message.toString());
	}

	public void debug(Object message, Throwable t) {
		logger.debug(message.toString(), t);
	}

	public void info(Object message) {
		logger.info(message.toString());
	}

	public void info(Object message, Throwable t) {
		logger.info(message.toString(), t);
	}

	public void warn(Object message) {
		logger.warn(message.toString());
	}

	public void warn(Object message, Throwable t) {
		logger.warn(message.toString(), t);
	}

	public void error(Object message) {
		logger.error(message.toString());
	}

	public void error(Object message, Throwable t) {
		logger.error(message.toString(), t);
	}
}
