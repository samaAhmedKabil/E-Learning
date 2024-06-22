package com.example.elearning;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.elearning.databinding.ActivityStudentGradesBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentGradesActivity extends AppCompatActivity {
    ActivityStudentGradesBinding binding ;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentGradesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String courseId = getIntent().getStringExtra("courseId");
        getQuizTotal(courseId);
        getAttendTotal(courseId);
        getAttendGrade(courseId);
        getQuizGrade(courseId);
    }

    private void getQuizTotal(String courseId)
    {
        ref.child(ConstData.COURSES).child(courseId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ModelCourse course = snapshot.getValue(ModelCourse.class);
                binding.textView9.setText(course.getquizGrades());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getAttendTotal(String courseId)
    {
        ref.child(ConstData.COURSES).child(courseId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ModelCourse course = snapshot.getValue(ModelCourse.class);
                binding.textView14.setText(course.getAttendGrades());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getAttendGrade(String courseId)
    {
        ref.child(ConstData.COURSES).child(courseId).child(ConstData.MEMBERS).child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ModelMember member = snapshot.getValue(ModelMember.class);
                binding.textView13.setText(member.getAttendanceGrade()+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getQuizGrade(String courseId)
    {
        ref.child(ConstData.COURSES).child(courseId).child(ConstData.MEMBERS).child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ModelMember member = snapshot.getValue(ModelMember.class);
                binding.textView7.setText(member.getQuizGrade()+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null ;
    }
}
