package tests;

import utils.BrowserSetup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

public class BaseTest {
    protected WebDriver driver;

    @BeforeEach
    public void setup() {
        String browser = System.getProperty("browser", "chrome"); // Varsayılan: Chrome
        driver = BrowserSetup.initializeBrowser(browser);
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}