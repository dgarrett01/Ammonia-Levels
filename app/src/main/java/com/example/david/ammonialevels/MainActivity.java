package com.example.david.ammonialevels;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import android.widget.TabHost;

import com.example.david.ammonialevels.R;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner trainSpinner = (Spinner) findViewById(R.id.spinnerNumberOfTrains);
        ArrayAdapter<CharSequence> trainAdapter = ArrayAdapter.createFromResource(this, R.array.trains, android.R.layout.simple_spinner_dropdown_item);
        trainAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        trainSpinner.setAdapter(trainAdapter);
    }

    public void showAnswer(View v){

        // New decimal format -- 2 decimal places
        DecimalFormat df = new DecimalFormat("#.00");

        // Declare variables
        int intAirSpeed;
        double currentDoubleValue;
        int levelToPumpAt;
        double shiftsToGo;
        double airFraction = 1.125;
        int trains;

        // Get values from GUI elements
        EditText current = (EditText)findViewById(R.id.editTextCurrentLevel);
        EditText airSpeed = (EditText)findViewById(R.id.editTextAirSpeed);
        EditText pumpAt = (EditText)findViewById(R.id.editTextLevelToPump);
        Spinner spinner = (Spinner)findViewById(R.id.spinnerNumberOfTrains);

        trains = Integer.parseInt(spinner.getSelectedItem().toString());

        // Check if level to pump at is empty
        if(pumpAt.getText().toString().isEmpty())
        {
            levelToPumpAt = 30;
        } else {
            levelToPumpAt = Integer.parseInt(pumpAt.getText().toString());
        }

        // Check if current ammonia value is empty
        if(current.getText().toString().isEmpty())
        {
            currentDoubleValue = 82;
        } else {
            currentDoubleValue = Double.parseDouble(current.getText().toString());
        }

        // Check for empty air speed
        if(airSpeed.getText().toString().isEmpty())
        {
            intAirSpeed = 50;
        } else {
            intAirSpeed = Integer.parseInt(airSpeed.getText().toString());
        }

        if (airSpeed.getText().toString().isEmpty())
        {
            airFraction = 1.125;
        }

        switch (intAirSpeed)
        {
            case 50: airFraction = 1.125;
                break;
            case 52: airFraction = 1.1875;
                break;
            case 54: airFraction = 1.25;
                break;
            case 56: airFraction = 1.3125;
                break;
            case 60: airFraction = 1.375;
                break;
        }

        // 1.25 is equal to 10 percent per 8 hours, or 10/8. This will
        // change depending upon air speed.
        shiftsToGo = ((currentDoubleValue - levelToPumpAt) / airFraction) / 8;

        if (trains != 2)
        {
            shiftsToGo = shiftsToGo * 2;
        }

        // Create calendar object to get current date/time. Convert shiftsToGo
        // to date/time object. Then add shiftsToGo to current date/time.
        // shiftsToGo needs to be converted to hours and minutes.
        double hoursToGo;
        long hourPart;
        double minutePart;
        hoursToGo = shiftsToGo * 8;
        hourPart = (long) hoursToGo;
        minutePart = hoursToGo - hourPart;
        double minutesToGo = minutePart * 60;
        Calendar currentDate = Calendar.getInstance();
        currentDate.add(Calendar.HOUR,(int) hourPart);
        currentDate.add(Calendar.MINUTE,(int) minutesToGo);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE MMMM d, h:mm a");


        new AlertDialog.Builder(this)
                .setTitle("Current Values")
                .setNeutralButton("Okay", null)
                .setMessage("Current Ammonia Value: " + currentDoubleValue + "\n" +
                        "Number of trains running: " + trains + "\n" +
                        "Current air speed: " + intAirSpeed + "\n" +
                        "Level to pump at: " + levelToPumpAt + "\n" +
                        "Shifts to go before pumping: " + df.format(shiftsToGo) + "\n" +
                        "Date of next pump: " + sdf.format(currentDate.getTime())).show();
    }

    public void clearFields(View v)
    {
        EditText current = (EditText)findViewById(R.id.editTextCurrentLevel);
        EditText airSpeed = (EditText)findViewById(R.id.editTextAirSpeed);
        EditText pumpAt = (EditText)findViewById(R.id.editTextLevelToPump);

        current.setText("");
        airSpeed.setText("");
        pumpAt.setText("");
    }

    public void quitApp(View v)
    {
        finish();
        System.exit(0);
    }
}
