package base;

import io.github.cdimascio.dotenv.Dotenv;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.io.File;
import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

public class Browser {

    WebDriver driver;
    Dotenv env = Dotenv.load();

    public WebDriver initializeBrowserAndOpenApplicationURL(String browserName) {

        if(browserName.equalsIgnoreCase("chrome")) {

            driver = new ChromeDriver();

        }else if(browserName.equalsIgnoreCase("firefox")) {

            driver = new FirefoxDriver();

        }else if(browserName.equalsIgnoreCase("edge")) {

            driver = new EdgeDriver();

        }else if(browserName.equalsIgnoreCase("safari")) {

            driver = new SafariDriver();

        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        driver.get(env.get("url"));

        return driver;
    }
}