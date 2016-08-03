package cn.xuexi.webdriver.baseapi;

/**
 * ��װ����˼·��
 * 1����װ���÷���
 * 2�����ڷ�װ���ķ�����ʧ�ܵĲ�����operationCheck�н��н�ͼ
 *  
 **/

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;

import cn.xuexi.util.DateTimeUtil;
import cn.xuexi.util.PropUtil;
import cn.xuexi.util.RandomUtil;
import cn.xuexi.util.JScriptCollection;
import cn.xuexi.util.WebDriverTable;
import cn.xuexi.util.Browsers;
import cn.xuexi.util.BrowsersType;

public class WebdriverBaseApi {
	private WebDriver driver;
	Browsers browser=new Browsers(BrowsersType.chrome);
	private Actions actionDriver;
	private PropUtil PropUtil = new PropUtil("config/FrameWork.properties");
	private String capturePath = PropUtil.get("CapturePath");
	private int pauseTime = Integer.parseInt(PropUtil.get("pauseTime"));
	private Logger logger = Logger.getLogger(WebdriverBaseApi.class);

		

	public WebdriverBaseApi(WebDriver driver) {
		Browsers browser=new Browsers(BrowsersType.firefox);
		this.driver = browser.driver;
		actionDriver = new Actions(this.driver);
	}

	/**
	 * 
	 * @param millisecond
	 *            time to wait, in millisecond
	 */
	public void pause(long millisecond) {
		//���� Thread.sleep() �� Object.wait()���������׳� InterruptedException�������ܺ�������쳣����Ϊ����һ������쳣��checked exception��
		try {
			Thread.sleep(millisecond);
		} catch (InterruptedException e) {
			logger.error("pause error:", e);
		}
	}

