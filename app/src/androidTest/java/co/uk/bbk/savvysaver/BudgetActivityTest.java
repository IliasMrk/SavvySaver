package co.uk.bbk.savvysaver;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import android.text.Editable;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputEditText;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;

import co.uk.bbk.savvysaver.databinding.ActivityBudgetBinding;

@RunWith(AndroidJUnit4.class)
public class BudgetActivityTest {

    @Test
    public void testSetBudgetButton() {
        // Mock the binding
        ActivityBudgetBinding mockBinding = Mockito.mock(ActivityBudgetBinding.class);
        BudgetActivity mockActivity = Mockito.mock(BudgetActivity.class);

        // Mock the EditText and TextView
        TextInputEditText mockEditText = Mockito.mock(TextInputEditText.class);
        TextView mockTextView = Mockito.mock(TextView.class);
        Button mockButton = Mockito.mock(Button.class);

        // Setup the binding to return the mocked views
        when(mockBinding.textInputEditText).thenReturn(mockEditText);
        when(mockBinding.textView).thenReturn(mockTextView);
        when(mockBinding.setBudgetButton).thenReturn(mockButton);

        // Use reflection to set the mock binding in the activity
        try {
            Field bindingField = BudgetActivity.class.getDeclaredField("binding");
            bindingField.setAccessible(true);
            bindingField.set(mockActivity, mockBinding);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        // Launch the activity scenario
        try (ActivityScenario<BudgetActivity> scenario = ActivityScenario.launch(BudgetActivity.class)) {
            scenario.onActivity(realActivity -> {
                // Set the budget and click the button
                String inputBudget = "1000.0";
                when(mockEditText.getText()).thenReturn(Editable.Factory.getInstance().newEditable(inputBudget));
                mockButton.performClick();

                // Verify that the text view is updated correctly
                verify(mockTextView).setText("1000.0");
                String actualText = mockTextView.getText().toString();
                assertEquals("1000.0", actualText);
            });
        }
    }
}
