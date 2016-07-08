package ly.generalassemb.energize;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by darrankelinske on 7/1/16.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ShoppingCartActivityTest {

    @Rule
    public ActivityTestRule<ShoppingCartActivity> mActivityTestRule =
            new ActivityTestRule<>(ShoppingCartActivity.class);

    @Test
    public void emptyCartTest() throws Exception {

        onView(withText(R.string.empty_cart)).check(matches(isDisplayed()));

    }
}
