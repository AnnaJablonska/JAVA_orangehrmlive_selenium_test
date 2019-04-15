import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.util.Random;

public class ScreenshotUtils {

    private WebDriver driver;

    public ScreenshotUtils(WebDriver driver) {
        this.driver = driver;
    }

    public void captureScreenshot(WebDriver driver){
        //drawing a random number between 1 and 50 for a output file name
        Random random = new Random();
        int randomNR = random.nextInt(51)+50;
        //taking a screenshot and storing as a file format
        File src=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        try {
        //copying the screenshot to desired location using copyFile method
            FileUtils.copyFile(src, new File("C:/selenium/"+randomNR+".png"));
        } catch (IOException e)
        {
            System.out.println(e.getMessage());
        }
    }



}