	/**
	 * ��ȡ��Ļ��ͼ�����浽ָ��·��
	 * 
	 * @param filepath
	 *            ������Ļ��ͼ�����ļ����Ƽ�·��
	 * @return ��
	 */
	public void captureScreenshot(String filepath) {
		File screenShotFile = ((TakesScreenshot) driver)
				.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(screenShotFile, new File(filepath));
		} catch (Exception e) {
			logger.error("������Ļ��ͼʧ�ܣ�ʧ����Ϣ��", e);
		}
	}

	/**
	 * public method for handle assertions and screenshot.
	 * 
	 * @param isSucceed
	 *            if your operation success
	 */
	public void operationCheck(String methodName, boolean isSucceed) {
		if (isSucceed) {
			logger.info("method ��" + methodName + "�� ����ͨ����");
		} else {
			String dateTime = DateTimeUtil.formatedTime("-yyyyMMdd-HHmmssSSS");
			StringBuffer sb = new StringBuffer();
			String captureName = sb.append(capturePath).append(methodName)
					.append(dateTime).append(".png").toString();
			captureScreenshot(captureName);
			logger.error("method ��" + methodName + "�� ����ʧ�ܣ���鿴��ͼ���գ�"
					+ captureName);
		}
	}

	/**
	 * run js functions.</BR> ʹ��webdriverִ��JS������
	 * 
	 * @param js
	 *            js function string
	 */
	public void jsExecutor(String js) {
		logger.debug("execute js [ " + js + " ]");
		((JavascriptExecutor) driver).executeScript(js);
	}

	/**
	 * execute js functions to do something</BR> ʹ��webdriverִ��JS������
	 * 
	 * @param js
	 *            js function string
	 * @param args
	 *            js execute parameters
	 */
	public void jsExecutor(String js, Object... args) {
		logger.debug("execute js [ : " + js + " ],arguments is : "
				+ args.toString());
		((JavascriptExecutor) driver).executeScript(js, args);
	}

	/**
	 * get some value from js functions.</BR> ʹ��webdriverִ��JS�������һ�÷���ֵ��
	 * 
	 * @param js
	 *            js function string
	 */
	public Object jsReturner(String js) {
		logger.debug("execute js [ " + js + " ]");
		return ((JavascriptExecutor) driver).executeScript(js);
	}

	/**
	 * get some value from js functions.</BR> ʹ��webdriverִ��JS�������һ�÷���ֵ��
	 * 
	 * @param js
	 *            js function string
	 * @param args
	 *            js execute parameters
	 */
	public Object jsReturner(String js, Object... args) {
		logger.debug("execute js [ : " + js + " ],arguments is : "
				+ args.toString());
		return ((JavascriptExecutor) driver).executeScript(js, args);
	}

	/**
	 * rewrite the get method, adding user defined log</BR>
	 * ��ַ��ת������ʹ��WebDriverԭ��get����������ʧ�����ԵĴ������塣
	 * 
	 * @param url
	 *            the url you want to open.
	 * @param actionCount
	 *            retry times when load timeout occuers.
	 */
	public void get(String url, int actionCount) {
		boolean isSucceed = false;
		for (int i = 0; i < actionCount; i++) {
			try {
				driver.get(url);
				logger.debug("navigate to url [ " + url + " ]");
				isSucceed = true;
				break;
			} catch (Exception e) {
				logger.error(e);
			}
		}
		operationCheck("get", isSucceed);
	}

	/**
	 * rewrite the get method, adding user defined log</BR>
	 * ��ַ��ת������ʹ��WebDriverԭ��get������Ĭ�ϼ��س����ԡ�3���Ρ�
	 * 
	 * @param url
	 *            the url you want to open.
	 */
	public void get(String url) {
		get(url, 3);
	}

	/**
	 * navigate back</BR> ��ַ��ת��������WebDriverԭ��navigate.back����������ȫһ�¡�
	 * 
	 */
	public void navigateBack() {
		driver.navigate().back();
		logger.debug("navigate back");
	}

	/**
	 * navigate forward</BR> ��ַ��ת��������WebDriverԭ��navigate.forward����������ȫһ�¡�
	 */
	public void navigateForward() {
		driver.navigate().forward();
		logger.debug("navigate forward");
	}

	/**
	 * syncronize browser used Ajax, judge using jQuery.active.
	 * 
	 * @param timeout
	 *            timeout setting
	 */
	public boolean syncAjaxByJQuery(long timeout) {
		boolean isSucceed = false;
		//currentTimeMillis�÷����������Ƿ��ص�ǰ�ļ����ʱ�䣬ʱ��ı���ʽΪ��ǰ�����ʱ���GMTʱ��(��������ʱ��)1970��1��1��0ʱ0��0������ĺ�������
		long timeBegins = System.currentTimeMillis();
		do {
			//���ַ�����������Ϊ boolean ֵ����� String �������� null ���ں��Դ�Сдʱ����"true"���򷵻ص� boolean ��ʾ true ֵ��
			try {
				//toLowerCase����һ���ַ��������ַ����е���ĸ��ת��ΪСд��ĸ��
				//window.jQuery.active������ʾ��ǰͬʱ������ajax����ĸ���
				isSucceed = Boolean.parseBoolean(jsReturner(
						"return window.jQuery.active == 0").toString()
						.toLowerCase());
				if (isSucceed) {
					break;
				}
			} catch (Exception e) {
				logger.error(e);
			}
			pause(pauseTime);
		} while (System.currentTimeMillis() - timeBegins <= timeout * 1000);
		operationCheck("syncAjaxByJQuery", isSucceed);
		return isSucceed;
	}

	/**
	 * syncronize browser used Ajax, judge using Ajax.activeRequestCount.
	 * 
	 * @param timeout
	 *            timeout setting
	 */
	public boolean syncAjaxByPrototype(long timeout) {
		boolean isSucceed = false;
		long timeBegins = System.currentTimeMillis();
		do {
			try {
				isSucceed = Boolean.parseBoolean(jsReturner(
						"return window.Ajax.activeRequestCount == 0")
						.toString().toLowerCase());
				if (isSucceed) {
					break;
				}
			} catch (Exception e) {
				logger.error(e);
			}
			pause(pauseTime);
		} while (System.currentTimeMillis() - timeBegins <= timeout * 1000);
		operationCheck("syncAjaxByPrototype", isSucceed);
		return isSucceed;
	}

	/**
	 * syncronize browser used Ajax, judge using
	 * dojo.io.XMLHTTPTransport.inFlight.length.
	 * 
	 * @param timeout
	 *            timeout setting
	 */
	public boolean syncAjaxByDojo(long timeout) {
		boolean isSucceed = false;
		long timeBegins = System.currentTimeMillis();
		do {
			try {
				isSucceed = Boolean
						.parseBoolean(jsReturner(
								"return window.dojo.io.XMLHTTPTransport.inFlight.length == 0")
								.toString().toLowerCase());
				if (isSucceed) {
					break;
				}
			} catch (Exception e) {
				logger.error(e);
			}
			pause(pauseTime);
		} while (System.currentTimeMillis() - timeBegins <= timeout * 1000);
		operationCheck("syncAjaxByDojo", isSucceed);
		return isSucceed;
	}

	/**
	 * judge if the alert is present in specified seconds</BR>
	 * ��ָ����ʱ�����жϵ����ĶԻ���Dialog���Ƿ���ڡ�
	 * 
	 * @param timeout
	 *            timeout in seconds
	 */
	public boolean isAlertExists(long timeout) {
		boolean isSucceed = false;
		long timeBegins = System.currentTimeMillis();
		do {
			try {
				driver.switchTo().alert();
				isSucceed = true;
				break;
			} catch (Exception e) {
				logger.error(e);
			}
			pause(pauseTime);
		} while (System.currentTimeMillis() - timeBegins <= timeout * 1000);
		operationCheck("isAlertExists", isSucceed);
		return isSucceed;
	}

	/**
	 * judge if the alert is existing</BR> �жϵ����ĶԻ���Dialog���Ƿ���ڡ�
	 */
	public boolean isAlertExists() {
		return isAlertExists(0);
	}

	/**
	 * wait for specified text within timeout setting, fail if not found.
	 * 
	 * @param text
	 *            the element text you want to wait for
	 * @param timeout
	 *            ��ʱʱ�䣬��λ����
	 */
	public boolean isTextPresent(String expectedText, long timeout) {
		boolean isSucceed = false;

		long timeBegins = System.currentTimeMillis();
		do {
			try {
				if (getPageSource().contains(expectedText)) {
					isSucceed = true;
					logger.debug("find expectedText [" + expectedText
							+ "] success");
					break;
				}
			} catch (Exception e) {
				logger.error(e);
			}
			pause(pauseTime);
		} while (System.currentTimeMillis() - timeBegins <= timeout * 1000);

		operationCheck("isTextPresent", isSucceed);
		return isSucceed;
	}

	/**
	 * wait for specified text within timeout setting, fail if not found.
	 * 
	 * @param text
	 *            the element text you want to wait for
	 */
	public boolean isTextPresent(String expectedText) {
		return isTextPresent(expectedText, 0);
	}

	/**
	 * wait for specified text within timeout setting, fail if not found.
	 * 
	 * @param expectedText
	 *            the element text you want to wait for
	 * @param timeout
	 *            ��ʱʱ�䣬��λ����
	 */
	public boolean isContentAppeared(String expectedText, long timeout) {
		boolean isSucceed = false;
		logger.debug("expectedText is : " + expectedText);

		long timeBegins = System.currentTimeMillis();
		do {
			try {
				driver.findElement(By.xpath("//*[contains(.,'" + expectedText
						+ "')]"));
				isSucceed = true;
				logger.debug("find expectedText [" + expectedText + "] success");
				break;
			} catch (Exception e) {
				logger.error(e);
			}
			pause(pauseTime);
		} while (System.currentTimeMillis() - timeBegins <= timeout * 1000);

		operationCheck("isContentAppeared", isSucceed);
		return isSucceed;
	}

	/**
	 * wait for specified text within timeout setting, fail if not found.
	 * 
	 * @param expectedText
	 *            the element text you want to wait for
	 */
	public boolean isContentAppeared(String expectedText) {
		return isContentAppeared(expectedText, 0);
	}

	/**
	 * wait for specified text within timeout setting, fail if not found.
	 * 
	 * @param text
	 *            the element text you want to wait for
	 * @param timeout
	 *            ��ʱʱ�䣬��λ����
	 */
	public boolean isTextNotPresent(String expectedText, long timeout) {
		boolean isSucceed = false;
		logger.debug("expectedText is : " + expectedText);

		long timeBegins = System.currentTimeMillis();
		do {
			try {
				if (!getPageSource().contains(expectedText)) {
					isSucceed = true;
					logger.debug("expectedText [" + expectedText
							+ "] not exists");
					break;
				}
			} catch (Exception e) {
				logger.error(e);
			}
			pause(pauseTime);
		} while (System.currentTimeMillis() - timeBegins <= timeout * 1000);

		operationCheck("isTextNotPresent", isSucceed);
		return isSucceed;
	}

	/**
	 * wait for specified text within timeout setting, fail if not found.
	 * 
	 * @param text
	 *            the element text you want to wait for
	 */
	public boolean isTextNotPresent(String expectedText) {
		return isTextNotPresent(expectedText, 0);
	}

	/**
	 * wait for specified text within timeout setting, fail if not found.
	 * 
	 * @param text
	 *            the element text you want to wait for
	 * @param timeout
	 *            ��ʱʱ�䣬��λ����
	 */
	public boolean isContentNotAppeared(String expectedText, long timeout) {
		boolean isSucceed = true;
		logger.debug("expectedText is : " + expectedText);

		long timeBegins = System.currentTimeMillis();
		do {
			try {
				driver.findElement(By.xpath("//*[contains(.,'" + expectedText
						+ "')]"));
				isSucceed = false;
				logger.debug("find expectedText [" + expectedText + "] success");
				break;
			} catch (Exception e) {
				logger.error(e);
			}
			pause(pauseTime);
		} while (System.currentTimeMillis() - timeBegins <= timeout * 1000);

		operationCheck("isContentNotAppeared", isSucceed);
		return isSucceed;
	}

	/**
	 * wait for specified text within timeout setting, fail if not found.
	 * 
	 * @param text
	 *            the element text you want to wait for
	 */
	public boolean isContentNotAppeared(String expectedText) {
		return isContentNotAppeared(expectedText, 0);
	}

	/**
	 * wait for the specified element appears with timeout setting.
	 * 
	 * @param locator
	 *            the element locator on the page
	 * @param timeout
	 *            ��ʱʱ�䣬��λ����
	 */
	public boolean isElementPresent(By locator, long timeout) {
		boolean isSucceed = false;
		logger.debug("find element [" + locator.toString() + "]");

		long timeBegins = System.currentTimeMillis();
		do {
			try {
				driver.findElement(locator);
				isSucceed = true;
				logger.debug("find element [" + locator.toString()
						+ "] success");
				break;
			} catch (Exception e) {
				logger.error(e);
			}
			pause(pauseTime);
		} while (System.currentTimeMillis() - timeBegins <= timeout * 1000);

		operationCheck("isElementPresent", isSucceed);
		return isSucceed;
	}

	/**
	 * wait for the specified element appears with timeout setting.
	 * 
	 * @param locator
	 *            the element locator on the page
	 */
	public boolean isElementPresent(By locator) {
		return isElementPresent(locator, 0);
	}

	/**
	 * wait for the specified element appears with timeout setting.
	 * 
	 * @param locator
	 *            the element locator on the page
	 * @param timeout
	 *            ��ʱʱ�䣬��λ����
	 */
	public boolean isElementNotPresent(By locator, long timeout) {
		boolean isSucceed = true;
		logger.debug("find element [" + locator.toString() + "]");

		long timeBegins = System.currentTimeMillis();
		do {
			try {
				driver.findElement(locator);
				isSucceed = false;
				logger.debug("find element [" + locator.toString()
						+ "] success");
				break;
			} catch (Exception e) {
				logger.error(e);
			}
			pause(pauseTime);
		} while (System.currentTimeMillis() - timeBegins <= timeout * 1000);

		operationCheck("isElementNotPresent", isSucceed);
		return isSucceed;
	}

	/**
	 * wait for the specified element appears with timeout setting.
	 * 
	 * @param locator
	 *            the element locator on the page
	 */
	public boolean isElementNotPresent(By locator) {
		return isElementNotPresent(locator, 0);
	}

	/**
	 * wait for the specified element appears with timeout setting.
	 * 
	 * @param locator
	 *            the element locator on the page
	 * @param timeout
	 *            ��ʱʱ�䣬��λ����
	 */
	public boolean isElementDisplayed(By locator, long timeout) {
		boolean isSucceed = false;
		logger.debug("find element [" + locator.toString() + "]");

		long timeBegins = System.currentTimeMillis();
		do {
			try {
				isSucceed = driver.findElement(locator).isDisplayed();
				logger.debug("find element [" + locator.toString()
						+ "] success");
				break;
			} catch (Exception e) {
				logger.error(e);
			}
			pause(pauseTime);
		} while (System.currentTimeMillis() - timeBegins <= timeout * 1000);

		operationCheck("isElementDisplayed", isSucceed);
		return isSucceed;
	}

	/**
	 * wait for the specified element appears with timeout setting.
	 * 
	 * @param locator
	 *            the element locator on the page
	 */
	public boolean isElementDisplayed(By locator) {
		return isElementDisplayed(locator, 0);
	}

	/**
	 * rewrite the findElements method, adding user defined log</BR>
	 * ����ָ���Ķ�λ��ʽѰ����
	 * 
	 * @param by
	 *            the locator of the elements to be find
	 * @param timeout
	 *            ��ʱʱ�䣬��λ����
	 * @return the webelements you want to find
	 */
	public List<WebElement> findElements(By by, long timeout) {
		boolean isSucceed = false;
		logger.debug("find elements [" + by.toString() + "]");
		List<WebElement> elements = null;

		long timeBegins = System.currentTimeMillis();
		do {
			try {
				elements = driver.findElements(by);
				isSucceed = true;
				logger.debug("find element [" + by.toString()
						+ "] success , find [" + elements.size() + "] element");
				break;
			} catch (Exception e) {
				logger.error(e);
			}
			pause(pauseTime);
		} while (System.currentTimeMillis() - timeBegins <= timeout * 1000);

		operationCheck("findElements", isSucceed);
		return elements;
	}

	/**
	 * rewrite the findElements method, adding user defined log</BR>
	 * ����ָ���Ķ�λ��ʽѰ����
	 * 
	 * @param by
	 *            the locator of the elements to be find
	 * @return the webelements you want to find
	 */
	public List<WebElement> findElements(By by) {
		return findElements(by, 0);
	}

	/**
	 * rewrite the findElement method, adding user defined log</BR>
	 * ����ָ���Ķ�λ��ʽѰ����
	 * 
	 * @param by
	 *            the locator of the element to be find
	 * @param timeout
	 *            ��ʱʱ�䣬��λ����
	 * @return the first element accord your locator
	 */
	public WebElement findElement(By by, long timeout) {
		boolean isSucceed = false;
		logger.debug("find element [" + by.toString() + "]");
		WebElement element = null;
		long timeBegins = System.currentTimeMillis();
		do {
			try {
				element = driver.findElement(by);
				isSucceed = true;
				logger.debug("find element [" + by.toString() + "] success");
				break;
			} catch (Exception e) {
				logger.error(e);
			}
			pause(pauseTime);
		} while (System.currentTimeMillis() - timeBegins <= timeout * 1000);

		operationCheck("findElement", isSucceed);
		return element;
	}

	/**
	 * rewrite the findElement method, adding user defined log</BR>
	 * ����ָ���Ķ�λ��ʽѰ����
	 * 
	 * @param by
	 *            the locator of the element to be find
	 * @return the first element accord your locator
	 */
	public WebElement findElement(By by) {
		return findElement(by, 0);
	}

	/**
	 * rewrite the getTitle method, adding user defined log</BR>
	 * �빤��ԭ��API������ȫһ�£�ֻ�������˲������������־��¼��
	 * 
	 * @return the title on your current session
	 */
	public String getWindowTitle() {
		String title = driver.getTitle();
		logger.debug("current window title is :" + title);
		return title;
	}

	/**
	 * rewrite the getCurrentUrl method, adding user defined log</BR>
	 * �빤��ԭ��API������ȫһ�£�ֻ�������˲������������־��¼��
	 * 
	 * @return the url on your current session
	 */
	public String getCurrentUrl() {
		String url = driver.getCurrentUrl();
		logger.debug("current page url is :" + url);
		return url;
	}

	/**
	 * rewrite the getWindowHandles method, adding user defined log</BR>
	 * �빤��ԭ��API������ȫһ�£�ֻ�������˲������������־��¼��
	 * 
	 * @return the window handles set
	 */
	public Set<String> getWindowHandles() {
		Set<String> handles = driver.getWindowHandles();
		logger.debug("window handles count are:" + handles.size());
		logger.debug("window handles are: " + handles.toString());
		return handles;
	}

	/**
	 * rewrite the getWindowHandle method, adding user defined log</BR>
	 * �빤��ԭ��API������ȫһ�£�ֻ�������˲������������־��¼��
	 * 
	 * @return the window handle string
	 */
	public String getWindowHandle() {
		String handle = driver.getWindowHandle();
		logger.debug("current window handle is:" + handle);
		return handle;
	}

	/**
	 * rewrite the getPageSource method, adding user defined log</BR>
	 * �빤��ԭ��API������ȫһ�£�ֻ�������˲������������־��¼��
	 * 
	 * @return the page source string
	 */
	public String getPageSource() {
		//getPageSource���ҳ��Դ��
		String source = driver.getPageSource();
		// logger.debug("get PageSource : [ " + source + " ]");
		return source;
	}

	/**
	 * rewrite the getSessionId method, adding user defined log</BR>
	 * �빤��ԭ��API������ȫһ�£�ֻ�������˲������������־��¼��
	 * 
	 * @return current session id string
	 */
	public String getSessionId() {
		String sessionId = ((RemoteWebDriver) driver).getSessionId().toString();
		logger.debug("current sessionid is:" + sessionId);
		return sessionId;
	}

	/**
	 * rewrite the getSessionId method, adding user defined log</BR>
	 * �빤��ԭ��API������ȫһ�£�ֻ�������˲������������־��¼��
	 * 
	 * @return cookieValue
	 */
	public String getCookieNamed(String cookieName) {
		String cookieValue = driver.manage().getCookieNamed(cookieName)
				.getValue();
		logger.debug("cooike [" + cookieName + "] value is [" + cookieValue
				+ "]");
		return cookieValue;
	}

	/**
	 * rewrite the getTagName method, adding user defined log</BR>
	 * �빤��ԭ��API������ȫһ�£�ֻ�������˲������������־��¼��
	 * 
	 * @param element
	 *            the webelement you want to operate
	 * @return the tagname string
	 */
	public String getTagName(WebElement element) {
		String tagName = element.getTagName();
		logger.debug("element's TagName is: " + tagName);
		return tagName;
	}

	/**
	 * rewrite the getTagName method, find the element by By and get its tag
	 * name</BR> �빤��ԭ��API������ȫһ�£�ֻ�������˲������������־��¼��
	 * 
	 * @param by
	 *            the locator you want to find the element
	 * @return the tagname string
	 */
	public String getTagName(By by) {
		String tagName = driver.findElement(by).getTagName();
		logger.debug("element [ " + by.toString() + " ]'s TagName is: "
				+ tagName);
		return tagName;
	}

	/**
	 * rewrite the getAttribute method, adding user defined log</BR>
	 * �빤��ԭ��API������ȫһ�£�ֻ�������˲������������־��¼��
	 * 
	 * @param element
	 *            the webelement you want to operate
	 * @param attributeName
	 *            the name of the attribute you want to get
	 * @return the attribute value string
	 */
	public String getAttribute(WebElement element, String attributeName) {
		String value = element.getAttribute(attributeName);
		logger.debug("element's " + attributeName + "is: " + value);
		return value;
	}

	/**
	 * rewrite the getAttribute method, find the element by By and get its
	 * attribute value</BR> �빤��ԭ��API������ȫһ�£�ֻ�������˲������������־��¼��
	 * 
	 * @param by
	 *            the locator you want to find the element
	 * @param attributeName
	 *            the name of the attribute you want to get
	 * @return the attribute value string
	 */
	public String getAttribute(By by, String attributeName) {
		String value = driver.findElement(by).getAttribute(attributeName);
		logger.debug("element [ " + by.toString() + " ]'s " + attributeName
				+ "is: " + value);
		return value;
	}

	/**
	 * rewrite the clear method, adding user defined log</BR>
	 * �빤��ԭ��API������ȫһ�£�ֻ�������˲������������־��¼��
	 * 
	 * @param element
	 *            the webelement you want to operate
	 */
	public void clear(WebElement element) {
		element.clear();
		logger.debug("element [ " + element + " ] cleared");
	}

	/**
	 * rewrite the click method, adding user defined log</BR>
	 * �빤��ԭ��API������ȫһ�£�ֻ�������˲������������־��¼��
	 * 
	 * @param element
	 *            the webelement you want to operate
	 */
	public void click(WebElement element) {
		element.click();
		logger.debug("click on element [ " + element + " ] ");
	}

	/**
	 * rewrite the sendKeys method, adding user defined log</BR>
	 * �빤��ԭ��API������ȫһ�£�ֻ�������˲������������־��¼��
	 * 
	 * @param element
	 *            the webelement you want to operate
	 */
	public void sendKeys(WebElement element, String text) {
		element.sendKeys(text);
		logger.debug("input text [ " + text + " ] to element [ " + element
				+ " ]");
	}

	/**
	 * rewrite the isSelected method, adding user defined log</BR>
	 * �빤��ԭ��API������ȫһ�£�ֻ�������˲������������־��¼��
	 * 
	 * @param element
	 *            the webelement you want to operate
	 * @return the bool value of whether is the WebElement selected
	 */
	public boolean isSelected(WebElement element) {
		boolean isSelected = element.isSelected();
		logger.debug("element selected? " + String.valueOf(isSelected));
		return isSelected;
	}

	/**
	 * rewrite the isSelected method, the element to be find by By</BR>
	 * �빤��ԭ��API������ȫһ�£�ֻ�������˲������������־��¼��
	 * 
	 * @param by
	 *            the locator you want to find the element
	 * @return the bool value of whether is the WebElement selected
	 */
	public boolean isSelected(By by) {
		boolean isSelected = driver.findElement(by).isSelected();
		logger.debug("element [ " + by.toString() + " ] selected? "
				+ String.valueOf(isSelected));
		return isSelected;
	}

	/**
	 * rewrite the isEnabled method, adding user defined log</BR>
	 * �빤��ԭ��API������ȫһ�£�ֻ�������˲������������־��¼��
	 * 
	 * @param element
	 *            the webelement you want to operate
	 * @return the bool value of whether is the WebElement enabled
	 */
	public boolean isEnabled(WebElement element) {
		boolean isEnabled = element.isEnabled();
		logger.debug("element enabled? " + String.valueOf(isEnabled));
		return isEnabled;
	}

	/**
	 * rewrite the isEnabled method, the element to be find by By</BR>
	 * �빤��ԭ��API������ȫһ�£�ֻ�������˲������������־��¼��
	 * 
	 * @param by
	 *            the locator you want to find the element
	 * @return the bool value of whether is the WebElement enabled
	 */
	public boolean isEnabled(By by) {
		boolean isEnabled = driver.findElement(by).isEnabled();
		logger.debug("element [ " + by.toString() + " ] enabled? "
				+ String.valueOf(isEnabled));
		return isEnabled;
	}

	/**
	 * rewrite the getText method, adding user defined log</BR>
	 * �빤��ԭ��API������ȫһ�£�ֻ�������˲������������־��¼��
	 * 
	 * @param element
	 *            the webelement you want to operate
	 */
	public String getText(WebElement element) {
		String text = element.getText();
		logger.debug("element text is:" + text);
		return text;
	}

	/**
	 * rewrite the getText method, find the element by By and get its own
	 * text</BR> �빤��ԭ��API������ȫһ�£�ֻ�������˲������������־��¼��
	 * 
	 * @param by
	 *            the locator you want to find the element
	 * @return the text string
	 */
	public String getText(By by) {
		String text = driver.findElement(by).getText();
		logger.debug("element [ " + by.toString() + " ]'s text is: " + text);
		return text;
	}

	/**
	 * rewrite the isDisplayed method, adding user defined log</BR>
	 * �빤��ԭ��API������ȫһ�£�ֻ�������˲������������־��¼��
	 * 
	 * @param element
	 *            the webelement you want to operate
	 * @return the bool value of whether is the WebElement displayed
	 */
	protected boolean isDisplayed(WebElement element) {
		boolean isDisplayed = element.isDisplayed();
		logger.debug("element displayed? " + String.valueOf(isDisplayed));
		return isDisplayed;
	}

	/**
	 * rewrite the isDisplayed method, the element to be find by By</BR>
	 * �빤��ԭ��API������ȫһ�£�ֻ�������˲������������־��¼��
	 * 
	 * @param by
	 *            the locator you want to find the element
	 * @return the bool value of whether is the WebElement displayed
	 */
	public boolean isDisplayed(By by) {
		boolean isDisplayed = driver.findElement(by).isDisplayed();
		logger.debug("element [ " + by.toString() + " ] displayed? "
				+ String.valueOf(isDisplayed));
		return isDisplayed;
	}

	/**
	 * get its css property value</BR> �빤��ԭ��API������ȫһ�£�ֻ�������˲������������־��¼��
	 * 
	 * @param element
	 *            the webelement you want to operate
	 * @param propertyName
	 *            the name of the property you want to get
	 * @return the css property value string
	 */
	public String getCssValue(WebElement element, String propertyName) {
		String cssValue = element.getCssValue(propertyName);
		logger.debug("element's css [" + propertyName + "] value is:"
				+ cssValue);
		return cssValue;
	}

	/**
	 * find the element by By and get its css property value</BR>
	 * �빤��ԭ��API������ȫһ�£�ֻ�������˲������������־��¼��
	 * 
	 * @param by
	 *            the locator you want to find the element
	 * @param propertyName
	 *            the name of the property you want to get
	 * @return the css property value string
	 */
	public String getCssValue(By by, String propertyName) {
		String cssValue = driver.findElement(by).getCssValue(propertyName);
		logger.debug("element [ " + by.toString() + " ]'s css[" + propertyName
				+ "] value is: " + cssValue);
		return cssValue;
	}

	/**
	 * construct with parameters initialize.
	 * 
	 * @param driver
	 *            the WebDriver instance.
	 * @param tabFinder
	 *            the By locator of the table.
	 * @param bodyOrHead
	 *            choice of table body and head to operate.
	 */
	public WebDriverTable getTable(By tabFinder, String bodyOrHead) {
		return new WebDriverTable(driver, tabFinder, bodyOrHead);
	}

	/**
	 * construct with parameters initialize.
	 * 
	 * @param driver
	 *            the WebDriver instance.
	 * @param tabFinder
	 *            the By locator of the table.
	 */
	public WebDriverTable getTable(By tabFinder) {
		return new WebDriverTable(driver, tabFinder);
	}

	/**
	 * judge if the browser is existing, using part of the page title</BR>
	 * ������ҳ�����ж�ҳ���Ƿ���ڣ������ʹ�ò�������ƥ�䡣
	 * 
	 * @param browserTitle
	 *            part of the title to see if browser exists
	 */
	public boolean browserExists(String browserTitle) {
		try {
			String defaultHandle = driver.getWindowHandle();
			Set<String> windowHandles = driver.getWindowHandles();
			windowHandles = clearHandleCache(windowHandles);
			for (int i = 0; i <= 20; i++) {
				pause(500);
				if (driver.getWindowHandles().equals(windowHandles)) {
					break;
				}
				if (i == 20 && !driver.getWindowHandles().equals(windowHandles)) {
					return false;
				}
			}
			for (String handle : driver.getWindowHandles()) {
				driver.switchTo().window(handle);
				if (driver.getTitle().contains(browserTitle)) {
					driver.switchTo().window(defaultHandle);
					return true;
				}
			}
			driver.switchTo().window(defaultHandle);
		} catch (Exception e) {
			logger.error(e);
		}
		return false;
	}

	/**
	 * judge if the browser is present by title reg pattern in specified
	 * seconds</BR> ��ָ��ʱ���ڰ�����ҳ�����ж�ҳ���Ƿ���ڣ������ʹ�ò�������ƥ�䡣
	 * 
	 * @param browserTitle
	 *            part of the title to see if browser exists
	 * @param timeout
	 *            timeout in seconds
	 */
	public boolean isBrowserExists(String browserTitle, int timeout) {
		boolean isSucceed = false;
		long timeBegins = System.currentTimeMillis();
		do {
			try {
				isSucceed = browserExists(browserTitle);
				break;
			} catch (Exception e) {
				logger.error(e);
			}
		} while (System.currentTimeMillis() - timeBegins <= timeout * 1000);
		operationCheck("browserExists", isSucceed);
		return isSucceed;
	}

	/**
	 * judge if the browser is present by title reg pattern in specified
	 * seconds</BR> ��ָ��ʱ���ڰ�����ҳ�����ж�ҳ���Ƿ���ڣ������ʹ�ò�������ƥ�䡣
	 * 
	 * @param browserTitle
	 *            part of the title to see if browser exists
	 */
	public boolean isBrowserExists(String browserTitle) {
		return isBrowserExists(browserTitle, 0);
	}

	/**
	 * maximize browser window</BR> ��ҳ������󻯲�����
	 */
	public void windowMaximize() {
		driver.manage().window().maximize();
	}

	/**
	 * switch to new window supporting, by deleting first hanlder</BR>
	 * ѡ�����µ����Ĵ��ڣ���ҪԤ���һ�����ڵ�handle</BR>�� ������ֻ�������������ڵ����
	 * 
	 * @param firstHandle
	 *            the first window handle
	 */
	public void selectNewWindow(String firstHandle) {
		boolean isSucceed = false;
		try {
			Set<String> handles = null;
			//��������Iterator��ʹ��hasNext()����������Ƿ���Ԫ�ء�
			Iterator<String> it = null;
			handles = driver.getWindowHandles();
			handles.remove(firstHandle);
			it = handles.iterator();
			while (it.hasNext()) {
				driver.switchTo().window(it.next());
			}
			selectDefaultFrame();
			isSucceed = true;
		} catch (Exception e) {
			logger.error(e);
		}
		operationCheck("selectNewWindow", isSucceed);
	}

	/**
	 * switch to new window supporting, by deleting original hanlde</BR>
	 * ѡ�����µ����Ĵ��ڣ���ҪԤ���������д��ڵ�handles</BR>�� �����������������ϵ������ڵ����
	 * 
	 * @param originalHandles
	 *            the old window handles
	 */
	public void selectNewWindow(Set<String> originalHandles) {
		boolean isSucceed = false;
		try {
			Set<String> newHandles = driver.getWindowHandles();
			Iterator<String> olds = originalHandles.iterator();
			while (olds.hasNext()) {
				newHandles.remove(olds.next());
			}
			Iterator<String> news = newHandles.iterator();
			while (news.hasNext()) {
				driver.switchTo().window(news.next());
			}
			selectDefaultFrame();
			isSucceed = true;
		} catch (Exception e) {
			logger.error(e);
		}
		operationCheck("selectNewWindow", isSucceed);
	}

	/**
	 * switch to window by title</BR> ������ҳ����ѡ�񴰿ڣ�����������Ҫȫ��ƥ�䡣
	 * 
	 * @param windowTitle
	 *            the title of the window to be switched to
	 */
	public void selectWindow(String windowTitle) {
		boolean isSucceed = false;
		try {
			Set<String> windowHandles = driver.getWindowHandles();
			windowHandles = clearHandleCache(windowHandles);
			for (String handle : windowHandles) {
				driver.switchTo().window(handle);
				String title = driver.getTitle();
				if (windowTitle.equals(title)) {
					selectDefaultFrame();
					isSucceed = true;
					break;
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
		operationCheck("selectWindow", isSucceed);
	}

	/**
	 * switch to window by handle</BR> ����ָ�����ѡ�񴰿ڡ�
	 * 
	 * @param windowHandle
	 *            the handle of the window to be switched to
	 */
	public void selectWindowHandle(String windowHandle) {
		boolean isSucceed = false;
		try {
			Set<String> windowHandles = driver.getWindowHandles();
			windowHandles = clearHandleCache(windowHandles);
			for (String handle : windowHandles) {
				if (windowHandle.equals(handle)) {
					driver.switchTo().window(handle);
					selectDefaultFrame();
					isSucceed = true;
					break;
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
		operationCheck("selectWindowHandle", isSucceed);
	}

	/**
	 * switch to window by title</BR> ������ҳ����ѡ�񴰿ڣ�����������Ҫȫ��ƥ�䣬��ʱδ�����򱨴�
	 * 
	 * @param windowTitle
	 *            the title of the window to be switched to.
	 * @param timeout
	 *            time to wait for the window appears, unit of seconds.
	 */
	public void selectWindowWithTimeout(String windowTitle, int timeout) {
		if (isBrowserExists(windowTitle, timeout)) {
			selectWindow(windowTitle);
		}
	}

	/**
	 * switch to parent window when child was closed unexpectly.</BR>
	 * �ڴ򿪵��Ӵ��ڱ����⣨�������ǹ���Ԥ�ڵ���Ϊ���ر�֮���л��ظ����ڡ�
	 * 
	 * @param handles
	 *            handles set when child windows are still alive.
	 * @param childHandle
	 *            child window whitch to be closed.
	 * @param parentHandle
	 *            the parent handle of windows.
	 */
	public void selectParentWindow(Set<String> handles, String childHandle,
			String parentHandle) {
		if (!handles.toString().contains(childHandle)
				|| !handles.toString().contains(parentHandle)) {
			logger.debug("current all opened windows" + handles.toString()
					+ "do not contains the parentWindow" + parentHandle
					+ "and the childHandle" + childHandle);
		} else {
			handles.remove(childHandle);
			driver.switchTo().window(parentHandle);
		}
	}

	/**
	 * Description: switch to a window handle that exists now.</BR>
	 * �л���һ�����ھ��������˵��ǰ�����ڵģ��Ĵ��ڡ�
	 */
	public void selectExistWindow() {
		Set<String> windowHandles = driver.getWindowHandles();
		windowHandles = clearHandleCache(windowHandles);
		String exist_0 = windowHandles.toArray()[0].toString();
		if (!(exist_0 == null) && (!"".equalsIgnoreCase(exist_0))) {
			driver.switchTo().window(exist_0);
		} else {
			logger.debug("no opened windows!");
		}
	}

	/**
	 * close window by window title and its index if has the same title, by
	 * string full pattern</BR> ������ҳ����ѡ���ҹرմ��ڣ��������ڰ���ָ������������Źر�
	 * </BR>������������title�Ĵ��ڣ�����������Ҫȫ��ƥ�䡣
	 * 
	 * @param windowTitle
	 *            the title of the window to be closed.
	 * @param index
	 *            the index of the window which shared the same title, begins
	 *            with 1.
	 */
	public void closeWindow(String windowTitle, int index) {
		boolean isSucceed = false;
		try {
			List<String> winList = new ArrayList<String>();
			Set<String> windowHandles = driver.getWindowHandles();
			windowHandles = clearHandleCache(windowHandles);
			for (String handle : windowHandles) {
				driver.switchTo().window(handle);
				if (windowTitle.equals(driver.getTitle())) {
					winList.add(handle);
				}
			}
			driver.switchTo().window(winList.get(index - 1));
			driver.close();
			logger.debug("window [ " + windowTitle + " ] closed by index ["
					+ index + "]");
			isSucceed = true;
		} catch (Exception e) {
			logger.error(e);
		}
		operationCheck("closeWindow", isSucceed);
	}

	/**
	 * close the last window by the same window title, by string full
	 * pattern</BR> ������ҳ����ѡ�񴰿ڣ�������������title�Ĵ��ڣ�����������Ҫȫ��ƥ�䡣
	 * 
	 * @param windowTitle
	 *            the title of the window to be closed.
	 */
	public void closeWindow(String windowTitle) {
		boolean isSucceed = false;
		try {
			Set<String> windowHandles = driver.getWindowHandles();
			windowHandles = clearHandleCache(windowHandles);
			for (String handle : windowHandles) {
				driver.switchTo().window(handle);
				if (windowTitle.equals(driver.getTitle())) {
					driver.close();
					break;
				}
			}
			logger.debug("window [ " + windowTitle + " ] closed ");
			isSucceed = true;
		} catch (Exception e) {
			logger.error(e);
		}
		operationCheck("closeWindow", isSucceed);
	}

	/**
	 * close windows except specified window title, by string full pattern</BR>
	 * �رճ���ָ������ҳ��֮������д��ڣ�������������title�Ĵ��� </BR>����ָ��������˳��رգ�����������Ҫȫ��ƥ�䡣
	 * 
	 * @param windowTitle
	 *            the title of the window not to be closed
	 * @param index
	 *            the index of the window to keep shared the same title with
	 *            others, begins with 1.
	 */
	public void closeWindowExcept(String windowTitle, int index) {
		boolean isSucceed = false;
		try {
			Set<String> windowHandles = driver.getWindowHandles();
			windowHandles = clearHandleCache(windowHandles);
			for (String handle : windowHandles) {
				driver.switchTo().window(handle);
				String title = driver.getTitle();
				if (!windowTitle.equals(title)) {
					driver.switchTo().defaultContent();
					driver.close();
				}
			}

			Object[] winArray = driver.getWindowHandles().toArray();
			winArray = driver.getWindowHandles().toArray();
			for (int i = 0; i < winArray.length; i++) {
				if (i + 1 != index) {
					driver.switchTo().defaultContent();
					driver.close();
				}
			}
			logger.debug("keep only window [ " + windowTitle
					+ " ] by title index [ " + index + " ]");
			isSucceed = true;
		} catch (Exception e) {
			logger.error(e);
		}
		operationCheck("closeWindowExcept", isSucceed);

	}

	/**
	 * close windows except specified window title, by string full pattern</BR>
	 * �رճ���ָ������ҳ��֮������д��ڣ����������ⴰ��������title�����������������Ҫȫ��ƥ�䡣
	 * 
	 * @param windowTitle
	 *            the title of the window not to be closed
	 */
	public void closeWindowExcept(String windowTitle) {
		boolean isSucceed = false;
		try {
			Set<String> windowHandles = driver.getWindowHandles();
			windowHandles = clearHandleCache(windowHandles);
			for (String handle : windowHandles) {
				driver.switchTo().window(handle);
				String title = driver.getTitle();
				if (!windowTitle.equals(title)) {
					driver.close();
				}
			}
			logger.debug("all windows closed except [ " + windowTitle + " ]");
			isSucceed = true;
		} catch (Exception e) {
			logger.error(e);
		}
		operationCheck("closeWindowExcept", isSucceed);
	}

	/**
	 * close window by specified window hanlde, by string full pattern</BR>
	 * �ر�ָ������Ĵ��ڡ�
	 * 
	 * @param windowHandle
	 *            the hanlde of the window to be closed.
	 */
	public void closeWindowHandle(String windowHandle) {
		boolean isSucceed = false;
		try {
			Set<String> windowHandles = driver.getWindowHandles();
			windowHandles = clearHandleCache(windowHandles);
			for (String handle : windowHandles) {
				if (windowHandle.equals(handle)) {
					driver.switchTo().window(handle);
					driver.close();
					break;
				}
			}
			logger.debug("window [ " + windowHandle + " ] closed ");
			isSucceed = true;
		} catch (Exception e) {
			logger.error(e);
		}
		operationCheck("closeWindowHandle", isSucceed);
	}

	/**
	 * close windows except specified window hanlde, by string full pattern</BR>
	 * �رճ���ָ�����֮������д��ڡ�
	 * 
	 * @param windowHandle
	 *            the hanlde of the window not to be closed.
	 */
	public void closeWindowExceptHandle(String windowHandle) {
		boolean isSucceed = false;
		try {
			Set<String> windowHandles = driver.getWindowHandles();
			windowHandles = clearHandleCache(windowHandles);
			for (String handle : windowHandles) {
				if (!windowHandle.equals(handle)) {
					driver.switchTo().window(handle);
					driver.close();
				}
			}
			logger.debug("all windows closed except handle [ " + windowHandle
					+ " ]");
			isSucceed = true;
		} catch (Exception e) {
			logger.error(e);
		}
		operationCheck("closeWindowExceptHandle", isSucceed);
	}

	/**
	 * Description: clear error handles does not actruely.</BR>
	 * �����ʵ���ϲ������ڵĴ��ھ�����档
	 * 
	 * @param windowHandles
	 *            the window handles Set.
	 */
	private Set<String> clearHandleCache(Set<String> windowHandles) {
		List<String> errors = new ArrayList<String>();
		for (String handle : windowHandles) {
			try {
				driver.switchTo().window(handle);
				driver.getTitle();
			} catch (Exception e) {
				errors.add(handle);
				logger.debug("window handle " + handle
						+ " does not exist acturely!");
			}
		}
		for (int i = 0; i < errors.size(); i++) {
			windowHandles.remove(errors.get(i));
		}
		return windowHandles;
	}

	/**
	 * select default window and default frame</BR> �ڵ�ǰҳ�����Զ�ѡ��Ĭ�ϵ�ҳ���ܣ�frame����
	 */
	public void selectDefaultFrame() {
		driver.switchTo().defaultContent();
	}

	/**
	 * select a frame by index</BR> �������ѡ���ܣ�frame����
	 * 
	 * @param index
	 *            the index of the frame to select
	 * @param timeout
	 *            ��ʱʱ�䣬��λ����
	 */
	public void selectFrame(int index, long timeout) {
		boolean isSucceed = false;
		long timeBegins = System.currentTimeMillis();
		do {
			try {
				selectDefaultFrame();
				driver.switchTo().frame(index);
				logger.debug("select frame by index [ " + index + " ]");
				isSucceed = true;
				break;
			} catch (Exception e) {
				logger.error(e);
			}
			pause(pauseTime);
		} while (System.currentTimeMillis() - timeBegins <= timeout * 1000);
		operationCheck("selectFrame", isSucceed);
	}

	/**
	 * select a frame by index</BR> �������ѡ���ܣ�frame����
	 * 
	 * @param index
	 *            the index of the frame to select
	 */
	public void selectFrame(int index) {
		selectFrame(index, 0);
	}

	/**
	 * select a frame by name or id</BR> �������ƻ���IDѡ���ܣ�frame����
	 * 
	 * @param nameOrId
	 *            the name or id of the frame to select
	 * @param timeout
	 *            ��ʱʱ�䣬��λ����
	 */
	public void selectFrame(String nameOrId, long timeout) {
		boolean isSucceed = false;
		long timeBegins = System.currentTimeMillis();
		do {
			try {
				selectDefaultFrame();
				driver.switchTo().frame(nameOrId);
				logger.debug("select frame by name or id [ " + nameOrId + " ]");
				isSucceed = true;
				break;
			} catch (Exception e) {
				logger.error(e);
			}
			pause(pauseTime);
		} while (System.currentTimeMillis() - timeBegins <= timeout * 1000);
		operationCheck("selectFrame", isSucceed);
	}

	/**
	 * select a frame by name or id</BR> �������ƻ���IDѡ���ܣ�frame����
	 * 
	 * @param nameOrId
	 *            the name or id of the frame to select
	 */
	public void selectFrame(String nameOrId) {
		selectFrame(nameOrId, 0);
	}

	/**
	 * select a frame by frame element locator: By</BR> ����ָ����Ԫ�ض�λ��ʽѡ���ܣ�frame����
	 * 
	 * @param by
	 *            the frame element locator
	 * @param timeout
	 *            ��ʱʱ�䣬��λ����
	 */
	public void selectFrame(By by, long timeout) {
		boolean isSucceed = false;
		long timeBegins = System.currentTimeMillis();
		do {
			try {
				selectDefaultFrame();
				driver.switchTo().frame(driver.findElement(by));
				logger.debug("select frame by frame locator [ " + by.toString()
						+ " ]");
				isSucceed = true;
				break;
			} catch (Exception e) {
				logger.error(e);
			}
			pause(pauseTime);
		} while (System.currentTimeMillis() - timeBegins <= timeout * 1000);
		operationCheck("selectFrame", isSucceed);
	}

	/**
	 * select a frame by frame element locator: By</BR> ����ָ����Ԫ�ض�λ��ʽѡ���ܣ�frame����
	 * 
	 * @param by
	 *            the frame element locator
	 */
	public void selectFrame(By by) {
		selectFrame(by, 0);
	}

	/**
	 * select a frame by frame tagName: tagName</BR> ����ָ����Ԫ�ض�λ��ʽѡ���ܣ�frame����
	 * 
	 * @param tagName
	 *            the frame tagName,such as : frame or iframe
	 * @param index
	 *            the frame index,begin with 0
	 * @param timeout
	 *            ��ʱʱ�䣬��λ����
	 */
	public void selectFrame(String tagName, int index, long timeout) {
		boolean isSucceed = false;
		long timeBegins = System.currentTimeMillis();
		do {
			try {
				List<WebElement> frames = driver.findElements(By
						.tagName(tagName));
				selectDefaultFrame();
				driver.switchTo().frame(frames.get(index));
				logger.debug("select frame by tagName [ " + tagName + " ]");
				isSucceed = true;
				break;
			} catch (Exception e) {
				logger.error(e);
			}
			pause(pauseTime);
		} while (System.currentTimeMillis() - timeBegins <= timeout * 1000);
		operationCheck("selectFrame", isSucceed);
	}

	/**
	 * select a frame by frame tagName: tagName</BR> ����ָ����Ԫ�ض�λ��ʽѡ���ܣ�frame����
	 * 
	 * @param by
	 *            the frame element locator
	 * @param index
	 *            the frame index,begin with 0
	 */
	public void selectFrame(String tagName, int index) {
		selectFrame(tagName, index, 0);
	}

	/**
	 * select a frame by index</BR> �������ѡ���ܣ�frame����
	 * 
	 * @param index
	 *            the index of the frame to select
	 * @param timeout
	 *            ��ʱʱ�䣬��λ����
	 */
	public void selectFrameNoDefault(int index, long timeout) {
		boolean isSucceed = false;
		long timeBegins = System.currentTimeMillis();
		do {
			try {
				driver.switchTo().frame(index);
				logger.debug("select frame by index [ " + index + " ]");
				isSucceed = true;
				break;
			} catch (Exception e) {
				logger.error(e);
			}
			pause(pauseTime);
		} while (System.currentTimeMillis() - timeBegins <= timeout * 1000);
		operationCheck("selectFrameNoDefault", isSucceed);
	}

	/**
	 * select a frame by index</BR> �������ѡ���ܣ�frame����
	 * 
	 * @param index
	 *            the index of the frame to select
	 */
	public void selectFrameNoDefault(int index) {
		selectFrameNoDefault(index, 0);
	}

	/**
	 * select a frame by name or id</BR> �������ƻ���IDѡ���ܣ�frame����
	 * 
	 * @param nameOrId
	 *            the name or id of the frame to select
	 * @param timeout
	 *            ��ʱʱ�䣬��λ����
	 */
	public void selectFrameNoDefault(String nameOrId, long timeout) {
		boolean isSucceed = false;
		long timeBegins = System.currentTimeMillis();
		do {
			try {
				driver.switchTo().frame(nameOrId);
				logger.debug("select frame by name or id [ " + nameOrId + " ]");
				isSucceed = true;
				break;
			} catch (Exception e) {
				logger.error(e);
			}
			pause(pauseTime);
		} while (System.currentTimeMillis() - timeBegins <= timeout * 1000);
		operationCheck("selectFrameNoDefault", isSucceed);
	}

	/**
	 * select a frame by name or id</BR> �������ƻ���IDѡ���ܣ�frame����
	 * 
	 * @param nameOrId
	 *            the name or id of the frame to select
	 */
	public void selectFrameNoDefault(String nameOrId) {
		selectFrameNoDefault(nameOrId, 0);
	}

	/**
	 * select a frame by frame element locator: By</BR> ����ָ����Ԫ�ض�λ��ʽѡ���ܣ�frame����
	 * 
	 * @param by
	 *            the frame element locator
	 * @param timeout
	 *            ��ʱʱ�䣬��λ����
	 */
	public void selectFrameNoDefault(By by, long timeout) {
		boolean isSucceed = false;
		long timeBegins = System.currentTimeMillis();
		do {
			try {
				driver.switchTo().frame(driver.findElement(by));
				logger.debug("select frame by frame locator [ " + by.toString()
						+ " ]");
				isSucceed = true;
				break;
			} catch (Exception e) {
				logger.error(e);
			}
			pause(pauseTime);
		} while (System.currentTimeMillis() - timeBegins <= timeout * 1000);
		operationCheck("selectFrameNoDefault", isSucceed);
	}

	/**
	 * select a frame by frame element locator: By</BR> ����ָ����Ԫ�ض�λ��ʽѡ���ܣ�frame����
	 * 
	 * @param by
	 *            the frame element locator
	 */
	public void selectFrameNoDefault(By by) {
		selectFrameNoDefault(by, 0);
	}

	/**
	 * select a frame by frame tagName: tagName</BR> ����ָ����Ԫ�ض�λ��ʽѡ���ܣ�frame����
	 * 
	 * @param tagName
	 *            the frame tagName,such as : frame or iframe
	 * @param index
	 *            the frame index,begin with 0
	 * @param timeout
	 *            ��ʱʱ�䣬��λ����
	 */
	public void selectFrameNoDefault(String tagName, int index, long timeout) {
		boolean isSucceed = false;
		long timeBegins = System.currentTimeMillis();
		do {
			try {
				List<WebElement> frames = driver.findElements(By
						.tagName(tagName));
				driver.switchTo().frame(frames.get(index));
				logger.debug("select frame by tagName [ " + tagName + " ]");
				isSucceed = true;
				break;
			} catch (Exception e) {
				logger.error(e);
			}
			pause(pauseTime);
		} while (System.currentTimeMillis() - timeBegins <= timeout * 1000);
		operationCheck("selectFrameNoDefault", isSucceed);
	}

	/**
	 * select a frame by frame tagName: tagName</BR> ����ָ����Ԫ�ض�λ��ʽѡ���ܣ�frame����
	 * 
	 * @param by
	 *            the frame element locator
	 * @param index
	 *            the frame index,begin with 0
	 */
	public void selectFrameNoDefault(String tagName, int index) {
		selectFrame(tagName, index, 0);
	}

	/**
	 * edit a content editable iframe</BR> �༭ָ����ܣ�frame���ڵ���ֱ��չʾ�ı����ݡ�
	 * 
	 * @param index
	 *            the index of the frame to select
	 * @param text
	 *            the text string to be input
	 */
	protected void editFrameText(int index, String text) {
		boolean isSucceed = false;
		try {
			driver.switchTo().frame(index);
			driver.switchTo().activeElement().sendKeys(text);
			logger.debug("input text [ " + text + " ] to frame by index [ "
					+ index + " ]");
			isSucceed = true;
		} catch (Exception e) {
			logger.error(e);
		}
		operationCheck("editFrameText", isSucceed);
	}

	/**
	 * edit a content editable iframe</BR> �༭ָ����ܣ�frame���ڵ���ֱ��չʾ�ı����ݡ�
	 * 
	 * @param nameOrId
	 *            the name or id of the frame to select
	 * @param text
	 *            the text string to be input
	 */
	protected void editFrameText(String nameOrId, String text) {
		boolean isSucceed = false;
		try {
			driver.switchTo().frame(nameOrId);
			driver.switchTo().activeElement().sendKeys(text);
			logger.debug("input text [ " + text + " ] to frame [ " + nameOrId
					+ " ]");
			isSucceed = true;
		} catch (Exception e) {
			logger.error(e);
		}
		operationCheck("editFrameText", isSucceed);
	}

	/**
	 * edit a content editable iframe</BR> �༭ָ����ܣ�frame���ڵ���ֱ��չʾ�ı����ݡ�
	 * 
	 * @param by
	 *            the frame element locaotr
	 * @param text
	 *            the text string to be input
	 */
	protected void editFrameText(By by, String text) {
		boolean isSucceed = false;
		try {
			driver.switchTo().frame(driver.findElement(by));
			driver.switchTo().activeElement().sendKeys(text);
			logger.debug("input text [ " + text + " ] to frame [ "
					+ by.toString() + " ]");
			isSucceed = true;
		} catch (Exception e) {
			logger.error(e);
		}
		operationCheck("editFrameText", isSucceed);
	}

	/**
	 * rewrite the click method, click on the element to be find by By</BR>
	 * �ڵȵ�����ɼ�֮����ָ���Ķ���
	 * 
	 * @param by
	 *            the locator you want to find the element
	 * @param timeout
	 *            ��ʱʱ�䣬��λ����
	 */
	public void click(By by, long timeout) {
		boolean isSucceed = false;
		try {
			if (isElementPresent(by, timeout)) {
				driver.findElement(by).click();
				logger.debug("click on element [ " + by.toString() + " ] ");
				isSucceed = true;
			}
		} catch (Exception e) {
			logger.error(e);
		}
		operationCheck("click", isSucceed);
	}

	/**
	 * rewrite the click method, click on the element to be find by By</BR>
	 * �ڵȵ�����ɼ�֮����ָ���Ķ���
	 * 
	 * @param by
	 *            the locator you want to find the element
	 */
	public void click(By by) {
		click(by, 0);
	}

	/**
	 * doubleclick on the element</BR> �ڵȵ�����ɼ�֮��˫��ָ���Ķ���.
	 * 
	 * @param by
	 *            the locator you want to find the element
	 * @param timeout
	 *            ��ʱʱ�䣬��λ����
	 */
	public void doubleClick(By by, long timeout) {
		boolean isSucceed = false;
		try {
			if (isElementPresent(by, timeout)) {
				actionDriver.doubleClick(driver.findElement(by));
				actionDriver.perform();
				logger.debug("doubleClick on element [ " + by.toString()
						+ " ] ");
				isSucceed = true;
			}
		} catch (Exception e) {
			logger.error(e);
		}
		operationCheck("doubleClick", isSucceed);
	}

	/**
	 * doubleclick on the element</BR> �ڵȵ�����ɼ�֮��˫��ָ���Ķ���.
	 * 
	 * @param by
	 *            the locator you want to find the element
	 */
	public void doubleClick(By by) {
		doubleClick(by, 0);
	}

	/**
	 * moveToElement</BR> �ڵȵ�����ɼ�֮���ƶ���ָ���Ķ���.
	 * 
	 * @param by
	 *            the locator you want to find the element
	 * @param timeout
	 *            ��ʱʱ�䣬��λ����
	 */
	public void moveToElement(By by, long timeout) {
		boolean isSucceed = false;
		try {
			if (isElementPresent(by, timeout)) {
				actionDriver.moveToElement(driver.findElement(by));
				actionDriver.perform();
				logger.debug("moveToElement [ " + by.toString() + " ] ");
				isSucceed = true;
			}
		} catch (Exception e) {
			logger.error(e);
		}
		operationCheck("moveToElement", isSucceed);
	}

	/**
	 * moveToElement</BR> �ڵȵ�����ɼ�֮���ƶ���ָ���Ķ���.
	 * 
	 * @param by
	 *            the locator you want to find the element
	 */
	public void moveToElement(By by) {
		moveToElement(by, 0);
	}

	/**
	 * right click on the element</BR> �ڵȵ�����ɼ�֮������Ҽ����ָ���Ķ���
	 * 
	 * @param by
	 *            the locator you want to find the element
	 * @param timeout
	 *            ��ʱʱ�䣬��λ����
	 */
	public void rightClick(By by, long timeout) {
		boolean isSucceed = false;
		try {
			if (isElementPresent(by, timeout)) {
				actionDriver.contextClick(driver.findElement(by));
				actionDriver.perform();
				logger.debug("rightClick on element [ " + by.toString() + " ] ");
				isSucceed = true;
			}
		} catch (Exception e) {
			logger.error(e);
		}
		operationCheck("rightClick", isSucceed);
	}

	/**
	 * right click on the element</BR> �ڵȵ�����ɼ�֮������Ҽ����ָ���Ķ���
	 * 
	 * @param by
	 *            the locator you want to find the element
	 */
	public void rightClick(By by) {
		rightClick(by, 0);
	}

	/**
	 * rewrite the submit method, submit on the element to be find by By</BR>
	 * �ڵȵ�ָ������ɼ�֮���ڸö�������ȷ��/�ύ�Ĳ�����
	 * 
	 * @param by
	 *            the locator you want to find the element
	 * @param timeout
	 *            ��ʱʱ�䣬��λ����
	 */
	public void submitForm(By by, long timeout) {
		boolean isSucceed = false;
		try {
			if (isElementPresent(by, timeout)) {
				driver.findElement(by).submit();
				logger.debug("submit on element [ " + by.toString() + " ]");
				isSucceed = true;
			}
		} catch (Exception e) {
			logger.error(e);
		}
		operationCheck("submit", isSucceed);
	}

	/**
	 * rewrite the submit method, submit on the element to be find by By</BR>
	 * �ڵȵ�ָ������ɼ�֮���ڸö�������ȷ��/�ύ�Ĳ�����
	 * 
	 * @param by
	 *            the locator you want to find the element
	 */
	public void submitForm(By by) {
		submitForm(by, 0);
	}

	/**
	 * rewrite the clear method, clear on the element to be find by By</BR>
	 * �ڵȵ�ָ������ɼ�֮���ڸö����������������һ������������ѡ���
	 * 
	 * @param by
	 *            the locator you want to find the element
	 */
	public void clear(By by) {
		boolean isSucceed = false;
		try {
			WebElement element = driver.findElement(by);
			element.clear();
			logger.debug("element [ " + by.toString() + " ] cleared");
			isSucceed = true;
		} catch (Exception e) {
			logger.error(e);
		}
		operationCheck("clear", isSucceed);
	}

	/**
	 * rewrite the sendKeys method, sendKeys on the element to be find by
	 * By</BR> ��׷���ı���ģʽ��ָ���ɱ༭�����������ı�������֮ǰ�Զ��ȴ�������ɼ���
	 * 
	 * @param by
	 *            the locator you want to find the element
	 * @param text
	 *            the text you want to input to element
	 * @param timeout
	 *            ��ʱʱ�䣬��λ����
	 */
	public void sendKeysAppend(By by, String text, long timeout) {
		boolean isSucceed = false;
		try {
			if (isElementPresent(by, timeout)) {
				driver.findElement(by).sendKeys(text);
				logger.debug("input text [ " + text + " ] to element [ "
						+ by.toString() + " ]");
				isSucceed = true;
			}
		} catch (Exception e) {
			logger.error(e);
		}
		operationCheck("sendKeysAppend", isSucceed);
	}

	/**
	 * rewrite the sendKeys method, sendKeys on the element to be find by
	 * By</BR> ��׷���ı���ģʽ��ָ���ɱ༭�����������ı�������֮ǰ�Զ��ȴ�������ɼ���
	 * 
	 * @param by
	 *            the locator you want to find the element
	 * @param text
	 *            the text you want to input to element
	 */
	public void sendKeysAppend(By by, String text) {
		sendKeysAppend(by, text, 0);
	}

	/**
	 * rewrite the sendKeys method, sendKeys on the element to be find by
	 * By</BR> ����ָ���������Ѿ�����������������룬����֮ǰ�Զ��ȴ�������ɼ���
	 * 
	 * @param by
	 *            the locator you want to find the element
	 * @param text
	 *            the text you want to input to element
	 * @param timeout
	 *            ��ʱʱ�䣬��λ����
	 */
	public void sendKeys(By by, String text, long timeout) {
		boolean isSucceed = false;
		try {
			if (isElementPresent(by, timeout)) {
				clear(by);
				driver.findElement(by).sendKeys(text);
				logger.debug("input text [ " + text + " ] to element [ "
						+ by.toString() + " ]");
				isSucceed = true;
			}
		} catch (Exception e) {
			logger.error(e);
		}
		operationCheck("sendKeys", isSucceed);
	}

	/**
	 * rewrite the sendKeys method, sendKeys on the element to be find by
	 * By</BR> ����ָ���������Ѿ�����������������룬����֮ǰ�Զ��ȴ�������ɼ���
	 * 
	 * @param by
	 *            the locator you want to find the element
	 * @param text
	 *            the text you want to input to element
	 */
	public void sendKeys(By by, String text) {
		sendKeys(by, text, 0);
	}

	/**
	 * readonly text box or richtext box input</BR> ʹ��DOM��Documnet Object
	 * Modal���޸�ҳ���ж�����ı�����ֵ��ʹ��ID��λ�����򷵻�Ψһ�������෵�����顣
	 * 
	 * @param by
	 *            the attribute of the element, default support is
	 *            TagName/Name/Id
	 * @param byValue
	 *            the attribute value of the element
	 * @param text
	 *            the text you want to input to element
	 * @param index
	 *            the index of the elements shared the same attribute value
	 * @throws IllegalArgumentException
	 */
	public void sendKeysByDOM(String by, String byValue, String text, int index) {
		String js = null;
		boolean isSucceed = false;
		try {
			if (by.equalsIgnoreCase("tagname")) {
				js = "document.getElementsByTagName('" + byValue + "')["
						+ index + "].value='" + text + "'";
			} else if (by.equalsIgnoreCase("name")) {
				js = "document.getElementsByName('" + byValue + "')[" + index
						+ "].value='" + text + "'";
			} else if (by.equalsIgnoreCase("id")) {
				js = "document.getElementById('" + byValue + "').value='"
						+ text + "'";
			} else {
				logger.debug("only can find element by TagName/Name/Id");
			}
			jsExecutor(js);
			isSucceed = true;
		} catch (Exception e) {
			logger.error(e);
		}
		operationCheck("sendKeysByDOM", isSucceed);
	}

	/**
	 * readonly text box or richtext box input, finding elements by element
	 * id</BR> ����ID��λҳ���ж��󣬲�ʹ��DOM��Documnet Object Modal���޸����ı�����ֵ��
	 * 
	 * @param elementId
	 *            the id of the element
	 * @param text
	 *            the text you want to input to element
	 */
	public void sendKeysById(String elementId, String text) {
		sendKeysByDOM("Id", elementId, text, 0);
	}

	/**
	 * readonly text box or richtext box input, finding elements by element
	 * name</BR> �������ƣ�Name������Ŷ�λҳ���ж��󣬲�ʹ��DOM��Documnet Object Modal���޸����ı�����ֵ��
	 * 
	 * @param elementName
	 *            the name of the element
	 * @param text
	 *            the text you want to input to element
	 * @param elementIndex
	 *            the index of the elements shared the same name, begins with 0
	 */
	public void sendKeysByName(String elementName, String text, int elementIndex) {
		sendKeysByDOM("Name", elementName, text, elementIndex);
	}

	/**
	 * readonly text box or richtext box input, finding elements by element tag
	 * name</BR> ���ձ�ǩ���ƣ�TagName������Ŷ�λҳ���ж��󣬲�ʹ��DOM��Documnet Object
	 * Modal���޸����ı�����ֵ��
	 * 
	 * @param elementTagName
	 *            the tag name of the element
	 * @param text
	 *            the text you want to input to element
	 * @param elementIndex
	 *            the index of the elements shared the same tag name, begins
	 *            with 0
	 */
	public void sendKeysByTagName(String elementTagName, String text,
			int elementIndex) {
		sendKeysByDOM("TagName", elementTagName, text, elementIndex);
	}

	/**
	 * edit rich text box created by FCKEditor</BR>
	 * ʹ��JS����FCKEditor������Ľӿڣ���ҳ��FCKEditor����������ָ�����ı���
	 * 
	 * @param editorId
	 *            FCKEditor id
	 * @param text
	 *            the text you want to input to element
	 */
	protected void setTextOnFCKEditor(String editorId, String text) {
		boolean isSucceed = false;
		try {
			String javascript = "FCKeditorAPI.GetInstance('" + editorId
					+ "').SetHTML('<p>" + text + "</p>');";
			jsExecutor(javascript);
			logger.debug("input text [ " + text + " ] to FCKEditor [ "
					+ editorId + " ]");
			isSucceed = true;
		} catch (Exception e) {
			logger.error(e);
		}
		operationCheck("setTextOnFCKEditor", isSucceed);
	}

	/**
	 * edit rich text box created by kindeditor</BR>
	 * ʹ��JS����KindEditor������Ľӿڣ���ҳ��KindEditor����������ָ�����ı���
	 * 
	 * @param editorId
	 *            KindEditor id
	 * @param text
	 *            the text you want to input to element
	 */
	protected void setTextOnKindEditor(String editorId, String text) {
		boolean isSucceed = false;
		try {
			String javascript = "KE.html('" + editorId + "','<p>" + text
					+ "</p>');";
			jsExecutor(javascript);
			logger.debug("input text [ " + text + " ] to KindEditor [ "
					+ editorId + " ]");
			isSucceed = true;
		} catch (Exception e) {
			logger.error(e);
		}
		operationCheck("setTextOnKindEditor", isSucceed);
	}

	/**
	 * edit rich text box created by kindeditor</BR>
	 * ʹ��JS����UEditor������Ľӿڣ���ҳ��UEditor����������ָ�����ı���
	 * 
	 * @param editorId
	 *            UEditor id
	 * @param text
	 *            the text you want to input to element
	 */
	protected void setTextOnUEditor(String editorId, String text) {
		boolean isSucceed = false;
		try {
			String javascript = "UE.getEditor('" + editorId + "').setContent('"
					+ text + "');";
			jsExecutor(javascript);
			logger.debug("input text [ " + text + " ] to UEditor [ " + editorId
					+ " ]");
			isSucceed = true;
		} catch (Exception e) {
			logger.error(e);
		}
		operationCheck("setTextOnUEditor", isSucceed);
	}

	/**
	 * execute the script to set the text value
	 * 
	 * @param elementid
	 *            -- the web element's id
	 * @param text
	 *            --the text we need to set ,it's string
	 * @param isReadOnly
	 *            --true/false
	 */
	public void setTextById(String elementid, String text, boolean isReadOnly) {
		boolean isSucceed = false;
		try {
			if (isReadOnly) {
				jsExecutor("window.document.getElementById('"
						+ elementid
						+ "').removeAttribute('readOnly');window.document.getElementById('"
						+ elementid + "').setAttribute('value', '" + text
						+ "');");
			} else {
				jsExecutor("window.document.getElementById('" + elementid
						+ "').setAttribute('value', '" + text + "');");
			}
			logger.debug("input text [ " + text + " ] to [ " + elementid + " ]");
			isSucceed = true;
		} catch (Exception e) {
			logger.error(e);
		}
		operationCheck("setTextById", isSucceed);
	}

	/**
	 * execute the script to set the text value
	 * 
	 * @param by
	 *            the locator you want to find the element
	 * @param text
	 *            --the text we need to set ,it's string
	 * @param isReadOnly
	 *            --true/false
	 */
	public void setText(By by, String text, boolean isReadOnly) {
		boolean isSucceed = false;
		try {
			WebElement element = findElement(by);
			if (isReadOnly) {
				jsExecutor(
						"arguments[0].removeAttribute('readOnly');arguments[0].setAttribute('value', '"
								+ text + "');", element);
			} else {
				jsExecutor("arguments[0].setAttribute('value', '" + text
						+ "');", element);
			}
			logger.debug("input text [ " + text + " ] to [ " + by.toString()
					+ " ]");
			isSucceed = true;
		} catch (Exception e) {
			logger.error(e);
		}
		operationCheck("setText", isSucceed);
	}

	/**
	 * select an item from a picklist by index</BR> ����ָ�����ѡ�������б��е�ѡ�
	 * 
	 * @param by
	 *            the locator you want to find the element
	 * @param index
	 *            the index of the item to be selected
	 * @param timeout
	 *            ��ʱʱ�䣬��λ����
	 */
	public void selectByIndex(By by, int index, long timeout) {
		boolean isSucceed = false;
		try {
			if (isElementPresent(by, timeout)) {
				WebElement element = driver.findElement(by);
				Select select = new Select(element);
				select.selectByIndex(index);
				logger.debug("item selected by index [ " + index + " ] on [ "
						+ by.toString() + " ]");
				isSucceed = true;
			}
		} catch (Exception e) {
			logger.error(e);
		}
		operationCheck("selectByIndex", isSucceed);

	}

	/**
	 * select an item from a picklist by index</BR> ����ָ�����ѡ�������б��е�ѡ�
	 * 
	 * @param by
	 *            the locator you want to find the element
	 * @param index
	 *            the index of the item to be selected
	 */
	public void selectByIndex(By by, int index) {
		selectByIndex(by, index, 0);
	}

	/**
	 * select an item from a picklist by item value</BR>
	 * ����ָ��ѡ���ʵ��ֵ�����ǿɼ��ı�ֵ�����Ƕ���ġ�value�����Ե�ֵ��ѡ�������б��е�ѡ�
	 * 
	 * @param by
	 *            the locator you want to find the element
	 * @param itemValue
	 *            the item value of the item to be selected
	 * @param timeout
	 *            ��ʱʱ�䣬��λ����
	 */
	public void selectByValue(By by, String itemValue, long timeout) {
		boolean isSucceed = false;
		try {
			if (isElementPresent(by, timeout)) {
				WebElement element = driver.findElement(by);
				Select select = new Select(element);
				select.selectByValue(itemValue);
				logger.debug("item selected by item value [ " + itemValue
						+ " ] on [ " + by.toString() + " ]");
				isSucceed = true;
			}
		} catch (Exception e) {
			logger.error(e);
		}
		operationCheck("selectByValue", isSucceed);

	}

	/**
	 * select an item from a picklist by item value</BR>
	 * ����ָ��ѡ���ʵ��ֵ�����ǿɼ��ı�ֵ�����Ƕ���ġ�value�����Ե�ֵ��ѡ�������б��е�ѡ�
	 * 
	 * @param by
	 *            the locator you want to find the element
	 * @param itemValue
	 *            the item value of the item to be selected
	 */
	public void selectByValue(By by, String itemValue) {
		selectByValue(by, itemValue, 0);
	}

	/**
	 * select an item from a picklist by item value</BR>
	 * ����ָ��ѡ��Ŀɼ��ı�ֵ���û�ֱ�ӿ��Կ������ı���ѡ�������б��е�ѡ�
	 * 
	 * @param by
	 *            the locator you want to find the element
	 * @param text
	 *            the item value of the item to be selected
	 * @param timeout
	 *            ��ʱʱ�䣬��λ����
	 */
	public void selectByVisibleText(By by, String text, long timeout) {
		boolean isSucceed = false;
		try {
			if (isElementPresent(by, timeout)) {
				WebElement element = driver.findElement(by);
				Select select = new Select(element);
				select.selectByVisibleText(text);
				logger.debug("item selected by visible text [ " + text
						+ " ] on [ " + by.toString() + " ]");
				isSucceed = true;
			}
		} catch (Exception e) {
			logger.error(e);
		}
		operationCheck("selectByVisibleText", isSucceed);
	}

	/**
	 * select an item from a picklist by item value</BR>
	 * ����ָ��ѡ��Ŀɼ��ı�ֵ���û�ֱ�ӿ��Կ������ı���ѡ�������б��е�ѡ�
	 * 
	 * @param by
	 *            the locator you want to find the element
	 * @param text
	 *            the item value of the item to be selected
	 */
	public void selectByVisibleText(By by, String text) {
		selectByVisibleText(by, text, 0);
	}

	/**
	 * set the checkbox on or off</BR> ��ָ���ĸ�ѡ���������Ϊѡ�л��߲�ѡ��״̬��
	 * 
	 * @param by
	 *            the locator you want to find the element
	 * @param onOrOff
	 *            on or off to set the checkbox
	 * @param timeout
	 *            ��ʱʱ�䣬��λ����
	 */
	public void setCheckBox(By by, String onOrOff, long timeout) {
		boolean isSucceed = false;
		try {
			if (isElementPresent(by, timeout)) {
				WebElement checkBox = driver.findElement(by);
				if ((onOrOff.toLowerCase().contains("on") && !checkBox
						.isSelected())
						|| (onOrOff.toLowerCase().contains("off") && checkBox
								.isSelected())) {
					checkBox.click();
					logger.debug("the checkbox [ " + by.toString()
							+ " ] is set to [ " + onOrOff.toUpperCase() + " ]");
				} else {
					if ((onOrOff.toLowerCase().contains("on") && checkBox
							.isSelected())) {
						logger.debug("the checkbox [ " + by.toString()
								+ " ] is already set to [ " + onOrOff.toUpperCase()
								+ " ]");
					}

					if ((onOrOff.toLowerCase().contains("off") && !checkBox
							.isSelected())) {
						logger.debug("the checkbox [ " + by.toString()
								+ " ] is already set to [ " + onOrOff.toUpperCase()
								+ " ]");
					}
				}
				isSucceed = true;
			}
		} catch (Exception e) {
			logger.error(e);
		}
		operationCheck("setCheckBox", isSucceed);
	}

	/**
	 * set the checkbox on or off</BR> ��ָ���ĸ�ѡ���������Ϊѡ�л��߲�ѡ��״̬��
	 * 
	 * @param by
	 *            the locator you want to find the element
	 * @param onOrOff
	 *            on or off to set the checkbox
	 */
	public void setCheckBox(By by, String onOrOff) {
		setCheckBox(by, onOrOff, 0);
	}

	/**
	 * set the RadioGroup on or off</BR> ��ָ���ĵ�ѡ��ť��������Ϊѡ�л��߲�ѡ��״̬��
	 * 
	 * @param by
	 *            the locator you want to find the element
	 * @param onOrOff
	 *            on or off to set the RadioGroup
	 * @param timeout
	 *            ��ʱʱ�䣬��λ����
	 */
	public void setRadioGroup(By by, String onOrOff, long timeout) {
		boolean isSucceed = false;
		try {
			if (isElementPresent(by, timeout)) {
				WebElement radioGroup = driver.findElement(by);
				if ((onOrOff.toLowerCase().contains("on") && !radioGroup
						.isSelected())
						|| (onOrOff.toLowerCase().contains("off") && radioGroup
								.isSelected())) {
					radioGroup.click();
					logger.debug("the radioGroup [ " + by.toString()
							+ " ] is set to [ " + onOrOff.toUpperCase() + " ]");
				} else {
					if ((onOrOff.toLowerCase().contains("on") && radioGroup
							.isSelected())) {
						logger.debug("the radioGroup [ " + by.toString()
								+ " ] is already set to [ " + onOrOff.toUpperCase()
								+ " ]");
					}

					if ((onOrOff.toLowerCase().contains("off") && !radioGroup
							.isSelected())) {
						logger.debug("the radioGroup [ " + by.toString()
								+ " ] is already set to [ " + onOrOff.toUpperCase()
								+ " ]");
					}
				}
				isSucceed = true;
			}
		} catch (Exception e) {
			logger.error(e);
		}
		operationCheck("setRadioGroup", isSucceed);
	}

	/**
	 * set the RadioGroup on or off</BR> ��ָ���ĵ�ѡ��ť��������Ϊѡ�л��߲�ѡ��״̬��
	 * 
	 * @param by
	 *            the locator you want to find the element
	 * @param onOrOff
	 *            on or off to set the RadioGroup
	 */
	public void setRadioGroup(By by, String onOrOff) {
		setRadioGroup(by, onOrOff, 0);
	}

	/**
	 * ��ȡbyҳ��Ԫ�ض�λ����Ӧ��ҳ��Ԫ�ظ���
	 * 
	 * @param by
	 *            Seleniumҳ��Ԫ�ض�λ��
	 * @param timeout
	 *            ��ʱʱ�䣬��λ����
	 * @return byҳ��Ԫ�ض�λ����Ӧ��ҳ��Ԫ�ظ���
	 */
	public int getNumberOfElements(By by, long timeout) {
		boolean isSucceed = false;
		int elementCount = 0;
		long timeBegins = System.currentTimeMillis();
		do {
			try {
				elementCount = driver.findElements(by).size();
				isSucceed = true;
				logger.debug("find element [ " + by.toString()
						+ " ] count is [ " + elementCount + " ]");
				break;
			} catch (Exception e) {
				logger.error(e);
			}
			pause(pauseTime);
		} while (System.currentTimeMillis() - timeBegins <= timeout * 1000);
		operationCheck("getNumberOfElements", isSucceed);
		return elementCount;
	}

	/**
	 * ��ȡbyҳ��Ԫ�ض�λ����Ӧ��ҳ��Ԫ�ظ���
	 * 
	 * @param by
	 *            Seleniumҳ��Ԫ�ض�λ��
	 * @return byҳ��Ԫ�ض�λ����Ӧ��ҳ��Ԫ�ظ���
	 */
	public int getNumberOfElements(By by) {
		return getNumberOfElements(by, 0);
	}

	/**
	 * ����option���ѡ��WebList�������һ��
	 * 
	 * @param selectLocator
	 *            webListԪ�ض�λ��
	 * @param timeout
	 *            ��ʱʱ�䣬��λ����
	 * @return ��
	 */
	public void webList_RandomSelectByOption(By selectLocator, long timeout) {
		boolean isSucceed = false;
		long timeBegins = System.currentTimeMillis();
		do {
			try {
				WebElement select = driver.findElement(selectLocator);
				Select selectElement = new Select(select);
				List<WebElement> options = selectElement.getOptions();
				WebElement option = options.get(RandomUtil.getRandomNumber(0,
						options.size()));
				String itemValue = option.getAttribute("value");
				selectElement.selectByValue(itemValue);
				isSucceed = true;
				logger.debug("item selected by item value [ " + itemValue
						+ " ] on [ " + selectLocator.toString() + " ]");
				break;
			} catch (Exception e) {
				logger.error(e);
			}
			pause(pauseTime);
		} while (System.currentTimeMillis() - timeBegins <= timeout * 1000);
		operationCheck("webList_RandomSelectByOption", isSucceed);

	}

	/**
	 * ����option���ѡ��WebList�������һ��
	 * 
	 * @param selectLocator
	 *            webListԪ�ض�λ��
	 * @return ��
	 */
	public void webList_RandomSelectByOption(By selectLocator) {
		webList_RandomSelectByOption(selectLocator, 0);
	}

	/**
	 * ��������index���ѡ��WebList�������һ��
	 * 
	 * @param selectLocator
	 *            webListԪ�ض�λ��
	 * @param timeout
	 *            ��ʱʱ�䣬��λ����
	 * @return ��
	 */
	public void webList_RandomSelectByIndex(By selectLocator, long timeout) {
		boolean isSucceed = false;
		long timeBegins = System.currentTimeMillis();
		do {
			try {
				WebElement select = driver.findElement(selectLocator);
				Select selectElement = new Select(select);
				List<WebElement> options = selectElement.getOptions();
				int optionIndex = RandomUtil.getRandomNumber(0, options.size());
				String itemValue = options.get(optionIndex).getAttribute(
						"value");
				selectElement.selectByIndex(optionIndex);
				isSucceed = true;
				logger.debug("item selected by item value [ " + itemValue
						+ " ] on [ " + selectLocator.toString() + " ]");
				break;
			} catch (Exception e) {
				logger.error(e);
			}
			pause(pauseTime);
		} while (System.currentTimeMillis() - timeBegins <= timeout * 1000);
		operationCheck("webList_RandomSelectByIndex", isSucceed);

	}

	/**
	 * ��������index���ѡ��WebList�������һ��
	 * 
	 * @param selectLocator
	 *            webListԪ�ض�λ��
	 * @return ��
	 */
	public void webList_RandomSelectByIndex(By selectLocator) {
		webList_RandomSelectByIndex(selectLocator, 0);
	}

	/**
	 * ��ȡWebList�������option����
	 * 
	 * @param selectLocator
	 *            webListԪ�ض�λ��
	 * @param timeout
	 *            ��ʱʱ�䣬��λ����
	 * @return WebList�������option����
	 */
	public int webList_GetItemsCount(By selectLocator, long timeout) {
		boolean isSucceed = false;
		int itemsCount = 0;
		long timeBegins = System.currentTimeMillis();
		do {
			try {
				WebElement select = driver.findElement(selectLocator);
				Select selectElement = new Select(select);
				List<WebElement> options = selectElement.getOptions();
				itemsCount = options.size();
				isSucceed = true;
				logger.debug("WebList item's count is [ " + itemsCount
						+ " ] on [ " + selectLocator.toString() + " ]");
				break;
			} catch (Exception e) {
				logger.error(e);
			}
			pause(pauseTime);
		} while (System.currentTimeMillis() - timeBegins <= timeout * 1000);
		operationCheck("webList_GetItemsCount", isSucceed);

		return itemsCount;
	}

	/**
	 * ��ȡWebList�������option����
	 * 
	 * @param selectLocator
	 *            webListԪ�ض�λ��
	 * @return WebList�������option����
	 */
	public int webList_GetItemsCount(By selectLocator) {
		return webList_GetItemsCount(selectLocator, 0);
	}

	/**
	 * ���ѡ��WebRadioGroup��ѡ��ť�е�һ��
	 * 
	 * @param webRadioGroupLocator
	 *            webRadioGroupԪ�ض�λ��
	 * @param timeout
	 *            ��ʱʱ�䣬��λ����
	 * @return ��
	 * @example webRadioGroup_RandomSelectOne("//input[@type='radio' and
	 *          contains(@name,'action')]","on",10)
	 */
	public void webRadioGroup_RandomSelectOne(By webRadioGroupLocator,
			String onOrOff, long timeout) {
		boolean isSucceed = false;
		long timeBegins = System.currentTimeMillis();
		do {
			try {
				int webRadioGroupItemsCount = getNumberOfElements(webRadioGroupLocator);
				int randomNum = RandomUtil.getRandomNumber(0,
						webRadioGroupItemsCount);
				WebElement radioGroup = driver.findElements(
						webRadioGroupLocator).get(randomNum);
				if ((onOrOff.toLowerCase().contains("on") && !radioGroup
						.isSelected())
						|| (onOrOff.toLowerCase().contains("off") && radioGroup
								.isSelected())) {
					radioGroup.click();
				}
				logger.debug("the radioGroup [ "
						+ webRadioGroupLocator.toString() + " ] is set to [ "
						+ onOrOff.toUpperCase() + " ]");

				isSucceed = true;
				break;
			} catch (Exception e) {
				logger.error(e);
			}
			pause(pauseTime);
		} while (System.currentTimeMillis() - timeBegins <= timeout * 1000);
		operationCheck("webRadioGroup_RandomSelectOne", isSucceed);
	}

	/**
	 * ���ѡ��WebRadioGroup��ѡ��ť�е�һ��
	 * 
	 * @param webRadioGroupLocator
	 *            webRadioGroupԪ�ض�λ��
	 * @return ��
	 * @example webRadioGroup_RandomSelectOne("//input[@type='radio' and
	 *          contains(@name,'action')]","on")
	 */
	public void webRadioGroup_RandomSelectOne(By webRadioGroupLocator) {
		webRadioGroup_RandomSelectOne(webRadioGroupLocator, "on", 0);
	}

	/**
	 * ���ѡ��webLink�����е�һ��
	 * 
	 * @param webLinkLocator
	 *            webLinkԪ�ض�λ��
	 * @param timeout
	 *            ��ʱʱ�䣬��λ����
	 * @return ��
	 * @example webLink_RandomSelectOne(
	 *          "//a[contains(@href,'admin_toushu.asp?action=del')]",10)
	 */
	public void webLink_RandomSelectOne(By webLinkLocator, long timeout) {
		boolean isSucceed = false;
		long timeBegins = System.currentTimeMillis();
		do {
			try {
				int webLinkItemsCount = getNumberOfElements(webLinkLocator);
				int randomNum = RandomUtil
						.getRandomNumber(0, webLinkItemsCount);
				WebElement link = driver.findElements(webLinkLocator).get(
						randomNum);
				link.click();
				isSucceed = true;
				logger.debug("the link [ " + webLinkLocator.toString()
						+ " ] is clicked!");
				break;
			} catch (Exception e) {
				logger.error(e);
			}
			pause(pauseTime);
		} while (System.currentTimeMillis() - timeBegins <= timeout * 1000);
		operationCheck("webLink_RandomSelectOne", isSucceed);
	}

	/**
	 * ���ѡ��webLink�����е�һ��
	 * 
	 * @param webLinkLocator
	 *            webLinkԪ�ض�λ��
	 * @return ��
	 * @example webLink_RandomSelectOne(
	 *          "//a[contains(@href,'admin_toushu.asp?action=del')]")
	 */
	public void webLink_RandomSelectOne(By webLinkLocator) {
		webLink_RandomSelectOne(webLinkLocator, 0);
	}

	/**
	 * use js to judge if the browser load completed. ��js����ֵ�ж�ҳ���Ƿ������ϡ�
	 * 
	 * @return load comploete or not.
	 */
	public boolean pageLoadSucceed() {
		String js = JScriptCollection.BROWSER_READY_STATUS.getValue();
		Object loadCompleted = jsReturner(js);
		logger.debug("execute js [ " + js + " ] return value is : [ "
				+ loadCompleted.toString() + " ]");
		return loadCompleted.toString().toLowerCase().equals("complete");
	}

	/**
	 * use js to judge if the browser load completed.
	 * ��js����ֵ�ж�ҳ���Ƿ������ϣ���ʱδ��������򱨴�
	 * 
	 * @param timeout
	 *            max time used to load page.
	 */
	public void waitForPageToLoad(int timeout) {
		boolean isSucceed = false;
		long start = System.currentTimeMillis();
		do {
			if (pageLoadSucceed()) {
				isSucceed = true;
				break;
			}
			pause(pauseTime);
		} while (((System.currentTimeMillis() - start) < timeout * 1000));
		operationCheck("waitForPageToLoad", isSucceed);
	}

	/**
	 * make the alert dialog not to appears</BR>
	 * ͨ��JS�������أ��ڶԻ���Alert������֮ǰ�������������˵�ȼ��ڲ�������֡�
	 */
	public void ensrueBeforeAlert() {
		jsExecutor(JScriptCollection.ENSRUE_BEFORE_ALERT.getValue());
	}

	/**
	 * make the warn dialog not to appears when window.close()</BR>
	 * ͨ��JS�������أ�����������ڹر�֮ǰ��ȥ���ĸ澯��ʾ��
	 */
	public void ensureBeforeWinClose() {
		jsExecutor(JScriptCollection.ENSURE_BEFORE_WINCLOSE.getValue());
	}

	/**
	 * make the confirm dialog not to appears choose default option OK</BR>
	 * ͨ��JS�������أ���ȷ�Ͽ�Confirm������֮ǰ���ȷ�ϣ�����˵�ȼ��ڲ�������ֶ�ֱ��ȷ�ϡ�
	 */
	public void ensureBeforeConfirm() {
		jsExecutor(JScriptCollection.ENSURE_BEFORE_CONFIRM.getValue());
	}

	/**
	 * make the confirm dialog not to appears choose default option Cancel</BR>
	 * ͨ��JS�������أ���ȷ�Ͽ�Confirm������֮ǰ���ȡ��������˵�ȼ��ڲ�������ֶ�ֱ��ȡ����
	 */
	public void dismissBeforeConfirm() {
		jsExecutor(JScriptCollection.DISMISS_BEFORE_CONFIRM.getValue());
	}

	/**
	 * make the prompt dialog not to appears choose default option OK</BR>
	 * ͨ��JS�������أ�����ʾ��Prompt������֮ǰ���ȷ�ϣ�����˵�ȼ��ڲ�������ֶ�ֱ��ȷ�ϡ�
	 */
	public void ensureBeforePrompt() {
		jsExecutor(JScriptCollection.ENSURE_BEFORE_PROMPT.getValue());
	}

	/**
	 * make the prompt dialog not to appears choose default option Cancel</BR>
	 * ͨ��JS�������أ�����ʾ��Prompt������֮ǰ���ȡ��������˵�ȼ��ڲ�������ֶ�ֱ��ȡ����
	 */
	public void dismisBeforePrompt() {
		jsExecutor(JScriptCollection.DISMISS_BEFORE_PROMPT.getValue());
	}

	/**
	 * choose OK/Cancel button's OK on alerts</BR> �ڵ����ĶԻ���Dialog���ϵ��ȷ��/�ǵȽ����԰�ť��
	 * 
	 * @param timeout
	 *            ��ʱʱ�䣬��λ����
	 */
	public void chooseOKOnAlert(long timeout) {
		boolean isSucceed = false;
		try {
			if (isAlertExists(timeout)) {
				driver.switchTo().alert().accept();
				logger.debug("click OK button on alert");
				isSucceed = true;
			}
		} catch (Exception e) {
			logger.error(e);
		}
		operationCheck("chooseOKOnAlert", isSucceed);
	}

	/**
	 * choose OK/Cancel button's OK on alerts</BR> �ڵ����ĶԻ���Dialog���ϵ��ȷ��/�ǵȽ����԰�ť��
	 * 
	 */
	public void chooseOKOnAlert() {
		chooseOKOnAlert(0);
	}

	/**
	 * choose Cancel on alerts</BR> �ڵ����ĶԻ���Dialog���ϵ��ȡ��/��Ⱦܾ��԰�ť��
	 * 
	 * @param timeout
	 *            ��ʱʱ�䣬��λ����
	 */
	public void chooseCancelOnAlert(long timeout) {
		boolean isSucceed = false;
		try {
			if (isAlertExists(timeout)) {
				driver.switchTo().alert().dismiss();
				logger.debug("click Cancel on alert dialog");
				isSucceed = true;
			}
		} catch (Exception e) {
			logger.error(e);
		}
		operationCheck("chooseCancelOnAlert", isSucceed);
	}

	/**
	 * choose Cancel on alerts</BR> �ڵ����ĶԻ���Dialog���ϵ��ȡ��/��Ⱦܾ��԰�ť��
	 * 
	 */
	public void chooseCancelOnAlert() {
		chooseCancelOnAlert(0);
	}

	/**
	 * get the text of the alerts</BR> ���ضԻ���Dialog���ϵ���ʾ��Ϣ�ı����ݡ�
	 * 
	 * @param timeout
	 *            ��ʱʱ�䣬��λ����
	 * 
	 * @return alert text string
	 */
	public String getTextOfAlert(long timeout) {
		boolean isSucceed = false;
		String alerts = "";
		try {
			if (isAlertExists(timeout)) {
				alerts = driver.switchTo().alert().getText();
				logger.debug("the text of the alert is: " + alerts);
				isSucceed = true;
			}
		} catch (Exception e) {
			logger.error(e);
		}
		operationCheck("getTextOfAlert", isSucceed);
		return alerts;
	}

	/**
	 * get the text of the alerts</BR> ���ضԻ���Dialog���ϵ���ʾ��Ϣ�ı����ݡ�
	 * 
	 * @return alert text string
	 */
	public String getTextOfAlert() {
		return getTextOfAlert(0);
	}

	/**
	 * set text on alerts</BR> ��Ի���InputBox���������ı���
	 * 
	 * @param text
	 *            the text string you want to input on alerts
	 * @param timeout
	 *            ��ʱʱ�䣬��λ����
	 */
	public void setTextOnAlert(String text, long timeout) {
		boolean isSucceed = false;
		try {
			if (isAlertExists(timeout)) {
				driver.switchTo().alert().sendKeys(text);
				logger.debug("set text [ " + text + " ] on alert");
				isSucceed = true;
			}
		} catch (Exception e) {
			logger.error(e);
		}
		operationCheck("setTextOnAlert", isSucceed);
	}

	/**
	 * set text on alerts</BR> ��Ի���InputBox���������ı���
	 * 
	 * @param text
	 *            the text string you want to input on alerts
	 */
	public void setTextOnAlert(String text) {
		setTextOnAlert(text, 0);
	}

	/**
	 * use js to make the element to be un-hidden</BR> ʹ��JSִ�еķ���ǿ����ĳЩ���صĿؼ���ʾ������
	 * 
	 * @param by
	 *            the By locator to find the element
	 */
	public void makeElementUnHidden(By by) {
		jsExecutor(JScriptCollection.MAKE_ELEMENT_UNHIDDEN.getValue(),
				driver.findElement(by));
	}
	
	public void quit(){
		driver.quit();
	}
}
