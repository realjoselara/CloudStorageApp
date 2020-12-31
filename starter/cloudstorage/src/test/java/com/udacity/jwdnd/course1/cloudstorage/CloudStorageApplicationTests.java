package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;
	private static String firstName = "Jose";
	private static String lastName = "Lara";
	private static String userName = "realjoselara";
	private static String password = "123456690";
	private static String noteTitle = "Testing Title";
	private static String noteDescription = "Testing Testing, Hello from the other side.";
	private static String credURL = "https://joselara.me";

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}


	@Test
	public void getUnauthorizedResultPage() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		driver.get("http://localhost:" + this.port + "/result");
		wait.until(ExpectedConditions.elementToBeClickable(By.id("buttonLogin")));
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void getUnauthorizedHomePage() {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void CreateUserTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait(driver, 60);

		// Test to signup
		driver.get("http://localhost:" + this.port + "/signup");
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.sendKeys(firstName);
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.sendKeys(lastName);
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.sendKeys(userName);
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.sendKeys(password);
		WebElement signUpButton = driver.findElement(By.id("buttonSignUp"));
		signUpButton.click();

		// Test login
		driver.get("http://localhost:" + this.port + "/login");
		inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.sendKeys(userName);
		inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.sendKeys(password);
		WebElement loginButton = driver.findElement(By.id("buttonLogin"));
		loginButton.click();
		Assertions.assertEquals("Home", driver.getTitle());

		// Test logout
		WebElement logoutButton = driver.findElement(By.id("buttonLogout"));
		Thread.sleep(4000);
		logoutButton.click();
		WebElement LoginButton =  wait.until(webDriver -> driver.findElement(By.id("buttonLogin")));
		wait.until(ExpectedConditions.elementToBeClickable(LoginButton));

		// Test homepage redirect to login after the Logout happened.
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void CreateNoteTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait (driver, 30);
		JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;

		// Test to signup
		driver.get("http://localhost:" + this.port + "/signup");
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.sendKeys(firstName);
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.sendKeys(lastName);
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.sendKeys(userName);
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.sendKeys(password);
		WebElement signUpButton = driver.findElement(By.id("buttonSignUp"));
		signUpButton.click();


		// login
		driver.get("http://localhost:" + this.port + "/login");
		inputUsername = wait.until(webDriver ->driver.findElement(By.id("inputUsername")));
		inputUsername.sendKeys(userName);
		inputPassword = wait.until(webDriver ->driver.findElement(By.id("inputPassword")));
		inputPassword.sendKeys(password);
		WebElement loginButton = wait.until(webDriver -> driver.findElement(By.id("buttonLogin")));
		loginButton.click();
		Assertions.assertEquals("Home", driver.getTitle());

		// added note
		WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
		javascriptExecutor.executeScript("arguments[0].click()", notesTab);
		Thread.sleep(3000);
		WebElement newNote = driver.findElement(By.id("buttonAddNewNote"));
		wait.until(ExpectedConditions.elementToBeClickable(newNote)).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).sendKeys(noteTitle);
		WebElement notedescription = driver.findElement(By.id("note-description"));
		notedescription.sendKeys(noteDescription);
		WebElement savechanges = driver.findElement(By.id("saveNoteButton"));
		savechanges.click();
		Assertions.assertEquals("Result", driver.getTitle());

		//check for note
		driver.get("http://localhost:" + this.port + "/home");
		notesTab = driver.findElement(By.id("nav-notes-tab")); javascriptExecutor.executeScript("arguments[0].click()", notesTab);
		WebElement notesTable = driver.findElement(By.id("userTable"));
		List<WebElement> notesList = notesTable.findElements(By.tagName("td"));
		Boolean created = false;
		for (int i=0; i < notesList.size(); i++) {
			WebElement element = notesList.get(i);
			if (element.getAttribute("innerHTML").equals(noteTitle)) {
				created = true;
				break;
			}
		}
		Assertions.assertTrue(created);
	}

	@Test
	public void UpdateNoteTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait (driver, 30);
		JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
		String newNoteTitle = "new note title";

		// signup
		driver.get("http://localhost:" + this.port + "/signup");
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.sendKeys(firstName);
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.sendKeys(lastName);
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.sendKeys(userName);
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.sendKeys(password);
		WebElement signUpButton = driver.findElement(By.id("buttonSignUp"));
		signUpButton.click();


		// login
		driver.get("http://localhost:" + this.port + "/login");
		inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.sendKeys(userName);
		inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.sendKeys(password);
		WebElement loginButton = driver.findElement(By.id("buttonLogin"));
		loginButton.click();
		Assertions.assertEquals("Home", driver.getTitle());

		// added note
		WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
		javascriptExecutor.executeScript("arguments[0].click()", notesTab);
		Thread.sleep(3000);
		WebElement newNote = driver.findElement(By.id("buttonAddNewNote"));
		wait.until(ExpectedConditions.elementToBeClickable(newNote)).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).sendKeys(noteTitle);
		WebElement notedescription = driver.findElement(By.id("note-description"));
		notedescription.sendKeys(noteDescription);
		WebElement savechanges = driver.findElement(By.id("saveNoteButton"));
		savechanges.click();
		Assertions.assertEquals("Result", driver.getTitle());

		// update note
		driver.get("http://localhost:" + this.port + "/home");
		notesTab = driver.findElement(By.id("nav-notes-tab"));
		javascriptExecutor.executeScript("arguments[0].click()", notesTab);
		WebElement notesTable = driver.findElement(By.id("userTable"));
		List<WebElement> notesList = notesTable.findElements(By.tagName("td"));
		WebElement editElement = null;
		for (int i = 0; i < notesList.size(); i++) {
			WebElement element = notesList.get(i);
			editElement = element.findElement(By.name("edit"));
			if (editElement != null){
				break;
			}
		}
		wait.until(ExpectedConditions.elementToBeClickable(editElement)).click();
		WebElement notetitle = driver.findElement(By.id("note-title"));
		wait.until(ExpectedConditions.elementToBeClickable(notetitle));
		notetitle.clear();
		notetitle.sendKeys(newNoteTitle);
		savechanges = driver.findElement(By.id("saveNoteButton"));
		savechanges.click();
		Assertions.assertEquals("Result", driver.getTitle());

		//check the updated note
		driver.get("http://localhost:" + this.port + "/home");
		notesTab = driver.findElement(By.id("nav-notes-tab"));
		javascriptExecutor.executeScript("arguments[0].click()", notesTab);
		notesTable = driver.findElement(By.id("userTable"));
		notesList = notesTable.findElements(By.tagName("td"));
		Boolean edited = false;
		for (int i = 0; i < notesList.size(); i++) {
			WebElement element = notesList.get(i);
			if (element.getAttribute("innerHTML").equals(newNoteTitle)) {
				edited = true;
				break;
			}
		}
		Assertions.assertTrue(edited);
	}

	@Test
	public void DeleteNoteTest() throws InterruptedException {

		WebDriverWait wait = new WebDriverWait (driver, 30);
		JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;

		// signup
		driver.get("http://localhost:" + this.port + "/signup");
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.sendKeys(firstName);
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.sendKeys(lastName);
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.sendKeys(userName);
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.sendKeys(password);
		WebElement signUpButton = driver.findElement(By.id("buttonSignUp"));
		signUpButton.click();

		//login
		driver.get("http://localhost:" + this.port + "/login");
		inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.sendKeys(userName);
		inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.sendKeys(password);
		WebElement loginButton = driver.findElement(By.id("buttonLogin"));
		loginButton.click();
		Assertions.assertEquals("Home", driver.getTitle());

		// added note
		WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
		javascriptExecutor.executeScript("arguments[0].click()", notesTab);
		Thread.sleep(3000);
		WebElement newNote = driver.findElement(By.id("buttonAddNewNote"));
		wait.until(ExpectedConditions.elementToBeClickable(newNote)).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).sendKeys(noteTitle);
		WebElement notedescription = driver.findElement(By.id("note-description"));
		notedescription.sendKeys(noteDescription);
		WebElement savechanges = driver.findElement(By.id("saveNoteButton"));
		savechanges.click();
		Assertions.assertEquals("Result", driver.getTitle());

		// Delete note
		driver.get("http://localhost:" + this.port + "/home");
		notesTab = driver.findElement(By.id("nav-notes-tab"));
		javascriptExecutor.executeScript("arguments[0].click()", notesTab);
		WebElement notesTable = driver.findElement(By.id("userTable"));
		List<WebElement> notesList = notesTable.findElements(By.tagName("td"));
		WebElement deleteElement = null;
		for (int i = 0; i < notesList.size(); i++) {
			WebElement element = notesList.get(i);
			deleteElement = element.findElement(By.name("delete"));
			if (deleteElement != null){
				break;
			}
		}
		wait.until(ExpectedConditions.elementToBeClickable(deleteElement)).click();
		Assertions.assertEquals("Result", driver.getTitle());
	}

	@Test
	public void CreateCredentialTest() {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;

		// Step 1: login the user
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());

		WebElement usernameInputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		usernameInputField.sendKeys(userName);

		WebElement passwordInputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		passwordInputField.sendKeys(password);

		WebElement loginButton = driver.findElement(By.id("buttonLogin"));
		wait.until(ExpectedConditions.elementToBeClickable(loginButton)).click();

		WebElement logoutButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonLogout")));

		Assertions.assertEquals("Home", driver.getTitle());

		// Step 2: Create the credential
		JavascriptExecutor executor = (JavascriptExecutor) driver;

		WebElement navCredentialsTab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));

		executor.executeScript("arguments[0].click()", navCredentialsTab);

		WebElement addCredentialButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonAddNewCredential")));
		addCredentialButton.click();

		WebElement credentialUrlInputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		credentialUrlInputField.clear();
		credentialUrlInputField.sendKeys(credURL);

		WebElement credentialUsernameInputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		credentialUsernameInputField.clear();
		credentialUsernameInputField.sendKeys(userName);

		WebElement credentialPasswordInputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		credentialPasswordInputField.clear();
		credentialPasswordInputField.sendKeys(password);

		WebElement saveCredentialButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("saveCredentialButton")));

		saveCredentialButton.click();

		WebElement successDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("divSuccess")));

		Assertions.assertEquals("Result", driver.getTitle());

		// Step 3: Check if the credential is listed
		driver.get("http://localhost:"+this.port+"/dashboard");

		Assertions.assertEquals("Home", driver.getTitle());

		navCredentialsTab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));

		driver.get("http://localhost:" + this.port + "/home");
		WebElement credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
		javascriptExecutor.executeScript("arguments[0].click()", credentialsTab);
		WebElement credentialsTable = driver.findElement(By.id("credentialTable"));
		List<WebElement> notesList = credentialsTable.findElements(By.tagName("td"));

		Boolean created = false;

		for (int i=0; i < notesList.size(); i++) {
			WebElement element = notesList.get(i);
			System.out.println(element.getAttribute("innerHTML"));
			if (element.getAttribute("innerHTML").equals(credURL)) {
				created = true;
				break;
			}
		}
		Assertions.assertTrue(created);
	}

	@Test
	public void UpdateCredentialTest() throws InterruptedException {
		WebDriverWait wait = new WebDriverWait (driver, 30);
		JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
		String newCredUsername = "AwesomeUser";

		// signup
		driver.get("http://localhost:" + this.port + "/signup");
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.sendKeys(firstName);
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.sendKeys(lastName);
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.sendKeys(userName);
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.sendKeys(password);
		WebElement signUpButton = driver.findElement(By.id("buttonSignUp"));
		signUpButton.click();

		//login
		driver.get("http://localhost:" + this.port + "/login");
		inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.sendKeys(userName);
		inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.sendKeys(password);
		WebElement loginButton = driver.findElement(By.id("buttonLogin"));
		loginButton.click();
		Assertions.assertEquals("Home", driver.getTitle());

		// add credentials
		WebElement credentialTab = driver.findElement(By.id("nav-credentials-tab"));
		javascriptExecutor.executeScript("arguments[0].click()", credentialTab);
		Thread.sleep(3000);
		WebElement credential = driver.findElement(By.id("buttonAddNewCredential"));
		wait.until(ExpectedConditions.elementToBeClickable(credential)).click();
		wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url"))).sendKeys(credURL);
		WebElement credUsername = driver.findElement(By.id("credential-username"));
		credUsername.sendKeys(userName);
		WebElement credPassword = driver.findElement(By.id("credential-password"));
		credPassword.sendKeys(password);
		WebElement submit = driver.findElement(By.id("saveCredentialButton"));
		submit.click();
		Assertions.assertEquals("Result", driver.getTitle());

		//update credential
		driver.get("http://localhost:" + this.port + "/home");
		credentialTab = driver.findElement(By.id("nav-credentials-tab"));
		javascriptExecutor.executeScript("arguments[0].click()", credentialTab);
		WebElement credentialTable = driver.findElement(By.id("credentialTable"));
		List<WebElement> credentialsList = credentialTable.findElements(By.tagName("td"));
		WebElement editElement = null;
		for (int i = 0; i < credentialsList.size(); i++) {
			WebElement element = credentialsList.get(i);
			editElement = element.findElement(By.name("CredentialEdit"));
			if (editElement != null){
				break;
			}
		}
		wait.until(ExpectedConditions.elementToBeClickable(editElement)).click();
		credUsername = driver.findElement(By.id("credential-username"));
		wait.until(ExpectedConditions.elementToBeClickable(credUsername));
		credUsername.clear();
		credUsername.sendKeys(newCredUsername);
		WebElement savechanges = driver.findElement(By.id("saveCredentialButton"));
		savechanges.click();
		Assertions.assertEquals("Result", driver.getTitle());

		// check the updated note
		driver.get("http://localhost:" + this.port + "/home");
		credentialTab = driver.findElement(By.id("nav-credentials-tab"));
		javascriptExecutor.executeScript("arguments[0].click()", credentialTab);
		credentialTable = driver.findElement(By.id("credentialTable"));
		credentialsList = credentialTable.findElements(By.tagName("td"));
		Boolean edited = false;
		for (int i = 0; i < credentialsList.size(); i++) {
			WebElement element = credentialsList.get(i);
			if (element.getAttribute("innerHTML").equals(newCredUsername)) {
				edited = true;
				break;
			}
		}
		Assertions.assertTrue(edited);
	}

	@Test
	public void DeleteCredentialTest() {
		WebDriverWait wait = new WebDriverWait (driver, 30);
		JavascriptExecutor jse =(JavascriptExecutor) driver;

		//login
		driver.get("http://localhost:" + this.port + "/login");
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.sendKeys(userName);
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.sendKeys(password);
		WebElement loginButton = driver.findElement(By.id("buttonLogin"));
		loginButton.click();
		Assertions.assertEquals("Home", driver.getTitle());

		WebElement credTab = driver.findElement(By.id("nav-credentials-tab"));
		jse.executeScript("arguments[0].click()", credTab);
		WebElement credsTable = driver.findElement(By.id("credentialTable"));
		List<WebElement> credentialsList = credsTable.findElements(By.tagName("td"));
		WebElement deleteElement = null;
		for (int i = 0; i < credentialsList.size(); i++) {
			WebElement element = credentialsList.get(i);
			deleteElement = element.findElement(By.name("CredentialDelete"));
			if (deleteElement != null){
				break;
			}
		}
		wait.until(ExpectedConditions.elementToBeClickable(deleteElement)).click();
		Assertions.assertEquals("Result", driver.getTitle());
	}

}
