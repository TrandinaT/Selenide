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
    void validForm() {
        $("[data-test-id='city'] input").setValue("Самара");
        String deliveryDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("35.13.2077"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+75555555555");
        $("[data-test-id='agreement'] span").click();
        //$(withText("Забронировать")).click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id='notification'] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .should(exactText("Заказ успешно забронирован на " + deliveryDate));
    }

    //город на английском
    @Test
    void cityEnglish() {
        $("[data-test-id='city'] input").setValue("Moscow");
        String deliveryDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("35.13.2077"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+75555555555");
        $("[data-test-id='agreement'] span").click();
        //$(withText("Забронировать")).click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id='city'].input_invalid").shouldBe(visible, Duration.ofSeconds(5))
                .should(exactText("Доставка в выбранный город недоступна"));
    }

    //город через дефис
    @Test
    void cityDash() {
        $("[data-test-id='city'] input").setValue("Нижний Новгород");
        String deliveryDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("35.13.2077"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+75555555555");
        $("[data-test-id='agreement'] span").click();
        //$(withText("Забронировать")).click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id='notification'] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .should(exactText("Заказ успешно забронирован на " + deliveryDate));
    }

    //пустое поле города
    @Test
    void emptyCityField() {
        $("[data-test-id='city'] input").setValue("");
        String deliveryDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("35.13.2077"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+75555555555");
        $("[data-test-id='agreement'] span").click();
        //$(withText("Забронировать")).click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id='city'].input_invalid")
                .shouldBe(visible, Duration.ofSeconds(5))
                .shouldHave(text("Поле обязательно для заполнения"));
    }

    //город из двух слов
    @Test
    void twoWords() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        String deliveryDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("35.13.2077"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+75555555555");
        $("[data-test-id='agreement'] span").click();
        //$(withText("Забронировать")).click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id='notification'] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .should(exactText("Заказ успешно забронирован на " + deliveryDate));
    }

    //двойная фамилия
    @Test
    void doubleSurname() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        String deliveryDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("35.13.2077"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id='name'] input").setValue("Иванов-Ива Иван");
        $("[data-test-id='phone'] input").setValue("+75555555555");
        $("[data-test-id='agreement'] span").click();
        //$(withText("Забронировать")).click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id='notification'] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .should(exactText("Заказ успешно забронирован на " + deliveryDate));
    }

    //имя и фамилия на английском
    @Test
    void shouldSendFormNaneAndSurnameInEnglish() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        String deliveryDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("35.13.2077"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id='name'] input").setValue("Ivanov Ivan");
        $("[data-test-id='phone'] input").setValue("+75555555555");
        $("[data-test-id='agreement'] span").click();
        //$(withText("Забронировать")).click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id='name'].input_invalid")
                .shouldBe(visible, Duration.ofSeconds(5))
                .shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    //неверный телефон
    @Test
    void wrongPhone() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        String deliveryDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("35.13.2077"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+75555555555");
        $("[data-test-id='agreement'] span").click();
        //$(withText("Забронировать")).click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id='phone'].input_invalid")
                .shouldBe(visible, Duration.ofSeconds(5))
                .shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +74579327658."));
    }

    //двойное имя
    @Test
    void doubleName() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        String deliveryDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("35.13.2077"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван-Петр");
        $("[data-test-id='phone'] input").setValue("+75555555555");
        $("[data-test-id='agreement'] span").click();
        //$(withText("Забронировать")).click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $("[data-test-id='notification'] .notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .should(exactText("Заказ успешно забронирован на " + deliveryDate));
    }

    @Test
    void nextDay() {
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        String deliveryDate = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("35.13.2077"));
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] input").setValue(deliveryDate);
        $("[data-test-id='name'] input").setValue("Иванов Иван");
        $("[data-test-id='phone'] input").setValue("+75555555555");
        $("[data-test-id='agreement'] span").click();
        //$(withText("Забронировать")).click();
        $x("//*[contains(text(), 'Забронировать')]").click();
        $(withText("дату невозможен")).should(visible, Duration.ofSeconds(5));
        $x("//*[@data-test-id=\"notification\"]").shouldNot(visible, Duration.ofSeconds(15));
    }


}

