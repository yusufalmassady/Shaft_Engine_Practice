package siteResources;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;

import com.shaft.driver.SHAFT;
import com.shaft.tools.io.ExcelFileManager;

import utils.ExcelReader;

public class IdentityPage {
	private SHAFT.GUI.WebDriver driver = new SHAFT.GUI.WebDriver();
	
	ExcelFileManager excelLocator2Data = new ExcelFileManager(System.getProperty("DBPageExcelPath"));
	ExcelFileManager excelFileDataReader=new ExcelFileManager(System.getProperty("SignInExcelPath"));
	ExcelFileManager excelLocatorData = new ExcelFileManager(System.getProperty("IDPageExcelPath"));
	ExcelReader excelFileReaderNummed=new ExcelReader(System.getProperty("IDPageExcelPath"));
	ExcelReader excelSIDataNummed=new ExcelReader(System.getProperty("SignUpExcelPath"));
	public IdentityPage (SHAFT.GUI.WebDriver driver)
	{
		this.driver=driver;

	}
	//element Locator
	//private By OrganizationName =By.id(excelLocatorData.getCellData("OrganizationName", "value"));
	private By OrganizationName =By.id(excelFileReaderNummed.getCellData(System.getProperty("IDPageExcelPath"), 1, 1).toString());
	private By EmailBox=By.id(excelLocatorData.getCellData("EmailBox", "value"));
	private By PasswordBox=By.id(excelLocatorData.getCellData("PasswordBox", "value"));
	private By LoginButton=By.xpath(excelLocatorData.getCellData("LoginButton", "value"));
	private By ErrorMessage=By.xpath(excelLocatorData.getCellData("ErrorMessage", "value"));

 //   private By Recaptcha=By.xpath(excelLocatorData.getCellData("Recaptcha", "value"));
	
	
	//Actions
	public void UserSignIn(String RowName) throws InterruptedException {
		driver.element()
		.type(OrganizationName, "admin")
		.type(EmailBox, excelFileDataReader.getCellData(RowName, "Email"))
		.type(PasswordBox, excelFileDataReader.getCellData(RowName, "Password"));
		//.click(Recaptcha)
		//Stop right there criminal scum
		//TimeUnit.SECONDS.sleep(5);
		try {
		driver.element().click(LoginButton);
		}catch(Exception e) {
			  //  Block of code to handle errors
		}
		//.hover(LogOutButton)
		//.click(LogOutButton)
		//.click(YesButton);;
		
	}
	
	public By GetErrorMessageElement()
	{
		return ErrorMessage;
	}
	public void UserSignIn()
	   {
		   
	   }
	public void UserSignIn(int RowNum, int ColumNum)
	   {
		driver.element()
		.type(OrganizationName, "admin")
		.type(EmailBox, excelSIDataNummed.getCellData(RowNum, ColumNum).toString())
		.type(PasswordBox, excelSIDataNummed.getCellData(RowNum, ColumNum+1).toString());
		//.click(Recaptcha)
		driver.element().click(LoginButton);
		//.hover(LogOutButton)
		//.click(LogOutButton)
		//.click(YesButton);;
	   }
	public void UserSignIn(String excelPath,String RowName) throws InterruptedException {
		ExcelFileManager excelSigningDataReader=new ExcelFileManager(System.getProperty(excelPath));
		driver.element()
		.type(OrganizationName, "admin")
		.type(EmailBox, excelSigningDataReader.getCellData(RowName, "Email"))
		.type(PasswordBox, excelSigningDataReader.getCellData(RowName, "Password"));
		//.click(Recaptcha)
		//Stop right there criminal scum
		//TimeUnit.SECONDS.sleep(5);
		try {
		driver.element().click(LoginButton);
		}catch(Exception e) {
			  //  Block of code to handle errors
		}
	
		
	}
}
   