package progi.project.eventovci;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class SeleniumTests {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        try {
            System.setProperty("webdriver.chrome.driver", "C:\\webdrivers\\chromedriver.exe");
            driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

        } catch (Exception e) {
            e.printStackTrace();
             Assertions.fail("Failed to initialize WebDriver");
        }
    }



    @Test
    public void loginValidTest() throws InterruptedException {

        driver.get("https://eventovci.onrender.com/login");

        WebElement element = driver.findElement(By.id("nameField"));
        element.sendKeys("registeredUserr");
        element = driver.findElement(By.id("sifrafild"));
        element.sendKeys("password");

        driver.findElement(By.cssSelector("button[type='submit']")).click();
        Thread.sleep(5000);

        String url = driver.getCurrentUrl();
        driver.quit();
        Assertions.assertEquals("https://eventovci.onrender.com/home", url);
    }

    @Test
    public void loginInvalidTest() throws InterruptedException {

        driver.get("https://eventovci.onrender.com/login");

        WebElement element = driver.findElement(By.id("nameField"));
        element.sendKeys("registeredUserr");
        element = driver.findElement(By.id("sifrafild"));
        element.sendKeys("wrongPassword");

        driver.findElement(By.cssSelector("button[type='submit']")).click();
        Thread.sleep(5000);

        String url = driver.getCurrentUrl();
        driver.quit();
        Assertions.assertEquals("https://eventovci.onrender.com/login", url);
    }

    @Test
    public void registerValidTest() throws InterruptedException {

        driver.get("https://eventovci.onrender.com/login");

        driver.findElement(By.cssSelector("button")).click();
        Thread.sleep(5000);

        WebElement element = driver.findElement(By.id("nameField"));
        element.sendKeys("NewUserRegisterrr");
        element = driver.findElement(By.id("sifrafild"));
        element.sendKeys("password");
        element = driver.findElement(By.id("email-field"));
        element.sendKeys("NewUserRegisterrr@gmail.com");

        driver.findElement(By.cssSelector("button[type='submit']")).click();
        Thread.sleep(5000);

        String url = driver.getCurrentUrl();
        driver.quit();
        Assertions.assertEquals("https://eventovci.onrender.com/home", url);
    }

    @Test
    public void registerInvalidTest() throws InterruptedException {

        driver.get("https://eventovci.onrender.com/login");

        driver.findElement(By.cssSelector("button")).click();
        Thread.sleep(5000);

        WebElement element = driver.findElement(By.id("nameField"));
        element.sendKeys("registerNewUserr");
        element = driver.findElement(By.id("sifrafild"));
        element.sendKeys("password");
        element = driver.findElement(By.id("email-field"));
        element.sendKeys("registerNewUser-wrongMail");

        driver.findElement(By.cssSelector("button[type='submit']")).click();
        Thread.sleep(5000);

        String url = driver.getCurrentUrl();
        driver.quit();
        Assertions.assertEquals("https://eventovci.onrender.com/login", url);
    }


    @Test
    public void addNewEventInvalid() throws InterruptedException {

        driver.get("https://eventovci.onrender.com/login");
        WebElement element = driver.findElement(By.id("nameField"));
        element.sendKeys("eventCoordinator");
        element = driver.findElement(By.id("sifrafild"));
        element.sendKeys("password");

        driver.findElement(By.cssSelector("button[type='submit']")).click();
        Thread.sleep(5000);

        driver.findElement(By.cssSelector("div.category img[alt='img for MOJ RAČUN']")).click();


        String url = driver.getCurrentUrl();
        Assertions.assertEquals("https://eventovci.onrender.com/my-account", url);

        driver.findElement(By.xpath("//button[text()='Dodaj događanje']")).click();

        url = driver.getCurrentUrl();
        Assertions.assertEquals("https://eventovci.onrender.com/add-event", url);

        driver.findElement(By.id("nameField")).sendKeys("Event Name");
        driver.findElement(By.id("typeField")).sendKeys("Koncert");
        driver.findElement(By.id("locationField")).sendKeys("Centar");
        driver.findElement(By.id("timeField")).sendKeys("2024-01-15T12:00");
        driver.findElement(By.id("durationField")).sendKeys("02:00");
        //driver.findElement(By.id("priceField")).sendKeys("0");
        driver.findElement(By.xpath("//textarea")).sendKeys("Event Description");


        driver.findElement(By.cssSelector("button.dodaj-dogadjanje")).click();
        Thread.sleep(5000);

        url = driver.getCurrentUrl();
        driver.quit();
        Assertions.assertEquals("https://eventovci.onrender.com/add-event", url);

    }
}
