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
		//����־�ļ��д�ӡ�������Կ�ʼִ�е���־��Ϣ
		Log.startTestCase("testJavaScriptClick");
		driver.get(baseUrl+"/");
		 //��ӡ�򿪵���־��Ϣ
		Log.info("�� ��ҳ");
		  WebElement inputBox=driver.findElement(By.id("query"));
		  Assert.assertTrue(inputBox.isDisplayed());
		  inputBox.sendKeys("����456");
		  WebElement searchButton=driver.findElement(By.id("stb"));
		  JavaScriptClick(searchButton);
		  //��ӡ��JavaScript����������ť������־��Ϣ
		  Log.info("JavaScript����������ť");
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
				System.out.println("ʹ��JavaScript����ҳ��Ԫ�صĵ���");
				((JavascriptExecutor)driver).executeScript("arguments[0].click();", element);
			}
			else{
				System.out.println("ҳ���ϵ�Ԫ���޷����е�������");
			}
		}catch (StaleElementReferenceException e){
				System.out.println("ҳ��Ԫ��û�и�������ҳ��"+e.getStackTrace());
			}catch (NoSuchElementException e ){
				System.out.println("��ҳ����û���ҵ�Ҫ������Ԫ��"+e.getStackTrace());
			}catch (Exception e){
				System.out.println("�޷���ɵ�������"+e.getStackTrace());
			}


		}
}