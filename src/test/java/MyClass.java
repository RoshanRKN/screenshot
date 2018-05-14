import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class MyClass {
    public static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<RemoteWebDriver>();

    public static void main(String[] args) throws Exception {
        MyClass cls = new MyClass();
        cls.lunch("chrome");
        cls.navigate("http://test.victoriassecret.com/account/signin");
        cls.makeFullScreenshot(driver.get(),"./target/roshan.png");
        cls.quit();

    }

    public static void lunch(String browser) {

        if (browser.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", "./src/test/resources/driver/chromedriver");
            driver.set(new ChromeDriver());
            driver.get().manage().window().maximize();

        } else if (browser.equals("firefox")) {
            System.setProperty("webdriver.gecko.driver", "./src/test/resources/driver/geckodriver");
//            driver.set(new FirefoxDriver());
            DesiredCapabilities capa = DesiredCapabilities.firefox();
            capa.setCapability("marionette", true);
            driver.set(new FirefoxDriver(capa));
            driver.get().manage().window().maximize();

        } else if (browser.equals("safari")) {
            driver.set(new SafariDriver());
            driver.get().manage().window().maximize();

        } else {
            System.out.println("=====Please select a valid browser=====");
        }

    }

    public static void navigate(String url) {
        driver.get().navigate().to(url);
    }

    public static void quit() {
        driver.get().quit();
    }


//    public void takescreenshot1() throws Exception {
//
//        String javaIoTmpDir = System.getProperty("java.io.tmpdir"); //tmp dir
//
//        //For large pages - body over 850 px highh - take addition screenshot of the end of page
//        if (driver.get().findElements(By.id("panel_body")).size()>0) {
//            WebElement body = driver.get().findElement(By.xpath("//*[@class='fabric']"));
//            int bodyHight = body.getSize().getHeight();
//            if (bodyHight > 850) {
//                Robot robot = new Robot();
//                robot.keyPress(KeyEvent.VK_END);
//                robot.keyRelease(KeyEvent.VK_END);
//                Thread.sleep(1000);
//                File scrFile = ((TakesScreenshot) driver.get()).getScreenshotAs(OutputType.FILE);
//                String timePictureEnd = javaIoTmpDir+"\\scr_"+String.valueOf(System.currentTimeMillis())+getClass().toString().substring(getClass().toString().lastIndexOf(".qa.")+3)+".png";
//                FileUtils.copyFile(scrFile, new File(timePictureEnd));
//                robot.keyPress(KeyEvent.VK_HOME); //back to top page for next screen
//                robot.keyRelease(KeyEvent.VK_HOME);
//                Thread.sleep(1000);
//            }
//        }
//        String timePicture = javaIoTmpDir+"\\scr_"+String.valueOf(System.currentTimeMillis())+getClass().toString().substring(getClass().toString().lastIndexOf(".qa.")+3)+".png";
//        File scrFile = ((TakesScreenshot) driver.get()).getScreenshotAs(OutputType.FILE);
//        FileUtils.copyFile(scrFile, new File(timePicture));
//    }

//        public static void takeScreenshot () throws IOException{
//
//            JavascriptExecutor jse = (JavascriptExecutor) driver.get();
//            Object mydriver = jse.executeScript("document.body.style.zoom=(top.window.screen.height-70)/Math.max(document.body.scrollHeight, document.body.offsetHeight, document.documentElement.clientHeight, document.documentElement.scrollHeight, document.documentElement.offsetHeight);");
//
//
//            Screenshot s = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver.get());
//            ImageIO.write(s.getImage(), "PNG", new File(System.getProperty("user.dir") + "/target/roshan.png"));
//
//        }

//    public static void takeScreenshot() throws IOException{
//
//        JavascriptExecutor jse = (JavascriptExecutor)driver.get();
//        Object mydriver=jse.executeScript("document.body.style.zoom=(top.window.screen.height-70)/Math.max(document.body.scrollHeight, document.body.offsetHeight, document.documentElement.clientHeight, document.documentElement.scrollHeight, document.documentElement.offsetHeight);");
//
//
//        Screenshot fpScreenshot=new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver.get());
//            try {
//                ImageIO.write(fpScreenshot.getImage(), "png", new File("/target/roshan.png"));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//    }


//        public void takescreenshot() throws Exception {
//
//            Thread.sleep(5000);
//            (new WebDriverWait(driver.get(), 60))
//                    .until(new ExpectedCondition<WebElement>() {
//                        public WebElement apply(WebDriver newDriver) {
//                            return newDriver.findElement(By.id("nav-filter"));
//                        }
//                    });
//            final Screenshot screenshot = new AShot().shootingStrategy(
//                    new ViewportPastingStrategy(500)).takeScreenshot(driver.get());
//            final BufferedImage image = screenshot.getImage();
//            ImageIO.write(image, "PNG", new File("./target/roshan.png"));
//            driver.get().quit();
//        }





    //BEGIN- This Code is working fine with safari =====================================================================

    private static final String JS_RETRIEVE_DEVICE_PIXEL_RATIO = "var pr = window.devicePixelRatio; if (pr != undefined && pr != null)return pr; else return 1.0;";

    private static void hideScroll(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("document.documentElement.style.overflow = 'hidden';");
    }

    private static void showScroll(WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript("document.documentElement.style.overflow = 'visible';");
    }

    private static byte[] getScreenShot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    //This is my Main method- execution starts from here
    public void makeFullScreenshot(WebDriver driver,String strFilename) throws IOException, InterruptedException {

        //This is used to scroll to the end of the page
        long scrollHeight1=(Long) ((JavascriptExecutor) driver).executeScript("return " +
                "document.body.scrollHeight;");
        for(long scroll=0;scroll<scrollHeight1;)
        {
            ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,"+scroll+")");
            scroll=scroll+30;
        }

        scrollVerticallyTo(driver, 0);
        hideScroll(driver);

        byte[] bytes = getScreenShot(driver);
        long longScrollHeight = (Long) ((JavascriptExecutor) driver).executeScript("return Math.max("
                + "document.body.scrollHeight, document.documentElement.scrollHeight,"
                + "document.body.offsetHeight, document.documentElement.offsetHeight,"
                + "document.body.clientHeight, document.documentElement.clientHeight);"
        );
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));
        int capturedWidth = image.getWidth();
        int capturedHeight = image.getHeight();
        Double devicePixelRatio = ((Number) ((JavascriptExecutor) driver).executeScript(JS_RETRIEVE_DEVICE_PIXEL_RATIO)).doubleValue();
        int scrollHeight = (int) longScrollHeight;

        int adaptedCapturedHeight = (int) (((double) capturedHeight) / devicePixelRatio);
        BufferedImage resultingImage;
        if (Math.abs(adaptedCapturedHeight - scrollHeight) > 40) {
            int scrollOffset = adaptedCapturedHeight;
            int times = scrollHeight / adaptedCapturedHeight;
            int leftover = scrollHeight % adaptedCapturedHeight;
            final BufferedImage tiledImage = new BufferedImage(capturedWidth, (int) (((double) scrollHeight) * devicePixelRatio), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2dTile = tiledImage.createGraphics();
            g2dTile.drawImage(image, 0, 0, null);
            int scroll = 0;
            for (int i = 0; i < times - 1; i++) {
                scroll += scrollOffset;
                scrollVerticallyTo(driver, scroll);
                BufferedImage nextImage = ImageIO.read(new ByteArrayInputStream(getScreenShot(driver)));
                g2dTile.drawImage(nextImage, 0, (i + 1) * capturedHeight, null);
            }
            if (leftover > 0) {
                scroll += scrollOffset;
                scrollVerticallyTo(driver, scroll);
                BufferedImage nextImage = ImageIO.read(new ByteArrayInputStream(getScreenShot(driver)));
                BufferedImage lastPart = nextImage.getSubimage(0, nextImage.getHeight() - (int) (((double) leftover) * devicePixelRatio), nextImage.getWidth(), leftover);
                g2dTile.drawImage(lastPart, 0, times * capturedHeight, null);
            }
            scrollVerticallyTo(driver, 0);
            resultingImage = tiledImage;
        } else {
            resultingImage = image;
        }
        showScroll(driver);
        ImageIO.write(resultingImage, "png", new File(strFilename));
    }

    private static void scrollVerticallyTo(WebDriver driver, int scroll) {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, " + scroll + ");");
        try {
            waitUntilItIsScrolledToPosition(driver, scroll);
        } catch (InterruptedException e) {
//             LOG.trace("Interrupt error during scrolling occurred.", e);
            System.out.println(e);
        }
    }

    private static void waitUntilItIsScrolledToPosition(WebDriver driver, int scrollPosition) throws InterruptedException {
        //SCREENSHOT_FULLPAGE_SCROLLWAIT
        int hardTime = 0;
        if (hardTime > 0) {
            Thread.sleep(hardTime);
        }
        //SCREENSHOT_FULLPAGE_SCROLLTIMEOUT
        int time = 250;
        boolean isScrolledToPosition = false;
        while (time >= 0 && !isScrolledToPosition) {
            Thread.sleep(50);
            time -= 50;
            isScrolledToPosition = Math.abs(obtainVerticalScrollPosition(driver) - scrollPosition) < 3;
        }
    }
    private static int obtainVerticalScrollPosition(WebDriver driver) {
        Long scrollLong = (Long) ((JavascriptExecutor) driver).executeScript("return (window.pageYOffset !== undefined) ? window.pageYOffset : (document.documentElement || document.body.parentNode || document.body).scrollTop;");
        return scrollLong.intValue();
    }

//END===================================================================================================================


}
