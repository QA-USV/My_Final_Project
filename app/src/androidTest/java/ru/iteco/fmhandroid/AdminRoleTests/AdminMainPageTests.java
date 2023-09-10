package ru.iteco.fmhandroid.AdminRoleTests;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.allOf;
import static ru.iteco.fmhandroid.ValuesForTests.Methods.childAtPosition;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.matcher.ViewMatchers;
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
public class AdminMainPageTests extends ru.iteco.fmhandroid.ValuesForTests.ValuesForTests {

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
            onView(withId(android.R.id.button2)).perform(click());
        } catch (NoMatchingViewException ignore) {
            try {
                CancelCreatingOrEditing();
            } catch (NoMatchingViewException ignore1) {
                try {
                    ToSignOutApp();
                } catch (NoMatchingViewException ignore2) {
                }
            }
        }
    }

    @Test
    public void shouldScrollMainPage() {
        onView(withId(R.id.news_list_recycler_view))
                .perform(scrollTo())
                .check(matches(isDisplayed()));

        onView(withId(R.id.claim_list_recycler_view))
                .perform(scrollTo())
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldOpenAndScrollNewsPage() throws InterruptedException {
        onView(withId(R.id.all_news_text_view))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(4000);

        onView(withId(R.id.news_list_recycler_view))
                .perform(scrollToPosition(positionNum))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldGoToNewsPageFromMenu() {
        menuButton
                .check(matches(isDisplayed()))
                .perform(click());

        onView(allOf(withId(android.R.id.title), withText(news)))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.news_list_recycler_view))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldOpenAndScrollClaimsPage() throws InterruptedException {
        onView(withId(R.id.all_claims_text_view))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(4000);

        onView(withId(R.id.claim_list_recycler_view))
                .perform(scrollToPosition(positionNum))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldGoToClaimsPageFromMenu() {
        menuButton
                .check(matches(isDisplayed()))
                .perform(click());

        onView(allOf(withId(android.R.id.title),
                withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE), withText(claims)))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.claim_list_recycler_view))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldGoToAboutPage() {
        menuButton
                .check(matches(isDisplayed()))
                .perform(click());

        onView(allOf(withId(android.R.id.title),
                withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE), withText(about)))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.about_version_title_text_view))
                .check(matches(isDisplayed()));

        onView(withId(R.id.about_version_value_text_view))
                .check(matches(isDisplayed()));

        onView(withId(R.id.about_back_image_button))
                .check(matches(isDisplayed()))
                .perform(click());
    }

    @Test
    public void shouldGoToOurMissionPage() throws InterruptedException {
        onView(allOf(withId(R.id.our_mission_image_button)))
                .check(matches(isDisplayed()))
                .check(matches(isClickable()))
                .perform(click());

        Thread.sleep(2000);

        onView(withId(R.id.our_mission_item_list_recycler_view))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldBackToMainPageFromNewsPage() {
        menuButton
                .perform(click());

        newsPageMenuButton
                .perform(click());

        menuButton
                .perform(click());

        mainPageMenuButton
                .perform(click());

        onView(withId(R.id.container_list_news_include_on_fragment_main))
                .check(matches(isDisplayed()));

        onView(withId(R.id.container_list_claim_include_on_fragment_main))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldBackToMainPageFromClaimsPage() {
        menuButton
                .perform(click());

        claimsPageMenuButton
                .perform(click());

        menuButton
                .perform(click());

        mainPageMenuButton
                .perform(click());

        onView(withId(R.id.container_list_news_include_on_fragment_main))
                .check(matches(isDisplayed()));

        onView(withId(R.id.container_list_claim_include_on_fragment_main))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldBackToMainPageFromAboutPage() throws InterruptedException {
        menuButton
                .perform(click());

        aboutPageMenuButton
                .perform(click());

        Thread.sleep(1000);

        onView(withId(R.id.about_back_image_button))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.container_list_news_include_on_fragment_main))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldBackToMainPageFromNewsPagePressBack() {
        menuButton
                .perform(click());

        newsPageMenuButton
                .perform(click());

        pressBack();

        onView(withId(R.id.container_list_news_include_on_fragment_main))
                .check(matches(isDisplayed()));

        onView(withId(R.id.container_list_claim_include_on_fragment_main))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldBackToMainPageFromClaimsPagePressBack() {
        menuButton
                .perform(click());

        claimsPageMenuButton
                .perform(click());

        pressBack();

        onView(withId(R.id.container_list_news_include_on_fragment_main))
                .check(matches(isDisplayed()));

        onView(withId(R.id.container_list_claim_include_on_fragment_main))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldBackToMainPageFromAboutPagePressBack() {
        menuButton
                .perform(click());

        aboutPageMenuButton
                .perform(click());

        pressBack();

        onView(withId(R.id.container_list_news_include_on_fragment_main))
                .check(matches(isDisplayed()));

        onView(withId(R.id.container_list_claim_include_on_fragment_main))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldBackToMainPageFromOurMissionPagePressBack() throws InterruptedException {
        ourMissionButton
                .perform(click());

        Thread.sleep(1000);

        pressBack();

        onView(withId(R.id.container_list_news_include_on_fragment_main))
                .check(matches(isDisplayed()));

        onView(withId(R.id.container_list_claim_include_on_fragment_main))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldExpandAndCollapseLastNews() throws InterruptedException {
        onView(allOf(withId(R.id.expand_material_button),
                childAtPosition(childAtPosition(
                        withId(R.id.container_list_news_include_on_fragment_main),
                        0), 4)))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(2000);

        onView(withId(R.id.news_list_recycler_view))
                .check(matches(not(isDisplayed())));

        onView(allOf(withId(R.id.expand_material_button),
                childAtPosition(childAtPosition(
                        withId(R.id.container_list_news_include_on_fragment_main),
                        0), 4)))
                .perform(click());

        Thread.sleep(4000);

        onView(withId(R.id.news_list_recycler_view))
                .perform(scrollTo())
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldExpandAndCollapseLastClaims() throws InterruptedException {
        //If Claims are not visible, first collapse News
        onView(allOf(withId(R.id.expand_material_button),
                childAtPosition(childAtPosition(
                        withId(R.id.container_list_news_include_on_fragment_main),
                        0), 4)))
                .perform(click());

        onView(allOf(withId(R.id.expand_material_button),
                childAtPosition(childAtPosition(
                        withId(R.id.container_list_claim_include_on_fragment_main),
                        0), 3)))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.claim_list_recycler_view))
                .check(matches(not(isDisplayed())));

        onView(allOf(withId(R.id.expand_material_button),
                childAtPosition(childAtPosition(
                        withId(R.id.container_list_claim_include_on_fragment_main),
                        0), 3)))
                .perform(click());

        Thread.sleep(3000);

        onView(withId(R.id.claim_list_recycler_view))
                .perform(scrollTo())
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldGoToCreatingClaimPageFromMainPage() throws InterruptedException {
        onView(withId(R.id.add_new_claim_material_button))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(2000);

        onView(allOf(withId(R.id.custom_app_bar_title_text_view), withText(creating)))
                .check(matches(isDisplayed()));

        onView(allOf(withId(R.id.custom_app_bar_sub_title_text_view), withText(claims)))
                .check(matches(isDisplayed()));
    }
}