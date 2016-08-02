package cn.xuexi;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.Assert;

public class TestSogou {
	WebDriver driver;
	String baseUrl="http://www.sogou.com";
  @Test
  public void testSogou() {
	  System.out.println("#ThreadA: " +Thread.currentThread().getId());
	  driver.get(baseUrl+"/");
	  WebElement inputBox=driver.findElement(By.id("query"));
	  Assert.assertTrue(inputBox.isDisplayed());
	  inputBox.sendKeys("²âÊÔ123");
	  driver.findElement(By.id("stb")).click();
	  try{
		  Thread.sleep(5000);
	  }catch (InterruptedException e){
		  e.printStackTrace();
	  }
	  Assert.assertTrue(driver.getPageSource().contains("ÍøÒ×Æû³µ"));
  }
  @Parameters("browser")
  @BeforeMethod
  public void beforeMethod(String browser) {
	if (browser.equalsIgnoreCase("firefox")){
		System.setProperty("webdriver.firefox.bin", "D:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
		driver=new FirefoxDriver();
	}
	else{
  	System.setProperty("webdriver.chrome.bin", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
	driver=new ChromeDriver();
	}
		
  }

  @AfterMethod
  public void afterMethod() {
	  driver.quit();
  }

}
