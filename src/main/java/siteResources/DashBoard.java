package siteResources;

import org.openqa.selenium.By;

import com.shaft.driver.SHAFT;
import com.shaft.tools.io.ExcelFileManager;

import utils.ExcelReader;

public class DashBoard {
	private SHAFT.GUI.WebDriver driver /* = new SHAFT.GUI.WebDriver() */;
	ExcelFileManager excelLocatorData = new ExcelFileManager(System.getProperty("DBPageExcelPath"));
	ExcelReader ButtonData = new ExcelReader(System.getProperty("ButtonDataExcelPath"));
	ExcelFileManager ActivationData = new ExcelFileManager(System.getProperty("ButtonDataExcelPath"));

	public DashBoard(SHAFT.GUI.WebDriver driver) {
		this.driver = driver;

	}

	// element Locator
	private By LogOutButton = By.xpath(excelLocatorData.getCellData("LogOutButton", "value"));
	private By YesButton = By.id(excelLocatorData.getCellData("YesButton", "value"));
	private By SiteManagement = By.xpath(excelLocatorData.getCellData("SiteManagement", "value"));
	private By Users = By.xpath(excelLocatorData.getCellData("Users", "value"));
	private By FastAccessWidgetremoteserviceselector = By
			.xpath(excelLocatorData.getCellData("FastAccessWidgetremoteserviceselector", "value"));

	public void UserLogOut() {
		driver.element().hover(LogOutButton).click(LogOutButton).click(YesButton);
	}

	public void ChooseServiceForFastAccessWidget(String ServiceName) {
		driver.element().scrollToElement(FastAccessWidgetremoteserviceselector)
				.select(FastAccessWidgetremoteserviceselector, ServiceName);
	}

	public By returnButtonPath(int buttonNum) {
		return By.id(
				excelLocatorData.getCellData("ButtonID", "value") + ButtonData.getCellData(buttonNum, 0).toString());
	}

	public void gotoUserScreen() {
		driver.element().click(SiteManagement).click(Users);
	}

	public void doFastActionsAssertions(int iterator) {
		By buttonElemnt = returnButtonPath(iterator);
		driver.element().scrollToElement(buttonElemnt).assertThat(buttonElemnt).text()
				.isEqualTo(ActivationData.getCellData(String.valueOf(iterator), "Name")).perform();
	}
}
