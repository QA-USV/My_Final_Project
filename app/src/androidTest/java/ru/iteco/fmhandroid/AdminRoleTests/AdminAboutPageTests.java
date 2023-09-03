package ru.iteco.fmhandroid.AdminRoleTests;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasLinks;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

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
public class AdminAboutPageTests extends ValuesForTests.ValuesForTests {

    @Rule
    public ActivityScenarioRule<AppActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(AppActivity.class);

    @Before
    public void setUp() throws InterruptedException {
        try {
            onView(withId(R.id.about_back_image_button)).perform(click());
        } catch (NoMatchingViewException ignore) {
            try {
                Thread.sleep(1000);
                ToSignOutApp();
            } catch (NoMatchingViewException ignore1) {
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
    }

    @After
    public void QuitApp() throws InterruptedException {
        Thread.sleep(1000);
        try {
            onView(withId(R.id.about_back_image_button)).perform(click());
        } catch (NoMatchingViewException ignore1) {
            try {
                ToSignOutApp();
            } catch (NoMatchingViewException ignore2) {
            }
        }
    }


    @Test
    public void shouldBeAccessToPrivacyPolicy() {
        menuButton
                .check(matches(isDisplayed()))
                .perform(click());

        onView(allOf(withId(android.R.id.title), withText(about)))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.about_privacy_policy_label_text_view))
                .check(matches(isDisplayed()))
                .check((matches(withText(privacyPolicy))));

        onView(withId(R.id.about_privacy_policy_value_text_view))
                .check(matches(isDisplayed()))
                .check(matches(hasLinks()))
                .check(matches(withText(privacyPolicyUrl)));
    }

    @Test
    public void shouldBeAccessToTermsOfUse() {
        menuButton
                .check(matches(isDisplayed()))
                .perform(click());

        onView(allOf(withId(android.R.id.title), withText(about)))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.about_terms_of_use_label_text_view))
                .check(matches(isDisplayed()))
                .check(matches(withText(termsOfUse)));

        onView(withId(R.id.about_terms_of_use_value_text_view))
                .check(matches(isDisplayed()))
                .check(matches(hasLinks()))
                .check(matches(withText(termsOfUseUrl)));
    }
}