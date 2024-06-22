package com.example.elearning;

import static android.provider.Settings.System.getString;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.elearning.databinding.ActivityCreateCourseBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateCourse extends AppCompatActivity {

    ActivityCreateCourseBinding binding ;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateCourseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        createCourseClick();
    }

    private void validate(String name , String project , String attend , String assign)
    {
        if (name.isEmpty())
        {
            binding.courseName.setError(getString(R.string.Required));
        }
        else if (project.isEmpty())
        {
            binding.editProjectGrade.setError(getString(R.string.Required));
        }
        else if (attend.isEmpty())
        {
            binding.editAttendGrade.setError(getString(R.string.Required));
        }
        else if (assign.isEmpty())
        {
            binding.editAssignGrade.setError(getString(R.string.Required));
        }
        else {
            sendDataToRealtime(name , project , attend , assign);
        }
    }
    private void createCourseClick()
    {
        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String courseName = binding.courseName.getText().toString().trim();
                String projectGrades = binding.editProjectGrade.getText().toString().trim();
                String attendGrades = binding.editAttendGrade.getText().toString().trim();
                String assignGrades = binding.editAssignGrade.getText().toString().trim();
                validate(courseName , projectGrades , attendGrades , assignGrades);
                Intent intent = new Intent(CreateCourse.this , InstructorCoursesActivity.class);
                intent.putExtra("courseName" , courseName);
                startActivity(intent);
            }
        });
    }
    private void sendDataToRealtime(String courseName , String projectGrades , String attendGrades , String assignGrades)
    {
        String id = ref.push().getKey();
        ref.child(ConstData.COURSES).child(id).setValue(new ModelCourse(id , courseName , auth.getUid(), projectGrades , assignGrades , attendGrades));
    }
}
