package ru.iteco.fmhandroid.AdminRoleTests;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.view.View;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ui.AppActivity;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class AdminAuthorizationTests extends ValuesForTests.ValuesForTests {

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
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void shouldSuccessfullySignInApp() throws InterruptedException {
        loginField
                .check(matches(isDisplayed()))
                .perform(replaceText(loginAdmin));

        passwordField
                .check(matches(isDisplayed()))
                .perform(replaceText(passwordAdmin));

        enterButton
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(3000);

        vHospiceTradeMarkImage
                .check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.container_list_news_include_on_fragment_main))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotAuthoriseWithWrongPassword() {
        loginField
                .perform(replaceText(loginAdmin))
                .check(matches(isDisplayed()));

        passwordField
                .perform(replaceText(wrongPasswordAdmin))
                .check(matches(isDisplayed()));

        enterButton
                .perform(click());

        onView(withText(wrongLogPassNotification))
                .inRoot(withDecorView(Matchers.not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotAuthoriseWithWrongLogin() {
        loginField
                .perform(replaceText(wrongLoginAdmin));

        passwordField
                .perform(replaceText(passwordAdmin));

        enterButton
                .perform(click());

        onView(withText(wrongLogPassNotification))
                .inRoot(withDecorView(Matchers.not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotAuthoriseWithLoginAndPassword() {
        loginField
                .perform(replaceText(wrongLoginAdmin))
                .check(matches(isDisplayed()));

        passwordField
                .perform(replaceText(wrongPasswordAdmin))
                .check(matches(isDisplayed()));

        enterButton
                .perform(click());

        onView(withText(wrongLogPassNotification))
                .inRoot(withDecorView(Matchers.not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotAuthoriseWithoutLogin() {
        passwordField
                .perform(replaceText(passwordAdmin))
                .check(matches(isDisplayed()));

        enterButton
                .perform(click());

        onView(withText(emptyLogPassNotification))
                .inRoot(withDecorView(Matchers.not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotAuthoriseWithoutPassword() {
        loginField
                .perform(replaceText(loginAdmin))
                .check(matches(isDisplayed()));

        enterButton
                .perform(click());

        onView(withText(emptyLogPassNotification))
                .inRoot(withDecorView(Matchers.not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotAuthoriseWithoutLoginAndPassword() {
        enterButton
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withText(emptyLogPassNotification))
                .inRoot(withDecorView(Matchers.not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldSignOutApp() throws InterruptedException {
        AdminAuthorization();

        Thread.sleep(5000);

        userImage
                .perform(click());

        onView(withId(android.R.id.title))
                .perform(click());

        loginField
                .check(matches(isDisplayed()))
                .check(matches(withText(emptyStringField)));

        passwordField
                .check(matches(isDisplayed()))
                .check(matches(withText(emptyStringField)));

        enterButton
                .check(matches(isDisplayed()));
    }
}