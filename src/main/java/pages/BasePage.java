package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import org.openqa.selenium.By;

import java.util.List;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // **WebDriverWait objesini başlat**

    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    // **Sayfanın tamamen yüklenmesini bekler**
    public void waitForPageLoad() {

        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // **WebDriverWait objesini başlat**

        // **1️⃣ Sayfanın tamamen yüklenmesini bekle (document.readyState = 'complete')**
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
        System.out.println("✅ Sayfa tamamen yüklendi!");

        // **2️⃣ Sayfadaki önemli bir elementin (örneğin ana içerik) yüklenmesini bekle**

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body")));
        System.out.println("✅ Ana sayfa içeriği tamamen yüklendi!");

        // **3️⃣ AJAX isteklerinin tamamlanmasını bekleyelim (Eğer gerekliyse)**

        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return jQuery.active == 0"));
        System.out.println("✅ Tüm AJAX istekleri tamamlandı!");

    }

    // **Dropdown'un DOM içinde var olup olmadığını kontrol eder**
    public WebElement waitForElementToBePresent(By locator, int timeout) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeout))
                .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    // **Elementin tıklanabilir olmasını bekler ve döndürür**
    public WebElement waitForElementToBeClickable(By locator, int timeout) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeout))
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    // **Elementin görünmesini bekler ve döndürür**
    public WebElement waitForElementToBeVisible(By locator, int timeout) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeout))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // **Birden fazla elementin görünmesini bekler ve liste olarak döndürür**
    public List<WebElement> waitForElementListToBeVisible(By locator, int timeout) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeout))
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }
}

