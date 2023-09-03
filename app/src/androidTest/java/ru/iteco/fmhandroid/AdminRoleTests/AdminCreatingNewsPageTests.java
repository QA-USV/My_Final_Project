package ru.iteco.fmhandroid.AdminRoleTests;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.PickerActions.setDate;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
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
public class AdminCreatingNewsPageTests extends ValuesForTests.ValuesForTests {

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
            AdminOpenCreatingNewsPage();
        }
        Thread.sleep(1000);
    }

    @After
    public void QuitApp() throws InterruptedException {
        Thread.sleep(2000);
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
    public void shouldCreateNewsWithTimePicker() throws InterruptedException {
        onView(withId(R.id.news_item_category_text_auto_complete_text_view))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(2000);

        onData(anything())
                .inRoot(isPlatformPopup())
                .atPosition(categoryNum)
                .perform(click());

        onView(withId(R.id.news_item_title_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(newsTitle),
                        closeSoftKeyboard());

       SetPublicationDate();

        onView(withId(R.id.news_item_publish_time_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click());

        SetPlanTimeWithTimePicker();

        onView(withId(R.id.news_item_description_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(newsDescription),
                        closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        Thread.sleep(5000);

        onView(withId(R.id.news_list_recycler_view))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldCreateNewsWithTimeKeyboard() throws InterruptedException {
        onView(withId(R.id.news_item_category_text_auto_complete_text_view))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(1000);

        onData(anything())
                .inRoot(isPlatformPopup())
                .atPosition(categoryNum)
                .perform(click());

        onView(withId(R.id.news_item_title_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(newsTitle),
                        closeSoftKeyboard());

        SetPublicationDate();

        onView(withId(R.id.news_item_publish_time_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click());

        SetPlanTimeWithKeyboard();

        onView(withId(R.id.news_item_description_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(newsDescription),
                        closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        Thread.sleep(5000);

        onView(withId(R.id.news_list_recycler_view))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotCreateNewsWithEmptyCategoryField() throws InterruptedException {
        onView(withId(R.id.news_item_title_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(newsTitle),
                        closeSoftKeyboard());

        SetPublicationDate();

        onView(withId(R.id.news_item_publish_time_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click());

        SetPlanTimeWithTimePicker();

        onView(withId(R.id.news_item_description_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(newsDescription),
                        closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        Thread.sleep(2000);

        onView(withText(emptyFieldsNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotCreateNewsWithEmptyTitleField() throws InterruptedException {
        onView(withId(R.id.news_item_category_text_auto_complete_text_view))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(1000);

        onData(anything())
                .inRoot(isPlatformPopup())
                .atPosition(categoryNum)
                .perform(click());

        onView(withId(R.id.news_item_title_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(emptyStringField),
                        closeSoftKeyboard());

        SetPublicationDate();

        onView(withId(R.id.news_item_publish_time_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click());

        SetPlanTimeWithTimePicker();

        onView(withId(R.id.news_item_description_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(newsDescription),
                        closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        Thread.sleep(2000);

        onView(withText(emptyFieldsNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotCreateNewsWithEmptyDateField() throws InterruptedException {
        onView(withId(R.id.news_item_category_text_auto_complete_text_view))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(1000);

        onData(anything())
                .inRoot(isPlatformPopup())
                .atPosition(categoryNum)
                .perform(click());

        onView(withId(R.id.news_item_title_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(newsTitle),
                        closeSoftKeyboard());

        onView(withId(R.id.news_item_publish_time_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click());

        SetPlanTimeWithTimePicker();

        onView(withId(R.id.news_item_description_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(newsDescription),
                        closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        Thread.sleep(2000);

        onView(withText(emptyFieldsNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotCreateNewsWithEmptyTimeField() throws InterruptedException {
        onView(withId(R.id.news_item_category_text_auto_complete_text_view))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(1000);

        onData(anything())
                .inRoot(isPlatformPopup())
                .atPosition(categoryNum)
                .perform(click());

        onView(withId(R.id.news_item_title_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(newsTitle),
                        closeSoftKeyboard());

        SetPublicationDate();

        onView(withId(R.id.news_item_description_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(newsDescription),
                        closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        Thread.sleep(2000);

        onView(withText(emptyFieldsNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotCreateNewsWithEmptyDescriptionField() throws InterruptedException {
        onView(withId(R.id.news_item_category_text_auto_complete_text_view))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(1000);

        onData(anything())
                .inRoot(isPlatformPopup())
                .atPosition(categoryNum)
                .perform(click());

        onView(withId(R.id.news_item_title_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(newsTitle),
                        closeSoftKeyboard());

        SetPublicationDate();

        onView(withId(R.id.news_item_publish_time_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click());

        SetPlanTimeWithTimePicker();

        onView(withId(R.id.news_item_description_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(emptyStringField),
                        closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        Thread.sleep(2000);

        onView(withText(emptyFieldsNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotCreateNewsWithOnlyHyphenInTitleField() throws InterruptedException {
        onView(withId(R.id.news_item_category_text_auto_complete_text_view))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(1000);

        onData(anything())
                .inRoot(isPlatformPopup())
                .atPosition(categoryNum)
                .perform(click());

        onView(withId(R.id.news_item_title_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(hyphenInStringField),
                        closeSoftKeyboard());

        SetPublicationDate();

        onView(withId(R.id.news_item_publish_time_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click());

        SetPlanTimeWithTimePicker();

        onView(withId(R.id.news_item_description_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(newsDescription),
                        closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        Thread.sleep(2000);

        onView(withText(emptyFieldsNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotCreateNewsWithOnlyHyphenInDescriptionField() throws InterruptedException {
        onView(withId(R.id.news_item_category_text_auto_complete_text_view))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(1000);

        onData(anything())
                .inRoot(isPlatformPopup())
                .atPosition(categoryNum)
                .perform(click());

        onView(withId(R.id.news_item_title_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(newsTitle),
                        closeSoftKeyboard());

        SetPublicationDate();

        onView(withId(R.id.news_item_publish_time_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click());

        SetPlanTimeWithTimePicker();

        onView(withId(R.id.news_item_description_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(hyphenInStringField),
                        closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        Thread.sleep(2000);

        onView(withText(emptyFieldsNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldCreateNewsWithHyphenFirstInTitleField() throws InterruptedException {
        onView(withId(R.id.news_item_category_text_auto_complete_text_view))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(1000);

        onData(anything())
                .inRoot(isPlatformPopup())
                .atPosition(categoryNum)
                .perform(click());

        onView(withId(R.id.news_item_title_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(hyphenFirstInStringField),
                        closeSoftKeyboard());

        SetPublicationDate();

        onView(withId(R.id.news_item_publish_time_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click());

        SetPlanTimeWithTimePicker();

        onView(withId(R.id.news_item_description_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(newsDescription),
                        closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        Thread.sleep(3000);

        onView(withId(R.id.news_list_recycler_view))
                .check(matches(not(hasDescendant(withText(hyphenFirstInStringField)))));
    }

    @Test
    public void shouldCreateNewsWithHyphenFirstInDescriptionField() throws InterruptedException {
        onView(withId(R.id.news_item_category_text_auto_complete_text_view))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(1000);

        onData(anything())
                .inRoot(isPlatformPopup())
                .atPosition(categoryNum)
                .perform(click());

        onView(withId(R.id.news_item_title_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(newsTitle),
                        closeSoftKeyboard());

        SetPublicationDate();

        onView(withId(R.id.news_item_publish_time_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click());

        SetPlanTimeWithTimePicker();

        onView(withId(R.id.news_item_description_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(hyphenFirstInStringField),
                        closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        Thread.sleep(3000);

        onView(withId(R.id.news_list_recycler_view))
                .check(matches(not(hasDescendant(withText(hyphenFirstInStringField)))));
    }

    @Test
    public void shouldNotLetInputInvalidSymbolsInTitleFieldCreatingNewsPage() {
        onView(withId(R.id.news_item_title_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(invalidSymbols),
                        closeSoftKeyboard());

        onView(withText(invalidSymbolsNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotLetInputInvalidSymbolsInDescriptionFieldCreatingNewsPage() {
        onView(withId(R.id.news_item_description_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(invalidSymbols),
                        closeSoftKeyboard());

        onView(withText(invalidSymbolsNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotCreateNewsWithUnderLowerEdgeInTitleField() throws InterruptedException {
        onView(withId(R.id.news_item_category_text_auto_complete_text_view))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(1000);

        onData(anything())
                .inRoot(isPlatformPopup())
                .atPosition(categoryNum)
                .perform(click());

        onView(withId(R.id.news_item_title_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(lessThen5ValidSymbolsTitleOrComment),
                        closeSoftKeyboard());

        SetPublicationDate();

        onView(withId(R.id.news_item_publish_time_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click());

        SetPlanTimeWithTimePicker();

        onView(withId(R.id.news_item_description_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(newsDescription),
                        closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        onView(withText(symbolsOutOfEdgesTitleNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotLetInputSymbolsAboveUpperEdgeInTitleFieldCreatingNewsPage() {
        onView(withId(R.id.news_item_title_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(moreThen30ValidSymbolsTitleOrComment),
                        closeSoftKeyboard());

        onView(withText(symbolsOutOfEdgesTitleNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotCreateNewsWithUnderLowerEdgeInDescriptionField() throws InterruptedException {
        onView(withId(R.id.news_item_category_text_auto_complete_text_view))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(1000);

        onData(anything())
                .inRoot(isPlatformPopup())
                .atPosition(categoryNum)
                .perform(click());

        onView(withId(R.id.news_item_title_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(newsTitle),
                        closeSoftKeyboard());

        SetPublicationDate();

        onView(withId(R.id.news_item_publish_time_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click());

        SetPlanTimeWithTimePicker();

        onView(withId(R.id.news_item_description_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(lessThen10ValidSymbolsDescription),
                        closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        onView(withText(symbolsOutOfEdgesDescriptionNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotLetInputSymbolsAboveUpperEdgeInDescriptionFieldCreatingNewsPage() {
        onView(withId(R.id.news_item_description_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(moreThen100ValidSymbolsDescription),
                        closeSoftKeyboard());

        onView(withText(symbolsOutOfEdgesDescriptionNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotCreateNewsWithInvalidPastPublicationDate() {
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
    public void shouldNotCreateNewsWithInvalidPublicationTime() {
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
    public void shouldCancelCreatingNews() throws InterruptedException {
        onView(withId(R.id.cancel_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        onView(withText(areYouSureCancelMessage))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));

        onView(withId(android.R.id.button1))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(3000);

        onView(withId(R.id.news_list_recycler_view))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldReturnToCreatingNewsPageAfterRefusalToCancel() {
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