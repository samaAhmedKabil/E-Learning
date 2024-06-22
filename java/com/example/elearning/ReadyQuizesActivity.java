package com.example.elearning;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.elearning.databinding.ActivityStudentMaterialAndQuizBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ReadyQuizesActivity extends AppCompatActivity {
    ActivityStudentMaterialAndQuizBinding binding ;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    ArrayList<String> list = new ArrayList<String>();
    StudentQuizAdapter adapter = new StudentQuizAdapter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentMaterialAndQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String courseId = getIntent().getStringExtra("courseId");
        getData(courseId);
        quizClick(courseId);
    }
    private void getData(String courseId)
    {
        ref.child(ConstData.COURSE_QUIZ).child(courseId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren())
                {
                    list.add(snapshot1.getKey());
                }
                adapter.setList(list);
                binding.recyclerMaterialCourse.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void quizClick(String courseId)
    {
        adapter.setOnItemClick(new StudentQuizAdapter.OnItemClick() {
            @Override
            public void onClick(String m) {
                Intent intent = new Intent(ReadyQuizesActivity.this , SolveQuizActivity.class);
                String courseId = getIntent().getStringExtra("courseId");
                intent.putExtra("quizId" , m);
                intent.putExtra("courseId" , courseId);
                startActivity(intent);
            }
        });
    }
}
