package com.cognition.jecdex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        methods();
    }


    public void methods()
    {
        goTakeAttendence();
    }

    private void goTakeAttendence() {

        MaterialButton takeAttendence=(MaterialButton)findViewById(R.id.takeAttendence);
        takeAttendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attendenceActivity();
            }
        });
    }

    void attendenceActivity()
    {
        Intent i=new Intent(this,TakeAttendence.class);
        startActivity(i);
        finish();
    }
}
