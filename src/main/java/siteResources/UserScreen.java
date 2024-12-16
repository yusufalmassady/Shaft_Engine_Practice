package siteResources;

import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.By;

import com.shaft.driver.SHAFT;
import com.shaft.tools.io.ExcelFileManager;
import com.shaft.tools.io.ReportManager;

public class UserScreen {
	private SHAFT.GUI.WebDriver driver /* = new SHAFT.GUI.WebDriver() */;

	public UserScreen(SHAFT.GUI.WebDriver driver) {
		this.driver = driver;

	}

	ExcelFileManager excelDataLocator = new ExcelFileManager(System.getProperty("UserManagementExcelPath"));
	private By addUsers = By.id(excelDataLocator.getCellData("addUsers", "Value"));
	private By FullName = By.id(excelDataLocator.getCellData("FullName", "Value"));
	private By Description = By.id(excelDataLocator.getCellData("Description", "Value"));
	private By Email = By.id(excelDataLocator.getCellData("Email", "Value"));
	private By Password = By.id(excelDataLocator.getCellData("Password", "Value"));
	private By ConfirmPassword = By.id(excelDataLocator.getCellData("ConfirmPassword", "Value"));
	private By Phone = By.id(excelDataLocator.getCellData("Phone", "Value"));
	private By Tenant = By.xpath(excelDataLocator.getCellData("Tenant", "Value"));
	private By admintenant = By.id(excelDataLocator.getCellData("admintenant", "Value"));
	private By createUser = By.id(excelDataLocator.getCellData("createUser", "Value"));
	private By DeleteConfirm = By.xpath(excelDataLocator.getCellData("DeleteConfirm", "Value"));
	private By label = By.xpath(excelDataLocator.getCellData("label", "Value"));
	private By editUser = By.id(excelDataLocator.getCellData("editUser", "Value"));
	String DeleteUserExpression1 = excelDataLocator.getCellData("deleteUser1", "Value");
	String DeleteUserExpression2 = excelDataLocator.getCellData("deleteUser2", "Value");
	String EditUserExpression1 = excelDataLocator.getCellData("editUserExp1", "Value");
	String EditUserExpression2 = excelDataLocator.getCellData("editUserExp2", "Value");

	public void UserSignUp(String excelPath, String RowName) {
		ExcelFileManager excelSignupgDataReader = new ExcelFileManager(System.getProperty(excelPath));
		driver.element().click(addUsers).type(FullName, excelSignupgDataReader.getCellData(RowName, "FullName"))
				.type(Description, excelSignupgDataReader.getCellData(RowName, "Description"))
				.type(Email, excelSignupgDataReader.getCellData(RowName, "Email"))
				.type(Password, excelSignupgDataReader.getCellData(RowName, "Password"))
				.type(ConfirmPassword, excelSignupgDataReader.getCellData(RowName, "Password"))
				.type(Phone, excelSignupgDataReader.getCellData(RowName, "Phone")).click(Tenant).click(admintenant)
				.click(label).scrollToElement(createUser).click(createUser);
	}

	public void UserDeletion(String RowName) {

		ExcelFileManager excelSignupgDataReader = new ExcelFileManager(System.getProperty("SignUpExcelPath"));
		String toBeDeleted = excelSignupgDataReader.getCellData(RowName, "FullName");

		try {
			driver.element().click(By.xpath(DeleteUserExpression1 + toBeDeleted + DeleteUserExpression2))
					.click(DeleteConfirm);
		} catch (Exception e) {
			ReportManager.log("No Such User");
		}

	}

	public void EditUser(Map<String, String> editDataUser) {

		driver.element().click(By.xpath(EditUserExpression1 + editDataUser.get("FullName") + EditUserExpression2))
				.type(FullName, editDataUser.get("FullNameUpdated")).type(Description, editDataUser.get("Description"))
				.type(Email, editDataUser.get("Email")).type(Phone, editDataUser.get("Phone")).click(Tenant)
				.click(admintenant)
				// .click(label)
				.click(editUser);

		driver.element()
				.click(By.xpath(EditUserExpression1 + editDataUser.get("FullNameUpdated") + EditUserExpression2));

		driver.element().verifyThat(FullName).text().isEqualTo(editDataUser.get("FullNameUpdated")).perform();
		driver.element().verifyThat(Description).text().isEqualTo(editDataUser.get("Description")).perform();
		driver.element().verifyThat(Email).text().isEqualTo(editDataUser.get("Email")).perform();
		driver.element().verifyThat(Phone).text().isEqualTo(editDataUser.get("Phone")).perform();

	}

}
