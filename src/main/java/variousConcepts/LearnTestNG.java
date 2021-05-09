package variousConcepts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LearnTestNG {

	WebDriver driver;
	String browser = null;
	String url;
	
	@BeforeClass
	public void readConfig() {
		//BufferedReader //InputStream //FileReader //Scanner
		
		Properties prop = new Properties();
		
		try {
			
			InputStream input = new FileInputStream("./src/main/java/config/config.properties");
			prop.load(input);
			browser = prop.getProperty("browser");
			System.out.println("Browser used: " + browser);
			url =  prop.getProperty("url");
			
			
		}catch(IOException e) {
			
			e.printStackTrace();
			
		}
		
	}

	@BeforeMethod
	public void launchBrowser() {
		
		if(browser.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
			driver = new ChromeDriver();
			
		}else if(browser.equalsIgnoreCase("Firefox")) {
			System.setProperty("webdriver.gecko.driver", "drivers\\geckodriver.exe");
			driver = new FirefoxDriver();

		}
				
		
		// go to website
		driver.get(url);
		// maximize the window
		driver.manage().window().maximize();
		// delete the cookies
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	//@Test (priority=1) 
	public void loginTest() throws InterruptedException {
		
		Assert.assertEquals(driver.getTitle(), "Login - iBilling", "Wrong page!!!");

		// Element Lib
		WebElement USER_NAME_ELEMENT = driver.findElement(By.xpath("//input[@id='username']"));
		WebElement PASSWORD_ELEMENT = driver.findElement(By.xpath("//input[@id='password']"));
		WebElement SUBMIT_BUTTON_ELEMENT = driver.findElement(By.xpath("//button[@type='submit']"));
		
		
//		login data
		String loginId = "demo@techfios.com";
		String password = "abc123";
		
		USER_NAME_ELEMENT.sendKeys(loginId);
		PASSWORD_ELEMENT.sendKeys(password);
		SUBMIT_BUTTON_ELEMENT.click();
		
		WebElement DASHBOARD_TITLE_ELEMENT = driver.findElement(By.xpath("//h2[contains(text(), ' Dashboard ')]"));
		Assert.assertEquals(DASHBOARD_TITLE_ELEMENT.getText(), "Dashboard", "Wrong page!!!");

	}
	
	@Test (priority=2) 
	public void addCustomer() {
		
		Assert.assertEquals(driver.getTitle(), "Login - iBilling", "Wrong page!!!");

		// Element Lib
		WebElement USER_NAME_ELEMENT = driver.findElement(By.xpath("//input[@id='username']"));
		WebElement PASSWORD_ELEMENT = driver.findElement(By.xpath("//input[@id='password']"));
		WebElement SUBMIT_BUTTON_ELEMENT = driver.findElement(By.xpath("//button[@type='submit']"));
		
		
//		login data
		String loginId = "demo@techfios.com";
		String password = "abc123";
		
		//Test data or Mock data
		String fullName = "Test Janurary";
		String companyName = "Google";
		String eamil = "techfios@gmail.com";
		String phone = "21435523";
		
		
		USER_NAME_ELEMENT.sendKeys(loginId);
		PASSWORD_ELEMENT.sendKeys(password);
		SUBMIT_BUTTON_ELEMENT.click();
		
		WebElement DASHBOARD_TITLE_ELEMENT = driver.findElement(By.xpath("//h2[contains(text(), ' Dashboard ')]"));
		Assert.assertEquals(DASHBOARD_TITLE_ELEMENT.getText(), "Dashboard", "Wrong page!!!");
		
		WebElement CUSOMER_ELEMENT = driver.findElement(By.xpath("//*[@id=\"side-menu\"]/li[3]/a/span[1]"));
		WebElement ADD_CUSOMER_ELEMENT = driver.findElement(By.xpath("//*[@id=\"side-menu\"]/li[3]/ul/li[1]/a"));
		
		
		CUSOMER_ELEMENT.click();
		ADD_CUSOMER_ELEMENT.click();
		
		WebElement FULL_NAME_ELEMENT = driver.findElement(By.xpath("//*[@id=\"account\"]"));
		WebElement COMPANY_DROPDOWN_ELEMENT = driver.findElement(By.xpath("//select[@id='cid']"));
		WebElement EMAIL_ELEMENT = driver.findElement(By.xpath("//*[@id=\"email\"]"));
		
		
		FULL_NAME_ELEMENT.sendKeys(fullName);
		
		//Dropdown
		Select sel = new Select(COMPANY_DROPDOWN_ELEMENT);
		sel.selectByVisibleText(companyName);
		
		//Generate Random Number
		Random rnd = new Random();
		int randomNum = rnd.nextInt(999);
		
		//fill email
		EMAIL_ELEMENT.sendKeys(randomNum + eamil);

		
	}
	
	
	
	//@AfterMethod
	public void tearDown() {
		driver.close();
		driver.quit();
	}

}
