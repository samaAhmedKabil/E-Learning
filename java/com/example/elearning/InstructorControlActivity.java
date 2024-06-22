package com.example.elearning;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class InstructorControlActivity extends AppCompatActivity {
    com.example.elearning.databinding.ActivityInstructorControlBinding binding ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.elearning.databinding.ActivityInstructorControlBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getCourseId();
        attendClick();
        gradesClick();
        materialClick();
        quizClick();
        chatClick();
    }
    private void getCourseId ()
    {
        String courseId = getIntent().getStringExtra("courseId");
        binding.courseId.setText(courseId);
        copy(courseId);
    }
    private void copy(String textToCopy)
    {
        binding.courseId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Copied Text", textToCopy);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(getApplicationContext(), "Text copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void attendClick()
    {
        binding.cardViewAttend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstructorControlActivity.this , StudentAttendActivity.class);
                intent.putExtra("id" , binding.courseId.getText().toString().trim());
                startActivity(intent);
            }
        });
    }
    private void gradesClick()
    {
        binding.cardViewGrades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstructorControlActivity.this , InstructorGradesActivity.class);
                intent.putExtra("id" , binding.courseId.getText().toString().trim());
                startActivity(intent);
            }
        });
    }
    private void materialClick()
    {
        binding.cardViewMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstructorControlActivity.this , UploadMaterialActivity.class);
                intent.putExtra("id" , binding.courseId.getText().toString().trim());
                startActivity(intent);
            }
        });
    }
    private void quizClick()
    {
        binding.cardViewQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstructorControlActivity.this , CreateQuizActivity.class);
                intent.putExtra("id" , binding.courseId.getText().toString().trim());
                startActivity(intent);
            }
        });
    }
    private void chatClick()
    {
        binding.mess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstructorControlActivity.this , ChatActivity.class);
                intent.putExtra("id" , binding.courseId.getText().toString().trim());
                startActivity(intent);
            }
        });
    }
}
