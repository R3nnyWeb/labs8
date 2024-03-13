import assertk.assertFailure
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.testng.annotations.AfterClass
import org.testng.annotations.BeforeClass
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

class LitLifeTest {
    private lateinit var driver: WebDriver

    @BeforeClass
    fun setup() {
        driver = ChromeDriver()
    }

    @AfterClass
    fun teardown() {
        driver.close()
    }

    @DataProvider(name = "authData")
    fun data(): Array<Array<Any>> {
        return arrayOf(
            arrayOf("nikita.developer97@gmail.com", "Test12345", true),
            arrayOf("1234@gmail.com", "1234", false),
            arrayOf("testing@gmail.com", "test1278", true),
            arrayOf("s", "s", false)
        )
    }

    @Test(dataProvider = "authData")
    fun testLogin(email: String, password: String, expected: Boolean) {
        val page = LitLifeAuthPage(driver)

        page.login(email, password)
        if (expected) {
            page.logout()
        } else {
            assertFailure {
                page.logout()
            }
        }

    }

}
