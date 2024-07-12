import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.URL;
import java.util.List;

public class PriceFilterTest {
    private AppiumDriver<MobileElement> driver;

    @BeforeClass
    public void setUp() throws Exception {
        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        caps.setCapability(MobileCapabilityType.DEVICE_NAME, "Android Emulator");
        caps.setCapability(MobileCapabilityType.APP, "\"C:\\Users\\Luthvika\\Downloads\\Android.SauceLabs.Mobile.app.2.7.1.apk\"");

        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), caps);
    }

    @Test
    public void testPriceFilterHighToLow() {
    	 // Login
        driver.findElement(By.xpath("//input[@hint='Username']")).sendKeys("standard_user");
        driver.findElement(By.xpath("//input[@hint='Password']")).sendKeys("secret_sauce");
        driver.findElement (By.xpath("//button[@contentDescription='test-LOGIN']")); 
    	
        // Locate and click the sort/filter button
        driver.findElement(By.xpath("//button[@contentDescription='test-Modal Selector Button']"));

        // Select "Price: High to Low"
        driver.findElement(By.xpath("//android.widget.TextView[@text='Price (High to Low)]")).click();

        // Verify the products are sorted from high to low
        List<MobileElement> prices = driver.findElements(By.xpath("//button[@contentDescription='test-Price']"));
        for (int i = 1; i < prices.size(); i++) {
            double price1 = Double.parseDouble(prices.get(i - 1).getText().replace("$", ""));
            double price2 = Double.parseDouble(prices.get(i).getText().replace("$", ""));
            Assert.assertTrue(price1 >= price2, "Prices are not sorted from high to low");
        }
    }

    @Test
    public void testPriceFilterLowToHigh() {
        // Locate and click the sort/filter button
    	driver.findElement(By.xpath("//button[@contentDescription='test-Modal Selector Button']"));

        // Select "Price: Low to High"
        driver.findElement(By.xpath("//android.widget.TextView[@text='Price (Low to High)']")).click();

        // Verify the products are sorted from low to high
        List<MobileElement> prices = driver.findElements(By.xpath("//button[@contentDescription='test-Price']"));
        for (int i = 1; i < prices.size(); i++) {
            double price1 = Double.parseDouble(prices.get(i - 1).getText().replace("$", ""));
            double price2 = Double.parseDouble(prices.get(i).getText().replace("$", ""));
            Assert.assertTrue(price1 <= price2, "Prices are not sorted from low to high");
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
