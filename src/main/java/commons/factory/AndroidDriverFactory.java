package commons.factory;

import static commons.utils.ConfigReader.getProperty;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class AndroidDriverFactory {
  static String path = System.getProperty("user.dir") + getProperty("pathApp");

  public static AndroidDriver createDriver() {
    try {
      String appPackage = getProperty("appPackage");
      if (!isAppInstalled(appPackage)) {
        if (getProperty("pathApp") != null && !getProperty("pathApp").isEmpty()) {
          installApp(path);
        } else {
          try {
            Process startProcess = Runtime.getRuntime().exec(new String[]{
                "adb", "shell", "monkey", "-p", appPackage, "-c", "android.intent.category.LAUNCHER", "1"
            });
            startProcess.waitFor();
            System.out.println("Приложение было запущено вручную через ADB.");
          } catch (Exception ex) {
            throw new RuntimeException("Приложение не установлено и путь к APK не указан в конфиге, а запуск через ADB тоже не удался!", ex);
          }
        }
      }
      UiAutomator2Options options = new UiAutomator2Options()
          .setPlatformName(getProperty("platformName"))
          .setDeviceName(getProperty("deviceName"))
          .setAppPackage(appPackage)
          .setAppActivity(getProperty("appActivity"))
          .setNoReset(true);

      if (Boolean.parseBoolean(getProperty("forceReinstall"))) {
        options.setApp(path);
      }

      return new AndroidDriver(new URL(getProperty("url")), options);
    } catch (Exception e) {
      throw new RuntimeException("Не удалось создать AndroidDriver", e);
    }
  }

  private static boolean isAppInstalled(String packageName) {
    try {
      Process process = Runtime.getRuntime().exec(new String[]{"adb", "shell", "pm", "list", "packages", packageName});
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      String line;
      while ((line = reader.readLine()) != null) {
        if (line.contains(packageName)) {
          return true;
        }
      }
      return false;
    } catch (Exception e) {
      throw new RuntimeException("Ошибка при проверке установки приложения", e);
    }
  }

  private static void installApp(String apkPath) {
    try {
      Process process = Runtime.getRuntime().exec(new String[]{"adb", "install", apkPath});
      process.waitFor();
      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
      String line;
      while ((line = reader.readLine()) != null) {
        if (line.contains("Success")) {
          System.out.println("Приложение успешно установлено!");
          return;
        }
      }
      throw new RuntimeException("Не удалось установить приложение: " + apkPath);
    } catch (Exception e) {
      throw new RuntimeException("Ошибка при установке приложения", e);
    }
  }
}