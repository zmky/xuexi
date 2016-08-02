package cn.xuexi;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.apache.log4j.xml.DOMConfigurator;
public class AjaxClick {
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
	@Test
	public void testAjax() throws InterruptedException {
		//向日志文件中打印搜索测试开始执行的日志信息
		Log.startTestCase("AjaxClick 搜狗浮动框");
		driver.get(baseUrl+"/");
		 //打印打开的日志信息
		Log.info("打开 首页");
		  WebElement inputBox=driver.findElement(By.id("query"));
		  Assert.assertTrue(inputBox.isDisplayed());
		  inputBox.click();
		  Thread.sleep(3000);
		  //将浮动框中的所有选项存储到suggestionOptions的容器中
		  List<WebElement> suggestionOptions=driver.findElements(By.xpath("//*[@id='vl']/div[1]/ul/li"));
		  /*
		   * 使用for循环遍历所有选项，如果选项包含“中国南海”就点击
		   */
		  for(WebElement suggestionOption:suggestionOptions){
			  if (suggestionOption.getText().contains("中国南海")){
				  System.out.println(suggestionOption.getText());
				  suggestionOption.click();
				  break;
			  }
		Log.info("点击包含中国南海的选项");
		  }
			  
		  //注释掉的内容为点击第三项
//		  WebElement suggestionOption=driver.findElement(By.xpath("//*[@id='vl']/div[1]/ul/li[3]"));
//		  suggestionOption.click();
		  try{
			  Thread.sleep(5000);
		  }catch (InterruptedException e){
			  e.printStackTrace();
		  }
		  
		  //向日志文件中打印搜索测试用例执行结束的日志信息
		  Log.endTestCase("AjaxClick 搜狗浮动框");
	}
	
}