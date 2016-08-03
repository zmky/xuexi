package cn.xuexi;
import org.apache.log4j.Logger;


public class Log {
	//��ʼ��һ��Logger����
	private static Logger Log=Logger.getLogger(Log.class.getName());
	public static void startTestCase(String sTestCaseName){
		Log.info("--------------------------");
		Log.info("******   "+sTestCaseName+"   ********");
	}
	public static void endTestCase(String sTestCaseName) {
		Log.info("*********    "+"��������ִ�н���"+"   ******");
		Log.info("----------------------------");
	}
	//����һ����̬info��������ӡ�Զ����info������־��Ϣ	
	public static void info(String message){
		Log.info(message);
	}
	//����һ����̬warn��������ӡ�Զ����warn������־��Ϣ	
	public static void warn(String message){
		Log.warn(message);
	}
	//����һ����̬error��������ӡ�Զ����error��Ϣ	
	public static void error(String message){
		Log.error(message);
	}
	//����һ����̬fatal��������ӡ�Զ����fatal��Ϣ	
	public static void fatal(String message){
		Log.fatal(message);
	}
	//����һ����̬debug��������ӡ�Զ����debug��Ϣ	
	public static void debug(String message){
		Log.debug(message);
	}
}
