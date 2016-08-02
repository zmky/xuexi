package cn.xuexi;
import org.testng.annotations.BeforeClass;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.apache.log4j.xml.DOMConfigurator;
public class DataProviderTest {
	WebDriver driver;
	String baseUrl;
	@DataProvider(name="searchWord")
	public static Object[][] words(){
		return new Object[][]{{"蝙蝠侠","主演","迈克尔"},{"超人","导演","唐纳"},{"生化危机","编剧","安德森"}};
	}
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
	@Test(dataProvider="searchWord")
	public void testDataProvider(String searchWord1,String searchWord2,String result){
		//向日志文件中打印搜索测试开始执行的日志信息
		Log.startTestCase("testDataProvider");
		driver.get(baseUrl+"/");
		 //打印打开的日志信息
		Log.info("打开 首页");
		  WebElement inputBox=driver.findElement(By.id("query"));
		  Assert.assertTrue(inputBox.isDisplayed());
		  inputBox.sendKeys(searchWord1+" "+searchWord2);
		  driver.findElement(By.id("stb")).click();
		  //打印“单击搜索按钮”的日志信息
		  Log.info("单击搜索按钮");
		  try{
			  Thread.sleep(5000);
		  }catch (InterruptedException e){
			  e.printStackTrace();
		  }
		  Assert.assertTrue(driver.getPageSource().contains(result));
		  //向日志文件中打印搜索测试用例执行结束的日志信息
		  Log.endTestCase("testDataProvider");
	}
	
}