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
//	//测试滚动条滚动到底部
//	@Test(priority=1)
//	public void testScrollingOne(){
//		//向日志文件中打印搜索测试开始执行的日志信息
//		Log.startTestCase("testScrollingOne");
//		driver.get(baseUrl+"/");
//		 //打印打开的日志信息
//		Log.info("打开 首页v.sogou.com");
//		((JavascriptExecutor)driver).executeScript("window.scrollTo(0,document.body.scrollHeight)");
//		try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		  //向日志文件中打印搜索测试用例执行结束的日志信息
//		  Log.endTestCase("testScrollingOne");
//	}
	
	//测试滚动条滚动到指定元素
	@Test(enabled=false)
	public void testScrollingTwo(){
		//向日志文件中打印搜索测试开始执行的日志信息
		Log.startTestCase("testScrollingTwo");
		driver.get(baseUrl+"/");
		 //打印打开的日志信息
		Log.info("打开 首页v.sogou.com");
		WebElement element=driver.findElement(By.linkText("大话西游之大圣娶亲"));
		((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",element);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		  //向日志文件中打印搜索测试用例执行结束的日志信息
		  Log.endTestCase("testScrollingTwo");
	}
	
	//测试滚动条向下移动800像素
	@Test(priority=3)
	public void testScrollingThree(){
		//向日志文件中打印搜索测试开始执行的日志信息
		Log.startTestCase("testScrollingThree");
		driver.get(baseUrl+"/");
		 //打印打开的日志信息
		Log.info("打开 首页v.sogou.com");
		((JavascriptExecutor)driver).executeScript("window.scrollBy(0,800)");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		  //向日志文件中打印搜索测试用例执行结束的日志信息
		  Log.endTestCase("testScrollingThree");
	}
}