package com.example.walkin;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.annotation.UiThreadTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class Workinghourstest {
    @Rule
    public ActivityTestRule<Patient_searchClinics>myActivityTestRule = new ActivityTestRule(Patient_searchClinics.class);
    private Patient_searchClinics hours = null;
    private TextView text;


    @Before
    public void setUp() throws Exception {
       hours = myActivityTestRule.getActivity();
    }
    @Test
    @UiThreadTest
    public void CheckInputs () throws Exception {
        //assertNotNull(rate.findViewById(R.id.searchBar));
        text = hours.findViewById(R.id.searchBar);



        text.setText("11:00 - 12:00");// search by working hours



        String hours = text.getText().toString();




        assertNotEquals("8:00 - 10:00", hours);
        assertEquals("11:00 - 12:00", hours);




    }
}