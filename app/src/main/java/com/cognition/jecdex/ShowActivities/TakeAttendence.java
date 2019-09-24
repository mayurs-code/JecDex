package com.cognition.jecdex.ShowActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cognition.jecdex.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class TakeAttendence extends AppCompatActivity {

    String branch;
    String year;
    String subject;
    ArrayList<String> subjects;
    Spinner branchSpinner;
    Spinner yearSpinner;
    Spinner subjectSpinner;

    TextView totalStudent;
    ArrayAdapter<String> subjectAdapter;


    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendence);
        initiateViews();
        methods();
    }

    void initiateViews() {
        totalStudent = findViewById(R.id.totalStudents);
    }

    private void methods() {
        branch_spinner();
        year_spinner();
        subject_spinner();
        buttonStartAttendenceActivity();
    }

    void branch_spinner() {
        subjects = new ArrayList<>();

        branchSpinner = (Spinner) findViewById(R.id.branch_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.branches, R.layout.spinner_text_item);
        adapter.setDropDownViewResource(R.layout.spinner_text_dropdown);
        branchSpinner.setAdapter(adapter);

        subject = branchSpinner.getSelectedItem().toString();
        branchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                year = yearSpinner.getSelectedItem().toString();
                branch = branchSpinner.getSelectedItem().toString();
                db.collection("subjects")
                        .document("TestDocument")
                        .collection("Master Document")
                        .whereEqualTo("branch", branch)
                        .whereEqualTo("year", year)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                db.collection("student")
                                        .document("TestStudent")
                                        .collection(year)
                                        .whereEqualTo("branch", branch)
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (!task.getResult().isEmpty()) {
                                                    totalStudent.setText("" + task.getResult().size());
                                                } else {
                                                    totalStudent.setText("0");

                                                }
                                            }

                                        });
                                subjects.clear();

                                for (QueryDocumentSnapshot document : (task.getResult())) {
                                    subjects.add(document.getString("s_code"));
                                }
                                if (task.getResult().isEmpty()) {
                                    subjects.clear();
                                    subjects.add("No Data Found");

                                }
                                subjectAdapter.notifyDataSetChanged();
                            }
                        });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    void year_spinner() {
        yearSpinner = (Spinner) findViewById(R.id.year_spinner);
        int yearYY = Calendar.getInstance().get(Calendar.YEAR);
        String years[] = {(yearYY) + "", (yearYY - 1) + "", (yearYY - 2) + "", (yearYY - 3) + "", (yearYY - 4) + ""};
        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                year = yearSpinner.getSelectedItem().toString();
                branch = branchSpinner.getSelectedItem().toString();
                Toast.makeText(TakeAttendence.this, year + "     " + branch, Toast.LENGTH_SHORT).show();
                db.collection("student")
                        .document("TestStudent")
                        .collection(year)
                        .whereEqualTo("branch", branch)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (!task.getResult().isEmpty()) {
                                    totalStudent.setText("" + task.getResult().size());
                                } else {
                                    totalStudent.setText("0");

                                }


                            }

                        });


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_text_item, years);
        adapter.setDropDownViewResource(R.layout.spinner_text_dropdown);
        yearSpinner.setAdapter(adapter);
        year = yearSpinner.getSelectedItem().toString();


    }

    void subject_spinner() {
        subjectSpinner = (Spinner) findViewById(R.id.sub_spinner);
        subjectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                year = yearSpinner.getSelectedItem().toString();
                branch = branchSpinner.getSelectedItem().toString();
                subject = subjectSpinner.getSelectedItem().toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        subjectAdapter = new ArrayAdapter<String>(this, R.layout.spinner_text_item, subjects);
        subjectAdapter.setDropDownViewResource(R.layout.spinner_text_dropdown);
        subjectSpinner.setAdapter(subjectAdapter);

    }

    void buttonStartAttendenceActivity() {
        MaterialButton button = (MaterialButton) findViewById(R.id.attendenceActivity);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AttendenceListActivity();
            }
        });
    }

    void AttendenceListActivity() {
        Intent i = new Intent(this, AttendenceActivity.class);
        i.putExtra("branch", branch);
        i.putExtra("year", year);
        i.putExtra("s_code", subject);
        startActivity(i);

    }

    void getFirestoreSubjects() {

    }


}
