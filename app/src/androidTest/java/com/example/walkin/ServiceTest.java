package com.example.walkin;

import androidx.test.runner.AndroidJUnit4;

import androidx.test.rule.ActivityTestRule;


import org.junit.Rule;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;
import android.widget.TextView;

//import static android.support.test.espresso.Espresso.onView;
//import static android.support.test.espresso.action.ViewActions.click;
//import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
//import static android.support.test.espresso.action.ViewActions.typeText;
//import static android.support.test.espresso.assertion.ViewAssertions.matches;
//import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
//import static android.support.test.espresso.matcher.ViewMatchers.withId;
//import static android.support.test.espresso.matcher.ViewMatchers.withText;






@RunWith(AndroidJUnit4.class)
public class ServiceTest {
    @Rule
    public ActivityTestRule<Admin> myActivityTestRule = new ActivityTestRule(Admin.class);
    private Admin myActivity=null;
    private TextView text;
    private TextView text1;


    @Before
    public void setUp() throws Exception{
        myActivity = myActivityTestRule.getActivity();
    }

    @Test
    @UiThreadTest
    public void checkInputs() throws Exception{
        assertNotNull(myActivity.findViewById(R.id.input_serviceRole));
        assertNotNull(myActivity.findViewById(R.id.input_serviceName));// JUnit test case that validates if input is not null
        text = myActivity.findViewById(R.id.input_serviceName);
        text1 = myActivity.findViewById(R.id.input_serviceRole);



        text.setText("Logan1");
        text1.setText("doctor");


        String name = text.getText().toString();
        String role = text1.getText().toString();
        assertNotEquals("Logan", name); // jUnit test case that validates the name input
        assertEquals("doctor", role); // jUnit test case that validates the role input









    }


}
