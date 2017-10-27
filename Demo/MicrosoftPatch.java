package com.MicroSoftPatch.Scenarios;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.security.UserAndPassword;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.apache.commons.io.FileUtils;
import com.tavant.base.DriverFactory;
import com.tavant.base.WebPage;
import com.tavant.kwutils.PageObject;
import com.tavant.kwutils.PageObjectLoader;
import com.tavant.utils.TwfException;
import jxl.read.biff.BiffException;

public class MicrosoftPatch extends WebPage{

	WebDriver driver;
	Set<String> windowHandles;
	static String parentWindow;
	static String secondWindow;

	//------------------------------------------------------------------------------------------------------------------------------------------------------------//
	/*@Navigate back 
	 * @Author-Nitish Nayak.
	 */

	public void naivgateBack(String k) throws TwfException {

		driver=DriverFactory.getDriver();
		driver.navigate().back();
	}
	//-------------------------------------------------------------------------------------------------------------------------------------------------------------//


	//------------------------------------------------------------------------------------------------------------------------------------------------------------//
	/*@Navigate to the new Window enter the discrete value and validate whether correct page is displayed or not.
	 *@Author-Nitish Nayak.
	 */

	public void validateRentalIncentiveReport(Map<String,String> data) throws TwfException, BiffException, IOException{

		driver=DriverFactory.getDriver();
		Set<String> windowHandles=driver.getWindowHandles();

		for (String windowHandle : windowHandles) {

			driver.switchTo().window(windowHandle);
		}

		getElementByUsing("FSICrystalReporting_Discrete_TextBox").sendKeys(data.get("Discrete"));
		getElementByUsing("FSICrystalReporting_OK_Button").click();
		waitForElement(getElementByUsing("FSICrystalReporting_Report_DropDown"), 40);
		if(getElementByUsing("FSICrystalReporting_RetailIncentives_Header").isDisplayed()){



		}else{

			addExceptionToReport("Retail Incentive Reports not displayed...", "", "");
		}


	}
	//------------------------------------------------------------------------------------------------------------------------------------------------------------//


	//------------------------------------------------------------------------------------------------------------------------------------------------------------//	
	/*@Navigate to the product tab choose Adjuvants and validate whether user lands to the Adjuvant page
	 * @Author-Nitish Nayak
	 */

	public void validateAdjuvantsPage(String k) throws TwfException, BiffException, IOException, InterruptedException{

		//double click on IE..
		driver=DriverFactory.getDriver();
		Actions action=new Actions(driver);
		action.moveToElement(getElementByUsing("AnswerPlot_Product_Tab")).build().perform();
		Thread.sleep(3000);
		getElementByUsing("AnswerPlot_Adjuvants_Tab").click();

		Thread.sleep(2000);
		if(twoWindow()==1){

			action.moveToElement(getElementByUsing("AnswerPlot_Product_Tab")).build().perform();
			Thread.sleep(3000);
			getElementByUsing("AnswerPlot_Adjuvants_Tab").click();

		}

		System.out.println("----> "+twoWindow());
		switchToLastWindow();

		waitForElement(getElementByUsing("AnswerPlot_AdjuvantsHeader"), 40);


		/*if(waitForVisibilty(getElementByUsing("AnswerPlot_AdjuvantsHeader_Header"), 40)){

			if(getElementByUsing("AnswerPlot_AdjuvantsProduct_Tab").isDisplayed()){


			}


				else{

			Assert.assertFalse(getElementByUsing("AnswerPlot_AdjuvantsProduct_Tab").isDisplayed(), "Unable to find the Adjuvants Product Tab..");

				}
	}else{

		Assert.assertFalse(waitForVisibilty(getElementByUsing("AnswerPlot_AdjuvantsHeader_Header"), 40), "Not able to find the page even after waiting for 40 seconds..");

	}
		 */	




	}
	//------------------------------------------------------------------------------------------------------------------------------------------------------------//


	//------------------------------------------------------------------------------------------------------------------------------------------------------------//	
	/*@Validate Two Window Opens
	 * @Author-Nitish.Nayak
	 */
	public int twoWindow() throws TwfException{

		driver=DriverFactory.getDriver();
		windowHandles=driver.getWindowHandles();
		System.out.println("windowHandles--> "+windowHandles);
		return windowHandles.size();

	}
	//------------------------------------------------------------------------------------------------------------------------------------------------------------//


	//------------------------------------------------------------------------------------------------------------------------------------------------------------//	
	/*@Switch to the last window
	 * @Author-Nitish Nayak
	 */
	public void switchToLastWindow() throws TwfException{

		driver=DriverFactory.getDriver();
		windowHandles=driver.getWindowHandles();

		System.out.println("----> "+windowHandles);

		for (String windowHandle : windowHandles) {

			driver.switchTo().window(windowHandle);
		}


	}
	//------------------------------------------------------------------------------------------------------------------------------------------------------------//


	//------------------------------------------------------------------------------------------------------------------------------------------------------------//	
	/*@Switch to frame by Id or Name
	 * @Author-Nitish Nayak
	 */

	public void switchToFrame(String frame) throws TwfException{


		driver=DriverFactory.getDriver();
		driver.switchTo().frame(frame);
	}
	//------------------------------------------------------------------------------------------------------------------------------------------------------------//


	//------------------------------------------------------------------------------------------------------------------------------------------------------------//
	/*@Switch to default content
	 * @Author-Nitish Nayak
	 */

	public void switchToDefault() throws TwfException{

		driver=DriverFactory.getDriver();
		driver.switchTo().defaultContent();

	}
	//------------------------------------------------------------------------------------------------------------------------------------------------------------//


	//------------------------------------------------------------------------------------------------------------------------------------------------------------//	
	/*@Wait for the visibilty of the web element and return boolean
	 * @Author-Nitish.Nayak
	 */

	public boolean waitForVisibilty(WebElement ele,long timeout) throws TwfException{

		boolean found=true;
		driver=DriverFactory.getDriver();
		WebDriverWait wait=new WebDriverWait(driver, timeout);

		try{
			wait.until(ExpectedConditions.visibilityOf(ele));

		}catch(TimeoutException e){

			found=false;
		}

		return found;

	}
	//------------------------------------------------------------------------------------------------------------------------------------------------------------//


	//------------------------------------------------------------------------------------------------------------------------------------------------------------//
	/*@Check if the click here link is present for OTM dairy and login to the app and validate domain D
	 * @Author-Nitish Nayak
	 */

