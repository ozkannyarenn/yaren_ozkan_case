package tests;

import pages.JobListingPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JobListingPageTest extends BaseTest {

    @Test
    public void testJobListingsWithFilterAndApplication() {
        driver.get("https://useinsider.com/careers/quality-assurance/");
        JobListingPage jobListingPage = new JobListingPage(driver);

        jobListingPage.clickSeeAllQAJobs();

        jobListingPage.filterJobs("Istanbul, Turkey");

         // İş ilanlarının yüklenmesini bekle

        Assertions.assertTrue(jobListingPage.areJobsDisplayed(), "❌ İş ilanları listelenmedi!");

        jobListingPage.clickFirstJobViewRole();

        Assertions.assertTrue(jobListingPage.isOnLeverApplicationPage(), "❌ Lever başvuru sayfasına yönlendirme başarısız!");
    }
}
