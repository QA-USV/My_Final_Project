package ru.iteco.fmhandroid.AdminRoleTests;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.PickerActions.setDate;
import static androidx.test.espresso.contrib.PickerActions.setTime;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static ValuesForTests.Methods.childAtPosition;

import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.AppActivity;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class AdminEditingNewsPageTests extends ValuesForTests.ValuesForTests {

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    private View decorView;

    @Before
    public void setUp() throws InterruptedException {
        mActivityScenarioRule.getScenario().onActivity(activity -> decorView = activity.getWindow().getDecorView());
        try {
            Thread.sleep(1000);
            ToSignOutApp();
        } catch (NoMatchingViewException ignore) {
        } finally {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            AdminOpenEditingNewsPage();
        }
        Thread.sleep(1000);
    }

    @After
    public void QuitApp() throws InterruptedException {
//        Thread.sleep(1000);
        try {
            onView(withId(android.R.id.button1)).perform(click());
        } catch (NoMatchingViewException ignore) {
            try {
                onView(withId(android.R.id.button2)).perform(click());
            } catch (NoMatchingViewException ignore1) {
                try {
                    CancelCreatingOrEditing();
                } catch (NoMatchingViewException ignore2) {
                    try {
                        ToSignOutApp();
                    } catch (NoMatchingViewException ignore3) {
                    }
                }
            }
        }
    }

    @Test
    public void shouldFullEditNewsWithTimePicker() throws InterruptedException {
        onView(withId(R.id.news_item_category_text_auto_complete_text_view))
                .check(matches(isDisplayed()))
                .perform(clearText(),
                        click());

        Thread.sleep(2000);

        onData(anything())
                .inRoot(isPlatformPopup())
                .atPosition(categoryNum)
                .perform(click());

        onView(withId(R.id.news_item_title_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(newsTitleEditing),
                        closeSoftKeyboard());

        onView(withId(R.id.news_item_publish_date_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(isAssignableFrom(DatePicker.class))
                .check(matches(isDisplayed()))
                .perform(setDate(
                        publicationDay.getYear(),
                        publicationDay.getMonthValue(),
                        publicationDay.getDayOfMonth()));

        onView(withId(android.R.id.button1))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.news_item_publish_time_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(isAssignableFrom(TimePicker.class))
                .check(matches(isDisplayed()))
                .perform(setTime(
                        planHourEditing,
                        planMinutesEditing));

        onView(withId(android.R.id.button1))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.news_item_description_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click())
                .perform(replaceText(newsDescriptionEditing),
                        closeSoftKeyboard());

        onView(withId(R.id.switcher))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.save_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        Thread.sleep(5000);

        onView(withId(R.id.news_list_recycler_view))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldFullEditNewsWithKeyboard() throws InterruptedException {
        onView(withId(R.id.news_item_category_text_auto_complete_text_view))
                .check(matches(isDisplayed()))
                .perform(clearText(),
                        click());

        Thread.sleep(2000);

        onData(anything())
                .inRoot(isPlatformPopup())
                .atPosition(categoryNum)
                .perform(click());

        onView(withId(R.id.news_item_title_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(newsTitleEditing),
                        closeSoftKeyboard());

        onView(withId(R.id.news_item_publish_date_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(isAssignableFrom(DatePicker.class))
                .check(matches(isDisplayed()))
                .perform(setDate(
                        publicationDay.getYear(),
                        publicationDay.getMonthValue(),
                        publicationDay.getDayOfMonth()));

        onView(withId(android.R.id.button1))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.news_item_publish_time_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(allOf(withClassName(is("androidx.appcompat.widget.AppCompatImageButton")),
                childAtPosition(
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                4),
                        0),
                isDisplayed()))
                .perform(click());

        onView(allOf(withClassName(is("androidx.appcompat.widget.AppCompatEditText")),
                childAtPosition(allOf(withClassName(is("android.widget.RelativeLayout")),
                                childAtPosition(withClassName(is("android.widget.TextInputTimePickerView")),
                                        1)),
                        0),
                isDisplayed()))
                .perform(replaceText(String.valueOf(planHour)), closeSoftKeyboard());

        onView(allOf(withClassName(is("androidx.appcompat.widget.AppCompatEditText")),
                childAtPosition(allOf(withClassName(is("android.widget.RelativeLayout")),
                                childAtPosition(withClassName(is("android.widget.TextInputTimePickerView")),
                                        1)),
                        3),
                isDisplayed()))
                .perform(replaceText(String.valueOf(planMinutes)), closeSoftKeyboard());

        onView(allOf(withId(android.R.id.button1)))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.news_item_description_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click())
                .perform(replaceText(newsDescriptionEditing),
                        closeSoftKeyboard());

        onView(withId(R.id.switcher))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.save_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        Thread.sleep(5000);

        onView(withId(R.id.news_list_recycler_view))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotEditNewsWithEmptyCategoryField() {
        onView(withId(R.id.news_item_category_text_auto_complete_text_view))
                .check(matches(isDisplayed()))
                .perform(clearText());

        onView(withId(R.id.save_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        onView(withText(emptyFieldsNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotEditNewsWithEmptyTitleField() {
        onView(withId(R.id.news_item_title_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(emptyStringField),
                        closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        onView(withText(emptyFieldsNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotEditNewsWithEmptyDescriptionField() {
        onView(withId(R.id.news_item_description_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click())
                .perform(replaceText(emptyStringField),
                        closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        onView(withText(emptyFieldsNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotEditNewsWithOnlyHyphenInTitleField() {
        onView(withId(R.id.news_item_title_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(hyphenInStringField),
                        closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        onView(withText(emptyFieldsNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotEditNewsWithOnlyHyphenInDescriptionField() {
        onView(withId(R.id.news_item_description_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click())
                .perform(replaceText(hyphenInStringField),
                        closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        onView(withText(emptyFieldsNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldEditNewsWithHyphenFirstSymbolInTitleField() throws InterruptedException {
        onView(withId(R.id.news_item_title_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(hyphenFirstInStringField),
                        closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        Thread.sleep(2000);

        onView(first(allOf(withId(R.id.edit_news_item_image_view),
                childAtPosition(childAtPosition(withId(R.id.news_item_material_card_view),
                        0), 15), isDisplayed())))
                .perform(click());

        Thread.sleep(2000);

        onView(withId(R.id.news_item_title_text_input_edit_text))
                .check(matches(isDisplayed()))
                .check(matches(withText(hyphenFirstInStringFieldDeleted)));
    }

    @Test
    public void shouldEditNewsWithHyphenFirstSymbolInDescriptionField() throws InterruptedException {
        onView(withId(R.id.news_item_description_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click())
                .perform(replaceText(hyphenInStringField),
                        closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        Thread.sleep(2000);

        onView(first(allOf(withId(R.id.edit_news_item_image_view),
                childAtPosition(childAtPosition(withId(R.id.news_item_material_card_view),
                        0), 15), isDisplayed())))
                .perform(click());

        Thread.sleep(2000);

        onView(withId(R.id.news_item_description_text_input_edit_text))
                .check(matches(isDisplayed()))
                .check(matches(withText(hyphenFirstInStringFieldDeleted)))
                .perform(closeSoftKeyboard());
    }

    @Test
    public void shouldNotEditNewsWithInvalidSymbolsInTitleField() {
        onView(withId(R.id.news_item_title_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(invalidSymbols),
                        closeSoftKeyboard());

        onView(withText(invalidSymbolsNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotEditNewsWithInvalidSymbolsInDescriptionField() {
        onView(withId(R.id.news_item_description_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(invalidSymbols),
                        closeSoftKeyboard());

        onView(withText(invalidSymbolsNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotEditNewsUnderLowerEdgeInTitleField() {
        onView(withId(R.id.news_item_title_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(lessThen5ValidSymbolsTitleOrComment),
                        closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        onView(withText(symbolsOutOfEdgesTitleNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotEditNewsAboveUpperEdgeInTitleField() {
        onView(withId(R.id.news_item_title_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(moreThen30ValidSymbolsTitleOrComment),
                        closeSoftKeyboard());

        onView(withText(symbolsOutOfEdgesTitleNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotEditNewsUnderLowerEdgeInDescriptionField() {
        onView(withId(R.id.news_item_description_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(lessThen10ValidSymbolsDescription),
                        closeSoftKeyboard());

        onView(withId(R.id.switcher))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.save_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        onView(withText(symbolsOutOfEdgesDescriptionNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotEditNewsAboveUpperEdgeInDescriptionField() {
        onView(withId(R.id.news_item_description_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(moreThen100ValidSymbolsDescription),
                        closeSoftKeyboard());

        onView(withText(symbolsOutOfEdgesDescriptionNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotEditNewsWithInvalidPastPublicationDate() {
        onView(withId(R.id.news_item_publish_date_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(isAssignableFrom(DatePicker.class))
                .check(matches(isDisplayed()))
                .perform(setDate(
                        invalidPublicationDay.getYear(),
                        invalidPublicationDay.getMonthValue(),
                        invalidPublicationDay.getDayOfMonth()));

        onView(allOf(withId(android.R.id.button1)))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withText(invalidPublicationDay.getDayOfMonth()))
                .check(doesNotExist());
    }

    @Test
    public void shouldNotEditNewsWithInvalidPublicationTimeKeyboard() {
        onView(withId(R.id.news_item_publish_time_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(allOf(withClassName(is("androidx.appcompat.widget.AppCompatImageButton")),
                childAtPosition(
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                4),
                        0),
                isDisplayed()))
                .perform(click());

        onView(allOf(withClassName(is("androidx.appcompat.widget.AppCompatEditText")),
                childAtPosition(allOf(withClassName(is("android.widget.RelativeLayout")),
                                childAtPosition(withClassName(is("android.widget.TextInputTimePickerView")),
                                        1)),
                        0),
                isDisplayed()))
                .perform(replaceText(String.valueOf(invalidPlanHour)), closeSoftKeyboard());

        onView(allOf(withClassName(is("androidx.appcompat.widget.AppCompatEditText")),
                childAtPosition(allOf(withClassName(is("android.widget.RelativeLayout")),
                                childAtPosition(withClassName(is("android.widget.TextInputTimePickerView")),
                                        1)),
                        3),
                isDisplayed()))
                .perform(replaceText(String.valueOf(invalidPlanMinutes)), closeSoftKeyboard());

        onView(allOf(withId(android.R.id.button1)))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withText(wrongTimeNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldCancelEditingNews() throws InterruptedException {
        onView(withId(R.id.cancel_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        onView(withText(areYouSureCancelMessage))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));

        onView(withId(android.R.id.button1))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(2000);

        onView(withId(R.id.news_list_recycler_view))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldReturnToEditingNesPageAfterRefusalToCancel() {
        onView(withId(R.id.news_item_title_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(newsTitle),
                        closeSoftKeyboard());

        onView(withId(R.id.cancel_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        onView(withText(areYouSureCancelMessage))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));

        onView(withId(android.R.id.button2))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.news_item_title_text_input_edit_text))
                .check(matches(isDisplayed()))
                .check(matches(withText(newsTitle)));
    }
}