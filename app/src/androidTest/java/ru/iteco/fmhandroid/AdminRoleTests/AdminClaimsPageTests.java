package ru.iteco.fmhandroid.AdminRoleTests;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotClickable;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static ru.iteco.fmhandroid.ValuesForTests.Methods.childAtPosition;

import android.view.View;

import androidx.test.espresso.NoMatchingRootException;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;

import org.hamcrest.core.IsInstanceOf;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import ru.iteco.fmhandroid.R;
import ru.iteco.fmhandroid.ValuesForTests.ToastMatcher;
import ru.iteco.fmhandroid.ui.AppActivity;

@LargeTest
@RunWith(AllureAndroidJUnit4.class)
public class AdminClaimsPageTests extends ru.iteco.fmhandroid.ValuesForTests.ValuesForTests {

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
            AdminAuthorization();
            Thread.sleep(4000);
            onView(withId(R.id.all_claims_text_view))
                    .perform(scrollTo())
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
    public void shouldScrollClaimsPage() {
        onView(withId(R.id.claim_list_recycler_view))
                .check(matches(isDisplayed()))
                .perform(scrollToPosition(positionNum));
    }

    @Test
    public void shouldFilterClaimsByStatusOpened() throws InterruptedException {
        onView(withId(R.id.filters_material_button))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.item_filter_in_progress))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.claim_list_filter_ok_material_button))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(2000);

        onView(withId(R.id.all_claims_cards_block_constraint_layout))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldOpenAndCloseClaimCard() throws InterruptedException {
        onView(withId(R.id.claim_list_recycler_view))
                .check(matches(isDisplayed()))
                .perform(actionOnItemAtPosition(positionNum, click()));

        onView(first(withId(R.id.container_custom_app_bar_include_on_fragment_open_claim)))
                .check(matches(isDisplayed()));

        Thread.sleep(2000);

        onView(withId(R.id.close_image_button))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(3000);

        onView(withId(R.id.claim_list_recycler_view))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldLeaveCommentsInClaimCard() throws Exception {
        onView(withId(R.id.claim_list_recycler_view))
                .check(matches(isDisplayed()))
                .perform(actionOnItemAtPosition(positionNum, click()));

        Thread.sleep(2000);

        onView(withId(R.id.add_comment_image_button))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(2000);

        onView(withId(R.id.comment_text_input_layout))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(allOf(childAtPosition(childAtPosition(
                        withId(R.id.comment_text_input_layout),
                        0), 0),
                isDisplayed()))
                .perform(replaceText(claimComments));

        onView(withId(R.id.save_button))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(2000);

        onView(allOf(withId(R.id.comment_description_text_view), withText(claimComments),
                withParent(withParent(withId(R.id.claim_comments_list_recycler_view)))))
                .perform(scrollTo())
                .check(matches(withText(claimComments)));
    }

    @Test
    public void shouldBanToLeaveEmptyCommentsInClaimCard() throws InterruptedException {
        onView(withId(R.id.claim_list_recycler_view))
                .check(matches(isDisplayed()))
                .perform(scrollToPosition(positionNum))
                .perform(actionOnItemAtPosition(positionNum, click()));

        Thread.sleep(2000);

        onView(withId(R.id.add_comment_image_button))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(2000);

        onView(withId(R.id.save_button))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withText(emptyOneFieldNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotLeaveOnlyHyphenInCommentsInClaimCard() throws InterruptedException {
        onView(withId(R.id.claim_list_recycler_view))
                .check(matches(isDisplayed()))
                .perform(scrollToPosition(positionNum))
                .perform(actionOnItemAtPosition(positionNum, click()));

        Thread.sleep(2000);

        onView(withId(R.id.add_comment_image_button))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(2000);

        onView(withId(R.id.comment_text_input_layout))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.comment_text_input_layout),
                                0), 0),
                isDisplayed()))
                .perform(replaceText(hyphenInStringField));

        onView(withId(R.id.save_button))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withText(emptyOneFieldNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldDeleteFirstHyphenInCommentsInClaimCard() throws Exception {
        onView(withId(R.id.claim_list_recycler_view))
                .check(matches(isDisplayed()))
                .perform(scrollToPosition(positionNum))
                .perform(actionOnItemAtPosition(positionNum, click()));

        Thread.sleep(3000);



        onView(withId(R.id.add_comment_image_button))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(3000);

        onView(withId(R.id.comment_text_input_layout))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.comment_text_input_layout),
                                0), 0),
                isDisplayed()))
                .perform(replaceText(hyphenFirstInStringField));

        onView(withId(R.id.save_button))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(3000);

        onView(withId(R.id.claim_comments_list_recycler_view))
                .check(matches(hasDescendant(withText(hyphenFirstInStringFieldDeleted))));
    }

    @Test
    public void shouldBanToInputInvalidSymbolsInCommentsInClaimCard() throws Exception {
        onView(withId(R.id.claim_list_recycler_view))
                .check(matches(isDisplayed()))
                .perform(scrollToPosition(positionNum))
                .perform(actionOnItemAtPosition(positionNum, click()));

        onView(withId(R.id.add_comment_image_button))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(2000);

        onView(withId(R.id.comment_text_input_layout))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.comment_text_input_layout),
                                0), 0),
                isDisplayed()))
                .perform(replaceText(invalidSymbols))
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
    public void shouldNotLeaveCommentsUnderLowerEdgeInClaimCard() throws Exception {
        onView(withId(R.id.claim_list_recycler_view))
                .check(matches(isDisplayed()))
                .perform(scrollToPosition(positionNum))
                .perform(actionOnItemAtPosition(positionNum, click()));

        onView(withId(R.id.add_comment_image_button))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(3000);

        onView(withId(R.id.comment_text_input_layout))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.comment_text_input_layout),
                                0), 0),
                isDisplayed()))
                .perform(replaceText(lessThen5ValidSymbolsTitleOrComment));

        onView(withId(R.id.save_button))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(isNotClickable()));

        try {
            onView(withText(symbolsOutOfEdgesCommentsNotification))
                    .inRoot(withDecorView(not(decorView)))
                    .check(matches(isDisplayed()));
        } catch (NoMatchingRootException e) {
            throw new Exception("There is no Toast: " + '"' + symbolsOutOfEdgesCommentsNotification + '"');
        }
    }

    @Test
    public void shouldCutOffCommentsAboveUpperEdgeInClaimCard() throws InterruptedException {
        onView(withId(R.id.claim_list_recycler_view))
                .check(matches(isDisplayed()))
                .perform(scrollToPosition(positionNum))
                .perform(actionOnItemAtPosition(positionNum, click()));

        Thread.sleep(2000);

        onView(withId(R.id.add_comment_image_button))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(2000);

        onView(withId(R.id.comment_text_input_layout))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.comment_text_input_layout),
                                0), 0),
                isDisplayed()))
                .perform(replaceText(moreThen50ValidSymbolsTitleOrComment))
                .check(matches(withText(cutSymbolsMoreThen50InTitleOrComment)));
    }

    @Test
    public void shouldCancelLeavingCommentsInClaimCard() throws InterruptedException {
        onView(withId(R.id.claim_list_recycler_view))
                .check(matches(isDisplayed()))
                .perform(scrollToPosition(1))
                .perform(actionOnItemAtPosition(1, click()));

        Thread.sleep(2000);

        onView(withId(R.id.add_comment_image_button))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.cancel_button))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.container_custom_app_bar_include_on_fragment_open_claim))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldChangeCommentsInClaimCard() throws Exception {
        onView(withId(R.id.claim_list_recycler_view))
                .check(matches(isDisplayed()))
                .perform(actionOnItemAtPosition(positionNum, click()));

        Thread.sleep(2000);

        // In case there is not a comment to change in Claim Card yet
        InCaseNoCommentInClaimCardYet();

        Thread.sleep(2000);

        onView(allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.comment_text_input_layout),
                                0),
                        1),
                isDisplayed()))
                .perform(click())
                .perform(replaceText(claimCommentsChanged));

        onView(withId(R.id.save_button))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        onView(allOf(withId(R.id.comment_description_text_view), withText(claimCommentsChanged),
                withParent(withParent(withId(R.id.claim_comments_list_recycler_view))),
                isDisplayed()))
                .check(matches(withText(claimCommentsChanged)));
    }

    @Test
    public void shouldTakeClaimForExecution() throws Exception {
        onView(withId(R.id.filters_material_button))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.item_filter_in_progress))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.claim_list_filter_ok_material_button))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(3000);

        onView(withId(R.id.claim_list_recycler_view))
                .perform(actionOnItemAtPosition(positionNum, click()));

        Thread.sleep(3000);

        onView(withId(R.id.status_processing_image_button))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        onView(allOf(withId(android.R.id.title), withText(takeClaimForExecutionButton)))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(2000);

        onView(allOf(withId(R.id.status_label_text_view),
                withParent(withParent(IsInstanceOf.instanceOf(androidx.cardview.widget.CardView.class))),
                isDisplayed()))
                .check(matches(withText(claimIsInProgressStatus)));
    }

    @Test
    public void shouldCancelClaim() throws Exception {
        onView(withId(R.id.filters_material_button))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.item_filter_in_progress))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.claim_list_filter_ok_material_button))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(4000);

        onView(withId(R.id.claim_list_recycler_view))
                .perform(scrollToPosition(positionNum))
                .perform(actionOnItemAtPosition(positionNum, click()));

        Thread.sleep(4000);


        onView(withId(R.id.status_processing_image_button))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        onView(allOf(withId(android.R.id.title), withText(cancelClaimButton)))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(1000);

        onView(allOf(withId(R.id.status_label_text_view),
                withParent(withParent(IsInstanceOf.instanceOf(androidx.cardview.widget.CardView.class))),
                isDisplayed()))
                .check(matches(withText(claimIsCanceled)));
    }

    @Test
    public void shouldResetClaim() throws Exception {
        onView(withId(R.id.filters_material_button))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.item_filter_open))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.claim_list_filter_ok_material_button))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(3000);

        onView(withId(R.id.claim_list_recycler_view))
                .perform(scrollToPosition(positionNum))
                .perform(actionOnItemAtPosition(positionNum, click()));

        Thread.sleep(2000);

        onView(withId(R.id.status_processing_image_button))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        onView(allOf(withId(android.R.id.title), withText(resetClaimButton)))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(2000);

        onView(withId(R.id.editText))
                .check(matches(isDisplayed()))
                .perform(replaceText(claimIsResetComment));

        onView(withId(android.R.id.button1))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(2000);

        onView(allOf(withId(R.id.status_label_text_view),
                withParent(withParent(IsInstanceOf.instanceOf(androidx.cardview.widget.CardView.class))),
                isDisplayed()))
                .check(matches(withText(claimIsOpenedStatus)));
    }

    @Test
    public void shouldNotResetClaimWithNoComments() throws Exception {
        onView(withId(R.id.filters_material_button))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.item_filter_open))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.claim_list_filter_ok_material_button))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(2000);

        onView(withId(R.id.claim_list_recycler_view))
                .perform(scrollToPosition(positionNum))
                .perform(actionOnItemAtPosition(positionNum, click()));

        Thread.sleep(2000);


        onView(withId(R.id.status_processing_image_button))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        onView(allOf(withId(android.R.id.title), withText(resetClaimButton)))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(2000);

        onView(withId(R.id.editText))
                .check(matches(isDisplayed()))
                .perform(replaceText(emptyStringField));

        onView(withId(android.R.id.button1))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(2000);

        onView(withText(emptyOneFieldNotification))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldExecuteClaim() throws Exception {
        onView(withId(R.id.filters_material_button))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.item_filter_open))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.claim_list_filter_ok_material_button))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(2000);

        onView(withId(R.id.claim_list_recycler_view))
                .perform(actionOnItemAtPosition(positionNum, click()));

        Thread.sleep(2000);

        onView(withId(R.id.status_processing_image_button))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        onView(allOf(withId(android.R.id.title), withText(executedClaimButton)))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(2000);

        onView(withId(R.id.editText))
                .check(matches(isDisplayed()))
                .perform(replaceText(claimIsExecutedComment));

        onView(withId(android.R.id.button1))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(2000);

        onView(allOf(withId(R.id.status_label_text_view),
                withParent(withParent(IsInstanceOf.instanceOf(androidx.cardview.widget.CardView.class))),
                isDisplayed()))
                .check(matches(withText(claimExecutedStatus)));
    }

    @Test
    public void shouldNotExecuteClaimWithNoComments() throws Exception {
        onView(withId(R.id.filters_material_button))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.item_filter_open))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.claim_list_filter_ok_material_button))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(2000);

        onView(withId(R.id.claim_list_recycler_view))
                .perform(scrollToPosition(positionNum))
                .perform(actionOnItemAtPosition(positionNum, click()));

        Thread.sleep(2000);

        onView(withId(R.id.status_processing_image_button))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        onView(allOf(withId(android.R.id.title), withText(executedClaimButton)))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(2000);

        onView(withId(R.id.editText))
                .check(matches(isDisplayed()))
                .perform(click())
                .perform(replaceText(emptyStringField));

        onView(withId(android.R.id.button1))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withText(emptyOneFieldNotification))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldGoToCreatingClaimPage() throws InterruptedException {
        onView(allOf(withId(R.id.add_new_claim_material_button),
                isDisplayed()))
                .perform(click());

        Thread.sleep(2000);

        onView(allOf(withId(R.id.custom_app_bar_title_text_view),
                isDisplayed()))
                .check(matches(withText(creating)));

        Thread.sleep(2000);

        onView(allOf(withId(R.id.custom_app_bar_sub_title_text_view),
                isDisplayed()))
            .check(matches(withText(claims)));
    }

    @Test
    public void shouldGoToEditingClaimPageWithOpenStatus() throws InterruptedException {
        onView(withId(R.id.filters_material_button))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.item_filter_in_progress))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.claim_list_filter_ok_material_button))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(5000);

        onView(withId(R.id.claim_list_recycler_view))
                .perform(scrollToPosition(positionNum))
                .perform(actionOnItemAtPosition(positionNum, click()));

        Thread.sleep(2000);

        onView(allOf(withId(R.id.edit_processing_image_button),
                childAtPosition(childAtPosition(
                        withClassName(is("com.google.android.material.card.MaterialCardView")),
                                0),25),
                isDisplayed()))
                .perform(scrollTo())
                .perform(click());

        onView(allOf(withId(R.id.custom_app_bar_title_text_view),
                isDisplayed()))
                .check(matches(withText(editing)));

        onView(allOf(withId(R.id.custom_app_bar_sub_title_text_view),
                isDisplayed()))
                .check(matches(withText(claims)));
    }

    @Test
    public void shouldNotGoToEditingClaimPageWithInProgressStatus() throws InterruptedException {
        onView(withId(R.id.filters_material_button))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.item_filter_open))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.claim_list_filter_ok_material_button))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(5000);

        onView(withId(R.id.claim_list_recycler_view))
                .perform(scrollToPosition(positionNum))
                .perform(actionOnItemAtPosition(positionNum, click()));

        Thread.sleep(2000);

        onView(allOf(withId(R.id.edit_processing_image_button),
                childAtPosition(childAtPosition(
                        withClassName(is("com.google.android.material.card.MaterialCardView")),
                        0),25),
                isDisplayed()))
                .perform(scrollTo())
                .perform(click());

        onView(withText(impossibleToEditClaimNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotGoToEditingClaimPageWithExecutedStatus() throws InterruptedException {
        onView(withId(R.id.filters_material_button))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.item_filter_open))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.item_filter_in_progress))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.item_filter_executed))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.claim_list_filter_ok_material_button))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(5000);

        onView(withId(R.id.claim_list_recycler_view))
                .perform(scrollToPosition(positionNum))
                .perform(actionOnItemAtPosition(positionNum, click()));

        Thread.sleep(2000);

        onView(allOf(withId(R.id.edit_processing_image_button),
                childAtPosition(childAtPosition(
                        withClassName(is("com.google.android.material.card.MaterialCardView")),
                        0),25),
                isDisplayed()))
                .perform(scrollTo())
                .perform(click());

        Thread.sleep(2000);

        onView(withText(impossibleToEditClaimNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldNotGoToEditingClaimPageWithCanceledStatus() throws InterruptedException {
        onView(withId(R.id.filters_material_button))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.item_filter_open))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.item_filter_in_progress))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.item_filter_cancelled))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withId(R.id.claim_list_filter_ok_material_button))
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(5000);

        onView(withId(R.id.claim_list_recycler_view))
                .perform(scrollToPosition(positionNum))
                .perform(actionOnItemAtPosition(positionNum, click()));

        Thread.sleep(2000);

        onView(allOf(withId(R.id.edit_processing_image_button),
                childAtPosition(childAtPosition(
                        withClassName(is("com.google.android.material.card.MaterialCardView")),
                        0),25), isDisplayed()))
                .perform(scrollTo())
                .perform(click());

        Thread.sleep(3000);

        onView(withText(impossibleToEditClaimNotification))
                .inRoot(withDecorView(not(decorView)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldBanChangeCommentsIntoEmptyInClaimCard() throws Exception {
        onView(withId(R.id.claim_list_recycler_view))
                .check(matches(isDisplayed()))
                .perform(actionOnItemAtPosition(positionNum, click()));

        Thread.sleep(2000);

        //In case there is not a comment to change in Claim Card yet
        InCaseNoCommentInClaimCardYet();

        Thread.sleep(2000);

        onView(allOf(childAtPosition(
                childAtPosition(
                        withId(R.id.comment_text_input_layout),
                        0),1), isDisplayed()))
                .perform(click())
                .perform(replaceText(emptyStringField));

        onView(withId(R.id.save_button))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(2000);

        onView(withText(emptyOneFieldNotification))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldBanChangeCommentsWithOnlyHyphenInClaimCard() throws InterruptedException {
        onView(withId(R.id.claim_list_recycler_view))
                .check(matches(isDisplayed()))
                .perform(actionOnItemAtPosition(positionNum, click()));

        Thread.sleep(2000);

        onView(withId(R.id.add_comment_image_button))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(2000);

        onView(withId(R.id.comment_text_input_layout))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(allOf(childAtPosition(
                childAtPosition(
                        withId(R.id.comment_text_input_layout),
                        0),1), isDisplayed()))
                .perform(click())
                .perform(replaceText(hyphenInStringField));

        Thread.sleep(2000);

        onView(allOf(withId(R.id.save_button), withText(saveButtonText)))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(2000);

        onView(withText(emptyOneFieldNotification))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }

    @Test
    public void shouldCutOffFirstHyphenInChangeCommentsInClaimCard() throws Exception {
        onView(withId(R.id.claim_list_recycler_view))
                .check(matches(isDisplayed()))
                .perform(actionOnItemAtPosition(positionNum, click()));

        Thread.sleep(2000);

        // In case there is not a comment to change in Claim Card yet
        InCaseNoCommentInClaimCardYet();

        onView(allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.comment_text_input_layout),
                                0),1), isDisplayed()))
                .perform(click())
                .perform(replaceText(hyphenFirstInStringField));

        onView(withId(R.id.save_button))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .perform(click());

        Thread.sleep(2000);

        onView(allOf(withId(R.id.comment_description_text_view),
                withText(containsString(hyphenFirstInStringFieldDeleted)),
                withParent(withParent(withId(R.id.claim_comments_list_recycler_view))),
                isDisplayed()))
                .perform(scrollTo())
                .check(matches(withText(hyphenFirstInStringFieldDeleted)));
    }

    @Test
    public void shouldBanInputInvalidSymbolsInChangedCommentsInClaimCard() throws Exception {
        onView(withId(R.id.claim_list_recycler_view))
                .check(matches(isDisplayed()))
                .perform(actionOnItemAtPosition(positionNum, click()));

        Thread.sleep(2000);

        // In case there is not a comment to change in Claim Card yet
        InCaseNoCommentInClaimCardYet();

        Thread.sleep(2000);

        onView(allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.comment_text_input_layout),
                                0),
                        1),
                isDisplayed()))
                .perform(click())
                .perform(replaceText(invalidSymbols))
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
    public void shouldNotChangeCommentsLessLowerEdgeInClaimCard() throws Exception {
        onView(withId(R.id.claim_list_recycler_view))
                .check(matches(isDisplayed()))
                .perform(actionOnItemAtPosition(positionNum, click()));

        Thread.sleep(2000);

        // In case there is not a comment to change in Claim Card yet
        InCaseNoCommentInClaimCardYet();

        Thread.sleep(2000);

        onView(allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.comment_text_input_layout),
                                0),
                        1),
                isDisplayed()))
                .perform(click())
                .perform(replaceText(lessThen5ValidSymbolsTitleOrComment));

        onView(withId(R.id.save_button))
                .perform(scrollTo())
                .check(matches(isDisplayed()))
                .check(matches(isNotClickable()));

        try {
            onView(withText(symbolsOutOfEdgesCommentsNotification))
                    .inRoot(withDecorView(not(decorView)))
                    .check(matches(isDisplayed()));
        } catch (NoMatchingRootException e) {
            throw new Exception("There is no Toast: " + '"' + symbolsOutOfEdgesCommentsNotification + '"');
        }
    }

    @Test
    public void shouldCutOffChangedCommentsAboveUpperEdgeInClaimCard() throws Exception {
        onView(withId(R.id.claim_list_recycler_view))
                .check(matches(isDisplayed()))
                .perform(actionOnItemAtPosition(positionNum, click()));

        Thread.sleep(2000);

        // In case there is not a comment to change in Claim Card yet
        InCaseNoCommentInClaimCardYet();

        Thread.sleep(2000);

        onView(allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.comment_text_input_layout),
                                0),
                        1),
                isDisplayed()))
                .perform(click())
                .perform(replaceText(moreThen50ValidSymbolsTitleOrComment))
                .check(matches(withText(cutSymbolsMoreThen50InTitleOrComment)));
    }
}