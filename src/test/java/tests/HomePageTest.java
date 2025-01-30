package tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class HomePageTest extends BaseTest{

    @Test
    public void testHomePageIsOpened() {
        driver.get("https://useinsider.com/");
        Assertions.assertTrue(driver.getTitle().contains("Insider"), "Ana sayfa açılmadı");
    }
}
