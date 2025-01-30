package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CareersPage extends BasePage {
    private By locationsBlock = By.xpath("//h3[@class='category-title-media ml-0']");
    private By teamsBlock = By.xpath("//h3[@class='category-title-media ml-0']");
    private By lifeAtInsiderBlock = By.xpath("//h2[.='Life at Insider']");

    public CareersPage(WebDriver driver) {
        super(driver);
    }

    public boolean isCareersPageLoaded() {
        return driver.findElement(locationsBlock).isDisplayed() &&
                driver.findElement(teamsBlock).isDisplayed() &&
                driver.findElement(lifeAtInsiderBlock).isDisplayed();
    }
}

