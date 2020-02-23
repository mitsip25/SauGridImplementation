package com.qa.hubspot.tests;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.qa.hubspot.base.BasePage;
import com.qa.hubspot.pages.LoginPage;
import com.qa.hubspot.util.Constants;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

public class LoginPageTest {

	BasePage basePage;
	Properties prop;
	RemoteWebDriver driver;
	LoginPage loginPage;
	DesiredCapabilities caps;

	@BeforeMethod
	@Parameters(value={"browser"})
	public void setUp(String browser) throws MalformedURLException {
//		caps = new DesiredCapabilities();
//		caps.setCapability("browserName", browser);
		basePage = new BasePage();
		prop = basePage.init_properties();
//		BasePage.tldriver.set(new RemoteWebDriver(new URL("http://192.168.99.100:4444/wd/hub"), caps));
		
		String browserName=null;
		
		if(browser.equals(null) || browser.equals("") || browser.isEmpty()){
			browserName = prop.getProperty("browser");
		}else{
			browserName=browser;
		}
		driver  = basePage.init_driver(browserName);
//		BasePage.getDriver().get(prop.getProperty("url"));
		driver.get(prop.getProperty("url"));
		loginPage = new LoginPage(driver);
	}

	@Description("login page title verification test...")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority = 1)
	public void loginPageTitleTest() {
		String title = loginPage.getLoginPageTitle();
		System.out.println("login page title is: " + title);
		Assert.assertEquals(title, Constants.LOGIN_PAGE_TITLE);
	}

	@Description("sign up link verification test...")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority = 2)
	public void signUpLinkTest() {
		Assert.assertTrue(loginPage.signUpLinkIsDisplayed());
	}

	@Description("valid login verification test...")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority = 3)
	public void loginTest() {
		loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}

	@AfterMethod
	public void tearDown() {
		driver.quit();
	}

}
