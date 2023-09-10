package ru.iteco.fmhandroid.ValuesForTests;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.PickerActions.setDate;
import static androidx.test.espresso.contrib.PickerActions.setTime;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static ru.iteco.fmhandroid.ValuesForTests.Methods.childAtPosition;

import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import java.time.LocalDate;
import java.util.Random;

import ru.iteco.fmhandroid.R;

public class ValuesForTests {

    /**
     * Variables for Admin Authorization
     **/
    public String loginAdmin = "login2";
    public String passwordAdmin = "password2";
    public String wrongLoginAdmin = "wrongLogin";
    public String wrongPasswordAdmin = "wrongPassword";
    public String login = getApplicationContext().getString(R.string.login);
    public String password = getApplicationContext().getString(R.string.password);

    /**
     * Authorization Page
     **/
    public ViewInteraction loginField = onView(allOf(
            withParent(withParent(withId(R.id.login_text_input_layout))),
            withHint(login),
            isDisplayed()));

    public ViewInteraction passwordField = onView(allOf(
            withParent(withParent(withId(R.id.password_text_input_layout))),
            withHint(password),
            isDisplayed()));

    public ViewInteraction enterButton = onView(allOf(
            withId(R.id.enter_button),
            isDisplayed()));

    /**
     * MAIN Page
     **/
    public String mainMenu = getApplicationContext().getString(R.string.main_menu);
    public String news = getApplicationContext().getString(R.string.news);
    public String main = getApplicationContext().getString(R.string.main);
    public String claims = getApplicationContext().getString(R.string.claims);
    public String about = getApplicationContext().getString(R.string.about);

    public ViewInteraction vHospiceTradeMarkImage = onView(
            withId(R.id.trademark_image_view));

