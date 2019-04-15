import com.github.javafaker.Faker;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;

public class DashboardPage {

    private WebDriver driver;
    private WebElement btnLogin;
    private Integer idNumber;



    public DashboardPage(WebDriver driver) {
        this.driver = driver;
    }

    public void logInWIthValidCredentials(){
        //filling Username and Password
        driver.findElement(By.id("txtUsername")).sendKeys("Admin");
        driver.findElement(By.id("txtPassword")).sendKeys("admin123");
        btnLogin = driver.findElement(By.id("btnLogin"));
        btnLogin.click();
        String currentUrl = driver.getCurrentUrl();
    }

    public void logInWithInvalidPass() {
        //user try to log In with invalid credentials
        driver.findElement(By.id("txtUsername")).sendKeys("Admin123");
        driver.findElement(By.id("txtPassword")).sendKeys("admin12345");
        btnLogin = driver.findElement(By.id("btnLogin"));
        btnLogin.click();

    }

    public void logOut(){
        //logging out - on the dashboard page. Trying to catch Dropdown Welcome Button
        WebElement welcome = driver.findElement(By.id("welcome"));
        welcome.click();
        WebDriverWait wait = new WebDriverWait(driver,5);
        WebElement btnLogOut = wait.until( ExpectedConditions.presenceOfElementLocated(By.linkText("Logout")));
        btnLogOut.click();
    }

    public void openAndCloseAboutBtn(){
        //open and close ABOUT Modal window (pop-up)
        WebElement welcome = driver.findElement(By.id("welcome"));
        welcome.click();
        WebDriverWait wait = new WebDriverWait(driver,5);
        WebElement aboutBtnElement = wait.until( ExpectedConditions.presenceOfElementLocated(By.linkText("About")));
        aboutBtnElement.click();
        //closing pop-up
        driver.findElement(By.linkText("About")).click();
    }

    public String addEmployee(){
        //navigate to Add employee bookmark and perform it
        WebElement pimButton = driver.findElement(By.id("menu_pim_viewPimModule"));
        WebElement addEmployee = driver.findElement(By.id("menu_pim_addEmployee"));
        Actions navigateAction = new Actions(driver);
        navigateAction.moveToElement(pimButton)
                .moveToElement(addEmployee)
                .click()
                .perform();

        // example of FAKER class using - adding random name and last name. Saving the name for the next step
        Faker faker = new Faker();
        WebElement employeeName = driver.findElement(By.id("firstName"));
        employeeName.sendKeys(faker.name().firstName());
        employeeName.getText();

        driver.findElement(By.id("lastName")).sendKeys(faker.name().lastName());
        driver.findElement(By.id("chkLogin")).click();
        driver.findElement(By.id("user_name")).sendKeys(faker.name().username());


        //Example of RANDOM class using - generating employee ID
        Random random = new Random();
        //number from 1 - 500
        idNumber = random.nextInt(500)+1;
        WebElement randomId = driver.findElement(By.id("employeeId"));
        randomId.clear();
        randomId.sendKeys(idNumber.toString());

        //Catching the dropdown "window"
        Select dropdown = new Select(driver.findElement(By.id("status")));
        dropdown.selectByVisibleText("Disabled");
        driver.findElement(By.id("btnSave")).click();

        //checking the name of the new employee to see if he was added correctly - page Personal Details
//        String newEmployeeName = driver.findElement(By.id("personal_txtEmpFirstName")).getText();
//        Assert.assertEquals("Employee added correctly", employeeName, newEmployeeName);

    return idNumber.toString();
    }

    public void addReport(){
        //navigate to PIM button --> Add Employee and perform it
        WebElement pimButton = driver.findElement(By.id("menu_pim_viewPimModule"));
        WebElement reportButton = driver.findElement(By.id("menu_core_viewDefinedPredefinedReports"));
        Actions navigateAction = new Actions(driver);
        navigateAction.moveToElement(pimButton)
                .moveToElement(reportButton)
                .click()
                .perform();

        //clicking ADD REPORT button
        WebElement btnAdd = driver.findElement(By.id("btnAdd"));
        btnAdd.click();

        //adding fake report name
        Faker faker = new Faker();
        driver.findElement(By.id("report_report_name")).sendKeys(faker.name().firstName());

        //catching the dropdown list - SELECTED CRITERIA and save the report
        WebElement dropdown = driver.findElement(By.xpath("//fieldset[@id='criteria_fieldset']/ol/li/select[@id='report_include_comparision'][last()]"));
        dropdown.click();
        WebDriverWait wait = new WebDriverWait(driver,8);
        WebElement includeDrop = wait.until( ExpectedConditions.presenceOfElementLocated(By.xpath("//fieldset[@id='criteria_fieldset']/ol/li/select[@id='report_include_comparision']/option[contains(text(),'Current and Past Employees')]")));
        includeDrop.click();
        WebElement saveBtn = driver.findElement(By.id("btnSave"));
    }

    public void checkEmployeeList(){
        //navigate to PIM button --> Employee List and perform it
        WebElement pimButton = driver.findElement(By.id("menu_pim_viewPimModule"));
        WebElement EmployeeListemployeeListBtn = driver.findElement(By.id("menu_pim_viewEmployeeList"));
        Actions navigateAction = new Actions(driver);
        navigateAction.moveToElement(pimButton)
                .moveToElement(EmployeeListemployeeListBtn)
                .click()
                .perform();

        driver.findElement(By.xpath("//form[@id='search_form']/fieldset/ol/li/input[@id='empsearch_id']")).sendKeys(idNumber.toString());
        driver.findElement(By.id("searchBtn")).click();

        WebDriverWait wait = new WebDriverWait(driver,10);
        WebElement waitForVisibleID = wait.until( ExpectedConditions.presenceOfElementLocated(By.className("left")));
        WebElement employeeDetailsAfterSearch = driver.findElement(By.xpath("//td[@class='left']"));
        Assert.assertTrue(driver.findElement(By.xpath("//td[@class='left']")).isDisplayed());
        employeeDetailsAfterSearch.getText();
        Assert.assertEquals("Cannot find added user", idNumber.toString(), employeeDetailsAfterSearch.getText());


    }

    public void removePerformanceTracker(){
        //toDO
    }

    public void userManagementSearch(){
        //toDo
    }



}
