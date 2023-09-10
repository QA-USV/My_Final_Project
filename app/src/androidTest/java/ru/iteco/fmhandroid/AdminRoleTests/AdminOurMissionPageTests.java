package ru.iteco.fmhandroid.AdminRoleTests;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewInteraction;
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
public class AdminOurMissionPageTests extends ru.iteco.fmhandroid.ValuesForTests.ValuesForTests {

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
            }
            Thread.sleep(3000);
    }


    @After
    public void QuitApp() throws InterruptedException {
        Thread.sleep(2000);
        try {
            ToSignOutApp();
            } catch (NoMatchingViewException ignore2) {

        }
    }


    @Test
    public void shouldScrollingOurMissionPage() throws InterruptedException {
        onView(allOf(withId(R.id.our_mission_image_button)))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(2000);

        onView(withId(R.id.our_mission_item_list_recycler_view))
                .check(matches(isDisplayed()))
                .perform(swipeUp());
    }

    @Test
    public void shouldExpandAndCollapseQuote() throws InterruptedException {
        onView(allOf(withId(R.id.our_mission_image_button)))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(2000);

        ViewInteraction quote = onView(withId(R.id.our_mission_item_list_recycler_view))
                .check(matches(isDisplayed()))
                .perform(actionOnItemAtPosition(1, click()));

        ViewInteraction quoteText =
                onView(allOf(withId(R.id.our_mission_item_description_text_view),
                        withText(containsString(quoteTextForPositionOne))))
                .check(matches(isDisplayed()))
                .check(matches(withText(containsString(quoteTextForPositionOne))));

        quote
                .perform(actionOnItemAtPosition(1, click()));

        quoteText
                .check(matches(not(isDisplayed())));
    }
}