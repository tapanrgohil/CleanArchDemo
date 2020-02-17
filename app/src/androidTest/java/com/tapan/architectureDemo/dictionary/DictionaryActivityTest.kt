package com.tapan.architectureDemo.dictionary


import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.rule.ActivityTestRule
import com.tapan.architectureDemo.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4ClassRunner::class)
class DictionaryActivityTest {
    @get:Rule
     val mActivityRule = ActivityTestRule<DictionaryActivity>(DictionaryActivity::class.java)

    /**
     * At start progress visibility gone
     * When user enter any thing api will trigger and progress will be visible
     * Test recycler view has data after api call and progress bar is gone
     */
    @Test
    fun checkUi() {
        onView(withId(R.id.progress)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.etSearch)).perform(typeText("test"), closeSoftKeyboard());
        Thread.sleep(1500)
        onView(withId(R.id.progress)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.groupError)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        assert(getRVcount()>0)
    }

    @Test
    fun checkErrorUi() {
        onView(withId(R.id.progress)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.etSearch)).perform(typeText("cscscscsdcsdcsc"), closeSoftKeyboard());
        Thread.sleep(1500)
        onView(withId(R.id.progress)).check(matches(withEffectiveVisibility(Visibility.GONE)))
        onView(withId(R.id.groupError)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        assert(getRVcount()>0)
    }

    private fun getRVcount(): Int {
        val recyclerView = mActivityRule.activity.findViewById(R.id.rvSuggestions) as RecyclerView
        return recyclerView.adapter!!.itemCount
    }
}
