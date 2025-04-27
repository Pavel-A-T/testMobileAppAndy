package commons.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.util.Map;

public class ConfigReader {
  static String fileName = "config.json";

  public static String getProperty(String property) {
    ObjectMapper objectMapper = new ObjectMapper();
    InputStream inputStream = ConfigReader.class.getClassLoader().getResourceAsStream(fileName);
    try {
      Map<String, Object> configMap = objectMapper.readValue(inputStream, Map.class);
      return configMap.get(property).toString();
    }
    catch (Exception e) {
      return null;
    }
  }
}
