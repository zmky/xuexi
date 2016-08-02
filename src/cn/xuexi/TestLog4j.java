package cn.xuexi;
import org.testng.annotations.BeforeClass;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.apache.log4j.xml.DOMConfigurator;

import cn.xuexi.util.*;
public class TestLog4j {
	WebDriver driver;
	String baseUrl;
	Browsers browser;
	@Parameters("platform")
	  @BeforeMethod
	  public void beforeMethod(String platform) {
//		System.setProperty("webdriver.firefox.bin", "D:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
//		driver=new FirefoxDriver();
//		System.setProperty("webdriver.chrome.bin", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
//		driver=new ChromeDriver();
		if (platform.equals("FF"))
		{browser=new Browsers(BrowsersType.firefox);}
		else if(platform.equals("chrome"))
		{browser=new Browsers(BrowsersType.chrome);}
		else
		{browser=new Browsers(BrowsersType.ie);}
		driver=browser.driver;
		baseUrl="http://www.sogou.com";
	}
	  @AfterMethod
	  public void afterMethod() {
		driver.quit();
	}
	@BeforeClass
	public void beforClass(){
		DOMConfigurator.configure("log4j.xml");
	}
	@Test
	public void testSogou(){
		//向日志文件中打印搜索测试开始执行的日志信息
		Log.startTestCase("testSogou");
		driver.get(baseUrl+"/");
		 //打印打开的日志信息
		Log.info("打开 首页");
		  WebElement inputBox=driver.findElement(By.id("query"));
		  Assert.assertTrue(inputBox.isDisplayed());
		  inputBox.sendKeys("测试123");
		  driver.findElement(By.id("stb")).click();
		  //打印“单击搜索按钮”的日志信息
		  Log.info("单击搜索按钮");
		  try{
			  Thread.sleep(5000);
		  }catch (InterruptedException e){
			  e.printStackTrace();
		  }
		  Assert.assertTrue(driver.getPageSource().contains("网易汽车"));
		  //向日志文件中打印搜索测试用例执行结束的日志信息
		  Log.endTestCase("testSogou");
	}
	
}