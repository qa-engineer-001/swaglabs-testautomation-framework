package base;

import java.io.File;
import java.lang.reflect.Method;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;
import listeners.RetryAnalyzer;
import utils.ConfigReader;

public class BaseTest {

    private static final Logger logger = LogManager.getLogger(BaseTest.class);

    public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public static WebDriver getThreadLocalDriver() {
        return driver.get();
    }

    protected WebDriver getDriver() {
        return driver.get();
    }

    protected static ExtentReports extent;
    protected static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    public static ExtentTest getTest() {
        return test.get();
    }

    @BeforeSuite
    public void setUpReport() {
        logger.info("Report setup started");
        ExtentSparkReporter spark = new ExtentSparkReporter("reports/TestReport.html");
        extent = new ExtentReports();
        extent.attachReporter(spark);
    }

    @BeforeMethod
    @Parameters("browser")
    public void setUp(@Optional String browser, Method method) {
        logger.info("Starting test: " + method.getName());

        if (browser == null) {
            browser = ConfigReader.get("browser");
        }

        if (browser.equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--incognito");
            driver.set(new ChromeDriver(options));
        } else if (browser.equals("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            options.addArguments("--private-window");
            driver.set(new FirefoxDriver(options));
        } else if (browser.equals("edge")) {
            EdgeOptions options = new EdgeOptions();
            options.addArguments("--inprivate");
            driver.set(new EdgeDriver(options));
        } else {
            throw new IllegalArgumentException("Browser not supported: " + browser);
        }

        logger.info("Browser launched: " + browser);

        driver.get().manage().deleteAllCookies();
        driver.get().manage().window().maximize();
        driver.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.get().get(ConfigReader.get("url"));

        logger.info("URL navigated to: " + ConfigReader.get("url"));

        test.set(extent.createTest(method.getName()));
        test.get().log(Status.PASS, "Browser: " + browser);
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            RetryAnalyzer retry = (RetryAnalyzer) result.getMethod().getRetryAnalyzer(result);
            boolean willRetry = retry.retry(result);

            if (!willRetry) {
                try {
                    TakesScreenshot ts = (TakesScreenshot) driver.get();
                    File screenshot = ts.getScreenshotAs(OutputType.FILE);
                    String sspath = System.getProperty("user.dir") +
                            "/reports/screenshots/" + result.getName() + ".png";
                    FileUtils.copyFile(screenshot, new File(sspath));
                    test.get().addScreenCaptureFromPath(sspath);
                } catch (Exception e) {
                    logger.error("Screenshot failed: " + e.getMessage());
                }
            }
            logger.error("Test failed: " + result.getName());
        } else {
            logger.info("Test passed: " + result.getName());
        }
        driver.get().quit();
        driver.remove();
    }

    @AfterSuite
    public void tearDownReport() {
        logger.info("Flushing report...");
        extent.flush();
    }
}
