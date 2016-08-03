package cn.xuexi;
import org.testng.annotations.BeforeClass;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.apache.log4j.xml.DOMConfigurator;
public class Scrolling {
	WebDriver driver;
	String baseUrl;
	  @BeforeMethod
	  public void beforeMethod() {
//		System.setProperty("webdriver.firefox.bin", "D:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
//		driver=new FirefoxDriver();
		System.setProperty("webdriver.chrome.bin", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
		driver=new ChromeDriver();
		baseUrl="http://v.sogou.com";
	}
	  @AfterMethod
	  public void afterMethod() {
		driver.quit();
	}
	@BeforeClass
	public void beforClass(){
		DOMConfigurator.configure("log4j.xml");
	}
//	//���Թ������������ײ�
//	@Test(priority=1)
//	public void testScrollingOne(){
//		//����־�ļ��д�ӡ�������Կ�ʼִ�е���־��Ϣ
//		Log.startTestCase("testScrollingOne");
//		driver.get(baseUrl+"/");
//		 //��ӡ�򿪵���־��Ϣ
//		Log.info("�� ��ҳv.sogou.com");
//		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,document.body.scrollHeight)");
//		try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		  //����־�ļ��д�ӡ������������ִ�н�������־��Ϣ
//		  Log.endTestCase("testScrollingOne");
//	}
	
	//���Թ�����������ָ��Ԫ��
	@Test(enabled=false)
	public void testScrollingTwo(){
		//����־�ļ��д�ӡ�������Կ�ʼִ�е���־��Ϣ
		Log.startTestCase("testScrollingTwo");
		driver.get(baseUrl+"/");
		 //��ӡ�򿪵���־��Ϣ
		Log.info("�� ��ҳv.sogou.com");
		WebElement element=driver.findElement(By.linkText("������֮��ʥȢ��"));
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",element);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		  //����־�ļ��д�ӡ������������ִ�н�������־��Ϣ
		  Log.endTestCase("testScrollingTwo");
	}
	
	//���Թ����������ƶ�800����
	@Test(priority=3)
	public void testScrollingThree(){
		//����־�ļ��д�ӡ�������Կ�ʼִ�е���־��Ϣ
		Log.startTestCase("testScrollingThree");
		driver.get(baseUrl+"/");
		 //��ӡ�򿪵���־��Ϣ
		Log.info("�� ��ҳv.sogou.com");
		((JavascriptExecutor)driver).executeScript("window.scrollBy(0,800)");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		  //����־�ļ��д�ӡ������������ִ�н�������־��Ϣ
		  Log.endTestCase("testScrollingThree");
	}
}