package mmt.modules.test;

import java.awt.AWTException;
import java.io.IOException;

import org.dom4j.DocumentException;
import org.testng.annotations.*;

import mmt.modules.pages.Homepage;
import mmt.modules.utilities.CommonFunctions;


public class TC01_SelectHotelPositiveScenario extends CommonFunctions{
	static Homepage hpage=new Homepage();
	
	@Test
	public static void selectHotel() throws DocumentException, IOException, InterruptedException, AWTException
    {
     	openurl();
		hpage.checkNumberOfFrames();
		hpage.selectToCity(driver);
		hpage.login();
		//hpage.selectGuest(driver);
		//hpage.clickSearchButton(driver);
	
    }
}
