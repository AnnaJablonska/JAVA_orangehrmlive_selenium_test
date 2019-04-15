import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestClass {

    private WebDriver driver;
    private DashboardPage action;


    //SETUP METHOD FOR DIFFERENT SYSTEMS
    @Before
    public void setUp() throws InterruptedException {
        String systemName = System.getProperty("os.name").toLowerCase();
        if (systemName.contains("windows")) {
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\Testarmy\\Desktop\\automationtest\\" +
                    "orange_hrmlive_opensource_demo\\src\\main\\resources\\chromedriver.exe");
        }
//        else if (systemName.contains("linux")) {
//            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/bin/chromedriver_linux");
//        } else {
//            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/bin/chromedriver_osx");
//        }
        driver = new ChromeDriver();
        driver.get("https://opensource-demo.orangehrmlive.com");
        driver.manage().window().maximize();
        Thread.sleep(1000);

    }

    //LOGIN PAGE --> TRY TO LOG IN WITH INVALID CREDENTIALS
    @Test
    public void loginValidCredentials(){
        action = new DashboardPage(driver);
        action.logInWIthValidCredentials();
        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals("User logged in correctly",
                currentUrl.toString(), "https://opensource-demo.orangehrmlive.com/index.php/dashboard");
        String welcomeLine = driver.findElement(By.id("welcome")).getText();
        Assert.assertTrue(welcomeLine.contains("Admin"));
    }

    //LOGIN PAGE --> TRY TO LOG IN WITH INVALID CREDENTIALS
    @Test
    public void loginInvalidCredentials(){
        action = new DashboardPage(driver);
        action.logInWithInvalidPass();
        String invalidCredentials = driver.findElement(By.id("spanMessage")).getText();
        Assert.assertEquals("Password is not correct. Test passed.", invalidCredentials, "Invalid credentials");
    }

    //DASHBOARD --> WELCOME --> LOGOUT
    @Test
    public void loggingOut(){
        action = new DashboardPage(driver);
        action.logInWIthValidCredentials();
        action.logOut();
        WebElement loginPanel = driver.findElement(By.id("logInPanelHeading"));
        Assert.assertEquals("User corrected logged out. Test passed", loginPanel.getText(), "LOGIN Panel");
    }

    //DASHBOARD --> WELCOME --> ABOUT
    @Test
    public void clickAboutBtn() throws InterruptedException {
        action = new DashboardPage(driver);
        action.logInWIthValidCredentials();
        action.openAndCloseAboutBtn();
//        Thread.sleep(1000);
        boolean aboutMessage = driver.findElement(By.id("companyInfo")).isDisplayed();
        Assert.assertTrue(aboutMessage);
    }

    //DASHBOARD --> PIM --> ADD EMPLOYEE
    @Test
    public void addingNewEmployee(){
        action = new DashboardPage(driver);
        action.logInWIthValidCredentials();
        action.addEmployee();
        WebElement personalDetails = driver.findElement(By.xpath("//*[@id=\"pdMainContainer\"]/div[1]/h1"));
        Assert.assertEquals("Employee added", personalDetails.getText(), "Personal Details");

        //taking a screenshot of the page with new employee
        ScreenshotUtils screenshot = new ScreenshotUtils(driver);
        screenshot.captureScreenshot(driver);
    }

    //DASHBOARD --> PIM --> REPORTS
    @Test
    public void addingReport(){
        action = new DashboardPage(driver);
        action.logInWIthValidCredentials();
        action.addReport();
    }

    //DASHBOARD --> PIM --> EMPLOYEE LIST
    @Test
    public void checkNewEmployeeOnList(){
        action = new DashboardPage(driver);
        action.logInWIthValidCredentials();
        action.addEmployee();
        action.checkEmployeeList();

        //taking a screenshot of the page with employee details
        ScreenshotUtils screenshot = new ScreenshotUtils(driver);
        screenshot.captureScreenshot(driver);
    }


    //CLOSE A BROWSER
    @After
    public void tearDown() {
        driver.quit();
    }
}
