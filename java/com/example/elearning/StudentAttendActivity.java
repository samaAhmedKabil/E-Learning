package com.example.elearning;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.elearning.databinding.ActivityStudentAttendBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class StudentAttendActivity extends AppCompatActivity {
    ActivityStudentAttendBinding binding ;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentAttendBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        generateCodeClick();
        confirmClick();
    }
    private String generateRandomCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            codeBuilder.append(randomChar);
        }
        return codeBuilder.toString();
    }
    private void generateCodeClick()
    {
        binding.btnGenerateCourseCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String randomCode = generateRandomCode(4);
                binding.editTextCourseCode.setText(randomCode);
            }
        });
    }
    private void confirmClick()
    {
        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = binding.editTextCourseCode.getText().toString().trim();
                String id = getIntent().getStringExtra("id");
                ref.child(ConstData.ATTEND).child(id).child(code+"").setValue("");
            }
        });
    }
}
