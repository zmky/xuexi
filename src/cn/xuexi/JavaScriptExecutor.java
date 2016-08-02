package cn.xuexi;

import org.testng.annotations.BeforeClass;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.apache.log4j.xml.DOMConfigurator;
@Test
public class JavaScriptExecutor {
	WebDriver driver;
	String baseUrl;
	  @BeforeMethod
	  public void beforeMethod() {
//		System.setProperty("webdriver.firefox.bin", "D:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
//		driver=new FirefoxDriver();
		System.setProperty("webdriver.chrome.bin", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
		driver=new ChromeDriver();
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
	public void testJavaScriptClick() throws Exception{
		//向日志文件中打印搜索测试开始执行的日志信息
		Log.startTestCase("testJavaScriptClick");
		driver.get(baseUrl+"/");
		 //打印打开的日志信息
		Log.info("打开 首页");
		  WebElement inputBox=driver.findElement(By.id("query"));
		  Assert.assertTrue(inputBox.isDisplayed());
		  inputBox.sendKeys("测试456");
		  WebElement searchButton=driver.findElement(By.id("stb"));
		  JavaScriptClick(searchButton);
		  //打印“JavaScript单击搜索按钮”的日志信息
		  Log.info("JavaScript单击搜索按钮");
		  try{
			  Thread.sleep(5000);
		  }catch (InterruptedException e){
			  e.printStackTrace();
		  }
		  Log.endTestCase("testJavaScriptClick");
	}
	public void JavaScriptClick(WebElement element) throws Exception{
		try{
			if (element.isEnabled()&&element.isDisplayed()){
				System.out.println("使用JavaScript进行页面元素的单击");
				((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
			}
			else{
				System.out.println("页面上的元素无法进行单击操作");
			}
		}catch (StaleElementReferenceException e){
				System.out.println("页面元素没有附加在网页中"+e.getStackTrace());
			}catch (NoSuchElementException e ){
				System.out.println("在页面中没有找到要操作的元素"+e.getStackTrace());
			}catch (Exception e){
				System.out.println("无法完成单击动作"+e.getStackTrace());
			}


		}
}