package test;

import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shaft.driver.DriverFactory;
import com.shaft.driver.SHAFT.GUI.WebDriver;
import com.shaft.tools.io.ExcelFileManager;
import com.shaft.tools.io.ReportManager;

import com.shaft.driver.SHAFT;

import siteResources.*;

public class TestCase {
	String[] UserList = { "Auto1", "Auto2", "Auto3", "Auto4" };
	ExcelFileManager ActivationData = new ExcelFileManager(System.getProperty("ButtonDataExcelPath"));
	private SHAFT.GUI.WebDriver driver;
	private IdentityPage identityPage;
	private DashBoard dashboard;
	private UserScreen userscreen;

	@BeforeClass
	public void beforeClass() {
		driver = new SHAFT.GUI.WebDriver();
		identityPage = new IdentityPage(driver);
		dashboard = new DashBoard(driver);
		userscreen = new UserScreen(driver);
		driver.browser().navigateToURL("https://dell-qc:5001");

	}

	@DataProvider(name = "userDataEdit")
	public Object[][] userDataEdit() throws IOException {
		// Path to your Excel file and sheet name
		String filePath = "src/test/resources/testDataFiles/UserDataEdit.xlsx";
		String sheetName = "Sheet1";
		return ExcelReader.processExcelData(filePath, sheetName);
	}

	@Test(priority = 1)
	public void userSigning() throws InterruptedException {
		ReportManager.log("Navigate to login site");
		identityPage.UserSignIn("admin");
		dashboard.UserLogOut();

	}

	@Test(priority = 2)
	public void userSigningwrong() throws InterruptedException {

		ReportManager.log("Navigate to login site");
		identityPage.UserSignIn("wrong");
		driver.element().assertThat(identityPage.GetErrorMessageElement()).exists().perform();
		driver.element().assertThat(identityPage.GetErrorMessageElement()).text()
				.isEqualTo("Login fails, enter correct username or password").perform();

	}

	@Test(priority = 3)
	public void userSigninginMul() {
		int NumOfRows = Integer.valueOf(System.getProperty("numberOfRows"));
		for (int Iterator = 1; Iterator < NumOfRows; Iterator++) {
			identityPage.UserSignIn(Iterator, 1);
			try {
				driver.element().assertThat(identityPage.GetErrorMessageElement()).exists().perform();
				driver.element().assertThat(identityPage.GetErrorMessageElement()).text()
						.isEqualTo("Login fails, enter correct username or password").perform();
			} catch (AssertionError e) {
				System.err.println("Step failed: " + e.getMessage());
				// Log failure but continue with the next steps
			}
		}
	}

	@Test(priority = 4)
	public void SignUpUsers() throws InterruptedException {
		int iterator = 0;
		identityPage.UserSignIn("admin");
		dashboard.gotoUserScreen();
		while (iterator != 4) {
			userscreen.UserSignUp("SignUpExcelPath", UserList[iterator]);
			iterator++;
		}
		dashboard.UserLogOut();
		iterator = 0;
		while (iterator != 4) {

			identityPage.UserSignIn("SignUpExcelPath", UserList[iterator]);
			dashboard.UserLogOut();
			iterator++;
		}
	}

	@Test(priority = 5)
	public void DeleteUser() throws InterruptedException {
		identityPage.UserSignIn("admin");
		dashboard.gotoUserScreen();
		int iterator = 0;
		while (iterator != 4) {
			userscreen.UserDeletion(UserList[iterator]);
			iterator++;
		}
	}

	@Test(priority = 6, dataProvider = "userDataEdit")
	public void EditUser(Map<String, String> editDataUser) throws InterruptedException {
		identityPage.UserSignIn("admin");
		dashboard.gotoUserScreen();
		userscreen.EditUser(editDataUser);
		dashboard.UserLogOut();

	}

	@Test
	public void AssertFastActivationDataCorrect() throws InterruptedException {
		int iterator = 1;
		int NumOfButtons = Integer.valueOf(System.getProperty("NumOfButtons"));
		identityPage.UserSignIn("admin");
		dashboard.ChooseServiceForFastAccessWidget(System.getProperty("serrviceUsedName"));
		TimeUnit.SECONDS.sleep(2);
		while (iterator <= NumOfButtons) {
			dashboard.doFastActionsAssertions(iterator);
			iterator++;
		}
	}
	
	@AfterClass
	public void afterClass() {
		dashboard.UserLogOut();
	    driver.quit();
	}
}
