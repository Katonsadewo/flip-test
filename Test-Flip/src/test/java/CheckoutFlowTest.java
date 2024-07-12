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

public class CheckoutFlowTest {
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
    public void testCheckoutFlow() {
        // Login
        driver.findElement(By.xpath("//input[@hint='Username']")).sendKeys("standard_user");
        driver.findElement(By.xpath("//input[@hint='Password']")).sendKeys("secret_sauce");
        driver.findElement (By.xpath("//button[@contentDescription='test-LOGIN']")); 

        // Add product to cart
        driver.findElement(By.xpath("(//button[@contentDescription='test-ADD TO CHART'])[1]"));

        // Go to cart
        driver.findElement(By.xpath("//button[@contentDescription='test-Chart']"));

        // Proceed to checkout
        driver.findElement(By.xpath("//button[@contentDescription='test-CHECKOUT']")).click();

        // Enter user information
        driver.findElement(By.xpath("//input[@contentDescription='test-Firt Name']")).sendKeys("Katon");
        driver.findElement(By.xpath("//input[@contentDescription='test-Last Name']")).sendKeys("Sadewo"); 
        driver.findElement(By.xpath("//input[@contentDescription='test-Zip/Postal Code']")); 
        driver.findElement(By.xpath("//button[@contentDescription='test-CONTINUE']")).click();

        // Finish checkout
        driver.findElement(By.xpath("//button[@contentDescription='test-FINISH']")).click();

        // Verify success message
        String successMessage = driver.findElement(By.xpath("//[@text='THANK YOU FOR YOUR ORDER']")).getText();
        Assert.assertEquals(successMessage, "THANK YOU FOR YOUR ORDER", "Success message mismatch");
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
