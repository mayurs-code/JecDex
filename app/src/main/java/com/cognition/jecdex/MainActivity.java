package com.cognition.jecdex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cognition.jecdex.ShowActivities.AddSubject;
import com.cognition.jecdex.ShowActivities.TakeAttendence;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

        FloatingActionButton takeAttendence= (FloatingActionButton) findViewById(R.id.takeAttendence);
        takeAttendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attendenceActivity();
            }
        });
        takeAttendence.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                subjectAddActivity();
                return true;
            }
        });
    }

    void attendenceActivity()
    {
        Intent i=new Intent(this, TakeAttendence.class);
        startActivity(i);
    }
    void subjectAddActivity()
    {
        Intent i=new Intent(this, AddSubject.class);
        startActivity(i);
    }
}
