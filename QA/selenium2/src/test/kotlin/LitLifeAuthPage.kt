import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class LitLifeAuthPage(val driver: WebDriver) {
    init {
        driver.get("https://litlife.club/")
    }

    fun login(email: String, password: String) {
        driver.findElement(By.id("login")).sendKeys(email)
        driver.findElement(By.id("login_password")).sendKeys(password)
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/aside/div[1]/form/button")).submit()
    }

    fun logout() {
        driver.findElement(By.xpath("/html/body/div[1]/div[2]/aside/div[1]/a[13]")).click()
    }

}
