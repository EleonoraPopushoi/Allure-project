package ru.netology.order;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.generator.DataGenerator.*;

public class OrderCardWithDeliveryTest {
	String newDate = makeDate();
	String makeName = makeName();
	String makePhone = makePhone();
	String makeCity = makeCity();
	String randomDate = randomDate();
	String makeInvalidName = makeInvalidName();
	String makeInvalidAboveMinPhoneNumber = makeInvalidAboveMinPhoneNumber();

	@BeforeAll
	static void setUpAll() {
		SelenideLogger.addListener("allure", new AllureSelenide());
	}

	@AfterAll
	static void tearDownAll() {
		SelenideLogger.removeListener("allure");
	}

	@BeforeEach
	void Setup() {
		open("http://localhost:9999");
	}

	@Test
	void shouldSubmitRequest() {

		$("[data-test-id=city] input").setValue(makeCity);
		$("[data-test-id=date] input").doubleClick().sendKeys(newDate);
		$("[data-test-id=name] input").setValue(makeName);
		$("[data-test-id=phone] input").setValue(makePhone);
		$("[data-test-id=agreement]").click();
		$(byText("Запланировать")).click();
		$("[data-test-id='success-notification'] .notification__content").waitUntil(visible,15000).shouldHave(exactText("Встреча успешно запланирована на " + newDate));
		$("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
		$("[data-test-id=date] input").doubleClick().setValue(randomDate);
		$(byText("Запланировать")).click();
		$(withText("У вас уже запланирована встреча на другую дату. Перепланировать?")).shouldBe(visible);
		$("[data-test-id='replan-notification'] .button__text").click();
		$("[data-test-id='success-notification'] .notification__content").waitUntil(visible,15000).shouldHave(exactText("Встреча успешно запланирована на " + randomDate));
	}


	@Test
	void shouldNotSubmitWithoutCity() {
		$("[data-test-id=date] input").doubleClick().sendKeys(newDate);
		$("[data-test-id=name] input").setValue(makeName);
		$("[data-test-id=phone] input").setValue(makePhone);
		$("[data-test-id=agreement]").click();
		$(".button__text").click();
		$("[data-test-id='city'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
	}

	@Test
	void shouldNotSubmitWithoutName() {
		$("[data-test-id=city] input").setValue(makeCity);
		$("[data-test-id=date] input").doubleClick().sendKeys(newDate);
		$("[data-test-id=phone] input").setValue(makePhone);
		$("[data-test-id=agreement]").click();
		$(".button__text").click();
		$("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
	}

	@Test
	void shouldNotSubmitWithInvalidName() {
		$("[data-test-id=city] input").setValue(makeCity);
		$("[data-test-id=date] input").doubleClick().sendKeys(newDate);
		$("[data-test-id=name] input").setValue(makeInvalidName);
		$("[data-test-id=phone] input").setValue(makePhone);
		$("[data-test-id=agreement]").click();
		$(".button__text").click();
		$("[data-test-id='name'].input_invalid .input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
	}

	@Test
	void shouldNotSubmitWithoutPhone() {
		$("[data-test-id=city] input").setValue(makeCity);
		$("[data-test-id=date] input").doubleClick().sendKeys(newDate);
		$("[data-test-id=name] input").setValue(makeName);
		$("[data-test-id=agreement]").click();
		$(".button__text").click();
		$("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
	}

	@Test
	void shouldNotSendAboveMinPhoneNumber() {   //баг , при вводе некорректного номера телефона, приходит тодтвкрждение об успешном процессе.
		$("[data-test-id=city] input").setValue(makeCity);
		$("[data-test-id=date] input").doubleClick().sendKeys(newDate);
		$("[data-test-id=name] input").setValue(makeName);;
		$("[data-test-id=phone] input").setValue(makeInvalidAboveMinPhoneNumber);
		$("[data-test-id=agreement]").click();
		$(".button__text").click();
		$("[data-test-id='phone'].input_invalid .input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
	}

	@Test
	void shouldNotSubmitWithoutCheckbox() {
		$("[data-test-id=city] input").setValue(makeCity);
		$("[data-test-id=date] input").doubleClick().sendKeys(newDate);
		$("[data-test-id=name] input").setValue(makeName);
		$("[data-test-id=phone] input").setValue(makePhone);
		$(".button__text").click();
		$(".checkbox_size_m.input_invalid .checkbox__text").shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
	}

}

