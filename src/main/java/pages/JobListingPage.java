package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class JobListingPage extends BasePage {
    private By seeAllQAJobsButton = By.xpath("//a[contains(text(), 'See all QA jobs')]");
    private By locationDropdown = By.id("select2-filter-by-location-container");
    private By locationOptions = By.xpath("//li[contains(@class, 'select2-results__option')]");
    private By jobListings = By.className("position-list-item");
    private By viewRoleButton = By.xpath(".//a[contains(@class, 'btn-navy')]");

    public JobListingPage(WebDriver driver) {
        super(driver);
    }

    public void clickSeeAllQAJobs() {
        WebElement button = driver.findElement(seeAllQAJobsButton);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", button);
    }

    public void filterJobs(String location) {
        try {
            System.out.println("🌍 Lokasyon filtresi açılıyor...");

            // **Gelişmiş sayfa yükleme bekleme metodunu çağır**
            waitForPageLoad();

            // **Dropdown'un DOM içinde olup olmadığını bekle**
            WebElement dropdown = waitForElementToBePresent(locationDropdown, 15);

            // **Dropdown açılmazsa JavaScript ile zorla aç**
            dropdown.click();

            // **Dropdown içindeki seçenekleri bekle**
            List<WebElement> optionsList = waitForElementListToBeVisible(locationOptions, 15);

            // **Belirtilen lokasyonu seç**
            WebElement selectedOption = optionsList.stream()
                    .filter(option -> option.getText().trim().equalsIgnoreCase(location))
                    .findFirst()
                    .orElse(null);

            if (selectedOption != null) {
                selectedOption.click();
                System.out.println("✅ '" + location + "' seçildi!");
            } else {
                throw new NoSuchElementException("❌ '" + location + "' bulunamadı!");
            }

        } catch (Exception e) {
            System.out.println("❌ Lokasyon filtresi sırasında hata oluştu: " + e.getMessage());
        }
    }

    public void waitForJobListings() {
        System.out.println("⏳ İş ilanlarının yüklenmesi bekleniyor...");
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(jobListings));
        System.out.println("✅ İş ilanları yüklendi.");
    }

    public boolean areJobsDisplayed() {
        return driver.findElements(jobListings).size() > 0;
    }

    public void clickFirstJobViewRole() {
        try {
            waitForJobListings(); // İş ilanlarının yüklendiğini bekle!

            // **İlanların DOM içinde olup olmadığını tekrar bul**
            List<WebElement> jobItems = waitForElementListToBeVisible(By.className("position-list-item"), 10);
            System.out.println("🔹 " + jobItems.size() + " iş ilanı bulundu, ilk ilana odaklanılıyor...");

            if (jobItems.isEmpty()) {
                throw new NoSuchElementException("❌ Hiç iş ilanı bulunamadı!");
            }

            // **İlk iş ilanını bul**
            WebElement firstJob = jobItems.get(0);

            // **İlk iş ilanı içindeki "View Role" butonunu doğru şekilde bul**
            WebElement viewRoleButton = wait.until(ExpectedConditions.elementToBeClickable(
                    firstJob.findElement(By.cssSelector("a.btn.btn-navy.rounded"))
            ));
            System.out.println("✅ 'View Role' butonu bulundu!");

            // **Butonu ekrana kaydır ve görünür hale getir**
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", viewRoleButton);
            Thread.sleep(500); // Küçük bir bekleme

            // **Butona tıklama işlemini yap**
            try {
                viewRoleButton.click();
                System.out.println("✅ 'View Role' butonuna tıklandı!");
            } catch (Exception e) {
                System.out.println("⚠️ Selenium click başarısız, JavaScript click deniyoruz...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", viewRoleButton);
            }

        } catch (Exception e) {
            System.out.println("❌ 'View Role' tıklama sırasında hata oluştu: " + e.getMessage());
        }
    }

    public boolean isOnLeverApplicationPage() {
        try {
            Thread.sleep(3000);
            driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());
            return driver.getCurrentUrl().contains("lever.co");
        } catch (Exception e) {
            System.out.println("❌ Lever başvuru sayfasına yönlendirilirken hata oluştu: " + e.getMessage());
            return false;
        }
    }
}