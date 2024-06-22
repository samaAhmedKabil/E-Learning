package com.example.elearning;

import static android.provider.Settings.System.getString;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.elearning.databinding.ActivityStudentCoursesBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentCoursesActivity extends AppCompatActivity {

    ActivityStudentCoursesBinding binding ;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    StudentCoursesAdapter studentCoursesAdapter = new StudentCoursesAdapter();
    ArrayList<ModelMember> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentCoursesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        showCourses();
        addMember();
        courseClick();
        logoutClick();
    }

    private void addMember() {
        String courseName = "courseName";
        binding.addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference valueRef = FirebaseDatabase.getInstance().getReference().child(ConstData.COURSES).child(binding.courseId.getText().toString()).child(courseName);
                valueRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            String value = dataSnapshot.getValue(String.class);
                            String courseId = binding.courseId.getText().toString().trim();
                            validate(courseId);
                            ref.child(ConstData.COURSES).child(courseId).child(ConstData.MEMBERS).child(auth.getUid()).setValue(new ModelMember(courseId, value, auth.getUid(), 0, 0));
                            ref.child(ConstData.MEMBERS).child(auth.getUid()).setValue(new ModelMember(courseId, value, auth.getUid(), 0, 0));
                            binding.courseId.setText("");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
            }
        });
    }
    private void validate(String id)
    {
        if (id.isEmpty())
        {
            binding.courseId.setError("Required");
        }
    }
    private void showCourses()
    {
        ref.child(ConstData.MEMBERS).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren())
                {
                    ModelMember modelMember = snapshot1.getValue(ModelMember.class);
                    if (modelMember.getStudentId().equals(auth.getUid())) {
                        list.add(snapshot1.getValue(ModelMember.class));
                    }
                }
                studentCoursesAdapter.setList(list);
                binding.recyclerViewStudentCourses.setAdapter(studentCoursesAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void courseClick() {
        studentCoursesAdapter.setOnItemClick(new StudentCoursesAdapter.OnItemClick() {
            @Override
            public void onClick(ModelMember m) {
                Intent intent = new Intent(StudentCoursesActivity.this , StudentPageActivity.class);
                intent.putExtra("courseName" , m.getCourseName());
                intent.putExtra("studentId" , m.getStudentId());
                intent.putExtra("courseId" , m.getCourseId());
                startActivity(intent);
            }
        });
    }
    private void logoutClick()
    {
        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                startActivity(new Intent(StudentCoursesActivity.this , LoginActivity.class));
                finish();
            }
        });
    }
}
