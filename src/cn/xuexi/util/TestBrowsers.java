package cn.xuexi.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class TestBrowsers {
	
	private WebDriver driver;
	
	
	@BeforeClass
	public void beforeClass(){
		Browsers browser = new Browsers(BrowsersType.chrome);
		driver = browser.driver;
	}

	@Test
	public void start(){
		
		
		driver.get("http://www.126.com");
		driver.findElement(By.xpath("//input[@id='idInput']")).clear();
		driver.findElement(By.xpath("//input[@id='idInput']")).sendKeys("zmmkyyy");
		
		driver.findElement(By.xpath("//input[@id='pwdInput']")).sendKeys("897437123plm");
		driver.findElement(By.xpath("//input[@id='pwdInput']")).submit();
		
		
	}
	
	@AfterClass
	public void quit(){
		driver.quit();
	}
	
	
}
