package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.sql.Time;
import java.time.Duration;
import java.util.List;

public class JobListingPage extends BasePage {
    private By seeAllQAJobsButton = By.xpath("//a[contains(text(), 'See all QA jobs')]");
    private By locationOptions = By.xpath("//li[contains(@class, 'select2-results__option')]");
    private By jobListings = By.className("position-list-item");
    private By viewRoleButton = By.xpath(".//a[contains(@class, 'btn-navy')]");
    private By locationDropdown = By.id("select2-filter-by-location-container");

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
            waitForPageLoad();

            int oldCount = driver.findElements(jobListings).size();

            WebElement dropdown = waitForElementToBePresent(locationDropdown, 15);

            wait.until(ExpectedConditions.visibilityOf(dropdown));
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
                waitForJobListingsRefresh(oldCount);

            } else {
                throw new NoSuchElementException("❌ '" + location + "' bulunamadı!");
            }

        } catch (Exception e) {
            System.out.println("❌ Lokasyon filtresi sırasında hata oluştu: " + e.getMessage());
        }
    }

    /**
     * "jobs-list" kısmının gerçekten yenilenmesi için,
     * eski ve yeni ilan sayısının farklı hale gelmesini bekliyoruz.
     */
    public void waitForJobListingsRefresh(int oldCount) {
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(15));

        customWait.until(driver -> {
            int newCount = driver.findElements(jobListings).size();
            return newCount != oldCount;
        });

        System.out.println("✅ İş ilanları yenilendi!");
    }

    public boolean areJobsDisplayed() {
        return driver.findElements(jobListings).size() > 0;
    }

    public void clickFirstJobViewRole() {
        try {

            List<WebElement> jobs = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(jobListings));

            WebElement firstJob = jobs.get(0);
            WebElement button = firstJob.findElement(viewRoleButton);

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);

        } catch (Exception e) {
            System.out.println("❌ 'View Role' tıklama sırasında hata oluştu: " + e.getMessage());
        }
    }

    public boolean isOnLeverApplicationPage() {
        try {
            driver.switchTo().window(driver.getWindowHandles().toArray()[1].toString());
            return driver.getCurrentUrl().contains("lever.co");
        } catch (Exception e) {
            System.out.println("❌ Lever başvuru sayfasına yönlendirilirken hata oluştu: " + e.getMessage());
            return false;
        }
    }
}