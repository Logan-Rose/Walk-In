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
public class PatientSignInTest {
    @Rule
    public ActivityTestRule<MainActivity>myActivityTestRule = new ActivityTestRule(MainActivity.class);
    private MainActivity Signin = null;
    private TextView text;
    private TextView text1;

    @Before
    public void setUp() throws Exception{
        Signin = myActivityTestRule.getActivity();
    }
    @Test
    @UiThreadTest
    public void CheckInputs() throws Exception{
        assertNotNull(Signin.findViewById(R.id.emailInput));
        assertNotNull(Signin.findViewById(R.id.InputPassword));// Junit test case. Checks if input is not null
        text = Signin.findViewById(R.id.emailInput);
        text1 = Signin.findViewById(R.id.InputPassword);

        text.setText("Jonathan@gmail.com");
        text1.setText("password");


        String email = text.getText().toString();
        String pass = text1.getText().toString();

        assertNotEquals("Lilian@hotmail.com", email);
        assertEquals("Jonathan@gmail.com" , email);

        assertNotEquals("fancypassword", pass);
        assertEquals("password", pass);


    }

}