package com.junear.simpleapps.simplebmicalc;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		OrientationEventListener orientationListener = new OrientationEventListener(
				getApplicationContext(), SensorManager.SENSOR_DELAY_UI) {
			public void onOrientationChanged(int orientation) {
				RadioButton usButton = (RadioButton) findViewById(R.id.radioUS);
				if (usButton.isChecked()) {
					TextView view1 = (TextView) findViewById(R.id.textView2);
					view1.setText("Height (in Inches)");
					TextView view2 = (TextView) findViewById(R.id.textView1);
					view2.setText("Weight (in Pounds)");

				} else {
					TextView view1 = (TextView) findViewById(R.id.textView2);
					view1.setText("Height (in Meters)");
					TextView view2 = (TextView) findViewById(R.id.textView1);
					view2.setText("Weight (in Kilograms)");
				}
			}
		};
		orientationListener.enable();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void onRadioButtonClicked(View view) {
		// Is the button now checked?
		boolean checked = ((RadioButton) view).isChecked();

		// Check which radio button was clicked
		switch (view.getId()) {
		case R.id.radioSI:
			if (checked) {
				TextView view1 = (TextView) findViewById(R.id.textView2);
				view1.setText("Height (in Meters)");
				TextView view2 = (TextView) findViewById(R.id.textView1);
				view2.setText("Weight (in Kilograms)");
			}
			break;
		case R.id.radioUS:
			if (checked) {
				TextView view1 = (TextView) findViewById(R.id.textView2);
				view1.setText("Height (in Inches)");
				TextView view2 = (TextView) findViewById(R.id.textView1);
				view2.setText("Weight (in Pounds)");
			}
			break;
		}
	}

	public void onButtonClick(View view) {
		EditText heightView = (EditText) findViewById(R.id.editTextHeight);
		EditText weightView = (EditText) findViewById(R.id.editTextWeight);

		if (heightView.getText().toString().length() > 0
				&& weightView.getText().toString().length() > 0) {
			InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

			inputManager.hideSoftInputFromWindow(getCurrentFocus()
					.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

			double height = Double.parseDouble(heightView.getText().toString());
			double weight = Double.parseDouble(weightView.getText().toString());

			int mult = 1;
			// Is the button now checked?
			RadioButton usButton = (RadioButton) findViewById(R.id.radioUS);
			if (usButton.isChecked())
				mult = 703;

			double bmi = 1.0 * mult * weight / (height * height * 1.0);
			DecimalFormat df = new DecimalFormat("#.##");
			TextView bmiText = (TextView) findViewById(R.id.bmi);
			bmiText.setText("BMI: " + String.valueOf(df.format(bmi)));

			TextView bmiDesc = (TextView) findViewById(R.id.textViewBMIDesc);

			if (bmi <= 15)
				bmiDesc.setText("You are: Very severely underweight");
			else if (bmi <= 16)
				bmiDesc.setText("You are: Severely underweight");
			else if (bmi <= 18.5)
				bmiDesc.setText("You are: Underweight");
			else if (bmi <= 25)
				bmiDesc.setText("You are: Normal (healthy weight)");
			else if (bmi <= 30)
				bmiDesc.setText("You are: Overweight");
			else if (bmi <= 35)
				bmiDesc.setText("You are: Obese Class I (Moderately obese)");
			else if (bmi <= 40)
				bmiDesc.setText("You are: Obese Class II (Severely obese)");
			else
				bmiDesc.setText("You are: Obese Class III (Very severely obese)");

		} else {
			TextView text = (TextView) findViewById(R.id.bmi);
			text.setText("");

			TextView bmiDesc = (TextView) findViewById(R.id.textViewBMIDesc);
			bmiDesc.setText("");

			Toast t = Toast.makeText(getApplicationContext(), "Invalid Input",
					Toast.LENGTH_SHORT);
			t.show();
		}
	}

}
