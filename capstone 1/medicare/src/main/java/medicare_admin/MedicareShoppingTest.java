package medicare_admin;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class MedicareShoppingTest {

    private WebDriver driver;

    @BeforeTest
    public void setUp() {
        // Set up Chrome WebDriver
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        driver = new ChromeDriver(options);
    }

    @Test(priority = 1)
    public void shopMedicare() {
        // Step 1: Go to the Medicare homepage
        driver.get("http://localhost:8081/medicare/");

        // Step 2: Click on the Antipyretics button
        WebElement antipyreticsButton = driver.findElement(By.xpath("//*[@id='a_Antipyretics']"));
        antipyreticsButton.click();

        // Step 3: Verify the URL is correct
        Assert.assertEquals(driver.getCurrentUrl(), "http://localhost:8081/medicare/show/category/1/products");

        // Step 4: Verify the presence of product names
        Assert.assertTrue(driver.getPageSource().contains("Paracetamol"));
        Assert.assertTrue(driver.getPageSource().contains("Combiflame"));

        // Step 5: Click on the first product's "Add to Cart" button
        WebElement addToCartButton = driver.findElement(By.xpath("//*[@id='productListTable']/tbody/tr[1]/td[6]/a[2]/span"));
        addToCartButton.click();

        // Step 6: Verify the product is added to the cart
        WebElement cartPageText = driver.findElement(By.xpath("//body[contains(text(),'Paracetamol')]"));
        Assert.assertTrue(cartPageText.isDisplayed());

        // Step 7: Click on the Cart button
        WebElement cartButton = driver.findElement(By.xpath("//*[@id='cart']/tfoot/tr[2]/td[1]/a"));
        cartButton.click();

        // Step 8: Verify the URL is correct
        Assert.assertEquals(driver.getCurrentUrl(), "http://localhost:8081/medicare/show/all/products");

        // Step 9: Click on the second product's "Add to Cart" button
        WebElement addToCartButton2 = driver.findElement(By.xpath("//*[@id='productListTable']/tbody/tr[2]/td[6]/a[2]"));
        addToCartButton2.click();

        // Step 10: Verify the product is added to the cart again
        WebElement cartPageText2 = driver.findElement(By.xpath("//body[contains(text(),'Paracetamol')]"));
        Assert.assertTrue(cartPageText2.isDisplayed());

        // Step 11: Click on the Checkout button
        WebElement checkoutButton = driver.findElement(By.xpath("//*[@id='cart']/tfoot/tr[2]/td[4]/a"));
        checkoutButton.click();

        // Step 12: Verify the URL is correct
        Assert.assertEquals(driver.getCurrentUrl(), "http://localhost:8081/medicare/cart/checkout?execution=e5s1");

        // Step 13: Verify the address is visible
        WebElement addressText = driver.findElement(By.xpath("//body[contains(text(),'hunumath nagar near iti college siruguppa - 583121 karnataka - india')]"));
        Assert.assertTrue(addressText.isDisplayed());

        // Step 14: Click on the "Continue" button
        WebElement continueButton = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/div/div[1]/div/div/div/a"));
        continueButton.click();

        // Step 15: Verify the URL is correct
        Assert.assertEquals(driver.getCurrentUrl(), "http://localhost:8081/medicare/cart/checkout?execution=e5s2");

        // Step 16: Fill out credit card information
        WebElement cardNumberField = driver.findElement(By.xpath("//*[@id='cardNumber']"));
        cardNumberField.sendKeys("996403456749");

        WebElement expiryMonthField = driver.findElement(By.xpath("//*[@id='expityMonth']"));
        expiryMonthField.sendKeys("12");

        WebElement expiryYearField = driver.findElement(By.xpath("//*[@id='expityYear']"));
        expiryYearField.sendKeys("10");

        WebElement cvCodeField = driver.findElement(By.xpath("//*[@id='cvCode']"));
        cvCodeField.sendKeys("451");

        // Step 17: Click on the "Confirm Order" button
        WebElement confirmOrderButton = driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/div/div[2]/a"));
        confirmOrderButton.click();

        // Step 18: Verify the URL is correct
        Assert.assertEquals(driver.getCurrentUrl(), "http://localhost:8081/medicare/cart/checkout?execution=e5s2&_eventId_pay");

        // Step 19: Verify the "Your Order is Confirmed!" text
        WebElement orderConfirmationText = driver.findElement(By.xpath("//body[contains(text(),'Your Order is Confirmed!')]"));
        Assert.assertTrue(orderConfirmationText.isDisplayed());
    }

    @AfterTest
    public void tearDown() {
        // Close the browser
        if (driver != null) {
            driver.quit();
        }
    }
}