	public void loginAndValidateDomain(Map<String,String> data) throws BiffException, IOException, TwfException, InterruptedException, InvalidFormatException{

		try {
			if(getElementByUsing("OTMDairy_ClickHere_Link").isDisplayed()){

				getElementByUsing("OTMDairy_ClickHere_Link").click();

			}
		}


		catch(Exception e){

			System.out.println("No click here link...");
		}
		waitForElement(getElementByUsing("OTMDairyUserName"), 30);
		getElementByUsing("OTMDairyUserName").sendKeys(data.get("OTMUserName"));
		getElementByUsing("OTMDairyPassword").sendKeys(data.get("OTMPassword"));
		getElementByUsing("OTMDairy_LogOn_Button").click();

		//Give time for the page to settle down


		System.out.println("frame id or name "+PageObjectLoader.getPageObject("OTMDairy_ToolBar_Frame").getTargetId());
		switchToFrame(PageObjectLoader.getPageObject("OTMDairy_ToolBar_Frame").getTargetId());
		waitForVisibilty(getElementByUsing("OTMDairy_Domain_Link"), 30);
		String demo=getElementByUsing("OTMDairy_Domain_Link").getText();
		System.out.println("-----> value "+demo);

		//validate if the domain name contains D
		if(demo.contains(data.get("DomainVerify"))){


		}else{

			addExceptionToReport("The domain name is incorrect", "", "");

		}

		switchToDefault();

	}
	//------------------------------------------------------------------------------------------------------------------------------------------------------------//


	//------------------------------------------------------------------------------------------------------------------------------------------------------------//	
	/*@Check if the click here link is present for OTM dairy and login to the app and validate domain D
	 * @Author-Nitish Nayak
	 */

	public void loginAndValidateDomainWinfield(Map<String,String> data) throws BiffException, IOException, TwfException, InterruptedException, InvalidFormatException{

		try {
			if(getElementByUsing("OTMDairy_ClickHere_Link").isDisplayed()){

				getElementByUsing("OTMDairy_ClickHere_Link").click();

			}
		}


		catch(Exception e){

			System.out.println("No click here link...");
		}
		waitForElement(getElementByUsing("OTMWinfieldUserName"), 30);
		getElementByUsing("OTMWinfieldUserName").sendKeys(data.get("OTMWinfieldUserName"));
		getElementByUsing("OTMWinfieldPassword").sendKeys(data.get("OTMWinfieldPassword"));
		getElementByUsing("OTMDairy_LogOn_Button").click();

		//Give time for the page to settle down


		System.out.println("frame id or name "+PageObjectLoader.getPageObject("OTMDairy_ToolBar_Frame").getTargetId());
		switchToFrame(PageObjectLoader.getPageObject("OTMDairy_ToolBar_Frame").getTargetId());
		waitForVisibilty(getElementByUsing("OTMDairy_Domain_Link"), 30);
		String demo=getElementByUsing("OTMDairy_Domain_Link").getText();
		System.out.println("-----> value "+demo);

		//validate if the domain name contains D
		if(demo.contains(data.get("DomainVerify"))){


		}else{

			addExceptionToReport("The domain name is incorrect", "", "");

		}

		switchToDefault();

	}
	//------------------------------------------------------------------------------------------------------------------------------------------------------------//


	//------------------------------------------------------------------------------------------------------------------------------------------------------------//
	/*Login and validate Domain
	 * @Author-Nitish.Nayak
	 */
	public void loginAndValidateDomainWithoutClickHereLink(Map<String,String> data) throws BiffException, IOException, TwfException, InterruptedException, InvalidFormatException{



		waitForElement(getElementByUsing("OTMDairyUserName"), 30);
		getElementByUsing("OTMDairyUserName").sendKeys(data.get("OTMUserName"));
		getElementByUsing("OTMDairyPassword").sendKeys(data.get("OTMPassword"));
		getElementByUsing("OTMDairy_LogOn_Button").click();

		//Give time for the page to settle down


		System.out.println("frame id or name "+PageObjectLoader.getPageObject("OTMDairy_ToolBar_Frame").getTargetId());
		switchToFrame(PageObjectLoader.getPageObject("OTMDairy_ToolBar_Frame").getTargetId());
		waitForVisibilty(getElementByUsing("OTMDairy_Domain_Link"), 30);
		String demo=getElementByUsing("OTMDairy_Domain_Link").getText();
		System.out.println("-----> value "+demo);

		//validate if the domain name contains D
		if(demo.contains(data.get("DomainVerify"))){


		}else{

			addExceptionToReport("The domain name is incorrect", "", "");

		}

		switchToDefault();

	}
	//------------------------------------------------------------------------------------------------------------------------------------------------------------//


	//------------------------------------------------------------------------------------------------------------------------------------------------------------//

	/*@Navigate to Order release click on search and validate record is populated for Dairy domain
	 *@Author-Nitish.Nayak
	 */

	public void recordIsPopulatedOnSearch(String k) throws BiffException, InvalidFormatException, TwfException, IOException{


		switchToFrame(PageObjectLoader.getPageObject("OTMDairy_SideBar_Frame").getTargetId());
		getElementByUsing("OTMDairy_OrderManagement_Link").click();
		getElementByUsing("OTMDairy_OrderRelease_Link").click();
		getElementByUsing("OTMDairy_OrderReleaseSubLink_Link").click();

		switchToDefault();
		//validating the Order release page..

		switchToFrame(PageObjectLoader.getPageObject("OTMDairy_MainBody_Frame").getTargetId());
		switchToFrame(PageObjectLoader.getPageObject("OTMDairy_FinderBody_Frame").getTargetId());

		waitForElement(getElementByUsing("OTMDairy_OrderReleaseId_TextBox"), 20);
		getElementByUsing("OTMDairy_QuoteID_TextBox").isDisplayed();
		getElementByUsing("OTMDairy_DomainName_TextBox").isDisplayed();

		switchToDefault();

		//switch to the button frame 
		switchToFrame(PageObjectLoader.getPageObject("OTMDairy_MainBody_Frame").getTargetId());
		switchToFrame(PageObjectLoader.getPageObject("OTMDairy_Buttons_Frame").getTargetId());

		getElementByUsing("OTMDairy_Search_Link").click();

		switchToDefault();

		switchToFrame(PageObjectLoader.getPageObject("OTMDairy_MainBody_Frame").getTargetId());
		switchToFrame(PageObjectLoader.getPageObject("OTMDairy_ResultBody_Frame").getTargetId());

		waitForElement(getElementByUsing("OTMDairy_ReleaseOrderRecord_Table"), 40);	

		driver=DriverFactory.getDriver();

		List<WebElement> elements=driver.findElements(By.xpath(PageObjectLoader.getPageObject("OTMDairy_ReleaseOrderRecord_Table").getTargetId()));

		System.out.println("The size of the list is--> "+elements.size());

		if(elements.size()>=1){


		}else{

			addExceptionToReport("No records found...", "", "");
		}

	}
	//------------------------------------------------------------------------------------------------------------------------------------------------------------//


