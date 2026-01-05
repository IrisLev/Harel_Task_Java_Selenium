package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.Duration;

public class DatesPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public DatesPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Select start date + number of days from today
    public DatesPage selectDepartureDatePlusDays(int daysFromToday) {
        LocalDate startDate = LocalDate.now().plusDays(daysFromToday);
        String formattedDate = startDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        WebElement startInput = wait.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector("[data-hrl-bo='startDateInput_input']"))
        );
        startInput.clear();
        startInput.sendKeys(formattedDate);

        return this;
    }

    // Select return date + number of days from the departure date
    public DatesPage selectReturnDatePlusDays(int daysAfterStart) {
        // First, read the start date from the input
        WebElement startInput = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-hrl-bo='startDateInput_input']"))
        );
        String startValue = startInput.getAttribute("value");
        LocalDate startDate = LocalDate.parse(startValue, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        LocalDate endDate = startDate.plusDays(daysAfterStart);
        String formattedEndDate = endDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        WebElement endInput = wait.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector("[data-hrl-bo='endDateInput_input']"))
        );
        endInput.clear();
        endInput.sendKeys(formattedEndDate);

        return this;
    }

    // Verify total days between start and end date
    public DatesPage verifyTotalDays(int expectedDays) {
        WebElement startInput = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-hrl-bo='startDateInput_input']"))
        );
        WebElement endInput = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-hrl-bo='endDateInput_input']"))
        );

        LocalDate startDate = LocalDate.parse(startInput.getAttribute("value"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate endDate = LocalDate.parse(endInput.getAttribute("value"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        long actualDays = ChronoUnit.DAYS.between(startDate, endDate);
        if (actualDays != expectedDays) {
            throw new AssertionError("Expected total days: " + expectedDays + ", but was: " + actualDays);
        }

        return this;
    }

    // Go to passenger details page
    public InsuranceDetails goToPassengerDetails() {
        WebElement nextButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.cssSelector("[data-hrl-bo='wizard-next-button']"))
        );

        // Scroll and click
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", nextButton);
        nextButton.click();

        return new InsuranceDetails(driver);
    }
}

