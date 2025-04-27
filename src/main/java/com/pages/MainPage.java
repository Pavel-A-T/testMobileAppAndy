package com.pages;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.appear;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class MainPage {
  private String greeting = "Hi Andy!";
  private String exerciseDescription = "Let's learn some words!";
  private String many = "OK. The rule is very simple: you should use \"many\" with countable nouns: \"many cats\".";
  private SelenideElement chat = $x("//android.widget.TextView[@text=\"Chat\"]");
  private SelenideElement message = $x("//android.widget.EditText[@content-desc=\"Type a message...\"]");
  private SelenideElement send = $x("//android.widget.TextView[@text=\"Send\"]");
  private SelenideElement grammar = $(By.xpath("//android.widget.TextView[@text=\"Grammar\"]"));
  private SelenideElement much = $(By.xpath("(//android.widget.TextView[@text=\"Start\"])[1]"));
  private SelenideElement button = $(By.id("android:id/button1"));
  private SelenideElement exercise = $x("//android.widget.TextView[@text=\"Exercise\"]");
  private SelenideElement start = $x("//android.widget.TextView[@text=\"Start\"]");
  private SelenideElement view = $x("//android.widget.ScrollView");

  private String getString(SelenideElement element) {
    ElementsCollection textViews = element.$$x(".//android.widget.TextView")
        .shouldHave(sizeGreaterThan(0), Duration.ofSeconds(5));

    List<String> foundTexts = textViews
        .stream()
        .map(SelenideElement::getText)
        .collect(Collectors.toList());
    return String.join(" ", foundTexts);
  }

  private void continueTest() {
    try {
      button.should(appear, Duration.ofSeconds(5)).click();
    } catch (Throwable ignored) {
      System.out.println("продолжаем тест...");
    }
  }

  public void checkMuchManyRule() {
    continueTest();
    grammar.hover().click();
    much.click();
    $$x("//android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup")
        .shouldHave(sizeGreaterThan(3), Duration.ofSeconds(10));

    SelenideElement container = $x("//android.widget.ScrollView/android.view.ViewGroup/"
        + "android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[1]");
    container.shouldBe(visible, Duration.ofSeconds(6));

    String result = getString(container);
    System.out.println("Найденный текст: " + result);
    assertEquals(many, result, "Текст - '" + result + " неправильный!");
  }

  public void seyHelloAndy() {
    continueTest();
    chat.hover().click();
    message.setValue(greeting);
    send.click();
    SelenideElement el = $x("//android.widget.TextView[@text, \"" + greeting + "\"]");
    el.shouldBe(visible);
  }

  public void startExercise() {
    continueTest();
    exercise.hover().click();
    start.hover().click();
    view.shouldBe(visible, Duration.ofSeconds(10));
    String result = getString(view);
    assertTrue(result.contains(exerciseDescription), "Текст - '" + result + " неправильный!");
  }
}
