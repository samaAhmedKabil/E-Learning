package com.example.elearning;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.elearning.databinding.ActivityStudentPageBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentPageActivity extends AppCompatActivity {
    ActivityStudentPageBinding binding ;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getStudentName();
        getCourseName();
        activateClick();
        gradesCardClick();
        materialCardClick();
        solveQuizCardClick();
        chatCardClick();
    }
    private void getStudentName()
    {
        String variableName = "userName";
        String studentId = getIntent().getStringExtra("studentId");
        DatabaseReference valueRef = FirebaseDatabase.getInstance().getReference().child(ConstData.USERS).child(studentId).child(variableName);
        valueRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Retrieve the value from the DataSnapshot
                    String value = dataSnapshot.getValue(String.class);
                    binding.studentName.setText(value);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle the error
            }
        });
    }
    private void getCourseName()
    {
        String courseName = getIntent().getStringExtra("courseName");
        binding.courseName.setText(courseName);
    }
    private void activateClick()
    {
        binding.btnAttend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = binding.editAttend.getText().toString().trim();
                String courseId = getIntent().getStringExtra("courseId");
                ref.child(ConstData.ATTEND).child(courseId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(code) && !code.isEmpty())
                        {
                            if (!snapshot.child(code).hasChild(auth.getUid()) && !code.isEmpty()) {
                                ref.child(ConstData.ATTEND).child(courseId).child(code).child(auth.getUid()).setValue(auth.getUid());
                                modifyAttendGrade(courseId);
                                binding.editAttend.setText("");
                            }
                            else
                            {
                                Toast.makeText(StudentPageActivity.this, "Already attended once before", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(StudentPageActivity.this, "Wrong Code", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    private void modifyAttendGrade(String courseId)
    {
        ref.child(ConstData.COURSES).child(courseId).child(ConstData.MEMBERS).child(auth.getUid()).child("attendanceGrade").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int grade = snapshot.getValue(int.class);
                ref.child(ConstData.COURSES).child(courseId).child(ConstData.MEMBERS).child(auth.getUid()).child("attendanceGrade").setValue(grade+1);
                ref.child(ConstData.MEMBERS).child(auth.getUid()).child("attendanceGrade").setValue(grade+1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void gradesCardClick()
    {
        binding.cardViewGrades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentPageActivity.this , StudentGradesActivity.class);
                String courseId = getIntent().getStringExtra("courseId");
                intent.putExtra("courseId" , courseId);
                startActivity(intent);
            }
        });
    }
    private void materialCardClick()
    {
        binding.cardViewMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentPageActivity.this , StudentMaterialActivity.class);
                String courseId = getIntent().getStringExtra("courseId");
                intent.putExtra("courseId" , courseId);
                startActivity(intent);
            }
        });
    }
    private void solveQuizCardClick()
    {
        binding.cardViewQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentPageActivity.this , ReadyQuizesActivity.class);
                String courseId = getIntent().getStringExtra("courseId");
                intent.putExtra("courseId" , courseId);
                startActivity(intent);
            }
        });
    }
    private void chatCardClick()
    {
        binding.cardViewChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StudentPageActivity.this , ChatActivity.class);
                String courseId = getIntent().getStringExtra("courseId");
                intent.putExtra("id" , courseId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null ;
    }
}
