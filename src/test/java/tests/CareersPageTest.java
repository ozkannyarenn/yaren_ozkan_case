package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pages.CareersPage;
import pages.HomePage;

public class CareersPageTest extends BaseTest{
    @Test
    public void testCareersPageIsOpened() {
        driver.get("https://useinsider.com/");
        HomePage homePage = new HomePage(driver);
        homePage.goToCareersPage();

        CareersPage careersPage = new CareersPage(driver);
        Assertions.assertTrue(careersPage.isCareersPageLoaded(), "Kariyer sayfası tam yüklenmedi!");
    }
}
