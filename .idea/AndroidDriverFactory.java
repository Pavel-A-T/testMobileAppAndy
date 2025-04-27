package commons.factory;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

import java.net.URL;

import static commons.utils.ConfigReader.getProperty;

public class AndroidDriverFactory {
  static String path = System.getProperty("user.dir") + getProperty("pathApp");

  public static AndroidDriver createDriver() {
    try {
      UiAutomator2Options options = new UiAutomator2Options()
          .setPlatformName(getProperty("platformName"))
          .setDeviceName(getProperty("deviceName"))
          .setApp(System.getProperty(path))
          .setAppPackage(getProperty("appPackage"))
          .setAppActivity(getProperty("appActivity"))
          .setNoReset(true);
      return new AndroidDriver(new URL(getProperty("url")), options);
    } catch (Exception e) {
      throw new RuntimeException("Не удалось создать AndroidDriver", e);
    }
  }
}