	//------------------------------------------------------------------------------------------------------------------------------------------------------------//
	/*@Navigate to Order release click on search and validate record is populated for Dairy domain
	 *@Author-Nitish.Nayak
	 */

	public void recordIsPopulatedOnSearchWinfieldDomain(String k) throws BiffException, InvalidFormatException, TwfException, IOException{


		switchToFrame(PageObjectLoader.getPageObject("OTMDairy_SideBar_Frame").getTargetId());
		waitForElement(getElementByUsing("OTM_ShipmentView_Link"), 20);
		getElementByUsing("OTM_ShipmentView_Link").click();


		switchToDefault();
		//validating the Order release page..

		switchToFrame(PageObjectLoader.getPageObject("OTMDairy_MainBody_Frame").getTargetId());
		switchToFrame(PageObjectLoader.getPageObject("OTMDairy_FinderBody_Frame").getTargetId());

		waitForElement(getElementByUsing("OTM_ShipmentId_TextField"), 20);
		getElementByUsing("OTM_OrderReleaseId_TextField").isDisplayed();

		switchToDefault();

		//switch to the button frame 
		switchToFrame(PageObjectLoader.getPageObject("OTMDairy_MainBody_Frame").getTargetId());
		switchToFrame(PageObjectLoader.getPageObject("OTMDairy_Buttons_Frame").getTargetId());

		getElementByUsing("OTMDairy_Search_Link").click();

		switchToDefault();

		switchToFrame(PageObjectLoader.getPageObject("OTMDairy_MainBody_Frame").getTargetId());
		switchToFrame(PageObjectLoader.getPageObject("OTMDairy_ResultBody_Frame").getTargetId());

		waitForElement(getElementByUsing("OTMDairy_ReleaseOrderRecord_Table"), 40);	

		driver=DriverFactory.getDriver();

		List<WebElement> elements=driver.findElements(By.xpath(PageObjectLoader.getPageObject("OTMDairy_ReleaseOrderRecord_Table").getTargetId()));

		System.out.println("The size of the list is--> "+elements.size());

		if(elements.size()>=1){


		}else{

			addExceptionToReport("No records found...", "", "");
		}

	}
	//------------------------------------------------------------------------------------------------------------------------------------------------------------//


	//------------------------------------------------------------------------------------------------------------------------------------------------------------//

	/*@Navigate to Order release click on search and validate record is populated for Feed domain
	 *@Author-Nitish.Nayak
	 */

	public void recordIsPopulatedOnSearchFeed(String k) throws BiffException, InvalidFormatException, TwfException, IOException{


		switchToFrame(PageObjectLoader.getPageObject("OTMDairy_SideBar_Frame").getTargetId());
		getElementByUsing("OTMDairy_OrderManagement_Link").click();
		getElementByUsing("FeedDairy_OrderRelease_Link").click();
		getElementByUsing("FeedDairy_OrderReleaseEdit_Link").click();

		switchToDefault();
		//validating the Order release page..

		switchToFrame(PageObjectLoader.getPageObject("OTMDairy_MainBody_Frame").getTargetId());
		switchToFrame(PageObjectLoader.getPageObject("OTMDairy_FinderBody_Frame").getTargetId());

		waitForElement(getElementByUsing("FeedDairy_EarlyPickUp_TextBox"), 20);
		getElementByUsing("FeedDairy_LatePickUp_TextBox").isDisplayed();
		getElementByUsing("FeedDairy_EarlyDeliveryDate_TextBox").isDisplayed();

		switchToDefault();

		//switch to the button frame 
		switchToFrame(PageObjectLoader.getPageObject("OTMDairy_MainBody_Frame").getTargetId());
		switchToFrame(PageObjectLoader.getPageObject("OTMDairy_Buttons_Frame").getTargetId());

		getElementByUsing("OTMDairy_Search_Link").click();

		switchToDefault();

		switchToFrame(PageObjectLoader.getPageObject("OTMDairy_MainBody_Frame").getTargetId());
		switchToFrame(PageObjectLoader.getPageObject("OTMDairy_ResultBody_Frame").getTargetId());

		waitForElement(getElementByUsing("OTMDairy_ReleaseOrderRecord_Table"), 40);	

		driver=DriverFactory.getDriver();

		List<WebElement> elements=driver.findElements(By.xpath(PageObjectLoader.getPageObject("OTMDairy_ReleaseOrderRecord_Table").getTargetId()));

		System.out.println("The size of the list is--> "+elements.size());

		if(elements.size()>=1){


		}else{

			addExceptionToReport("No records found...", "", "");
		}

	}
	//------------------------------------------------------------------------------------------------------------------------------------------------------------//


	//------------------------------------------------------------------------------------------------------------------------------------------------------------//
	/*@Validate FeedBusinessServices
	 * @Author-Nitish.Nayak
	 */

	public void validateFeedBusinessServices(String k) throws TwfException, BiffException, IOException, InterruptedException{


		/*waitForElement(getElementByUsing("FeedBusiness_Link"), 20);
		getElementByUsing("FeedBusiness_Link").click();*/

		/*switchToLastWindow();

		try{
			getElementByUsing("CustomerSupport_Link").isDisplayed();
			//waitForElement(getElementByUsing("CustomerSupport_Link"),1);

		}catch(Exception e){

			refreshPage();
		}*/
		try{
			getElementByUsing("CustomerSupport_Link").isDisplayed();

		}catch(Exception e){

			refreshPage();
		}
		getElementByUsing("BrowserReq_Link").isDisplayed();
		getElementByUsing("Destination_TextBox").isDisplayed();
		getElementByUsing("StartDate_TextBox").isDisplayed();
		getElementByUsing("EndDate_TextBox").isDisplayed();
		getElementByUsing("SalesReports_Link").click();

		switchToSecondWindow("");

		waitForElement(getElementByUsing("Menu_Link"), 30);
		getElementByUsing("Admin_Link").isDisplayed();
		getElementByUsing("DataInfo_Link").isDisplayed();
		getElementByUsing("CustomerReport_Link").isDisplayed();
		getElementByUsing("SalesReport_Link").isDisplayed();


		//temporary comment-------

		/*getElementByUsing("SalesReports_Link").click();
		//Thread.sleep(3000);
		switchToLastWindow();
		//switchFocusToFirstWindow("");
		waitForElement(getElementByUsing("Menu_Link"), 15);
		waitForElement(getElementByUsing("Admin_Link"), 15);
		getElementByUsing("Admin_Link").isDisplayed();
		getElementByUsing("DataInfo_Link").isDisplayed();
		getElementByUsing("CustomerReport_Link").isDisplayed();
		getElementByUsing("SalesReport_Link").isDisplayed();*/
		//closeTheWindow("");

		//closeTheCurrentSetTheFocusToFirstWindow("");

	}
	//------------------------------------------------------------------------------------------------------------------------------------------------------------//


