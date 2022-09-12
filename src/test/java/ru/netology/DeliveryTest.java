package ru.netology;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;
import static com.codeborne.selenide.Selectors.withText;

import com.codeborne.selenide.Condition;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class DeliveryTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999/");
    }

    @AfterEach
    void clear() {
        clearBrowserCookies();
        clearBrowserLocalStorage();
    }

    @Test
    void shouldSendForm() {
        $("[data-test-id='city'] input").setValue("Казань");
        String deliveryDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement'] span").click();
        //$(withText("Забронировать")).click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id='notification'] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .should(exactText("Встреча успешно забронирована на " + deliveryDate));
    }

    //город из двух слов
    @Test
    void shouldSendFormTwoWordsInTheNameOfTheCityDash() {
        $("[data-test-id='city'] input").setValue("Ростов-на-Дону");
        String deliveryDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement'] span").click();
        //$(withText("Забронировать")).click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id='notification'] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .should(exactText("Встреча успешно забронирована на " + deliveryDate));
    }

    //город через дефис
    @Test
    void shouldSendFormTwoWordsInTheNameOfTheCity() {
        $("[data-test-id='city'] input").setValue("Великий Новгород");
        String deliveryDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement'] span").click();
        //$(withText("Забронировать")).click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id='notification'] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .should(exactText("Встреча успешно забронирована на " + deliveryDate));
    }

    //город не административный центр
    @Test
    void shouldSendFormNotAnAdministrativeCenter() {
        $("[data-test-id='city'] input").setValue("Магнитогорск");
        String deliveryDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement'] span").click();
        //$(withText("Забронировать")).click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id='city'].input_invalid").shouldBe(visible, Duration.ofSeconds(5))
                .should(exactText("Доставка в выбранный город недоступна"));
    }

    //город на английском
    @Test
    void shouldSendFormCityInEnglish() {
        $("[data-test-id='city'] input").setValue("Moscow");
        String deliveryDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement'] span").click();
        //$(withText("Забронировать")).click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id='city'].input_invalid").shouldBe(visible, Duration.ofSeconds(5))
                .should(exactText("Доставка в выбранный город недоступна"));
    }

    //двойная фамилия
    @Test
    void shouldSendFormDoubleSurname() {
        $("[data-test-id='city'] input").setValue("Великий Новгород");
        String deliveryDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id='name'] input").setValue("Иванов-Петров Иван");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement'] span").click();
        //$(withText("Забронировать")).click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id='notification'] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .should(exactText("Встреча успешно забронирована на " + deliveryDate));
    }

    //двойное имя
    @Test
    void shouldSendFormDoubleName() {
        $("[data-test-id='city'] input").setValue("Великий Новгород");
        String deliveryDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван-Петр");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement'] span").click();
        //$(withText("Забронировать")).click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id='notification'] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .should(exactText("Встреча успешно забронирована на " + deliveryDate));
    }

    //имя и фамилия на английском
    @Test
    void shouldSendFormNaneAndSurnameInEnglish() {
        $("[data-test-id='city'] input").setValue("Великий Новгород");
        String deliveryDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id='name'] input").setValue("Ivanov Ivan");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement'] span").click();
        //$(withText("Забронировать")).click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id='name'].input_invalid")
                .shouldBe(visible, Duration.ofSeconds(5))
                .shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    //неверный телефон
    @Test
    void shouldSendFormPhone() {
        $("[data-test-id='city'] input").setValue("Великий Новгород");
        String deliveryDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+799999999999");
        $("[data-test-id='agreement'] span").click();
        //$(withText("Забронировать")).click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id='phone'].input_invalid")
                .shouldBe(visible, Duration.ofSeconds(5))
                .shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    //без чекбокса
    @Test
    void shouldSendFormCheckBox() {
        $("[data-test-id='city'] input").setValue("Великий Новгород");
        String deliveryDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        //$("[data-test-id='agreement'] span").click();
        //$(withText("Забронировать")).click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id='agreement'].input_invalid")
                .shouldBe(visible, Duration.ofSeconds(5))
                .shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }

    //пустое поле города
    @Test
    void shouldSendCityEmpty() {
        $("[data-test-id='city'] input").setValue("");
        String deliveryDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement'] span").click();
        //$(withText("Забронировать")).click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id='city'].input_invalid")
                .shouldBe(visible, Duration.ofSeconds(5))
                .shouldHave(text("Поле обязательно для заполнения"));
    }

    //дата на следующий день
    @Test
    void shouldSendNextDay() {
        $("[data-test-id='city'] input").setValue("Великий Новгород");
        String deliveryDate = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+79999999999");
        $("[data-test-id='agreement'] span").click();
        //$(withText("Забронировать")).click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $(withText("дату невозможен")).should(visible, Duration.ofSeconds(5));
        $x("//*[@data-test-id=\"notification\"]").shouldNot(visible, Duration.ofSeconds(15));
    }

}
