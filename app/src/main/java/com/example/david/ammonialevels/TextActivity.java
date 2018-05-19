package com.example.david.ammonialevels;

import android.os.Bundle;
import android.app.Activity;

import com.example.david.ammonialevels.R;

public class TextActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
