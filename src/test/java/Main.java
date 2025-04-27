import commons.utils.ConfigReader;

public class Main {
  public static void main(String[] args) {
    System.out.println(ConfigReader.getProperty("url"));
    System.out.println(ConfigReader.getProperty("platformName"));

    //"RZ8T121ZE3H" - my device
    //"emulator-5554"

  }
}
