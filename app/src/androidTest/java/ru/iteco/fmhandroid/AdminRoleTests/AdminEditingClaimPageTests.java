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
public class AdminEditingClaimPageTests extends ru.iteco.fmhandroid.ValuesForTests.ValuesForTests {

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
            AdminOpenEditingClaimPage();
        }
        Thread.sleep(2000);
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
    public void shouldFullEditClaimWithAppointmentExecutor() throws InterruptedException {
        onView(withId(R.id.title_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(claimTitleEditing), closeSoftKeyboard());

        onView(withId(R.id.executor_drop_menu_auto_complete_text_view))
                .check(matches(isDisplayed()))
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
                .perform(replaceText(claimDescriptionEditing), closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(2000);

        onView(withId(R.id.title_text_view))
                .check(matches(isDisplayed()))
                .check(matches(withText(claimTitleEditing)));

        onView(withId(R.id.executor_name_text_view))
                .check(matches(isDisplayed()))
                .check(matches(withText(claimExecutorName)));

        onView(withId(R.id.description_text_view))
                .check(matches(isDisplayed()))
                .check(matches(withText(claimDescriptionEditing)));
    }

    @Test
    public void shouldFullEditClaimNoAppointmentExecutor() throws InterruptedException {
        onView(withId(R.id.title_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(claimTitleEditing), closeSoftKeyboard());

        SetPlanDay();

        onView(withId(R.id.time_in_plan_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click());

        SetPlanTimeWithTimePicker();

        onView(withId(R.id.description_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(claimDescriptionEditing), closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(2000);

        onView(withId(R.id.title_text_view))
                .check(matches(isDisplayed()))
                .check(matches(withText(claimTitleEditing)));

        onView(withId(R.id.executor_name_text_view))
                .check(matches(isDisplayed()))
                .check(matches(withText(claimExecutorNotAppointed)));

        onView(withId(R.id.description_text_view))
                .check(matches(isDisplayed()))
                .check(matches(withText(claimDescriptionEditing)));

        onView(allOf(withId(R.id.status_label_text_view), withText(claimIsOpenedStatus)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotEditClaimWithEmptyTitleField() throws InterruptedException {
        onView(withId(R.id.title_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(emptyStringField), closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .check(matches(isDisplayed()))
                .perform(scrollTo(), click());

        Thread.sleep(2000);

        onView(withText(emptyFieldsNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotEditClaimWithEmptyDescriptionField() throws InterruptedException {
        onView(withId(R.id.description_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(emptyStringField), closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(2000);

        onView(withText(emptyFieldsNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotEditClaimWithOnlyHyphenInTitleField() throws InterruptedException {
        onView(withId(R.id.title_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(hyphenInStringField), closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(2000);

        onView(withText(emptyFieldsNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotEditClaimWithOnlyHyphenInDescriptionField() throws InterruptedException {
        onView(withId(R.id.description_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(hyphenInStringField), closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(2000);

        onView(withText(emptyFieldsNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldCutOffFirstHyphenInTitleFieldEditingClaim() throws InterruptedException {
        onView(withId(R.id.title_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(hyphenFirstInStringField), closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(2000);

        onView(withId(R.id.title_text_view))
                .check(matches(isDisplayed()))
                .check(matches(withText(hyphenFirstInStringFieldDeleted)));
    }

    @Test
    public void shouldCutOffFirstHyphenInInDescriptionFieldEditingClaim() throws InterruptedException {
        onView(withId(R.id.description_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(hyphenFirstInStringField), closeSoftKeyboard());

        onView(withId(R.id.save_button))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(2000);

        onView(withId(R.id.description_text_view))
                .check(matches(isDisplayed()))
                .check(matches(withText(hyphenFirstInStringFieldDeleted)));
    }

    @Test
    public void shouldBanToInputInvalidSymbolsInTitleFieldEditingClaimPage() throws Exception {
        onView(withId(R.id.title_edit_text))
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
    public void shouldBanToInputInvalidSymbolsInDescriptionFieldEditingClaimPage() throws Exception {
        onView(withId(R.id.description_edit_text))
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
    public void shouldNotEditClaimUnderLowerEdgeInTitleField() throws Exception {
        onView(withId(R.id.title_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(lessThen5ValidSymbolsTitleOrComment), closeSoftKeyboard());

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
    public void shouldCutOffInputSymbolsAboveUpperEdgeInTitleFieldEditingClaimPage() {
        onView(withId(R.id.title_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(moreThen50ValidSymbolsTitleOrComment))
                .check(matches(withText(cutSymbolsMoreThen50InTitleOrComment)));
    }

    @Test
    public void shouldNotEditClaimUnderLowerEdgeInDescriptionField() throws Exception {
        onView(withId(R.id.description_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(lessThen10ValidSymbolsDescription), closeSoftKeyboard());

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
    public void shouldCutOffInputSymbolsAboveUpperEdgeInDescriptionFieldEditingClaimPage() {
        onView(withId(R.id.description_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(moreThen100ValidSymbolsDescription))
                .check(matches(withText(cutSymbolsMoreThen100InDescription)));
    }

    @Test
    public void shouldNotEditClaimWithInvalidPastPlanDate() {
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
    public void shouldNotEditClaimWithInvalidPublicationTime() throws Exception {
        onView(withId(R.id.time_in_plan_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click());

        SetInvalidPlanTimeWithKeyboard();

        Thread.sleep(2000);

        onView(anyOf(withText(wrongTimeNotification1), withText(wrongTimeNotification2)))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldCancelEditingClaim() throws InterruptedException {
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

        onView(allOf(withId(R.id.container_custom_app_bar_include_on_fragment_open_claim),
                isDisplayed()))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldReturnToEditingClaimAfterRefusalToCancel() throws InterruptedException {
        onView(withId(R.id.title_edit_text))
                .check(matches(isDisplayed()))
                .perform(replaceText(claimTitleEditing),
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

        Thread.sleep(2000);

        onView(withId(R.id.title_edit_text))
                .check(matches(isDisplayed()))
                .check(matches(withText(claimTitleEditing)));
    }
}