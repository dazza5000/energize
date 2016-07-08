package ly.generalassemb.energize;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.KeyEvent;
import android.widget.EditText;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.pressKey;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by darrankelinske on 7/1/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class DrinksActivityTest {

    @Rule
    public ActivityTestRule<DrinksActivity> mActivityTestRule =
            new ActivityTestRule<>(DrinksActivity.class);

    @Test
    public void searchDrinkTest() throws Exception {

        onView(withId(R.id.search)).perform(click());

        onView(withId(android.support.design.R.id.search_src_text)).perform(click());
        onView(isAssignableFrom(EditText.class)).perform(typeText("red"),
                pressKey(KeyEvent.KEYCODE_ENTER));

    }

    @Test
    public void selectRedBullTest() throws Exception {

        onView(withText("Red Bull")).perform(click());
        onView(withId(R.id.menu_buy)).check(matches(isDisplayed()));
    }
}
