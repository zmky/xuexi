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
		return new Object[][]{{"������","����","���˶�"},{"����","����","����"},{"����Σ��","���","����ɭ"}};
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
		//����־�ļ��д�ӡ�������Կ�ʼִ�е���־��Ϣ
		Log.startTestCase("testDataProvider");
		driver.get(baseUrl+"/");
		 //��ӡ�򿪵���־��Ϣ
		Log.info("�� ��ҳ");
		  WebElement inputBox=driver.findElement(By.id("query"));
		  Assert.assertTrue(inputBox.isDisplayed());
		  inputBox.sendKeys(searchWord1+" "+searchWord2);
		  driver.findElement(By.id("stb")).click();
		  //��ӡ������������ť������־��Ϣ
		  Log.info("����������ť");
		  try{
			  Thread.sleep(5000);
		  }catch (InterruptedException e){
			  e.printStackTrace();
		  }
		  Assert.assertTrue(driver.getPageSource().contains(result));
		  //����־�ļ��д�ӡ������������ִ�н�������־��Ϣ
		  Log.endTestCase("testDataProvider");
	}
	
}