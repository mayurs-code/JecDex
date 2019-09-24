package com.cognition.jecdex.ShowActivities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cognition.jecdex.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.chip.Chip;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;

public class AttendenceActivity extends AppCompatActivity {


    FirebaseFirestore db = FirebaseFirestore.getInstance();
    attendenceListAdapter adapter;
    static int flag = 0;
    static ListView listView;
    ArrayList<String> studentRolls;
    ArrayList<String> presentRolls;
    TextView studentCounter;
    int presentCount = 0;
    String branch;
    String year;
    String subject;
    TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence);
        Log.i("NOT FOUND", "onCreate: started");
        getActivityData();
        methods();
        Log.i("NOT FOUND", "onCreate: methods done");

    }

    void methods() {
        getFirestoreRolls();
        initializeListView();//initializes listview where attendences are stored

    }

    void  getActivityData()
    {
        Intent i=getIntent();
        branch=i.getStringExtra("branch");
        year=i.getStringExtra("year");
        subject=i.getStringExtra("s_code");
        description =(TextView)findViewById(R.id.description);
        description.setText(branch+"\n"+year+"\n"+subject);
    }

    void getFirestoreRolls() {
        studentRolls = new ArrayList<>();

        db.collection("student")
                .document("TestStudent")
                .collection(year)
                .whereEqualTo("branch", branch)
                .whereEqualTo("year", year)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot document:task.getResult())
                        {
                            Log.i("SNAPSHOT", "onComplete: "+document.getString("roll"));
                            studentRolls.add(document.getString("roll"));
                            adapter.notifyDataSetChanged();

                        }
                    }
                });

    }


    void initializeListView() {
        ListView listView = (ListView) findViewById(R.id.attendenceListView);
        studentCounter = (TextView) findViewById(R.id.studentCount);

        Log.i("NOT FOUND", "onCreate: listview");

//        studentRolls = new ArrayList<>();
        presentRolls = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            studentRolls.add("Rollno" + i);
//        }
        setPresentCount(0);
         adapter = new attendenceListAdapter();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                flag = i;
                System.out.println(i);
                adapter.notifyDataSetChanged();

            }
        });

    }

    void setPresentCount(int x) {
        studentCounter.setText(x + "/" + studentRolls.size());

    }

    public class attendenceListAdapter extends BaseAdapter {


        @Override
        public int getItemViewType(int position) {
            return position;
        }


        @Override
        public int getCount() {
            return studentRolls.size();
        }

        @Override
        public String getItem(int i) {
            return studentRolls.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.attendence_custom_list, null);

            final TextView rollField = (TextView) view.findViewById(R.id.rollnoText);
            final Chip statusChip = (Chip) view.findViewById(R.id.statusChip);
            final LinearLayout layout = (LinearLayout) view.findViewById((R.id.itemLayout));

            if (presentRolls.contains(studentRolls.get(i))) {
                layout.setBackgroundColor(getResources().getColor(R.color.green));
                rollField.setTextColor(getResources().getColor(R.color.white));
                statusChip.setChipBackgroundColorResource(R.color.white);
                statusChip.setTextColor(getResources().getColor(R.color.green));
                statusChip.setText("PRESENT");
            } else {

                layout.setBackgroundColor(getResources().getColor(R.color.white));
                rollField.setTextColor(getResources().getColor(R.color.light_red));
                statusChip.setChipBackgroundColorResource(R.color.light_red);
                statusChip.setTextColor(getResources().getColor(R.color.white));
                statusChip.setText("ABSENT");
            }

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(AttendenceActivity.this, "" + rollField.getText().toString(), Toast.LENGTH_SHORT).show();
                    if (statusChip.getText().equals("ABSENT")) {
                        layout.setBackgroundColor(getResources().getColor(R.color.green));
                        rollField.setTextColor(getResources().getColor(R.color.white));
                        statusChip.setChipBackgroundColorResource(R.color.white);
                        statusChip.setTextColor(getResources().getColor(R.color.green));
                        statusChip.setText("PRESENT");
                        presentRolls.add(rollField.getText().toString());
                        setPresentCount(++presentCount);
                    } else if (statusChip.getText().equals("PRESENT")) {
                        layout.setBackgroundColor(getResources().getColor(R.color.white));
                        rollField.setTextColor(getResources().getColor(R.color.light_red));
                        statusChip.setChipBackgroundColorResource(R.color.light_red);
                        statusChip.setTextColor(getResources().getColor(R.color.white));
                        statusChip.setText("ABSENT");
                        presentRolls.remove(rollField.getText().toString());

                        setPresentCount(--presentCount);

                    }


                }
            });


            rollField.setText(studentRolls.get(i));


            return view;
        }
    }
}
