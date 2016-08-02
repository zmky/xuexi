package cn.xuexi;
import org.apache.log4j.Logger;


public class Log {
	//初始化一个Logger对象
	private static Logger Log=Logger.getLogger(Log.class.getName());
	public static void startTestCase(String sTestCaseName){
		Log.info("--------------------------");
		Log.info("******   "+sTestCaseName+"   ********");
	}
	public static void endTestCase(String sTestCaseName) {
		Log.info("*********    "+"测试用例执行结束"+"   ******");
		Log.info("----------------------------");
	}
	//定义一个静态info方法，打印自定义的info级别日志信息	
	public static void info(String message){
		Log.info(message);
	}
	//定义一个静态warn方法，打印自定义的warn级别日志信息	
	public static void warn(String message){
		Log.warn(message);
	}
	//定义一个静态error方法，打印自定义的error信息	
	public static void error(String message){
		Log.error(message);
	}
	//定义一个静态fatal方法，打印自定义的fatal信息	
	public static void fatal(String message){
		Log.fatal(message);
	}
	//定义一个静态debug方法，打印自定义的debug信息	
	public static void debug(String message){
		Log.debug(message);
	}
}
