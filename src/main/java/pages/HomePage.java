package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {

    private By companyMenu = By.xpath("//a[contains(.,'Company')]");
    private By careersLink = By.xpath("//a[.='Careers']");

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void goToCareersPage() {
        driver.findElement(companyMenu).click();
        driver.findElement(careersLink).click();
    }
}
