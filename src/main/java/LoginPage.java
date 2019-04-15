import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    private WebDriver driver;



    public void logInWithValidData(){
        driver.findElement(By.id("txtUsername")).sendKeys("Admin");
        driver.findElement(By.id("txtPassword")).sendKeys("admin123");
        driver.findElement(By.id("btnLogin")).click();

        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals("https://opensource-demo.orangehrmlive.com/index.php/dashboard",
                currentUrl.toString(), "User zalogowany");
        String welcomeline = driver.findElement(By.id("welcome")).getText();
        Assert.assertTrue(welcomeline.contains("Admin"));
    }

    public void logInWithInvalidData(){
        driver.findElement(By.id("txtUsername")).sendKeys("Admi");
        driver.findElement(By.id("txtPassword")).sendKeys("admin123");
        driver.findElement(By.id("btnLogin")).click();

        String invalidCredentials = driver.findElement(By.id("spanMessage")).getText();
        Assert.assertEquals("Invalid credentials", invalidCredentials, "User niezalogowany");
    }
}
