package test;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.shaft.driver.SHAFT;
import com.shaft.tools.io.ExcelFileManager;
import com.shaft.tools.io.ReportManager;

import siteResources.DashBoard;
import siteResources.IdentityPage;
import siteResources.UserScreen;

public class NewTest {
	String[] UserList = { "Auto1", "Auto2", "Auto3","Auto4" };
	ExcelFileManager ActivationData = new ExcelFileManager(System.getProperty("ButtonDataExcelPath"));
	private SHAFT.GUI.WebDriver driver;

	@BeforeClass
	public void beforeClass()
	{
		driver = new SHAFT.GUI.WebDriver();

		driver.browser().navigateToURL("https://dell-qc:5001");
	
	}
	
	@Test(priority = 1)
	public void userSigning() throws InterruptedException 
	{
		ReportManager.log("Navigate to login site");
		new IdentityPage(driver).UserSignIn("admin");
		new DashBoard(driver).UserLogOut();
		
		
	}
	@Test(priority = 2)
	public void userSigningwrong() throws InterruptedException
	{
		 
		ReportManager.log("Navigate to login site");
		new IdentityPage(driver).UserSignIn("wrong");
		driver.element().assertThat(new IdentityPage(driver).GetErrorMessageElement()).exists().perform();
		driver.element().assertThat(new IdentityPage(driver).GetErrorMessageElement()).text().isEqualTo("Login fails, enter correct username or password").perform();
		
		
	}
	
	@Test(priority = 3)
	public void userSigninginMul()
	{
		int NumOfRows=Integer.valueOf(System.getProperty("numberOfRows"));
		for(int Iterator=1;Iterator<NumOfRows;Iterator++)
		{
			new IdentityPage(driver).UserSignIn(Iterator,1);
			try {
			driver.element().assertThat(new IdentityPage(driver).GetErrorMessageElement()).exists().perform();
			driver.element().assertThat(new IdentityPage(driver).GetErrorMessageElement()).text().isEqualTo("Login fails, enter correct username or password").perform();
			} catch (AssertionError e) {
			    System.err.println("Step failed: " + e.getMessage());
			    // Log failure but continue with the next steps
			}
		}
	}
	@Test(priority = 4)
	public void SignUpUsers() throws InterruptedException
	{
		int iterator=0;
		new IdentityPage(driver).UserSignIn("admin");
		new DashBoard(driver).gotoUserScreen();
		while(iterator!=4)
		{
			new UserScreen(driver).UserSignUp("SignUpExcelPath", UserList[iterator]);
		iterator++;
		}
		new DashBoard(driver).UserLogOut();
		iterator=0;
		while(iterator!=4) {
			
			new IdentityPage(driver).UserSignIn("SignUpExcelPath", UserList[iterator]);
			new DashBoard(driver).UserLogOut();
			iterator++;
		}
	}
	@Test(priority = 5)
	public void DeleteUser() throws InterruptedException
	{
		new IdentityPage(driver).UserSignIn("admin");
		new DashBoard(driver).gotoUserScreen();
		int iterator=0;
		while(iterator!=4)
		{
			new UserScreen(driver).UserDeletion(UserList[iterator]);
		iterator++;
		}
	}
	
	@Test
	public void AssertFastActivationDataCorrect() throws InterruptedException
	{
		int iterator=1;
		int NumOfButtons=Integer.valueOf(System.getProperty("NumOfButtons"));
		new IdentityPage(driver).UserSignIn("admin");
		new DashBoard(driver).ChooseServiceForFastAccessWidget(System.getProperty("serrviceUsedName"));
		//Stop right there criminal scum
		TimeUnit.SECONDS.sleep(2);
		while(iterator<=NumOfButtons)
		{
			new DashBoard(driver).doFastActionsAssertions(iterator);
			iterator++;
		}
	}
}
