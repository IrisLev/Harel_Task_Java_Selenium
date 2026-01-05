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



    public LandingPage selectContinent(String continent) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Build dynamic selector
        String cssSelector = String.format(
                "[data-hrl-bo='%s'], [data-hrl-bo='%s-selected'], [data-hrl-bo*='%s']",
                continent, continent, continent
        );

        WebElement element = wait.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector(cssSelector))
        );

        // Scroll into view
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});",
                element
        );

        wait.until(ExpectedConditions.elementToBeClickable(element));

        // Try regular click, fallback to JS click
        try {
            element.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }

        return this;
    }

    // Generic getter for assertions
    public WebElement getContinentElement(String continent) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String cssSelector = String.format("[data-hrl-bo='%s-selected']", continent);
        return wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(cssSelector)));
    }

    public DatesPage goToDatesPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement nextButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("button[data-hrl-bo='wizard-next-button']")
                )
        );

        nextButton.click();
        return new DatesPage(driver);
    }
}
