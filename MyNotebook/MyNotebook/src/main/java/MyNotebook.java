import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;

//import static com.gargoylesoftware.htmlunit.html.impl.SimpleRange;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class MyNotebook {
    private WebDriver driver;
    private String baseUrl;
    private String firstElement;
    private String titleElement;
    private int sizeList;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    @Before
    public void setUp() throws Exception {
        System.setProperty("webdriver.gecko.driver", "src/main/resources/geckodriver.exe");
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        baseUrl = "https://yandex.ru/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void test() throws Exception {
        driver.get(baseUrl);
        driver.findElement(By.linkText("Маркет")).click();
        driver.findElement(By.linkText("Компьютеры")).click();
        driver.findElement(By.xpath("//div[contains(@class, 'catalog-menu__list')][1]/a[contains(text(),'Ноутбуки')]")).click();
        //driver.findElement(By.xpath("//a[contains(text(),'Ноутбуки')][2]")).click();
        driver.findElement(By.linkText("Перейти ко всем фильтрам")).click();
        driver.findElement(By.id("glf-priceto-var")).click();
        driver.findElement(By.id("glf-priceto-var")).clear();
        driver.findElement(By.id("glf-priceto-var")).sendKeys("30000");
        driver.findElement(By.xpath("//div[6]/a/span/label")).click();
        driver.findElement(By.xpath("//div[7]/a/span/label")).click();
        driver.findElement(By.linkText("Показать подходящие")).click();
        sizeList = driver.findElements(By.className("snippet-card__header-link")).size();
        if (sizeList == 10) {
            System.out.println("кол-во элементов на странице равно 10-ти");
        }
        else {
            System.out.println("Кол-во элементов:");
            System.out.println(sizeList);
        }
        firstElement = driver.findElements(By.className("snippet-card__header-link")).get(0).getText();
        driver.findElement(By.id("header-search")).clear();
        driver.findElement(By.id("header-search")).sendKeys(firstElement);
        TimeUnit.SECONDS.sleep(2);
        driver.findElement(By.xpath("//span[@class='search2__button']//button[.='Найти']")).click();
        TimeUnit.SECONDS.sleep(2);
        titleElement = driver.findElement(By.className("n-title__text")).findElement(By.className("title")).getText();
        if (titleElement.equals(firstElement)){
            System.out.println("Названия совпадают:");
        }
        else {
            System.out.println("Названия НЕ совпадают:");
        }
        System.out.println(titleElement +" и "+ firstElement);
    }

    @After
    public void tearDown() throws Exception {
        driver.quit();
        String verificationErrorString = verificationErrors.toString();
        if (!"".equals(verificationErrorString)) {
            fail(verificationErrorString);
        }
    }

    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }
}
