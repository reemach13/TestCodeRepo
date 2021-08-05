package mmt.modules.utilities;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.maven.surefire.shared.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import org.testng.annotations.AfterClass;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class CommonFunctions {
	
	public static WebDriver driver;
	static Document doc;
	static ExtentReports report;
	static ExtentTest test;
	
	public static void openurl() throws DocumentException, IOException {
	System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\web-drivers\\chromedriver.exe");
	driver= new ChromeDriver();
	driver.manage().window().maximize();
	driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
	String url= getdatafromxml("url");
	logger("URL read from XML successfully","PASS");
	driver.get(url);
	logger("Homepage is launched successfully","PASS");
	loggerWithScreenshot("HomePage launched successfully","PASS");
	}
	
	public static String getdatafromxml(String data) throws DocumentException {
		File in= new File(System.getProperty("user.dir")+"\\src\\test\\resources\\testdata.xml");
		SAXReader sx= new SAXReader();
		doc=sx.read(in);
		String key="//menu/"+data;
		String value=doc.selectSingleNode(key).getText();
		return value;
	}
	
	public static void getSnapshot(WebDriver driver,String method) throws IOException {
		TakesScreenshot tk= (TakesScreenshot)driver;
		File old= tk.getScreenshotAs(OutputType.FILE);
		String filewithpath= System.getProperty("user.dir")+"\\Snapshots\\"+method+".PNG";
	    File nw= new File(filewithpath);
		FileUtils.copyFile(old,nw);
	}
	
	public void tab(int n) throws AWTException {
		Robot r = new Robot();
		for (int i=0;i<n;i++) {
			r.keyPress(KeyEvent.VK_TAB);
			r.keyRelease(KeyEvent.VK_TAB);
		}
	}
	
	public void enter(int n) throws AWTException {
		Robot r = new Robot();
		for (int i=0;i<n;i++) {
			r.keyPress(KeyEvent.VK_ENTER);
			r.keyRelease(KeyEvent.VK_ENTER);
		}
	}
	
	public void pageloadwait() throws InterruptedException {
		Thread.sleep(6000);
	}
	
	
	@BeforeMethod
	public void reportSetup() {
		report= new ExtentReports(System.getProperty("user.dir")+"/Reports/ExtentReport.html",true);
		test= report.startTest(this.getClass().getName());
	}
	
	@AfterMethod
	public void closeReport() {
		report.endTest(test);
		report.flush();
	} 
	
	@AfterClass
	public void closeDriver() {
		driver.quit();		
	} 
	
	public static void logger(String method,String status) {
		test.log(LogStatus.valueOf(status), method);
	}
	
	public static void loggerWithScreenshot(String method,String status) throws IOException {
		getSnapshot(driver,method);
		String filewithpath= System.getProperty("user.dir")+"\\Snapshots\\"+method+".PNG";
		test.log(LogStatus.valueOf(status), test.addScreenCapture(filewithpath));
	}
}
