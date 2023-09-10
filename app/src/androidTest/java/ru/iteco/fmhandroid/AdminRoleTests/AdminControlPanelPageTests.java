package ru.iteco.fmhandroid.AdminRoleTests;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.PickerActions.setDate;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;
import static ru.iteco.fmhandroid.ValuesForTests.Methods.childAtPosition;

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
public class AdminControlPanelPageTests extends ru.iteco.fmhandroid.ValuesForTests.ValuesForTests {

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Before
    public void setUp() throws InterruptedException {
        mActivityScenarioRule.getScenario().onActivity(activity -> activity.getWindow().getDecorView());
        try {
            Thread.sleep(3000);
            ToSignOutApp();
        } catch (NoMatchingViewException ignore) {
        } finally {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            AdminAuthorization();
            Thread.sleep(4000);
            onView(withId(R.id.all_news_text_view))
                    .check(matches(isDisplayed()))
                    .perform(click());
            Thread.sleep(2000);
            onView(withId(R.id.edit_news_material_button))
                    .check(matches(isDisplayed()))
                    .perform(click());
        }
        Thread.sleep(3000);
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
    public void shouldScrollingControlPanelPage() {
        onView(withId(R.id.news_list_recycler_view))
                .perform(scrollToPosition(positionNum))
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

        onData(anything())
                .inRoot(isPlatformPopup())
                .atPosition(categoryNum)
                .perform(click());

        closeSoftKeyboard();

        onView(withId(R.id.filter_button))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(3000);

        onView(withId(R.id.news_list_recycler_view))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldFilteringNewsByPublicationPeriod() throws InterruptedException {
        onView(withId(R.id.filter_news_material_button))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.news_item_publish_date_start_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(isAssignableFrom(DatePicker.class))
                .check(matches(isDisplayed()))
                .perform(setDate(
                        startDay.getYear(),
                        startDay.getMonthValue(),
                        startDay.getDayOfMonth()));

        onView(allOf(withId(android.R.id.button1)))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.news_item_publish_date_end_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(isAssignableFrom(DatePicker.class))
                .check(matches(isDisplayed()))
                .perform(setDate(
                        endDay.getYear(),
                        endDay.getMonthValue(),
                        endDay.getDayOfMonth()));

        onView(allOf(withId(android.R.id.button1)))
                .check(matches(isDisplayed()))
                .perform(click());

        closeSoftKeyboard();

        onView(withId(R.id.filter_button))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(3000);

        onView(withId(R.id.news_list_recycler_view))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldFilteringNewsByStatusActive() throws InterruptedException {
        onView(withId(R.id.filter_news_material_button))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(allOf(withId(R.id.filter_news_inactive_material_check_box)))
                .check(matches(isDisplayed()))
                .perform(click());

        closeSoftKeyboard();

        onView(withId(R.id.filter_button))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(3000);

        onView(withId(R.id.news_list_recycler_view))
                .check(matches(not(hasDescendant(allOf(
                        withId(R.id.news_item_published_text_view),
                        withText(not_active))))));
    }

    @Test
    public void shouldFilteringNewsByStatusNotActive() throws InterruptedException {
        onView(withId(R.id.filter_news_material_button))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(allOf(withId(R.id.filter_news_active_material_check_box)))
                .check(matches(isDisplayed()))
                .perform(click());

        closeSoftKeyboard();

        onView(withId(R.id.filter_button))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(3000);

        onView(withId(R.id.news_list_recycler_view))
                .check(matches(not(hasDescendant(allOf(
                        withId(R.id.news_item_published_text_view),
                        withText(active))))));
    }

    @Test
    public void shouldFilteringNewsByCategoryDateStatus() throws InterruptedException {
        onView(withId(R.id.filter_news_material_button))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.news_item_category_text_auto_complete_text_view))
                .check(matches(isDisplayed()))
                .perform(click());

        onData(anything())
                .inRoot(isPlatformPopup())
                .atPosition(categoryNum)
                .perform(click());

        onView(withId(R.id.news_item_publish_date_start_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(isAssignableFrom(DatePicker.class))
                .check(matches(isDisplayed()))
                .perform(setDate(
                        startDay.getYear(),
                        startDay.getMonthValue(),
                        startDay.getDayOfMonth()));

        onView(withId(android.R.id.button1))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.news_item_publish_date_end_text_input_edit_text))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(isAssignableFrom(DatePicker.class))
                .check(matches(isDisplayed()))
                .perform(setDate(
                        endDay.getYear(),
                        endDay.getMonthValue(),
                        endDay.getDayOfMonth()));

        onView(withId(android.R.id.button1))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(allOf(withId(R.id.filter_news_inactive_material_check_box)))
                .check(matches(isDisplayed()))
                .perform(click());

        closeSoftKeyboard();

        onView(withId(R.id.filter_button))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(5000);

        onView(withId(R.id.news_list_recycler_view))
                .check(matches(not(hasDescendant(allOf(
                        withId(R.id.news_item_published_text_view),
                        withText(not_active))))));
    }

    @Test
    public void shouldCancelFilteringNews() {
        onView(withId(R.id.filter_news_material_button))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.news_item_category_text_auto_complete_text_view))
                .check(matches(isDisplayed()))
                .perform(click());

        onData(anything())
                .inRoot(isPlatformPopup())
                .atPosition(categoryNum)
                .perform(click());

        closeSoftKeyboard();

        onView(withId(R.id.cancel_button))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.news_list_recycler_view))
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
    public void shouldGoToCreatingNewsPage() throws InterruptedException {
        onView(withId(R.id.add_news_image_view))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(2000);

        onView(allOf(withId(R.id.custom_app_bar_title_text_view), withText(creating)))
                .check(matches(isDisplayed()));

        onView(allOf(withId(R.id.custom_app_bar_sub_title_text_view), withText(news)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldGoToEditingNewsPage() {
        onView(first(allOf(withId(R.id.edit_news_item_image_view),
               childAtPosition(
                        childAtPosition(
                                withId(R.id.news_item_material_card_view),
                                0),
                        15),
                isDisplayed())))
                .perform(click());

        onView(allOf(withId(R.id.custom_app_bar_title_text_view),
                withParent(allOf(
                        withId(R.id.container_custom_app_bar_include_on_fragment_create_edit_news),
                isDisplayed()))))
                .check(matches(withText(editing)));

        onView(allOf(withId(R.id.custom_app_bar_sub_title_text_view),
                withParent(allOf(withId(R.id.container_custom_app_bar_include_on_fragment_create_edit_news),
                isDisplayed()))))
                .check(matches(withText(news)));
    }

    @Test
    public void shouldDeleteNews() throws InterruptedException {
        onView(first(allOf(withId(R.id.delete_news_item_image_view),
                childAtPosition(
                        childAtPosition(
                                withId(R.id.news_item_material_card_view),
                                0),
                        14),
                isDisplayed())))
                .perform(click());

        Thread.sleep(3000);

        onView(allOf(withId(android.R.id.message), withText(areYouSureDeleteMessage)))
                .check(matches(isDisplayed()));

        onView(withId(android.R.id.button1))
                .check(matches(isDisplayed()))
                .perform(click());
    }
}