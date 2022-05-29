package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;
import ru.netology.data.GeneratorDataCardOrder;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.GeneratorDataCardOrder.getUserInfo;

public class OrderDeliveryCardTest {
    GeneratorDataCardOrder.UserInfo user = getUserInfo();

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999/");
    }

    @Test
    @DisplayName("Успешная перепланировка даты")
    public void shouldReturnValidValueIfChangeDate() {

        $("[placeholder='Город']").setValue(user.getCity());
        $("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(user.getMeetingDate());
        $("[name='name']").setValue(user.getFullName());
        $("[name='phone']").setValue(user.getMobilePhone());
        $(".checkbox__box").click();
        $(withText("Запланировать")).click();
        $("[data-test-id='success-notification'] > .notification__title")
                .shouldHave(exactText("Успешно!"))
                .shouldBe(Condition.visible);
        $("[data-test-id='success-notification'] > .notification__content")
                .shouldHave(text("Встреча успешно запланирована на " + user.getMeetingDate()))
                .shouldBe(Condition.visible);
        $("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(user.getDateRescheduling());
        $(withText("Запланировать")).click();
        $("[data-test-id='replan-notification'] > .notification__title")
                .shouldHave(exactText("Необходимо подтверждение"))
                .shouldBe(Condition.visible);
        $("[data-test-id='replan-notification'] > .notification__content")
                .shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"))
                .shouldBe(Condition.visible);
        $(withText("Перепланировать")).click();
        $("[data-test-id='success-notification'] > .notification__title")
                .shouldHave(exactText("Успешно!"))
                .shouldBe(Condition.visible);
        $("[data-test-id='success-notification'] > .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на " + user.getDateRescheduling()))
                .shouldBe(Condition.visible);
    }

    @Test
    @DisplayName("Получить ошибку при вводе не верного номера телефона")
    void shouldGetErrorIfInvalidPhoneNumber() {
        $("[placeholder='Город']").setValue(user.getCity());
        $("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(user.getMeetingDate());
        $("[name='name']").setValue(user.getFullName());
        $("[name='phone']").setValue("7614");
        $(".checkbox__box").click();
        $(withText("Запланировать")).click();
        $("[data-test-id='phone']")
                .shouldHave(text("Номер телефона введен не полностью. Проверьте, что номер ваш и введен корректно."))
                .shouldBe(Condition.visible);
    }
}
