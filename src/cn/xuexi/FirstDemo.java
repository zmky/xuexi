package cn.xuexi;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
public class FirstDemo {
	WebDriver driver;
	String baseUrl;
	  @BeforeMethod
	  public void beforeMethod() {
//		System.setProperty("webdriver.firefox.bin", "D:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
//		driver=new FirefoxDriver();
		System.setProperty("webdriver.chrome.bin", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
		driver=new ChromeDriver();
		baseUrl="http://www.baidu.com";
	}
	  @AfterMethod
	  public void afterMethod() {
		driver.quit();
	}
	  
	@Test
	public void testBaidu(){
		Log.startTestCase("testBaidu");
		 driver.get(baseUrl+"/");
		 Log.info("打开首页");
		  WebElement inputBox=driver.findElement(By.id("query"));
		  Assert.assertTrue(inputBox.isDisplayed());
		  inputBox.sendKeys("测试123");
		  driver.findElement(By.id("stb")).click();
		  try{
			  Thread.sleep(5000);
		  }catch (InterruptedException e){
			  e.printStackTrace();
		  }
		  Assert.assertTrue(driver.getPageSource().contains("网易汽车"));
		  Log.endTestCase("testBaidu");
	}
}
