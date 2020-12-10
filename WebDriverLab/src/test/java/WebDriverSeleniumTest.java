import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class WebDriverSeleniumTest {

    private WebDriver driver;
    private boolean isPlacingAnOrderButtonAvailable=false;

    @BeforeMethod(alwaysRun = true)
    public void browserSetup(){
        System.setProperty("webdriver.chrome.driver","C:/Drivers/chromedriver.exe");
        driver=new ChromeDriver();
    }

    @Test
    public void placingAnOrderWithAnEmptyShoppingCart() throws InterruptedException {

        driver.get("https://www.dcrussia.ru/on/demandware.store/Sites-DC-RU-Site/ru_RU/Cart-Show");
        try {
            List<WebElement> deleteButtons = waitAllWebElementLocatedBy(driver, By
                    .xpath("//button[@class='cart__button-remove-product btn-remove']"));

            System.out.println(deleteButtons.size());

            for (int i = 0; i < deleteButtons.size(); i++) {
                deleteButtons.get(i).click();
            }
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }

        try {
            WebElement acceptOrder = waitWebElementLocatedBy(driver, By
                    .xpath("//input[@class='cart__button btn-secure-checkout button label-animated']"));
        }
        catch (Exception ex)
        {
            isPlacingAnOrderButtonAvailable=true;
            System.out.println(ex.getMessage());
        }

        Assert.assertTrue(isPlacingAnOrderButtonAvailable,"Button place order not missing!!!!!!!!!");
    }

    @AfterMethod(alwaysRun = true)
    public void browserTearDown()
    {
        driver.quit();
        driver=null;
    }



    public static List<WebElement> waitAllWebElementLocatedBy(WebDriver driver, By by)
    {
        return (new WebDriverWait(driver, 3))
                .until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
    }

    public static WebElement waitWebElementLocatedBy(WebDriver driver, By by)
    {
        return (new WebDriverWait(driver, 3))
                .until(ExpectedConditions.presenceOfElementLocated(by));
    }
}
