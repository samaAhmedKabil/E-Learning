package com.example.elearning;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.elearning.databinding.ActivityStudentMaterialAndQuizBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentMaterialActivity extends AppCompatActivity {

    ArrayList<ModelMaterial> list = new ArrayList<>();
    StudentMaterialAdapter adapter = new StudentMaterialAdapter();
    ActivityStudentMaterialAndQuizBinding binding ;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentMaterialAndQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getFileData();
        onMaterialClick();
    }
    private void getFileData()
    {
        String courseId = getIntent().getStringExtra("courseId");
        ref.child(ConstData.COURSE_MATERIAL).child(courseId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    list.add((snapshot1.getValue(ModelMaterial.class)));
                }
                adapter.setList(list);
                binding.recyclerMaterialCourse.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void onMaterialClick() {
        adapter.setOnItemClick(new StudentMaterialAdapter.OnItemClick() {
            @Override
            public void onClick(ModelMaterial modelMaterial) {
                String link = modelMaterial.getFileLink();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(intent);
            }
        });
    }
}
