package tests;

import com.google.inject.Inject;
import com.pages.MainPage;
import commons.extentions.AndroidExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(AndroidExtension.class)
public class AndyTest {
  @Inject
  MainPage mainPage;

  @Test
  @DisplayName("'much' & 'many'")
  public void checkRuleTest() {
    mainPage.checkMuchManyRule();
  }

  @Test
  @DisplayName("Тест на отправку сообщения в чат")
  public void sendMessageToAndyTest() {
    mainPage.seyHelloAndy();
  }

  @Test
  @DisplayName("Запуск упражнения")
  public void getExerciseTest() {
    mainPage.startExercise();
  }
}
