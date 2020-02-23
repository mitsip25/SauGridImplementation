package com.qa.hubspot.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * 
 * @author NaveenKhunteta
 *
 */
public class BasePage {

	//public WebDriver driver;
	Properties prop;
	DesiredCapabilities caps;
	
	public static ThreadLocal<RemoteWebDriver> tldriver = new ThreadLocal<RemoteWebDriver>();
	
	public static synchronized RemoteWebDriver getDriver() {
		return tldriver.get();
	}

	/**
	 * this method is used to initalize the driver on the basis of browser
	 * @param browser 
	 * 
	 * @return driver
	 * @throws MalformedURLException 
	 */
	public RemoteWebDriver init_driver(String browser) throws MalformedURLException {
		
		System.out.println("browser name is: "+ browser);
		caps = new DesiredCapabilities();
//		String browser = prop.getProperty("browser");
		caps.setCapability("browserName", browser);
		
		String url = prop.getProperty("url");

		if (browser.equals("chrome")) {
			WebDriverManager.chromedriver().setup();
			//driver = new ChromeDriver();
			//tldriver.set(new ChromeDriver());
			tldriver.set(new RemoteWebDriver(new URL("http://192.168.99.100:4444/wd/hub"), caps));
		} else if (browser.equals("firefox")) {
			WebDriverManager.firefoxdriver().setup();
			//driver = new FirefoxDriver();
			tldriver.set(new RemoteWebDriver(new URL("http://192.168.99.100:4444/wd/hub"), caps));
		} else {
			System.out.println("please define the proper browser value....");
		}
		
		getDriver().manage().deleteAllCookies();
		getDriver().manage().window().maximize();
		getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
//		driver.manage().window().maximize();
//		driver.manage().deleteAllCookies();
//		driver.get(url);
		//return driver;
		return getDriver();
	}

	/**
	 * this method is used to get the properties from config prop file
	 * 
	 * @return prop
	 */
	public Properties init_properties() {

		prop = new Properties();
		try {
			FileInputStream ip = new FileInputStream(".\\src\\main\\java\\com\\qa\\hubspot"
					+ "\\config\\config.properties");
			prop.load(ip);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;

	}

}
