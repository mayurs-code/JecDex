package com.cognition.jecdex;

import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class TakeAttendence extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendence);
        methods();
    }

    private void methods() {
        branch_spinner();
        year_spinner();
        subject_spinner();
    }

    void branch_spinner() {
        Spinner branchSpinner = (Spinner) findViewById(R.id.branch_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.branches, R.layout.spinner_text_item);
        adapter.setDropDownViewResource(R.layout.spinner_text_dropdown);
        branchSpinner.setAdapter(adapter);

    }
    void year_spinner() {
        Spinner branchSpinner = (Spinner) findViewById(R.id.year_spinner);
        int year=Calendar.getInstance().get(Calendar.YEAR);
        String years[]={(year)+"",(year-1)+"",(year-2)+"",(year-3)+"",(year-4)+""};
        ArrayAdapter<String> adapter = new ArrayAdapter<String >(this,R.layout.spinner_text_item,years);
        adapter.setDropDownViewResource(R.layout.spinner_text_dropdown);
        branchSpinner.setAdapter(adapter);

    }
    void subject_spinner() {
        Spinner branchSpinner = (Spinner) findViewById(R.id.sub_spinner);
        String subjects[]={"BT1001","BT1002","BT1003","BT1004"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String >(this,R.layout.spinner_text_item,subjects);
        adapter.setDropDownViewResource(R.layout.spinner_text_dropdown);
        branchSpinner.setAdapter(adapter);

    }


}