	//------------------------------------------------------------------------------------------------------------------------------------------------------------//	
	/*Validate FeedBusinessServices QA
	 * @Author-Nitish.Nayak
	 */
	public void validateFeedBusinessServicesQA(String k) throws TwfException, BiffException, IOException, InterruptedException{


		/*waitForElement(getElementByUsing("FeedBusiness_Link"), 20);
		getElementByUsing("FeedBusiness_Link").click();*/

		switchToLastWindow();

		try{
			getElementByUsing("CustomerSupport_Link").isDisplayed();
			//waitForElement(getElementByUsing("CustomerSupport_Link"),1);

		}catch(Exception e){

			refreshPage();
		}

		Select dropdown=new Select(getElementByUsing("FBSDropDown"));
		dropdown.selectByVisibleText("CSS Sales");

		waitForElement(getElementByUsing("CustomerSupport_Link"), 30);	
		getElementByUsing("CustomerSupport_Link").isDisplayed();


		waitForElement(getElementByUsing("CustomerSupport_Link"), 30);	
		getElementByUsing("CustomerSupport_Link").isDisplayed();




		getElementByUsing("BrowserReq_Link").isDisplayed();
		getElementByUsing("Destination_TextBox").isDisplayed();
		getElementByUsing("StartDate_TextBox").isDisplayed();
		getElementByUsing("EndDate_TextBox").isDisplayed();
		getElementByUsing("SalesReports_Link").click();

		switchToSecondWindow("");

		waitForElement(getElementByUsing("Menu_Link"), 30);
		getElementByUsing("Admin_Link").isDisplayed();
		getElementByUsing("DataInfo_Link").isDisplayed();
		getElementByUsing("CustomerReport_Link").isDisplayed();
		getElementByUsing("SalesReport_Link").isDisplayed();


		//temporary comment-------

		/*getElementByUsing("SalesReports_Link").click();
		//Thread.sleep(3000);
		switchToLastWindow();
		//switchFocusToFirstWindow("");
		waitForElement(getElementByUsing("Menu_Link"), 15);
		waitForElement(getElementByUsing("Admin_Link"), 15);
		getElementByUsing("Admin_Link").isDisplayed();
		getElementByUsing("DataInfo_Link").isDisplayed();
		getElementByUsing("CustomerReport_Link").isDisplayed();
		getElementByUsing("SalesReport_Link").isDisplayed();*/
		//closeTheWindow("");

		//closeTheCurrentSetTheFocusToFirstWindow("");

	}
	//------------------------------------------------------------------------------------------------------------------------------------------------------------//


	//------------------------------------------------------------------------------------------------------------------------------------------------------------//
	/*@Validate ConnectThree
	 * @Author-Nitish.Nayak
	 */

	public void validateConnectThreeLink(String k) throws TwfException, BiffException, IOException, InterruptedException{


		waitForElement(getElementByUsing("Connect_Link"), 20);
		getElementByUsing("Connect_Link").click();
		switchToSecondWindow("");
		try{
			waitForElement(getElementByUsing("StewardCompliance_Tab"), 30);
			getElementByUsing("Inquiry_Tab").isDisplayed();
			getElementByUsing("Prepay_Tab").isDisplayed();
			getElementByUsing("Suggestions_Tab").isDisplayed();
		}catch(Exception e){

			Alert alert=driver.switchTo().alert();
			alert.accept();

		}

	}
	//------------------------------------------------------------------------------------------------------------------------------------------------------------//


	//------------------------------------------------------------------------------------------------------------------------------------------------------------//
	/*@Validate MOP Link
	 * @Author-Nitish.Nayak
	 */

	public void validateMPOLink(String k) throws TwfException, BiffException, IOException, InterruptedException{


		getElementByUsing("MilkProducer_Tab").click();
		switchToSecondWindow("");
		waitForElement(getElementByUsing("Overview_Tab"), 10);
		getElementByUsing("Production_Tab").isDisplayed();
		getElementByUsing("Financials_Tab").isDisplayed();
		getElementByUsing("Contracting_Tab").isDisplayed();

	}
	//------------------------------------------------------------------------------------------------------------------------------------------------------------//


	//------------------------------------------------------------------------------------------------------------------------------------------------------------//
	/*@Validate Equinox Link
	 * @Author-Nitish.Nayak
	 */

	public void validateEquinoxLink(String k) throws TwfException, BiffException, IOException, InterruptedException{


		getElementByUsing("EmeraldEquinox_Tab").click();
		switchToSecondWindow("");
		waitForElement(getElementByUsing("PPG_Tab"), 10);
		getElementByUsing("Focus_Tab").isDisplayed();
		getElementByUsing("WinFiledUnitedWholeSale_Tab").isDisplayed();
	}
	//------------------------------------------------------------------------------------------------------------------------------------------------------------//


	//------------------------------------------------------------------------------------------------------------------------------------------------------------//	
	/*@Validate AnswerPlotDetails Link
	 * @Author-Nitish.Nayak
	 */
	public void validateAnswerPlotAdmin(String k) throws TwfException, BiffException, IOException, InterruptedException, InvalidFormatException{


		waitForElement(getElementByUsing("AnswerPlotDetails_Tab"), 20);
		getElementByUsing("AnswerPlotDetails_Tab").click();
		waitForElement(getElementByUsing("Search_DropDown"), 10);

		try{
			getElementByUsing("Record_Table").isDisplayed();
			driver=DriverFactory.getDriver();
			List<WebElement> elements=driver.findElements(By.xpath(PageObjectLoader.getPageObject("Record_Table").getTargetId()));
			System.out.println("Record--> "+elements.size());
			if(elements.size()<1){

				addExceptionToReport("The table does not have any record...", "", "");
			}
		}catch(Exception e){

			addExceptionToReport("The table is not present...", "", "");
		}

	}
	//------------------------------------------------------------------------------------------------------------------------------------------------------------//


