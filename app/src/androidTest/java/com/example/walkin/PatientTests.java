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
public class PatientTests {
    @Rule
    public ActivityTestRule<Patient_searchClinics>myActivityTestRule = new ActivityTestRule(Patient_searchClinics.class);
    private Patient_searchClinics search = null;
    private TextView text;


    @Before
    public void setUp() throws Exception {
        search = myActivityTestRule.getActivity();
    }
        @Test
        @UiThreadTest
        public void CheckInputs () throws Exception {

            text = search.findViewById(R.id.searchBar);


            text.setText("hospital"); // search by name


            String search = text.getText().toString();


            assertNotEquals("google", search);
            assertEquals("hospital" , search);




        }
    }