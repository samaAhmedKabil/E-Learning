package com.example.elearning;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.elearning.databinding.ActivityInstructorCoursesBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InstructorCoursesActivity extends AppCompatActivity {
    ActivityInstructorCoursesBinding binding;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    ArrayList<ModelCourse> list = new ArrayList<>();
    InstructorCoursesAdapter instructorCoursesAdapter = new InstructorCoursesAdapter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInstructorCoursesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addCourse();
        getData();
        courseClick();
        logoutClick();
    }

    public void getData()
    {
        ref.child(ConstData.COURSES).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren())
                {
                    ModelCourse modelCourse = snapshot1.getValue(ModelCourse.class);
                    if (modelCourse.getInstructorId().equals(auth.getUid())) {
                        list.add(snapshot1.getValue(ModelCourse.class));
                    }
                }
                instructorCoursesAdapter.setList(list);
                binding.recyclerInstructorCourse.setAdapter(instructorCoursesAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void addCourse()
    {
        binding.addCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InstructorCoursesActivity.this , CreateCourse.class));
            }
        });
    }
    private void courseClick()
    {
        instructorCoursesAdapter.setOnItemClick(new InstructorCoursesAdapter.OnItemClick() {
            @Override
            public void onClick(ModelCourse m) {
                Intent intent = new Intent(InstructorCoursesActivity.this , InstructorControlActivity.class);
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
                startActivity(new Intent(InstructorCoursesActivity.this , LoginActivity.class));
                finish();
            }
        });
    }
}
