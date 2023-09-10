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
import static androidx.test.espresso.matcher.ViewMatchers.isNotClickable;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

import android.view.View;
import android.widget.DatePicker;

import androidx.test.espresso.NoMatchingRootException;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.contrib.RecyclerViewActions;
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
public class AdminCreatingNewsPageTests extends ru.iteco.fmhandroid.ValuesForTests.ValuesForTests {

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
        Thread.sleep(4000);
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

        onData(anything())
                .inRoot(isPlatformPopup())
                .atPosition(categoryNum)
                .perform(click());

        onView(withId(R.id.news_item_title_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(newsTitleForTimePicker),
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
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(4000);

        onView(withId(R.id.news_list_recycler_view))
                .perform(RecyclerViewActions.scrollTo(first(hasDescendant(allOf(withId(R.id.news_item_title_text_view),
                        withText(newsTitleForTimePicker))))))
                .check(matches(hasDescendant(allOf(withId(R.id.news_item_title_text_view),
                        withText(newsTitleForTimePicker)))));
    }

    @Test
    public void shouldCreateNewsWithTimeKeyboardForTime() throws Exception {
        onView(withId(R.id.news_item_category_text_auto_complete_text_view))
                .check(matches(isDisplayed()))
                .perform(click());

        onData(anything())
                .inRoot(isPlatformPopup())
                .atPosition(categoryNum)
                .perform(click());

        onView(withId(R.id.news_item_title_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(newsTitleForKeyboard),
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
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(4000);

        onView(withId(R.id.news_list_recycler_view))
                .perform(RecyclerViewActions.scrollTo(first(hasDescendant(allOf(withId(R.id.news_item_title_text_view),
                        withText(newsTitleForKeyboard))))))
                .check(matches(hasDescendant(allOf(withId(R.id.news_item_title_text_view),
                        withText(newsTitleForKeyboard)))));
    }

    @Test
    public void shouldNotCreateNewsWithEmptyCategoryField() {
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
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withText(emptyFieldsNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotCreateNewsWithEmptyTitleField() {
        onView(withId(R.id.news_item_category_text_auto_complete_text_view))
                .check(matches(isDisplayed()))
                .perform(click());

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
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withText(emptyFieldsNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotCreateNewsWithEmptyDateField() {
        onView(withId(R.id.news_item_category_text_auto_complete_text_view))
                .check(matches(isDisplayed()))
                .perform(click());

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
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withText(emptyFieldsNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotCreateNewsWithEmptyTimeField() {
        onView(withId(R.id.news_item_category_text_auto_complete_text_view))
                .check(matches(isDisplayed()))
                .perform(click());

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
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withText(emptyFieldsNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotCreateNewsWithEmptyDescriptionField() {
        onView(withId(R.id.news_item_category_text_auto_complete_text_view))
                .check(matches(isDisplayed()))
                .perform(click());

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

        onView(withText(emptyFieldsNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotCreateNewsWithOnlyHyphenInTitleField() {
        onView(withId(R.id.news_item_category_text_auto_complete_text_view))
                .check(matches(isDisplayed()))
                .perform(click());

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
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withText(emptyFieldsNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotCreateNewsWithOnlyHyphenInDescriptionField() {
        onView(withId(R.id.news_item_category_text_auto_complete_text_view))
                .check(matches(isDisplayed()))
                .perform(click());

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
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withText(emptyFieldsNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldCutOffFirstHyphenInTitleFieldCreatingNews() throws InterruptedException {
        onView(withId(R.id.news_item_category_text_auto_complete_text_view))
                .check(matches(isDisplayed()))
                .perform(click());

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
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(4000);

        onView(withId(R.id.news_list_recycler_view))
                .perform(RecyclerViewActions
                        .scrollTo(first(hasDescendant(allOf(withId(R.id.news_item_title_text_view),
                withText(hyphenFirstInStringFieldDeleted))))))
                .check(matches(hasDescendant(allOf(withId(R.id.news_item_title_text_view),
                        withText(hyphenFirstInStringFieldDeleted)))));
    }

    @Test
    public void shouldCutOffFirstHyphenInDescriptionFieldCreatingNews() throws InterruptedException {
        onView(withId(R.id.news_item_category_text_auto_complete_text_view))
                .check(matches(isDisplayed()))
                .perform(click());

        onData(anything())
                .inRoot(isPlatformPopup())
                .atPosition(categoryNum)
                .perform(click());

        onView(withId(R.id.news_item_title_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(newsTitleFirstHyphenInDescription),
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
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(4000);

        onView(withId(R.id.news_list_recycler_view))
                .perform(RecyclerViewActions
                        .scrollTo(first(hasDescendant(allOf(withId(R.id.news_item_title_text_view),
                                withText(containsString(newsTitleFirstHyphenInDescription)))))))
                .check(matches(hasDescendant(allOf(withId(R.id.news_item_description_text_view),
                        withText(hyphenFirstInStringFieldDeleted)))));
    }

    @Test
    public void shouldBanToInputInvalidSymbolsInTitleFieldCreatingNews() throws Exception {
        onView(withId(R.id.news_item_title_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(invalidSymbols), closeSoftKeyboard())
                .check(matches(withText(not(containsString(invalidSymbols)))));

        try {
            onView(withText(invalidSymbolsNotification))
                    .inRoot(withDecorView(not(decorView)))
                    .check(matches(isDisplayed()));
        } catch (NoMatchingRootException e) {
            throw new Exception("There is no Toast: " + '"' + invalidSymbolsNotification + '"');
        }
    }

    @Test
    public void shouldBanToInputInvalidSymbolsInDescriptionFieldCreatingNews() throws Exception {
        onView(withId(R.id.news_item_description_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(invalidSymbols), closeSoftKeyboard())
                .check(matches(withText(not(containsString(invalidSymbols)))));

        try {
            onView(withText(invalidSymbolsNotification))
                    .inRoot(withDecorView(not(decorView)))
                    .check(matches(isDisplayed()));
        } catch (NoMatchingRootException e) {
            throw new Exception("There is no Toast: " + '"' + invalidSymbolsNotification + '"');
        }
    }

    @Test
    public void shouldNotCreateNewsWithSymbolsUnderLowerEdgeInTitleField() throws Exception {
        onView(withId(R.id.news_item_category_text_auto_complete_text_view))
                .check(matches(isDisplayed()))
                .perform(click());

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
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(isNotClickable()));

        try {
            onView(withText(symbolsOutOfEdgesTitleNotification))
                    .inRoot(withDecorView(not(decorView)))
                    .check(matches(isDisplayed()));
        } catch (NoMatchingRootException e) {
            throw new Exception("There is no Toast: " + '"' + symbolsOutOfEdgesTitleNotification + '"');
        }
    }

    @Test
    public void shouldCutOffInputSymbolsAboveUpperEdgeInTitleFieldCreatingNews() {
        onView(withId(R.id.news_item_title_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(moreThen50ValidSymbolsTitleOrComment))
                .check(matches(withText(cutSymbolsMoreThen50InTitleOrComment)));
    }

    @Test
    public void shouldNotCreateNewsWithSymbolsUnderLowerEdgeInDescriptionField() throws Exception {
        onView(withId(R.id.news_item_category_text_auto_complete_text_view))
                .check(matches(isDisplayed()))
                .perform(click());

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
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(isNotClickable()));

        try {
            onView(withText(symbolsOutOfEdgesDescriptionNotification))
                    .inRoot(withDecorView(not(decorView)))
                    .check(matches(isDisplayed()));
        } catch (NoMatchingRootException e) {
            throw new Exception("There is no Toast: " + '"' + symbolsOutOfEdgesDescriptionNotification + '"');
        }
    }

    @Test
    public void shouldCutOffInputSymbolsAboveUpperEdgeInDescriptionFieldCreatingNews() {
        onView(withId(R.id.news_item_description_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click())
                .perform(replaceText(moreThen100ValidSymbolsDescription))
                .check(matches(withText(cutSymbolsMoreThen100InDescription)));
    }

    @Test
    public void shouldBanInputPastPublicationDateCreatingNews() {
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
    public void shouldBanInputInvalidPublicationTimeWithKeyboardCreatingNews() throws Exception {
        onView(withId(R.id.news_item_publish_time_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click());

        SetInvalidPlanTimeWithKeyboard();

        onView(anyOf(withText(wrongTimeNotification1), withText(wrongTimeNotification2)))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldCancelCreatingNews() throws InterruptedException {
        onView(withId(R.id.cancel_button))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

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
    public void shouldReturnToCreatingPageAfterRefusalToCancel() {
        onView(withId(R.id.news_item_title_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(newsTitle),
                        closeSoftKeyboard());

        onView(withId(R.id.cancel_button))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

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