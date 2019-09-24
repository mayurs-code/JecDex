package com.cognition.jecdex.ShowActivities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cognition.jecdex.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddSubject extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    Spinner yearSpinner;
    Spinner branchSpinner;
    String year;
    String branch;
    String subject_code;
    String subject_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subject);
        methods();
    }

    private void methods() {
        initYearSpinner();
        initBranchSpinner();
        onSaveSubject();
    }

    void initYearSpinner() {
        yearSpinner = findViewById(R.id.year_spinner_add);
        ArrayAdapter<String> yearAdapter;

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        int yearYY = Calendar.getInstance().get(Calendar.YEAR);
        String[] years = {(yearYY) + "", (yearYY - 1) + "", (yearYY - 2) + "", (yearYY - 3) + "", (yearYY - 4) + ""};
        yearAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text_item, years);
        yearAdapter.setDropDownViewResource(R.layout.spinner_text_dropdown);
        yearSpinner.setAdapter(yearAdapter);

    }

    void initBranchSpinner() {

        branchSpinner = (Spinner) findViewById(R.id.branch_spinner_add);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.branches, R.layout.spinner_text_item);
        adapter.setDropDownViewResource(R.layout.spinner_text_dropdown);
        branchSpinner.setAdapter(adapter);

        subject_code = branchSpinner.getSelectedItem().toString();
        branchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    void onSaveSubject()
    {
        Button storeButton=(Button)findViewById(R.id.addSubjectButton);
        final Map sampleData=new HashMap();

        final TextInputEditText subjectCode=(TextInputEditText)findViewById(R.id.s_codeField);
        final TextInputEditText subjectName=(TextInputEditText)findViewById(R.id.s_nameField);
        storeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year=yearSpinner.getSelectedItem().toString();
                branch=branchSpinner.getSelectedItem().toString();
                subject_code =subjectCode.getText().toString();
                subject_name=subjectName.getText().toString();
                sampleData.put("branch",branch);
                sampleData.put("s_name",subject_name);
                sampleData.put("s_code",subject_code.toUpperCase());
                sampleData.put("year",year);
                sampleData.put("t_code",getTcode());
                db.collection("subjects")
                        .document("TestDocument")
                        .collection("Master Document")
                        .add(sampleData)
                        .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        Toast.makeText(AddSubject.this, "DATA STORED", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    String getTcode()
    {
        return "Master Test";
    }
}
