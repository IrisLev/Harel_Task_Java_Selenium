package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LandingPage {

    private final  WebDriver driver;

    public LandingPage(WebDriver driver) {
        this.driver = driver;
    }

    public LandingPage open(String url) {
        driver.get(url);
        return this;
    }

    public LandingPage clickFirstPurchaseButton() {
        driver.findElement(By.xpath("//button//*[normalize-space()='לרכישה בפעם הראשונה']")).click();
        return this;
    }

    public LandingPage selectAnyContinent() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Try different possible selectors - adjust based on actual HTML structure
        WebElement australia = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("[data-hrl-bo='australia'], [data-hrl-bo='australia-selected'], [data-hrl-bo*='australia']")
                )
        );

        // Scroll into view
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});",
                australia
        );

        // Wait a moment for scroll
        wait.until(ExpectedConditions.elementToBeClickable(australia));

        // Try regular click, fallback to JS click
        try {
            australia.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", australia);
        }

        return this;
    }

    public WebElement getAustraliaElement() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.cssSelector("[data-hrl-bo='australia-selected']")
                )
        );
    }

    public DatesPage goToDatesPage() {
        driver.findElement(By.xpath("//button[contains(text(),'הלאה')]")).click();
        return new DatesPage(driver);
    }
}
