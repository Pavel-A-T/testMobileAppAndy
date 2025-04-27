package commons.extentions;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.google.inject.Guice;
import commons.factory.AndroidDriverFactory;
import commons.modules.GuiceModule;
import io.appium.java_client.android.AndroidDriver;
import org.junit.jupiter.api.extension.*;

public class AndroidExtension implements BeforeEachCallback, AfterEachCallback {

  @Override
  public void beforeEach(ExtensionContext extensionContext) {
    AndroidDriver driver = AndroidDriverFactory.createDriver();
    WebDriverRunner.setWebDriver(driver);
    extensionContext.getTestInstance().ifPresent(instance ->
        Guice.createInjector(new GuiceModule()).injectMembers(instance));
  }

  @Override
  public void afterEach(ExtensionContext extensionContext) throws Exception {
    Selenide.closeWebDriver();
  }
}
