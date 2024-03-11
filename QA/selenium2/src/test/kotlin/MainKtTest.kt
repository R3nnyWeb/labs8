import org.openqa.selenium.By
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.testng.annotations.AfterMethod
import org.testng.annotations.BeforeMethod
import org.testng.annotations.Test
import java.io.File

class MainKtTest {
    private lateinit var driver: WebDriver

    @BeforeMethod
    fun setup() {
        driver = ChromeDriver()
    }

    @Test    fun test() {
        driver.get("https://github.com/")
        driver.saveScreenshot("before.png")
        val element = driver.findElement(By.id("user_email"))
        element.sendKeys("r3nny@mail.ru")
        driver.saveScreenshot("after.png")
    }

    @AfterMethod
    fun teardown() {
        driver.close()
    }

}

private fun WebDriver.saveScreenshot(fileName: String) {
    val scrFile = (this as TakesScreenshot).getScreenshotAs(OutputType.FILE)
    scrFile.copyTo(File(fileName), overwrite = true)
}
