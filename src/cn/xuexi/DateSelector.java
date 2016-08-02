package cn.xuexi;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

public class DateSelector {
	WebDriver driver;
	String baseUrl="http://jqueryui.com/resources/demos/datepicker/other-months.html";
  @Test
  public void getDateSelector() {
	  driver.get(baseUrl);
	  try {
		Thread.sleep(8000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	  //找到日期输入框，直接输入日期，就可以变相模拟在日期控件输入了
	  driver.findElement(By.id("datepicker")).sendKeys("07/07/2016");
  }
  @BeforeMethod
  public void beforeMethod() {
	  System.setProperty("webdriver.chrome.bin", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
	  driver=new ChromeDriver();
  }

  @AfterMethod
  public void afterMethod() {
	  driver.quit();
  }

}
