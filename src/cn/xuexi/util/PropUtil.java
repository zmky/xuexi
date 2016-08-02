package cn.xuexi.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropUtil {
	//Java中有个比较重要的类Properties（Java.util.Properties），主要用于读取Java的配置文件
	private static Properties properties = null;
	private static Logger logger = Logger.getLogger(PropUtil.class);

	public PropUtil(String path) {
		initialize(path);
	}

	private void initialize(String path) {
		InputStream is = getClass().getClassLoader().getResourceAsStream(path);
		if (is == null) {
			logger.error("The properties is null.");
			return;
		}
		//load ( InputStream inStream)，从输入流中读取属性列表（键和元素对）。通过对指定的文件（比如说上面的 test.properties 文件）进行装载来获取该文件中的所有键 - 值对。以供 getProperty ( String key) 来搜索。
		properties = new Properties();
		try {
			properties.load(is);
		} catch (IOException e) {
			logger.error("执行PropUtil.initialize()方法发生异常，异常信息：", e);
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (Exception e) {
				logger.error("执行PropUtil.initialize()方法发生异常，异常信息：", e);
			}
		}
	}

	/**
	 * get specified key in config files
	 * 
	 * @param key
	 *            the key name to get value
	 */
	public String get(String key) {
		String keyValue = null;
		if (properties.containsKey(key)) {
			keyValue = (String) properties.get(key);
		}
		return keyValue;
	}
}