	//------------------------------------------------------------------------------------------------------------------------------------------------------------//
	/*@Validate FeedLicenseManufacturing Link
	 * @Author-Nitish.Nayak
	 */

	public void validateFeedLicensingManufacturing(String k) throws TwfException, BiffException, IOException, InterruptedException, InvalidFormatException{


		waitForElement(getElementByUsing("FeedLicensingManufacturing_Link"), 10);

		driver=DriverFactory.getDriver();
		WebElement element = driver.findElement(By.xpath(PageObjectLoader.getPageObject("FeedLicensingManufacturing_Link").getTargetId()));
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", element);
		//getElementByUsing("FeedLicensingManufacturing_Link").click();
		switchToSecondWindow("");
		waitForElement(getElementByUsing("IngredientSpecificationManual_Link"), 10);
		getElementByUsing("QualityAssuranceManual_Link");
		getElementByUsing("IngredientSpecificationManual_Link").click();

		waitForElement(getElementByUsing("Specifications_Link"), 10);
		getElementByUsing("Specifications_Link").click();

		//check whether the list of links is present
		driver=DriverFactory.getDriver();
		List<WebElement> elements=driver.findElements(By.xpath(PageObjectLoader.getPageObject("SpecificationLinks_List").getTargetId()));

		if(elements.size()<1){

			addExceptionToReport("No Links available in the Specifications link", "", "");

		}else{

			getElementByUsing("SpecificationsFirst_Link").click();
			//waiting for the pdf to load..
			Thread.sleep(7000);
			takeScreenShotPDF();

		}

	}
	//------------------------------------------------------------------------------------------------------------------------------------------------------------//


	//------------------------------------------------------------------------------------------------------------------------------------------------------------//	
	/*@Validate FeedLicenseManufacturingQA Link
	 * @Author-Nitish.Nayak
	 */

	public void validateFeedLicensingManufacturingQA(String k) throws TwfException, BiffException, IOException, InterruptedException, InvalidFormatException{


		driver=DriverFactory.getDriver();
		WebDriverWait wait=new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOf(getElementByUsing("FeedLicensingManufacturing_Link2")));

		WebElement element = driver.findElement(By.xpath(PageObjectLoader.getPageObject("FeedLicensingManufacturing_Link2").getTargetId()));
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", element);

		System.out.println("-->window "+driver.getWindowHandles());

		if(driver.getWindowHandles().size()==1){
			element = driver.findElement(By.xpath(PageObjectLoader.getPageObject("FeedLicensingManufacturing_Link2").getTargetId()));
			executor = (JavascriptExecutor)driver;
			executor.executeScript("arguments[0].click();", element);

		}

		//getElementByUsing("FeedLicensingManufacturing_Link").click();
		switchToSecondWindow("");
		waitForElement(getElementByUsing("IngredientSpecificationManual_Link"), 10);
		getElementByUsing("QualityAssuranceManual_Link");
		getElementByUsing("IngredientSpecificationManual_Link").click();

		waitForElement(getElementByUsing("Specifications_Link"), 10);
		getElementByUsing("Specifications_Link").click();

		//check whether the list of links is present
		driver=DriverFactory.getDriver();
		List<WebElement> elements=driver.findElements(By.xpath(PageObjectLoader.getPageObject("SpecificationLinks_List").getTargetId()));

