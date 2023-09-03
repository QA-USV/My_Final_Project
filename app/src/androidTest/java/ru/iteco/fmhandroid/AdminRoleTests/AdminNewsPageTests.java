package ru.iteco.fmhandroid.AdminRoleTests;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.PickerActions.setDate;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;

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
public class AdminNewsPageTests extends ValuesForTests.ValuesForTests {

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Before
    public void setUp() throws InterruptedException {
        mActivityScenarioRule.getScenario().onActivity(activity -> activity.getWindow().getDecorView());
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
            AdminAuthorization();
            Thread.sleep(3000);
            onView(withId(R.id.all_news_text_view))
                    .perform(click());
            Thread.sleep(2000);
        }
    }

    @After
    public void QuitApp() throws InterruptedException {
        Thread.sleep(1000);
        try {
            CancelCreatingOrEditing();
        } catch (NoMatchingViewException ignore) {
        }
    }

    @Test
    public void shouldScrollingNewsPage() {
        onView(withId(R.id.news_list_recycler_view))
                .perform(scrollToPosition(20))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldFilteringNewsByCategory() throws InterruptedException {
        onView(withId(R.id.filter_news_material_button))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.news_item_category_text_auto_complete_text_view))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(2000);

        onData(anything())
                .inRoot(isPlatformPopup())
                .atPosition(categoryNum)
                .perform(click());

        onView(withId(R.id.filter_button))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.all_news_cards_block_constraint_layout))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldFilteringNewsByPeriodOfTime() {
        onView(withId(R.id.filter_news_material_button))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.news_item_publish_date_start_text_input_edit_text))
                .perform(click());

        onView(isAssignableFrom(DatePicker.class))
                .perform(setDate(
                        startDay.getYear(),
                        startDay.getMonthValue(),
                        startDay.getDayOfMonth()));

        onView(withId(android.R.id.button1))
                .perform(click());

        onView(withId(R.id.news_item_publish_date_end_text_input_edit_text))
                .perform(click());

        onView(isAssignableFrom(DatePicker.class))
                .perform(setDate(
                        endDay.getYear(),
                        endDay.getMonthValue(),
                        endDay.getDayOfMonth()));

        onView(withId(android.R.id.button1))
                .perform(click());

        onView(withId(R.id.filter_button))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.all_news_cards_block_constraint_layout))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldFilteringNewsByCategoryAndTime() throws InterruptedException {
        onView(withId(R.id.filter_news_material_button))
                .perform(click());

        onView(withId(R.id.news_item_category_text_auto_complete_text_view))
                .perform(click());

        Thread.sleep(2000);

        onData(anything())
                .inRoot(isPlatformPopup())
                .atPosition(categoryNum)
                .perform(click());

        onView(withId(R.id.news_item_publish_date_start_text_input_edit_text))
                .perform(click());

        onView(isAssignableFrom(DatePicker.class))
                .perform(setDate(
                        startDay.getYear(),
                        startDay.getMonthValue(),
                        startDay.getDayOfMonth()));

        onView(withId(android.R.id.button1))
                .perform(click());

        onView(withId(R.id.news_item_publish_date_end_text_input_edit_text))
                .perform(click());

        onView(isAssignableFrom(DatePicker.class))
                .perform(setDate(
                        endDay.getYear(),
                        endDay.getMonthValue(),
                        endDay.getDayOfMonth()));

        onView(withId(android.R.id.button1))
                .perform(click());

        onView(withId(R.id.filter_button))
                .perform(click());

        onView(withId(R.id.all_news_cards_block_constraint_layout))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldExpandAndCollapseNewsDescription() {
        onView(withId(R.id.news_list_recycler_view))
                .check(matches(isDisplayed()))
                .perform(actionOnItemAtPosition(0, click()));

        onView(first(withId(R.id.news_item_description_text_view)))
                .check(matches(isDisplayed()));

        onView(first(withId(R.id.view_news_item_image_view)))
                .perform(click());

        onView(first(withId(R.id.news_item_description_text_view)))
                .check(matches(not(isDisplayed())));
    }

    @Test
    public void shouldGoToControlPanelPage() throws InterruptedException {
        onView(withId(R.id.edit_news_material_button))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(2000);

        onView(withId(R.id.news_list_recycler_view))
                .check(matches(isDisplayed()));
    }
}