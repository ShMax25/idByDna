package definitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pages.LoginPage;
import pages.MainPage;
import pages.AnalysisPage;

import java.util.Map;

import static support.TestContext.getData;

public class StepDefinitions {
  @Given("Open {string} page")
  public void openPage(String pageName) {
    switch (pageName) {
      case ("login"): new LoginPage().open();
      break;
      case ("main"): new MainPage().open();
      break;
      default: throw new RuntimeException("Unknown page " + pageName);
    }
  }

  @And("Login to the app")
  public void loginToTheApp () {
    LoginPage loginPage = new LoginPage();
    Map<String, String> data = getData("usersCredentials");
    loginPage.login(data.get("username"), data.get("password"));
  }

  @Then("Click the New Analysis button")
  public void clickTheNewAnalysisButton () {
    new MainPage().initiateNewAnalysis();
  }

  @And("Fill in the form and submit")
  public void fillInTheFormAndSubmit () {
    String analysisName = "test" + Math.random();
    new AnalysisPage().fillForm(analysisName);
  }

  @And("Click Select Rads and choose {string}")
  public void clickSelectRadsAndChoose (String readsOption) throws InterruptedException {
    new AnalysisPage().choseReadsOption(readsOption);


  }

  @And("Enter a {string} SRA Run ID")
  public void enterASRARunID (String option) {
    AnalysisPage analysisForm = new AnalysisPage();
    switch (option) {
      case ("valid"):
        Map<String, String> data = getData("runList");
        analysisForm.enterRunId(data.get("firstRun"));
        break;
      case ("invalid"):
        break;
      default: throw new RuntimeException("Unsuported option" + option);
    }
  }

  @When("Click submit analysis")
  public void clickSubmitAnalysis () {
    new AnalysisPage().submitAnalysis();
  }

  @Then("Verify that Analysis started")
  public void verifyThatAnalysisStarted () {
     boolean visibilityOfGraph = new AnalysisPage().isTestGraphShown();
  }
}
