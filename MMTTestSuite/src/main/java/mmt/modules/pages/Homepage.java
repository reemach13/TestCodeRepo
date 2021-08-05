package mmt.modules.pages;

import static mmt.modules.utilities.CommonFunctions.driver;

import java.awt.AWTException;
import java.awt.List;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import org.dom4j.DocumentException;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import mmt.modules.utilities.CommonFunctions;

public class Homepage extends CommonFunctions {
	
	public static WebElement element=null;
	
	public WebElement SignIn_google_option(WebDriver driver ) {
		element = driver.findElement(By.xpath("//div[@class='makeFlex googleLoginBtn flexOne paddingR10']"));
		return element;
	}
	
	public WebElement SignIn_NewWindow_Username(WebDriver driver ) {
		element = driver.findElement(By.id("identifierId"));
		return element;
	}
	
	public WebElement SignIn_NewWindow_Pwd(WebDriver driver ) {
		element = driver.findElement(By.name("password"));
		return element;
	}
	
	public WebElement Next_NewWindow_button(WebDriver driver ) {
		element = driver.findElement(By.xpath("//span[@class='VfPpkd-vQzf8d']"));
		return element;
	}
	

	public WebElement ToCity_clickbox(WebDriver driver ) {
		element = driver.findElement(By.id("city"));
		return element;
	}
	
	public WebElement ToCity_textbox(WebDriver driver ) {
		element = driver.findElement(By.xpath("//input[@class='react-autosuggest__input react-autosuggest__input--open']"));
		return element;
	}
	
	public WebElement Guest_selectbox(WebDriver driver ) {
		element = driver.findElement(By.xpath("//label[@for='guest']"));
		return element;
	}
	
	public WebElement Adult_select(WebDriver driver ) {
		element = driver.findElement(By.xpath("//li[@data-cy='adults-1']"));
		return element;
	}
	
	public WebElement Child_select(WebDriver driver ) throws DocumentException {
		String child=getdatafromxml("GuestChild");
		element = driver.findElement(By.xpath("//li[@data-cy='children-1']"));
		return element;
	}
	
	
	public WebElement Apply_button(WebDriver driver ) {
		element = driver.findElement(By.xpath("//button[@data-cy='submitGuest']"));
		return element;
	}
	
	public WebElement Search_button(WebDriver driver ) {
		element = driver.findElement(By.id("hsw_search_button"));
		return element;
	}
	
	
	public void checkNumberOfFrames() {
	java.util.List<WebElement> options =driver.findElements(By.tagName("iframe"));
	int s= options.size();
	logger("Number of frames in this page ="+s,"PASS");
	}
	

	/**Create methods to perform actions on the above page objects 
	 * @throws InterruptedException 
	 * @throws AWTException 
	 * @throws IOException 
	 * @throws DocumentException **/
	
	public void login() throws IOException {
		try {
		SignIn_google_option(driver).click();
		pageloadwait();
		String parent=driver.getWindowHandle();
		Set<String>s=driver.getWindowHandles();
		Iterator<String> I1= s.iterator();
		
		while(I1.hasNext())
		{
		String child_window=I1.next();
		if(!parent.equals(child_window))
		{
		driver.switchTo().window(child_window);
		SignIn_NewWindow_Username(driver).sendKeys(getdatafromxml("Uname"));
		Next_NewWindow_button(driver).click();
		pageloadwait();
		if (SignIn_NewWindow_Pwd(driver).isDisplayed()) {
		SignIn_NewWindow_Pwd(driver).sendKeys(getdatafromxml("Pwd"));
		Next_NewWindow_button(driver).click();
		driver.close();
		  } 
		} 
	}
		driver.switchTo().window(parent);
		} catch (Exception e) {
			logger("User not allowed to login using automated control","FAIL");
			loggerWithScreenshot("User not allowed to login using automated control","FAIL");
		}
	}
	
	public void selectToCity(WebDriver driver) {
		try {
		ToCity_clickbox(driver).click();
		pageloadwait();
		ToCity_clickbox(driver).click();
		ToCity_textbox(driver).click();
		pageloadwait();
		ToCity_textbox(driver).sendKeys(getdatafromxml("ToCity"));
		pageloadwait();
		ToCity_textbox(driver).sendKeys(Keys.ARROW_DOWN);
		ToCity_textbox(driver).sendKeys(Keys.ENTER);
		logger("City is selected","PASS");
		loggerWithScreenshot("City is selected","PASS");
	} catch (Exception e)
	 { 
		logger("Failed to interact with ToCity element unless logged in : element click intercepted Error","FAIL");
	 }
	}
	
	public void selectGuest(WebDriver driver) throws DocumentException, AWTException, InterruptedException, IOException {
		tab(3);
		Guest_selectbox(driver).click();
		pageloadwait();
		Adult_select(driver).click();
		pageloadwait();
		Child_select(driver).click();
		pageloadwait();
		Apply_button(driver).click();
		logger("Guests selected","PASS");
		loggerWithScreenshot("Guests selected","PASS");
	}
	
	public void clickSearchButton(WebDriver driver) throws InterruptedException {
		pageloadwait();
		driver.switchTo().parentFrame();
		Search_button(driver).click();
		}
	

	

}
