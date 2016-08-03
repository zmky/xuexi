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
		//����־�ļ��д�ӡ�������Կ�ʼִ�е���־��Ϣ
		Log.startTestCase("AjaxClick �ѹ�������");
		driver.get(baseUrl+"/");
		 //��ӡ�򿪵���־��Ϣ
		Log.info("�� ��ҳ");
		  WebElement inputBox=driver.findElement(By.id("query"));
		  Assert.assertTrue(inputBox.isDisplayed());
		  inputBox.click();
		  Thread.sleep(3000);
		  //���������е�����ѡ��洢��suggestionOptions��������
		  List<WebElement> suggestionOptions=driver.findElements(By.xpath("//*[@id='vl']/div[1]/ul/li"));
		  /*
		   * ʹ��forѭ����������ѡ����ѡ��������й��Ϻ����͵��
		   */
		  for(WebElement suggestionOption:suggestionOptions){
			  if (suggestionOption.getText().contains("�й��Ϻ�")){
				  System.out.println(suggestionOption.getText());
				  suggestionOption.click();
				  break;
			  }
		Log.info("��������й��Ϻ���ѡ��");
		  }
			  
		  //ע�͵�������Ϊ���������
//		  WebElement suggestionOption=driver.findElement(By.xpath("//*[@id='vl']/div[1]/ul/li[3]"));
//		  suggestionOption.click();
		  try{
			  Thread.sleep(5000);
		  }catch (InterruptedException e){
			  e.printStackTrace();
		  }
		  
		  //����־�ļ��д�ӡ������������ִ�н�������־��Ϣ
		  Log.endTestCase("AjaxClick �ѹ�������");
	}
	
}