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
public class servicesearchTest {
    @Rule
    public ActivityTestRule<Patient_searchClinics>myActivityTestRule = new ActivityTestRule(Patient_searchClinics.class);
    private Patient_searchClinics service = null;
    private TextView text;


    @Before
    public void setUp() throws Exception {
        service = myActivityTestRule.getActivity();
    }
    @Test
    @UiThreadTest
    public void CheckInputs () throws Exception {
        //assertNotNull(rate.findViewById(R.id.searchBar));
        text = service.findViewById(R.id.searchBar);


        text.setText("vaccine"); // search by name


        String service = text.getText().toString();



        assertNotEquals("colonoscopy" , service);
        assertEquals("vaccine" , service);


    }
}
