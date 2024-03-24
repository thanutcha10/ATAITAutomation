package ataitauten;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class ATAauto {
    public static void main(String[] args) throws InterruptedException {

        // System.setProperty("webdriver.chrome.driver",
        // "C:\\browserdrivers\\chromedriver.exe");
        WebDriverManager.chromedriver().setup();
        ChromeDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        driver.manage().window().maximize();

        WebElement username = driver.findElementByXPath("//*[@id=\"user-name\"]");
        username.sendKeys("standard_user");
        Thread.sleep(1000);

        WebElement password = driver.findElementByXPath("//*[@id=\"password\"]");
        password.sendKeys("secret_sauce");
        Thread.sleep(1000);

        WebElement loginButton = driver.findElementByXPath("//*[@id=\"login-button\"]");
        loginButton.click();

        // Validate that logged in successfully.
        String currentUrl1 = driver.getCurrentUrl();
        System.out.println("Your're login to " + currentUrl1);
        if (currentUrl1.contains("https://www.saucedemo.com/inventory.html")) {
            System.out.println("---- Validate that you're logged in successfully. ----");
        } else {
            System.out.println("---- Fail: Logged in unsuccessfully. ----");
            driver.close();
        }
        Thread.sleep(2000);

        // Sorting the price from high to low.
        WebElement dropDownPrice = driver.findElementByXPath("//*[@id=\"header_container\"]/div[2]/div/span/select");
        Select dropdownPriceSelect = new Select(dropDownPrice);
        dropdownPriceSelect.selectByValue("hilo");
        Thread.sleep(2000);

        // Complete the purchase for 2 items which has $15.99 price.
        List<WebElement> inventoryList = driver.findElements(By.xpath("//*[@class=\"inventory_list\"]/div"));
        for (WebElement inventoryItem : inventoryList) {
            WebElement itemPrice = inventoryItem.findElement(By.className("inventory_item_price"));
            if (itemPrice.getText().equals("$15.99")) {
                WebElement addButton = inventoryItem.findElement(By.tagName("button"));
                addButton.click();
            }
        }
        Thread.sleep(2000);

        // WebElement selectItem1 =
        // driver.findElementByXPath("//*[@id=\"add-to-cart-sauce-labs-bolt-t-shirt\"]");
        // selectItem1.click();
        // Thread.sleep(1000);

        // WebElement selectItem2 =
        // driver.findElementByXPath("//*[@id=\"add-to-cart-test.allthethings()-t-shirt-(red)\"]");
        // selectItem2.click();
        // Thread.sleep(1000);

        WebElement selectCart = driver.findElementByXPath("//*[@id=\"shopping_cart_container\"]/a");
        selectCart.click();
        Thread.sleep(1000);

        WebElement checkoutButton = driver.findElementByXPath("//*[@id=\"checkout\"]");
        checkoutButton.click();
        Thread.sleep(1000);

        WebElement firstname = driver.findElementByXPath("//*[@id=\"first-name\"]");
        firstname.sendKeys("Thanutcha");
        Thread.sleep(1000);

        WebElement lastname = driver.findElementByXPath("//*[@id=\"last-name\"]");
        lastname.sendKeys("Chamnoi");
        Thread.sleep(1000);

        WebElement postcode = driver.findElementByXPath("//*[@id=\"postal-code\"]");
        postcode.sendKeys("10110");
        Thread.sleep(1000);

        WebElement continueButton = driver.findElementByXPath("//*[@id=\"continue\"]");
        continueButton.click();
        Thread.sleep(2000);

        List<WebElement> cartList = driver.findElements(By.xpath("//*[@class=\"cart_list\"]/div"));
        int Totalsummary = cartList.size() - 2;
        System.out.println("Total checkout summary is: " + Totalsummary);

        for (int i = 2; i < cartList.size(); i++) {
            WebElement cartItem = cartList.get(i);
            System.out.println(cartItem.getText());
        }

        List<WebElement> summaryInfo = driver.findElements(By.xpath("//*[@class=\"summary_info\"]/div"));
        int realsummaryPayment = Math.min(summaryInfo.size() - 2, 7);
        for (int i = 0; i <= realsummaryPayment; i++) {
            WebElement summaryPayment = summaryInfo.get(i);
            System.out.println(summaryPayment.getText());
        }
        Thread.sleep(2000);

        // Process to Checkout
        WebElement finishButton = driver.findElementByXPath("//*[@id=\"finish\"]");
        finishButton.click();

        System.out.println("---- Thank you! Payment done Successfully. ----");

        WebElement backHomeButton = driver.findElementByXPath("//*[@id=\"back-to-products\"]");
        backHomeButton.click();

        WebElement burgerMenu = driver.findElementByXPath("//*[@id=\"react-burger-menu-btn\"]");
        burgerMenu.click();

        WebDriverWait wait = new WebDriverWait(driver, 20);
        WebElement logOutButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link")));
        Thread.sleep(2000);
        logOutButton.click();
        // Validate that user logout.
        if (currentUrl1.contains("https://www.saucedemo.com/")) {
            System.out.println("---- User logout successfully. ----");
        } else {
            System.out.println("---- Fail: Logout unsuccess. ----");
            driver.close();
        }
        Thread.sleep(2000);

        // Validate the “locked_out_user” user.
        username = driver.findElementByXPath("//*[@id=\"user-name\"]");
        username.sendKeys("locked_out_user");
        Thread.sleep(1000);

        password = driver.findElementByXPath("//*[@id=\"password\"]");
        password.sendKeys("secret_sauce");
        Thread.sleep(1000);

        loginButton = driver.findElementByXPath("//*[@id=\"login-button\"]");
        loginButton.click();

        String currentUrl2 = driver.getCurrentUrl();
        if (currentUrl2.contains("https://www.saucedemo.com/inventory.html")) {
            System.out.println("---- Validate that you're logged in successfully. ----");
        } else {
            WebElement messageError = driver.findElement(By.xpath("//*[@class=\"error-message-container error\"]"));
            System.out.println("---- " + messageError.getText() + " ----");
            Thread.sleep(2000);
            driver.close();
        }

    }

}
