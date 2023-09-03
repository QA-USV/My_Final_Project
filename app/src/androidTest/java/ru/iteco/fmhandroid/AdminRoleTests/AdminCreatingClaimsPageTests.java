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
public class AdminCreatingClaimsPageTests extends ValuesForTests.ValuesForTests {

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
            AdminOpenCreatingClaimPage();
        }
        Thread.sleep(1000);
    }

    @After
    public void QuitApp() throws InterruptedException {
        Thread.sleep(1000);
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
    public void shouldCreateClaimTimePickerNoExecutor() throws InterruptedException {
        onView(withId(R.id.title_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(claimTitle), closeSoftKeyboard());

        SetPlanDay();

        onView(withId(R.id.time_in_plan_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click());

        SetPlanTimeWithTimePicker();

        onView(withId(R.id.description_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(claimDescription), closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        Thread.sleep(3000);

        onView(withId(R.id.claim_list_recycler_view))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldCreateClaimKeyboardNoExecutor() throws InterruptedException {
        onView(withId(R.id.title_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(claimTitle), closeSoftKeyboard());

        SetPlanDay();

        onView(withId(R.id.time_in_plan_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click());

        SetPlanTimeWithKeyboard();

        onView(withId(R.id.description_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(claimDescription), closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        Thread.sleep(3000);

        onView(withId(R.id.claim_list_recycler_view))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldCreateClaimAppointExecutor() throws InterruptedException {
        onView(withId(R.id.title_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(claimTitle), closeSoftKeyboard());

        onView(withId(R.id.executor_drop_menu_auto_complete_text_view))
                .perform(click());

        onData(anything())
                .inRoot(isPlatformPopup())
                .atPosition(0)
                .perform(click());

        onView(withId(R.id.executor_drop_menu_auto_complete_text_view))
                .perform(closeSoftKeyboard());

        SetPlanDay();

        onView(withId(R.id.time_in_plan_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click());

        SetPlanTimeWithTimePicker();

        onView(withId(R.id.description_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(claimDescription), closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        Thread.sleep(3000);

        onView(withId(R.id.claim_list_recycler_view))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotCreateClaimAppointFakeExecutor() throws InterruptedException {
        onView(withId(R.id.title_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(claimTitle), closeSoftKeyboard());

        onView(withId(R.id.executor_drop_menu_auto_complete_text_view))
                .perform(click())
                .perform(replaceText(claimFakeExecutor), closeSoftKeyboard());

        Thread.sleep(2000);

        SetPlanDay();

        onView(withId(R.id.time_in_plan_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click());

        SetPlanTimeWithTimePicker();

        onView(withId(R.id.description_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(claimDescription), closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        Thread.sleep(4000);

        onView(withId(R.id.claim_list_recycler_view))
                .check(matches(not(hasDescendant(withText(claimFakeExecutor)))));
    }

    @Test
    public void shouldNotCreateClaimWithEmptyTitleField() {
        onView(withId(R.id.title_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(emptyStringField), closeSoftKeyboard());

        SetPlanDay();

        onView(withId(R.id.time_in_plan_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click());

        SetPlanTimeWithTimePicker();

        onView(withId(R.id.description_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(claimDescription), closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        onView(withText(emptyFieldsNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotCreateClaimWithEmptyDateField() {
        onView(withId(R.id.title_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(claimTitle), closeSoftKeyboard());

        onView(withId(R.id.time_in_plan_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click());

        SetPlanTimeWithTimePicker();

        onView(withId(R.id.description_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(claimDescription), closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        onView(withText(emptyFieldsNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotCreateClaimWithEmptyTimeField() {
        onView(withId(R.id.title_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(claimTitle), closeSoftKeyboard());

        SetPlanDay();

        onView(withId(R.id.description_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(claimDescription), closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        onView(withText(emptyFieldsNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotCreateClaimWithEmptyDescriptionField() {
        onView(withId(R.id.title_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(claimTitle), closeSoftKeyboard());

        SetPlanDay();

        onView(withId(R.id.time_in_plan_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click());

        SetPlanTimeWithTimePicker();

        onView(withId(R.id.description_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(emptyStringField), closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        onView(withText(emptyFieldsNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotCreateClaimWithOnlyHyphenInTitleField() throws InterruptedException {
        onView(withId(R.id.title_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(hyphenInStringField), closeSoftKeyboard());

        SetPlanDay();

        onView(withId(R.id.time_in_plan_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click());

        SetPlanTimeWithTimePicker();

        onView(withId(R.id.description_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(claimDescription), closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        Thread.sleep(3000);

        onView(withText(emptyFieldsNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotCreateClaimWithOnlyHyphenInDescriptionField() {
        onView(withId(R.id.title_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(claimTitle), closeSoftKeyboard());

        SetPlanDay();

        onView(withId(R.id.time_in_plan_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click());

        SetPlanTimeWithTimePicker();

        onView(withId(R.id.description_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(hyphenInStringField), closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        onView(withText(emptyFieldsNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldCreateClaimWithHyphenFirstInTitleField() throws InterruptedException {
        onView(withId(R.id.title_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(hyphenFirstInStringField), closeSoftKeyboard());

        SetPlanDay();

        onView(withId(R.id.time_in_plan_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click());

        SetPlanTimeWithTimePicker();

        onView(withId(R.id.description_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(claimDescription), closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        Thread.sleep(3000);

        onView(withId(R.id.claim_list_recycler_view))
                .check(matches(not(hasDescendant(withText(hyphenFirstInStringField)))));
    }

    @Test
    public void shouldCreateClaimWithHyphenFirstInDescriptionField() throws InterruptedException {
        onView(withId(R.id.title_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(claimTitle), closeSoftKeyboard());

        SetPlanDay();

        onView(withId(R.id.time_in_plan_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click());

        SetPlanTimeWithTimePicker();

        onView(withId(R.id.description_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(hyphenFirstInStringField), closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        Thread.sleep(3000);

        onView(withId(R.id.claim_list_recycler_view))
                .check(matches(not(hasDescendant(withText(hyphenFirstInStringField)))));
    }

    @Test
    public void shouldNotLetInputInvalidSymbolsInTitleFieldCreatingClaimPage() {
        onView(withId(R.id.title_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(invalidSymbols), closeSoftKeyboard());

        onView(withText(invalidSymbolsNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotLetInputInvalidSymbolsInDescriptionFieldCreatingClaimPage() {
        onView(withId(R.id.description_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(invalidSymbols), closeSoftKeyboard());

        onView(withText(invalidSymbolsNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotCreateClaimWithUnderLowerEdgeInTitleField() {
        onView(withId(R.id.title_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(lessThen5ValidSymbolsTitleOrComment), closeSoftKeyboard());

        AppointExecutor();

        SetPlanDay();

        onView(withId(R.id.time_in_plan_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click());

        SetPlanTimeWithTimePicker();

        onView(withId(R.id.description_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(claimDescription), closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        onView(withText(symbolsOutOfEdgesTitleNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotLetInputSymbolsAboveUpperEdgeInTitleFieldCreatingClaimPage() {
        onView(withId(R.id.title_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(moreThen30ValidSymbolsTitleOrComment), closeSoftKeyboard());

        onView(withText(symbolsOutOfEdgesTitleNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotCreateClaimWithUnderLowerEdgeInDescriptionField() {
        onView(withId(R.id.title_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(claimTitle), closeSoftKeyboard());

        AppointExecutor();

        SetPlanDay();

        onView(withId(R.id.time_in_plan_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click());

        SetPlanTimeWithTimePicker();

        onView(withId(R.id.description_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(lessThen10ValidSymbolsDescription), closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        onView(withText(symbolsOutOfEdgesDescriptionNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotLetInputSymbolsAboveUpperEdgeInDescriptionFieldCreatingClaimPage() {
        onView(withId(R.id.description_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(moreThen100ValidSymbolsDescription), closeSoftKeyboard());

        onView(withText(symbolsOutOfEdgesDescriptionNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotCreateClaimWithInvalidPastPlanDate() {
        onView(withId(R.id.date_in_plan_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(isAssignableFrom(DatePicker.class))
                .check(matches(isDisplayed()))
                .perform(setDate(
                        invalidPlanDay.getYear(),
                        invalidPlanDay.getMonthValue(),
                        invalidPlanDay.getDayOfMonth()));

        onView(allOf(withId(android.R.id.button1)))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withText(invalidPlanDay.getDayOfMonth()))
                .check(doesNotExist());
    }

    @Test
    public void shouldNotCreateClaimWithInvalidPlanTime() {
        onView(withId(R.id.time_in_plan_text_input_edit_text))
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
    public void shouldCancelCreatingClaim() throws InterruptedException {
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

        onView(withId(R.id.claim_list_recycler_view))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldReturnToCreatingClaimPageAfterRefusalToCancel() {
        onView(withId(R.id.title_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(claimTitle),
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

        onView(withId(R.id.title_edit_text))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(withText(claimTitle)));
    }
}