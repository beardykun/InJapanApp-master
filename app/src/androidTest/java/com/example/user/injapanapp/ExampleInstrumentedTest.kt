package com.example.user.injapanapp

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onData
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.RootMatchers.isDialog
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.user.injapanapp.ui.adapter.MainAdapter
import com.example.user.injapanapp.ui.main_activity.MainActivity
import org.hamcrest.CoreMatchers.*
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.example.user.injapanapp", appContext.packageName)
    }

    @Test
    @LargeTest
    fun addTask() {
        onView(withId(R.id.mainAddTask)).perform(click())
        onView(withId(R.id.createTaskNumberET)).perform(click()).perform(typeText("1111111"))
        onView(withId(R.id.createTaskTypeTV)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("TEST"))).perform(click())
        onView(withId(R.id.createTaskPriceTV)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("FOC"))).perform(click())
        onView(withId(R.id.createTaskShelfET)).perform(click()).perform(typeText("1111.11"))
            .perform(closeSoftKeyboard())
        onView(withId(R.id.createTaskPriorityTV)).perform(click())
        onData(allOf(`is`(instanceOf(String::class.java)), `is`("HIGH"))).perform(click())
        onView(withId(R.id.createTaskDescriptionET)).perform(typeText("I'm very much in love with my precious Kae-hime!"))
            .perform(closeSoftKeyboard())
        onView(withId(R.id.createTaskBtn)).perform(click())
    }

    @Test
    fun deleteTask() {
        onView(withId(R.id.mainRecyclerView)).perform(
            RecyclerViewActions.scrollToPosition<MainAdapter.MainViewHolder>(
                7
            )
        ).perform(
            RecyclerViewActions.actionOnItem<MainAdapter.MainViewHolder>(
                hasDescendant(withText("R-1111111")), longClick()
            )
        )
        onView(withText("OK")).inRoot(isDialog()).check(matches(isDisplayed())).perform(click())
    }
}
