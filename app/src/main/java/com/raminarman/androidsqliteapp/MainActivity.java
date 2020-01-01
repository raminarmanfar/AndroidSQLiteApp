package com.raminarman.androidsqliteapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    private EditText txtName, txtSurname, txtMarks, txtId;
    private Button btnAddStudent, btnStudentsList, btnUpdateStudent, btnDeleteStudent, btnGetStudent, btnAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        initWidgets();

        btnAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean inserted = myDb.insertNewStudent(new Student(txtName.getText().toString(), txtSurname.getText().toString(), txtMarks.getText().toString()));
                if (inserted) {
                    Toast.makeText(v.getContext(), "New student added successfully.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(v.getContext(), "Data insertion failed!", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnStudentsList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor stuList = myDb.getAllStudents();
                if (stuList.getCount() == 0) {
                    showMessage("No data found", "Student list is empty");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (stuList.moveToNext()) {
                    Student student = new Student(stuList.getString(1), stuList.getString(2), stuList.getString(3));
                    buffer.append("ID: " + stuList.getString(0) + ", " + student.toString() + "\n\n");
                }

                showMessage("Students", buffer.toString());
            }
        });

        btnUpdateStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtId.getText().length() > 0) {
                    Student student = new Student(txtName.getText().toString(), txtSurname.getText().toString(), txtMarks.getText().toString());

                    boolean updated = myDb.updateStudent(txtId.getText().toString(), student);
                    if (updated) {
                        Toast.makeText(v.getContext(), "Selected student information updated.", Toast.LENGTH_LONG).show();
                    } else {
                        showMessage("Student not found to update", "No student found with entered id.");
                    }
                } else {
                    showMessage("No id entered", "Please enter student id to update.");
                }
            }
        });

        btnDeleteStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtId.getText().length() > 0) {
                    boolean deleted = myDb.deleteStudent(txtId.getText().toString());
                    if (deleted) {
                        Toast.makeText(v.getContext(), "Selected student information deleted.", Toast.LENGTH_LONG).show();
                    } else {
                        showMessage("Student not found to delete", "No student found with entered id.");
                    }
                } else {
                    showMessage("No id entered", "Please enter student id to delete.");
                }
            }
        });

        btnGetStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtId.getText().length() > 0) {
                    Student student = myDb.getStudent(txtId.getText().toString());
                    if (student != null) {
                        showMessage("Student found", student.toString());
                    } else {
                        showMessage("Student not found", "No student found with entered id.");
                    }
                } else {
                    showMessage("No id entered", "Please enter student id.");
                }
            }
        });
    }

    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private void initWidgets() {
        txtName = findViewById(R.id.txtName);
        txtSurname = findViewById(R.id.txtSurname);
        txtMarks = findViewById(R.id.txtMarks);
        txtId = findViewById(R.id.txtId);
        btnAddStudent = findViewById(R.id.btnAddStudent);
        btnStudentsList = findViewById(R.id.btnStudentsList);
        btnUpdateStudent = findViewById(R.id.btnUpdateStudent);
        btnDeleteStudent = findViewById(R.id.btnDeleteStudent);
        btnGetStudent = findViewById(R.id.btnGetStudent);
        btnAbout = findViewById(R.id.btnAbout);
    }
}
