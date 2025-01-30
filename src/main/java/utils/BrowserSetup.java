package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class BrowserSetup {

    public static WebDriver initializeBrowser(String browser) {
        WebDriver driver;

        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;

            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        return driver;
    }
}