    public ViewInteraction userImage = onView(allOf(
            withId(R.id.authorization_image_button),
            withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

    public ViewInteraction menuButton = onView(allOf(withId(R.id.main_menu_image_button),
            withContentDescription(mainMenu),
            withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

    public ViewInteraction ourMissionButton = onView(allOf(
            withId(R.id.our_mission_image_button),
            withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));

    public ViewInteraction newsPageMenuButton = onView(allOf(
            withId(android.R.id.title),
            withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
            withText(news)));

    public ViewInteraction mainPageMenuButton = onView(allOf(
            withId(android.R.id.title),
            withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
            withText(main)));

    public ViewInteraction claimsPageMenuButton = onView(allOf(
            withId(android.R.id.title),
            withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
            withText(claims)));

    public ViewInteraction aboutPageMenuButton = onView(allOf(
            withId(android.R.id.title),
            withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE),
            withText(about)));

    /**
     * CONTROL PANEL & CLAIM Pages
     **/
    public String active = getApplicationContext().getString(R.string.news_control_panel_active);
    public String not_active = getApplicationContext().getString(R.string.news_control_panel_not_active);
    public String creating = getApplicationContext().getString(R.string.creating);
    public String editing = getApplicationContext().getString(R.string.editing);

    /**
     * Toasts & Notifications
     **/
    public String emptyLogPassNotification
            = getApplicationContext().getString(R.string.empty_login_or_password);

    public String wrongLogPassNotification
            = getApplicationContext().getString(R.string.wrong_login_or_password);

    public String emptyFieldsNotification
            = getApplicationContext().getString(R.string.empty_fields);

    public String emptyOneFieldNotification
            = getApplicationContext().getString(R.string.toast_empty_field);

    public String wrongTimeNotification1
            = "Указано недопустимое время.";

    public String wrongTimeNotification2 =
            "Введите действительное значение времени";

    public String impossibleToEditClaimNotification
            = getApplicationContext().getString(R.string.inability_to_edit_claim);

    public String areYouSureDeleteMessage
            = getApplicationContext().getString(R.string.irrevocable_deletion);

    public String areYouSureCancelMessage
            = getApplicationContext().getString(R.string.cancellation);

    public String invalidSymbolsNotification
            = "Поле может содержать только русские буквы и цифры";

    public String symbolsOutOfEdgesTitleNotification
            = "Поле Заголовок или Тема должно содержать от 5 до 50 символов";

    public String symbolsOutOfEdgesDescriptionNotification
            = "Поле Описание должно содержать от 10 до 100 символов";

    public String symbolsOutOfEdgesCommentsNotification
            = "Поле Комментарий должно содержать от 5 до 50 символов";

    /**
     * NEWS
     * Filling string fields
     **/
    public String newsTitle = "Тестовый Заголовок НОВОСТИ";
    public String newsTitleForTimePicker = "Заголовок НОВОСТИ для создания с циферблатом";
    public String newsTitleForKeyboard = "Заголовок НОВОСТИ для создания с клавиатурой";
    public String newsTitleFirstHyphenInDescription = "НОВОСТЬ с первым пробелом в Описании";
    public String newsTitleEditing = "Изменение Заголовка НОВОСТИ";
    public String newsDescription = "Тестовое Описание НОВОСТИ";
    public String newsDescriptionEditing = "Изменение Описания НОВОСТИ";

    /**
     * NEWS
     * Planning Dates for Filtering
     **/
    public int daysForStartFromNow = 6;
    public int daysForEndFromNow = 0;
    public LocalDate startDay = LocalDate.now().minusDays(daysForStartFromNow);
    public LocalDate endDay = LocalDate.now().minusDays(daysForEndFromNow);

    /**
     * NEWS
     * Random number of News category for Filtering
     **/
    public int[] categoriesNumber = {0, 1, 2, 3, 4, 6, 7};
    public int categoryNum = new Random().nextInt(categoriesNumber.length);

    /**
     * NEWS & CLAIMS
     * Random position number in RecycleView for actionOnItemAtPosition
     **/
    public int min = 10;
    public int max = 70;
    public int positionNum = new Random().nextInt(max - min + 1) + min;

    /**
     * CLAIMS
     * Status buttons and message
     **/
    public String saveButtonText = getApplicationContext().getString(R.string.save);
    public String cancelButtonText = getApplicationContext().getString(R.string.cancel);
    public String resetClaimButton = getApplicationContext().getString(R.string.throw_off);
    public String claimIsOpenedStatus = getApplicationContext().getString(R.string.status_open);
    public String takeClaimForExecutionButton = getApplicationContext().getString(R.string.take_to_work);
    public String claimIsInProgressStatus = getApplicationContext().getString(R.string.in_progress);
    public String executedClaimButton = getApplicationContext().getString(R.string.to_execute);
    public String claimExecutedStatus = getApplicationContext().getString(R.string.executed);
    public String cancelClaimButton = getApplicationContext().getString(R.string.cancel_claim);
    public String claimIsCanceled = getApplicationContext().getString(R.string.status_claim_canceled);

    /**
     * CLAIMS
     * Filling fields
     **/
    public String claimTitle = "Тестовая тема ЗАЯВКИ";
    public String claimTitleWithExecutor = "Тестовая тема ЗАЯВКИ с исполнителем";
    public String claimTitleForFakeExecutor = "Тестовая тема ЗАЯВКИ с несуществующим исполнителем";
    public String claimTitleNoExecutor = "Тестовая тема ЗАЯВКИ без исполнителя";
    public String claimTitleFirstHyphenInDescription = "ЗАЯВКА с первым пробелом в Описании";
    public String claimTitleAboveUpperEdgeInDescription = "ЗАЯВКА с превышением символов в Описании";
    public String claimTitleEditing = "Изменение темы ЗАЯВКИ";
    public String claimDescription = "Тестовое Описание ЗАЯВКИ";
    public String claimDescriptionEditing = "Изменение Описания ЗАЯВКИ";
    public String claimComments = "Комментарий к ЗАЯВКЕ";
    public String claimCommentsChanged = "Комментарий к ЗАЯВКЕ изменен";
    public String claimIsResetComment = "Комментарий к сбросу ЗАЯВКИ";
    public String claimIsExecutedComment = "ЗАЯВКА исполнена";
    public String claimExecutorName = "Ivanov Ivan Ivanovich";
    public String claimFakeExecutor = "Несуществующий исполнитель";
    public String claimExecutorNotAppointed = getApplicationContext().getString(R.string.not_assigned);

    /**
     * NEWS & CLAIMS
     * Common for string fields
     **/
    public String emptyStringField = "";
    public String hyphenInStringField = " ";
    public String hyphenFirstInStringField = " Текст с 1 пробелом в начале";
    public String hyphenFirstInStringFieldDeleted = "Текст с 1 пробелом в начале";
    public String invalidSymbols = "English and `~@#$%^&*|<>+_{}[]";
    public String lessThen5ValidSymbolsTitleOrComment = "Мало";
    public String moreThen50ValidSymbolsTitleOrComment
            = "Этот пример ввода текста содержит более 50 валидных символов.";
    public String cutSymbolsMoreThen50InTitleOrComment
            = "Этот пример ввода текста содержит более 50 валидны";
    public String lessThen10ValidSymbolsDescription = "Пока мало";
    public String moreThen100ValidSymbolsDescription
            = "Этот пример ввода текста содержит более 100 валидных символов и предназначен для тестирования поля Описание.";
    public String cutSymbolsMoreThen100InDescription
            = "Этот пример ввода текста содержит более 100 валидных символов и предназначен для тестирования поля О";

    /**
     * NEWS & CLAIMS
     * Planning Dated and Time for Executing Claims & Publishing News
     **/
    public int daysToExecuteClaim = 3;
    public int daysToPublishNews = 2;
    public LocalDate planDay = LocalDate.now().plusDays(daysToExecuteClaim);
    public LocalDate invalidPlanDay = LocalDate.now().minusDays(daysToExecuteClaim);
    public LocalDate publicationDay = LocalDate.now().plusDays(daysToPublishNews);
    public LocalDate invalidPublicationDay = LocalDate.now().minusDays(daysToPublishNews);
    public int planHour = 10;
    public int planMinutes = 30;
    public int planHourEditing = 15;
    public int planMinutesEditing = 45;
    public int invalidPlanHour = 24;
    public int invalidPlanMinutes = 60;

    /**
     * OUR MISSION
     */
    public String quoteTextForPositionOne = "Нет шаблона и стандарта";

    /**
     * ABOUT
     */
    public String privacyPolicy = getApplicationContext().getString(R.string.privacy_policy);
    public String termsOfUse = getApplicationContext().getString(R.string.terms_of_use);
    public String privacyPolicyUrl = getApplicationContext().getString(R.string.privacy_policy_url);
    public String termsOfUseUrl = getApplicationContext().getString(R.string.terms_of_use_url);

    /**
     * Authorization with Success
     **/
    public void AdminAuthorization() {
        loginField.perform(replaceText(loginAdmin));
        passwordField.perform(replaceText(passwordAdmin))
                .perform(closeSoftKeyboard());
        enterButton.perform(click());
    }

    /**
     * Opening Creating NEWS Page with the previous Authorization
     **/
    public void AdminOpenCreatingNewsPage() throws InterruptedException {
        AdminAuthorization();
        Thread.sleep(5000);
        onView(withId(R.id.all_news_text_view)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.edit_news_material_button)).perform(click());
        onView(withId(R.id.add_news_image_view)).perform(click());
        Thread.sleep(1000);
    }

    /**
     * NEWS
     * Opening Editing NEWS Page with the previous Authorization
     **/
    public void AdminOpenEditingNewsPage() throws InterruptedException {
        AdminAuthorization();
        Thread.sleep(5000);
        onView(withId(R.id.all_news_text_view)).perform(click());
        Thread.sleep(2000);
        onView(withId(R.id.edit_news_material_button)).perform(click());
        Thread.sleep(1000);
        onView(first(allOf(withId(R.id.edit_news_item_image_view),
                childAtPosition(childAtPosition(withId(R.id.news_item_material_card_view),
                        0), 15), isDisplayed())))
                .perform(click());
        Thread.sleep(2000);
    }

    /**
     * CLAIM
     * Opening Creating CLAIM Page with the previous Authorization
     **/
    public void AdminOpenCreatingClaimPage() throws InterruptedException {
        AdminAuthorization();
        Thread.sleep(5000);
        onView(withId(R.id.all_claims_text_view)).perform(scrollTo(), click());
        onView(withId(R.id.add_new_claim_material_button)).perform(click());
    }

    /**
     * Opening Editing CLAIM Page with the previous Authorization
     **/
    public void AdminOpenEditingClaimPage() throws InterruptedException {
        AdminAuthorization();
        Thread.sleep(5000);
        onView(withId(R.id.all_claims_text_view)).perform(scrollTo(), click());
        onView(withId(R.id.filters_material_button)).perform(click());
        onView(withId(R.id.item_filter_in_progress)).perform(click());
        onView(withId(R.id.claim_list_filter_ok_material_button)).perform(click());
        Thread.sleep(4000);
        onView(withId(R.id.claim_list_recycler_view))
                .perform(scrollToPosition(positionNum), actionOnItemAtPosition(positionNum, click()));
        Thread.sleep(1000);
        onView(allOf(withId(R.id.edit_processing_image_button),
                childAtPosition(childAtPosition(
                        withClassName(is("com.google.android.material.card.MaterialCardView")),
                        0), 25), isDisplayed()))
                .perform(scrollTo(), click());
    }

    /**
     * Sign out the App from MAIN Page
     **/
    public void ToSignOutApp() throws InterruptedException {
        Thread.sleep(3000);
        userImage.perform(click());
        Thread.sleep(1000);
        onView(withId(android.R.id.title)).perform(click());
    }

    /**
     * Closing Creating or Editing Page without saving
     **/
    public void CancelCreatingOrEditing() {
        onView(withId(R.id.cancel_button)).perform(scrollTo(), click());
        onView(withId(android.R.id.button1)).perform(click());
    }

    /**
     * Appoint an Executor for a CLAIM
     **/
    public void AppointExecutor() {
        onView(withId(R.id.executor_drop_menu_auto_complete_text_view)).perform(click());
        onData(anything()).inRoot(isPlatformPopup()).atPosition(0).perform(click());
        onView(withId(R.id.executor_drop_menu_auto_complete_text_view))
                .perform(closeSoftKeyboard());
    }

    /**
     * Set a Plan Date to execute CLAIM
     **/
    public void SetPlanDay() {
        onView(withId(R.id.date_in_plan_text_input_edit_text)).perform(click());
        onView(isAssignableFrom(DatePicker.class))
                .perform(setDate(planDay.getYear(), planDay.getMonthValue(), planDay.getDayOfMonth()));
        onView(allOf(withId(android.R.id.button1))).perform(click());
    }

    /**
     * Set a Publication Date for NEWS
     **/
    public void SetPublicationDate() {
        onView(withId(R.id.news_item_publish_date_text_input_edit_text)).perform(click());
        onView(isAssignableFrom(DatePicker.class))
                .perform(setDate(publicationDay.getYear(), publicationDay.getMonthValue(), publicationDay.getDayOfMonth()));
        onView(allOf(withId(android.R.id.button1))).perform(click());
    }

    /**
     * Set Time with TimePicker
     **/
    public void SetPlanTimeWithTimePicker() {
        onView(isAssignableFrom(TimePicker.class))
                .perform(setTime(planHour, planMinutes));
        onView(allOf(withId(android.R.id.button1))).perform(click());
    }

    /**
     * Set Time with keyboard
     **/
    public void SetPlanTimeWithKeyboard() throws Exception {
        try {
            onView(allOf(withClassName(is("androidx.appcompat.widget.AppCompatImageButton")),
                    childAtPosition(childAtPosition(
                            withClassName(is("android.widget.LinearLayout")),
                            4), 0), isDisplayed()))
                    .check(matches(isDisplayed()))
                    .perform(click());
        } catch (NoMatchingViewException e)
        {
            throw new Exception("There is no Keyboard button to set Time");
        }
        onView(allOf(withClassName(is("androidx.appcompat.widget.AppCompatEditText")),
                childAtPosition(allOf(withClassName(is("android.widget.RelativeLayout")),
                        childAtPosition(withClassName(is("android.widget.TextInputTimePickerView")),
                                1)), 0),
                isDisplayed()))
                .perform(replaceText(String.valueOf(planHour)), closeSoftKeyboard());
        onView(allOf(withClassName(is("androidx.appcompat.widget.AppCompatEditText")),
                childAtPosition(allOf(withClassName(is("android.widget.RelativeLayout")),
                        childAtPosition(withClassName(is("android.widget.TextInputTimePickerView")),
                                1)), 3),
                isDisplayed()))
                .perform(replaceText(String.valueOf(planMinutes)), closeSoftKeyboard());
        onView(allOf(withId(android.R.id.button1)))
                .perform(click());
    }

    public void SetInvalidPlanTimeWithKeyboard() throws Exception {
        try {
            onView(allOf(withClassName(is("androidx.appcompat.widget.AppCompatImageButton")),
                    childAtPosition(childAtPosition(
                            withClassName(is("android.widget.LinearLayout")),
                            4), 0), isDisplayed()))
                    .check(matches(isDisplayed()))
                    .perform(click());
        } catch (NoMatchingViewException e)
        {
            throw new Exception("There is no Keyboard button to set Time");
        }
        onView(allOf(withClassName(is("androidx.appcompat.widget.AppCompatEditText")),
                childAtPosition(allOf(withClassName(is("android.widget.RelativeLayout")),
                        childAtPosition(withClassName(is("android.widget.TextInputTimePickerView")),
                                1)), 0),
                isDisplayed()))
                .perform(replaceText(String.valueOf(invalidPlanHour)), closeSoftKeyboard());
        onView(allOf(withClassName(is("androidx.appcompat.widget.AppCompatEditText")),
                childAtPosition(allOf(withClassName(is("android.widget.RelativeLayout")),
                        childAtPosition(withClassName(is("android.widget.TextInputTimePickerView")),
                                1)), 3),
                isDisplayed()))
                .perform(replaceText(String.valueOf(invalidPlanMinutes)), closeSoftKeyboard());
        onView(allOf(withId(android.R.id.button1)))
                .perform(click());
    }

    public void InCaseNoCommentInClaimCardYet() throws Exception {
        try {
            onView(allOf(withId(R.id.edit_comment_image_button),
                    childAtPosition(
                            childAtPosition(
                                    withId(R.id.claim_comments_list_recycler_view), 0),
                            1),
                    isDisplayed()))
                    .perform(scrollTo())
                    .perform(click());
        } catch (NoMatchingViewException ignore) {
            onView(withId(R.id.add_comment_image_button))
                    .perform(scrollTo(),(click()));
            Thread.sleep(1000);
            onView(withId(R.id.comment_text_input_layout))
                    .perform(click());
            onView(allOf(childAtPosition(childAtPosition(
                            withId(R.id.comment_text_input_layout),
                            0), 0),
                    isDisplayed()))
                    .perform(replaceText(claimComments));
            onView(withId(R.id.save_button))
                    .perform(click());
            onView(allOf(withId(R.id.edit_comment_image_button),
                    childAtPosition(
                            childAtPosition(
                                    withId(R.id.claim_comments_list_recycler_view), 0),
                            1),
                    isDisplayed()))
                    .perform(scrollTo())
                    .perform(click());
        }
    }

    /**
     * Looking for the First Element
     **/
    public static <T> Matcher<T> first(final Matcher<T> matcher) {
        return new BaseMatcher<T>() {
            boolean isFirst = true;
            @Override
            public boolean matches(final Object item) {
                if (isFirst && matcher.matches(item)) {
                    isFirst = false;
                    return true;
                }
                return false;
            }
            @Override
            public void describeTo(final Description description) {
                description.appendText("should return first matching item");
            }
        };
    }
}