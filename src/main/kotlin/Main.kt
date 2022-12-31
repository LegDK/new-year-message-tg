import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.PageLoadStrategy
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration
import java.time.LocalDateTime

fun main(args: Array<String>) {

    System.setProperty("webdriver.chrome.driver", "/Users/vmaltsev/Downloads/chromedriver")
    val options = ChromeOptions()
    options.addArguments("user-data-dir=/Users/vmaltsev/Library/Application Support/Google/Chrome/Default", "--profile-directory=Default")
    options.setPageLoadStrategy(PageLoadStrategy.EAGER)
    val driver = ChromeDriver(options)

    val timeoutInSeconds = 30L

    driver.get("https://web.telegram.org/k/")
    val wait = WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds))

    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"folders-tabs\"]/div[4]/span/span")))

    driver.findElement(By.xpath("//*[@id=\"folders-tabs\"]/div[4]/div")).click()

    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"folders-container\"]/div[4]")))

    val ul = driver.findElement(By.xpath("//*[@id=\"folders-container\"]/div[4]/div[1]/ul"))

    val startTime = LocalDateTime.of(2023, 1, 1, 0, 0, 0)
    while (true) {
        if (LocalDateTime.now().isAfter(startTime)) {
            var subling = ul.findElement(By.tagName("a"))
            while (subling != null) {
                subling.click()
                subling = try {
                    subling.findElement(By.xpath("following-sibling::*"))
                } catch (t: Throwable) {
                    null
                }
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"column-center\"]/div/div/div[4]/div/div[1]/div/div[8]/div[1]/div[1]")))
                val textField = driver.findElement(By.xpath("//*[@id=\"column-center\"]/div/div/div[4]/div/div[1]/div/div[8]/div[1]/div[1]"))
                textField.sendKeys("С новым годом!)")
                textField.sendKeys(Keys.RETURN)
                Thread.sleep(5000)
            }

            break
        } else Thread.sleep(10000)
    }

    driver.close()
}