		if(elements.size()<1){

			addExceptionToReport("No Links available in the Specifications link", "", "");

		}else{

			getElementByUsing("SpecificationsFirst_Link").click();
			//waiting for the pdf to load..
			Thread.sleep(7000);
			takeScreenShotPDF();

		}

	}
	//------------------------------------------------------------------------------------------------------------------------------------------------------------//


	//------------------------------------------------------------------------------------------------------------------------------------------------------------//
	/*@Validate AnswerPlotDetails Link
	 * @Author-Nitish.Nayak
	 */

	public void validatePurinaFeedManual(String k) throws TwfException, BiffException, IOException, InterruptedException, InvalidFormatException{


		waitForElement(getElementByUsing("PurinaFeedManual_Link"), 30);
		getElementByUsing("PurinaFeedManual_Link").click();
		switchToSecondWindow("");
		waitForElement(getElementByUsing("Cattle_Link"), 20);

		driver=DriverFactory.getDriver();
		WebElement element = driver.findElement(By.xpath(PageObjectLoader.getPageObject("Cattle_Link").getTargetId()));
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", element);
		//getElementByUsing("Cattle_Link").click();

		//wait for the list of the PDF documents to be displayed then click on the first PDF document....

		waitForElement(getElementByUsing("PDFLink_Lists"), 180);
		getElementByUsing("PDFLink_Lists").click();

		validatePDFDocumentOpened("");


	}
	//------------------------------------------------------------------------------------------------------------------------------------------------------------//


	//------------------------------------------------------------------------------------------------------------------------------------------------------------//	
	/*@Switch to third window and validate PDF document opened
	 * @Author-Nitish.Nayak
	 */

	public void validatePDFDocumentOpened(String k) throws TwfException, InterruptedException{

		driver=DriverFactory.getDriver();
		windowHandles=driver.getWindowHandles();

		//switching to the window where PDF document would open up...
		int i=0;
		while(i<=2){

			for (String windowHandle : windowHandles) {

				driver.switchTo().window(windowHandle);

			}

			i++;

		}

		Thread.sleep(10000);
		String Url=driver.getCurrentUrl();
		String pdf="openpdf";
		System.out.println("The title of the page is----> "+Url);

		if(!Pattern.compile(Pattern.quote(pdf), Pattern.CASE_INSENSITIVE).matcher(Url).find()){

			addExceptionToReport("The link doesnot contains PDF.....", "", "");
		}else{

			Thread.sleep(2000);
			takeScreenShotPDF();
		}

	}
	//------------------------------------------------------------------------------------------------------------------------------------------------------------//


	//------------------------------------------------------------------------------------------------------------------------------------------------------------//	
	/*@Switch The focus to the first window
	 * @Author-Nitish.Nayak
	 */

	public void switchFocusToFirstWindow(String k) throws TwfException{

		driver=DriverFactory.getDriver();
		windowHandles=driver.getWindowHandles();
		System.out.println("---> "+windowHandles);
		for (String windowHandle : windowHandles) {

			driver.switchTo().window(windowHandle);
			break;

		}

	}
	//------------------------------------------------------------------------------------------------------------------------------------------------------------//


	//------------------------------------------------------------------------------------------------------------------------------------------------------------//	

	/* @Refresh the page..
	 * @Author-Nitish.Nayak
	 */

	public void refreshPage() throws TwfException{

		driver=DriverFactory.getDriver();
		driver.navigate().refresh();

	}
	//------------------------------------------------------------------------------------------------------------------------------------------------------------//


	//------------------------------------------------------------------------------------------------------------------------------------------------------------//	
	/*@Return the first window handle
	 * @Author-Nitish.Nayak
	 */

	public  String returnFirstWindowHandle(String k) throws TwfException{

		driver=DriverFactory.getDriver();
		String firstHandle= driver.getWindowHandle();
		return firstHandle;

	}
	//------------------------------------------------------------------------------------------------------------------------------------------------------------//


	//------------------------------------------------------------------------------------------------------------------------------------------------------------//	
	/*@@Switch focus to the second window
	 * @Author-Nitish.Nayak
	 */

	public void switchToSecondWindow(String k) throws TwfException{

		int i=0;

		driver=DriverFactory.getDriver();
		windowHandles=driver.getWindowHandles();
		System.out.println("----> "+windowHandles);
		while(i<2){

			for (String windowHandle : windowHandles) {

				driver.switchTo().window(windowHandle);

			}
			i++;

		}


	}
	//------------------------------------------------------------------------------------------------------------------------------------------------------------//

	//------------------------------Temporary methods--------------------------------------------------------------------
	/*@Store the parent window handle in a string
	 * @Author-Nitish.Nayak
	 */
	public void parentWindowHandle(String k) throws TwfException{

		driver=DriverFactory.getDriver();
		parentWindow=driver.getWindowHandle();


	}



	/*@Switch to the parent window
	 * @Author-Nitish.Nayak
	 */
	public void switchToParent(String k) throws TwfException{

		driver=DriverFactory.getDriver();
		driver.switchTo().window(parentWindow);


	}

	/*@Switch accept parent
	 * @Author-Nitish.Nayak
	 */

	public void switchAcceptParent(String k) throws TwfException{

		driver=DriverFactory.getDriver();
		windowHandles=driver.getWindowHandles();
		for (String windowHandle : windowHandles) {

			if(!windowHandle.equals(parentWindow)){

				driver.switchTo().window(windowHandle);
			}else{
				continue;
			}

		}

	}

	/*@Close if not the parent window
	 *@Author-Nitish.Nayak
	 */

	public void closeIfNotParent(String k) throws TwfException{

		driver=DriverFactory.getDriver();
		windowHandles=driver.getWindowHandles();
		for (String windowHandle : windowHandles) {

			if(!windowHandle.equals(parentWindow)){
				driver.switchTo().window(windowHandle).close();

			}
		}
	}



	/*@Store the second window handle
	 * @Author-Nitish.Nayak.
	 */

	public void storeSecondWindowHandle(String k) throws TwfException{

		driver=DriverFactory.getDriver();
		for (String windowHandle : windowHandles) {

			if(!windowHandle.equals(parentWindow)){

				secondWindow=windowHandle;
			}else{

				continue;
			}


		}

	}

	/*@Close second window
	 * @Author-Nitish.Nayak
	 */

	public void switchToThirdWindow(String k) throws TwfException{

		driver=DriverFactory.getDriver();
		for (String windowhandle : windowHandles) {

			if(!(windowhandle.equals(parentWindow)&& windowhandle.equals(secondWindow))){

				driver.switchTo().window(windowhandle);

			}else{
				continue;
			}

		}

	}
	//---------------------temporary-----------------------------------------------------------------------


	//------------------------------------------------------------------------------------------------------------------------------------------------------------//
	/*@Take the screenshot and give it a name in the format -TestCaseName followed by Time of execution 
	 * @Author-Nitish.Nayak
	 */

	public void takeScreenShotPDF() throws TwfException{

		driver=DriverFactory.getDriver();
		File src=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {

			String path="./pdfScreenShot/"+currentDateAndTime();
			String currentDir=System.getProperty("user.dir");
			FileUtils.copyFile(src, new File(currentDir + "\\pdfScreenShot\\" + currentDateAndTime() + ".png"));
			//FileUtils.copyFile(src, new File(currentDir + "\\screenshots\\" + currentDateAndTime() + ".png"));

		} catch (IOException e) {
			// TODO Auto-generated catch block

			System.out.println("Something is wrong....");
			e.printStackTrace();
		}



	}
	//------------------------------------------------------------------------------------------------------------------------------------------------------------//


	//------------------------------------------------------------------------------------------------------------------------------------------------------------//
	/*@Current system date
	 *@Author-Nitish.Nayak
	 */
	public String currentDateAndTime(){

		String dateTime="";

		DateFormat sdf = new SimpleDateFormat("MMddyyyy_HHmmss");
		Date date = new Date();
		dateTime=sdf.format(date).toString();
		System.out.println("----> "+dateTime);
		//System.out.println(DateTimeFormatter.ofPattern("yyy/MM/dd").format(localDate));

		return dateTime;
	}
	//------------------------------------------------------------------------------------------------------------------------------------------------------------//


	//------------------------------------------------------------------------------------------------------------------------------------------------------------//	
	/*@Get Current time
	 * @Author-Nitish.Nayak
	 */
	public String currentTime(){

		String time="";
		LocalDateTime now = LocalDateTime.now();
		time=DateTimeFormatter.ofPattern("hh:mm:ss").format(now).toString();

		return time;
	}
	//------------------------------------------------------------------------------------------------------------------------------------------------------------//


	//------------------------------------------------------------------------------------------------------------------------------------------------------------//	
	public void auth(String k) throws TwfException{

		driver=DriverFactory.getDriver();
		driver.get("http://sysadmin:rAwh1dehat@ahpap569.elandolakes.com:15402/stellent/idcplg?IdcService=GET_DOC_PAGE&Action=GetTemplatePage&Page=HOME_PAGE&Auth=internet/");

	}
	//------------------------------------------------------------------------------------------------------------------------------------------------------------//


	//------------------------------------------------------------------------------------------------------------------------------------------------------------//	
	/*@Check if the doc file is downloaded or not
	 * @Author-Nitish.Nayak
	 */

	public boolean isFileDownloaded(String k) {
		System.out.println("In the method..");
		String downloadPath="c:/Users/"+System.getProperty("user.name")+"/Downloads/";
		System.out.println("After path..");
		String fileName="USERMANUAL_internal.docx";
		System.out.println("After UserManual..");
		boolean flag = false;
		File dir = new File(downloadPath);
		File[] dir_contents = dir.listFiles();

		for (int i = 0; i < dir_contents.length; i++) {
			if (dir_contents[i].getName().equals(fileName))
				return flag=true;
		}

		System.out.println("Flag--> "+flag);
		return flag;
	}
	//------------------------------------------------------------------------------------------------------------------------------------------------------------//


	//------------------------------------------------------------------------------------------------------------------------------------------------------------//	
	/*@File is downloaded
	 * @Author-Nitish.Nayak
	 */

	public void fileDownloaded(String k) throws TwfException, InterruptedException, BiffException, IOException, AWTException, InvalidFormatException{



		getElementByUsing("PriceList_UserManual_Tab").isDisplayed();
		Thread.sleep(4000);
		//getElementByUsing("PriceList_UserManual_Tab").click();
		driver=DriverFactory.getDriver();
		WebElement element = driver.findElement(By.id(PageObjectLoader.getPageObject("PriceList_UserManual_Tab").getTargetId()));
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", element);
		//waiting for the file to download..
		Thread.sleep(15000);

		if(!DriverFactory.getBrowserName().contains("chrome")){

			Robot robo=new Robot();
			robo.keyPress(KeyEvent.VK_ALT);
			robo.keyPress(KeyEvent.VK_S);
			robo.keyRelease(KeyEvent.VK_S);
			robo.keyRelease(KeyEvent.VK_ALT);


		}



		Thread.sleep(15000);

		if(!isFileDownloaded("")){

			addExceptionToReport("User Manual document failed to download...", "", "");

		}


	}
	//------------------------------------------------------------------------------------------------------------------------------------------------------------//


	//------------------------------------------------------------------------------------------------------------------------------------------------------------//	
	/*Open the document file and take the screen shot for it
	 * @Author-Nitish.Nayak
	 */

	public void validateDocumentOpen(String k) throws BiffException, IOException, TwfException, InterruptedException, InvalidFormatException, AWTException{

		getElementByUsing("PriceList_UserManual_Tab").isDisplayed();
		Thread.sleep(4000);
		//getElementByUsing("PriceList_UserManual_Tab").click();
		driver=DriverFactory.getDriver();
		WebElement element = driver.findElement(By.id(PageObjectLoader.getPageObject("PriceList_UserManual_Tab").getTargetId()));
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", element);
		//waiting for the file to download..
		Thread.sleep(10000);
		Robot robo=new Robot();
		robo.keyPress(KeyEvent.VK_ALT);
		robo.keyPress(KeyEvent.VK_O);
		robo.keyRelease(KeyEvent.VK_O);
		robo.keyRelease(KeyEvent.VK_ALT);

		Thread.sleep(8000);

		robo.keyPress(KeyEvent.VK_TAB);
		robo.keyRelease(KeyEvent.VK_TAB);

		robo.keyPress(KeyEvent.VK_TAB);
		robo.keyRelease(KeyEvent.VK_TAB);

		robo.keyPress(KeyEvent.VK_TAB);
		robo.keyRelease(KeyEvent.VK_TAB);

		robo.keyPress(KeyEvent.VK_TAB);
		robo.keyRelease(KeyEvent.VK_TAB);

		robo.keyPress(KeyEvent.VK_ENTER);

		Thread.sleep(10000);
		takeScreenShotPDF();

	}
	//------------------------------------------------------------------------------------------------------------------------------------------------------------//


	//------------------------------------------------------------------------------------------------------------------------------------------------------------//
	/*@Enter the authentication username and password
	 * @Author-Nitish.Nayak
	 */

	public void sendAuthenticationCred(String k) throws AWTException, TwfException, InterruptedException, BiffException, IOException{

		/*System.out.println("In here...");
		String text = "sysadmin";
		StringSelection stringSelection = new StringSelection(text);
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.setContents(stringSelection, stringSelection);

		Robot robo=new Robot();
		robo.keyPress(KeyEvent.VK_ALT);
		robo.keyPress(KeyEvent.VK_TAB);
		robo.keyRelease(KeyEvent.VK_ALT);
		robo.keyRelease(KeyEvent.VK_TAB);

		robo.keyPress(KeyEvent.VK_CONTROL);
		robo.keyPress(KeyEvent.VK_V);
		robo.keyRelease(KeyEvent.VK_CONTROL);
		robo.keyRelease(KeyEvent.VK_V);



		Thread.sleep(15000);*/

		driver=DriverFactory.getDriver();
		driver.get("http://"+"sysadmin:rAwh1dehat@"+"ahpap569.elandolakes.com:15402");
		waitForElement(getElementByUsing("CMEXDoctitle_TextBox"), 30);
		getElementByUsing("CMEXContent_TextBox").isDisplayed();
		getElementByUsing("CMEXReleaseDateFrom_TextBox").isDisplayed();
		getElementByUsing("CMEXReleaseDateTo_TextBox").isDisplayed();
		getElementByUsing("CMEXReleaseFullText_TextBox").isDisplayed();
		System.out.println("Completetd");

	}
	//------------------------------------------------------------------------------------------------------------------------------------------------------------//


	//------------------------------------------------------------------------------------------------------------------------------------------------------------//	
	/*@More info link click on it and move forward
	 * @Author-Nitish.Nayak
	 */

	public void clickMoreInfo(String k) throws TwfException{

		driver=DriverFactory.getDriver();

		if(DriverFactory.getBrowserName().contains("iexplore")){
			try{

				waitForElement(getElementByUsing("MOPLMoreInfo_Link"), 10);
				getElementByUsing("MOPLMoreInfo_Link").click();
				waitForElement(getElementByUsing("MOPLGotoWebLink"), 20);
				getElementByUsing("MOPLGotoWebLink").click();
				Thread.sleep(3000);
				Robot robo=new Robot();
				robo.keyPress(KeyEvent.VK_TAB);
				robo.keyRelease(KeyEvent.VK_TAB);
				robo.keyPress(KeyEvent.VK_ENTER);
				robo.keyRelease(KeyEvent.VK_ENTER);

			}catch(Exception e){



			}
		}	
	}
	//------------------------------------------------------------------------------------------------------------------------------------------------------------//


	//------------------------------------------------------------------------------------------------------------------------------------------------------------//	
	/*@More info link click on it and move forward
	 * @Author-Nitish.Nayak
	 */

	public void clickMoreInfoWithoutRobo(String k) throws TwfException{

		driver=DriverFactory.getDriver();

		if(DriverFactory.getBrowserName().contains("iexplore")){
			try{

				waitForElement(getElementByUsing("MOPLMoreInfo_Link"), 10);
				getElementByUsing("MOPLMoreInfo_Link").click();
				waitForElement(getElementByUsing("MOPLGotoWebLink"), 20);
				getElementByUsing("MOPLGotoWebLink").click();
				Thread.sleep(3000);
				/*Robot robo=new Robot();
			robo.keyPress(KeyEvent.VK_TAB);
			robo.keyRelease(KeyEvent.VK_TAB);
			robo.keyPress(KeyEvent.VK_ENTER);
			robo.keyRelease(KeyEvent.VK_ENTER);*/

			}catch(Exception e){



			}
		}	
	}
	//------------------------------------------------------------------------------------------------------------------------------------------------------------//


	//------------------------------------------------------------------------------------------------------------------------------------------------------------//	
	/*@Validate MIMS Link
	 * @Author-Nitish.Nayak
	 */

	public void validateMIMSLink(String k) throws BiffException, InvalidFormatException, IOException, TwfException{

		driver=DriverFactory.getDriver();

		waitForElement(getElementByUsing("MIMS_Link1"), 15);
		getElementByUsing("MIMS_Link1").click();

		switchToLastWindow();

		waitForElement(getElementByUsing("MIMS_MapSlide"), 20);
		getElementByUsing("MIMS_ImageSection").isDisplayed();


	}
	//------------------------------------------------------------------------------------------------------------------------------------------------------------//

	
	//----------------------------------------------------------------------------------------------//
			/*@Check if Additional Authentication page appears 
			 * @Author-Nitish.Nayak
			 */
	//---------------------------------------------------------------------------------------------//
			public boolean checkForAdditionalAuthenticationPage(String k) throws TwfException, BiffException, IOException{

				boolean appears=true;
				driver=DriverFactory.getDriver();
				try{
					waitForElement(getElementByUsing("AdditionalAuthe_Text"),5);
					System.out.println("---> authentication text"+getElementByUsing("AdditionalAuthe_Text").getText());
				}catch(Exception e){

					appears=false;
				}

				return appears;

			}
	
	
	
	//----------------------------------------------------------------------------------------------//
			/*@Navigate To Home Page
			 * @Author-Nitish.Nayak
			 */
	//----------------------------------------------------------------------------------------------//
			public void navigateToHomePage(Map<String,String> data) throws TwfException, BiffException, IOException, InterruptedException{

				//If checkForAdditionalAuthenticationIsTrue then send the security answer 

				if(checkForAdditionalAuthenticationPage("")){

					//choose the role 
	/*
					if(data.get("Role").equalsIgnoreCase("NBQualityControl")){
						waitForElement(getElementByUsing("NBQualityControlRole"), 10);
						getElementByUsing("NBQualityControlRole").click();

					}


					if(data.get("Role").equalsIgnoreCase("NBCashApp")){
						waitForElement(getElementByUsing("NBCashAppRole"), 10);
						getElementByUsing("NBCashAppRole").click();

					}

					if(data.get("Role").equalsIgnoreCase("NB Landed costs & customer")){
						waitForElement(getElementByUsing("NBLandedCostsAndCustomerRole"), 10);
						getElementByUsing("NBLandedCostsAndCustomerRole").click();

					}
					
					if(data.get("Role").equalsIgnoreCase("NB Procurement")){
						
						waitForElement(getElementByUsing("ProcurementRole"), 10);
						getElementByUsing("ProcurementRole").click();
					}*/

					waitForElement(getElementByUsing("Question_Text"), 30);

					String question=getElementByUsing("Question_Text").getText();
					
					System.out.println("Question--> "+question);

					if(question.contains("What is the name of a college")){

						getElementByUsing("Answer_TextBox").sendKeys(data.get("CollegeApplied"));
						getElementByUsing("Sumit_Button").click();

					}else if(question.contains("In what city does your ")){

						getElementByUsing("Answer_TextBox").sendKeys(data.get("SiblingCity"));
						getElementByUsing("Sumit_Button").click();

					}else if(question.contains("What was your childhood nickname")){

						getElementByUsing("Answer_TextBox").sendKeys(data.get("Childhoodname"));
						getElementByUsing("Sumit_Button").click();

					}

					getElementByUsing("Continue_Button").click();

					waitForElement(getElementByUsing("List_Tab"), 20);


				}else{
					//click on the continue button
					getElementByUsing("Continue_Button").click();

				}


			}
			
			
			
			
			
	//--------------------------------------------------------------------------------//
			/*@Logout from NetSuite
			 * @Author-Nitish.Nayak
			 */
	//-------------------------------------------------------------------------------//
			public void logoutNetSuite(String k) throws TwfException, BiffException, IOException, InterruptedException{
				
				driver=DriverFactory.getDriver();
				Actions builder=new Actions(driver);
				builder.moveToElement(getElementByUsing("LogoutSection")).build().perform();
				waitForElement(getElementByUsing("logout"), 20);
				getElementByUsing("logout").click();
				waitForElement(getElementByUsing("Email"), 20);
				//Thread.sleep(5000);
			}
			
			
	//----------------------------------------------------------------------------//
			/*@Validate Address Page
			 * @Author-Nitish.Nayak
			 */
   //--------------------------------------------------------------------------//
			public void addressBookRevision(String k) throws BiffException, TwfException, IOException{
				
				driver=DriverFactory.getDriver();
				waitForElement(getElementByUsing("AddressBookRevisionTitle"), 20);
				String text=getElementByUsing("AddressBookRevisionTitle").getText();
				System.out.println("--> "+text);
				if(!text.contains(" Address Book Revisions - Work With Addresses")){
					
					addExceptionToReport("Page did not load properly..", "", "");
					
				}
				
				driver.switchTo().defaultContent();
			}
			
			
	//-------------------------------------------------------------------------------//
		/*@If alert present accept it 
		 * @Author-Nitish.Nayak
		 */
	//------------------------------------------------------------------------------//
			
			public void acceptOkButton(String k) throws TwfException, InterruptedException, AWTException{
				
				driver=DriverFactory.getDriver();
				/*WebDriverWait wait=new WebDriverWait(driver, 5);
				try{
				wait.until(ExpectedConditions.alertIsPresent());*/
				Thread.sleep(2000);
				Robot robo=new Robot();
				robo.keyPress(KeyEvent.VK_ENTER);
				robo.keyRelease(KeyEvent.VK_ENTER);
				/*}catch(Exception e ){
					
					System.out.println("No alert found..");
					
					
				}*/
				
				
			}
	public void checkPage() {
		// TODO Auto-generated method stub

	}













}
