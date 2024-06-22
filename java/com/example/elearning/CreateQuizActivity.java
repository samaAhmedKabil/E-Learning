package com.example.elearning;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.elearning.databinding.ActivityCreateQuizBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CreateQuizActivity extends AppCompatActivity {
    ActivityCreateQuizBinding binding ;
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    ArrayList<ModelQuiz> list = new ArrayList<>();
    int rightAnswer = 0 ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        String courseId = getIntent().getStringExtra("id");
        createQuiz();
        checkBoxClick();
        upload(courseId);
    }
    private void createQuiz()
    {
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String question = binding.quesPlace.getText().toString().trim();
                String Answer1 = binding.editAnswer1.getText().toString().trim();
                String Answer2 = binding.editAnswer2.getText().toString().trim();
                String Answer3 = binding.editAnswer3.getText().toString().trim();
                String Answer4 = binding.editAnswer4.getText().toString().trim();
                if (binding.answer1.isChecked())
                {
                    rightAnswer = 1 ;
                    list.add(new ModelQuiz(question , rightAnswer , Answer1 , Answer2 , Answer3 , Answer4));
                    binding.quesPlace.setText("");
                    binding.editAnswer1.setText("");
                    binding.editAnswer2.setText("");
                    binding.editAnswer3.setText("");
                    binding.editAnswer4.setText("");
                    binding.answer1.setChecked(false);
                }
                else if (binding.answer2.isChecked())
                {
                    rightAnswer = 2;
                    list.add(new ModelQuiz(question , rightAnswer , Answer1 , Answer2 , Answer3 , Answer4));
                    binding.quesPlace.setText("");
                    binding.editAnswer1.setText("");
                    binding.editAnswer2.setText("");
                    binding.editAnswer3.setText("");
                    binding.editAnswer4.setText("");
                    binding.answer2.setChecked(false);
                }
                else if (binding.answer3.isChecked())
                {
                    rightAnswer = 3;
                    list.add(new ModelQuiz(question , rightAnswer , Answer1 , Answer2 , Answer3 , Answer4));
                    binding.quesPlace.setText("");
                    binding.editAnswer1.setText("");
                    binding.editAnswer2.setText("");
                    binding.editAnswer3.setText("");
                    binding.editAnswer4.setText("");
                    binding.answer3.setChecked(false);
                }
                else if (binding.answer4.isChecked())
                {
                    rightAnswer = 4;
                    list.add(new ModelQuiz(question , rightAnswer , Answer1 , Answer2 , Answer3 , Answer4));
                    binding.quesPlace.setText("");
                    binding.editAnswer1.setText("");
                    binding.editAnswer2.setText("");
                    binding.editAnswer3.setText("");
                    binding.editAnswer4.setText("");
                    binding.answer4.setChecked(false);
                }
            }
        });

    }
    private void upload(String courseId)
    {
        binding.upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ref.child(ConstData.COURSE_QUIZ).child(courseId).push().setValue(list);
            }
        });
    }
    private void checkBoxClick()
    {
        binding.answer1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    binding.answer2.setChecked(!b);
                    binding.answer3.setChecked(!b);
                    binding.answer4.setChecked(!b);
                    binding.answer1.setButtonTintList(ColorStateList.valueOf(getColor(R.color.primaryTextColor)));
                }
            }
        });
        binding.answer2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    binding.answer1.setChecked(!b);
                    binding.answer3.setChecked(!b);
                    binding.answer4.setChecked(!b);
                    binding.answer2.setButtonTintList(ColorStateList.valueOf(getColor(R.color.primaryTextColor)));
                }
            }
        });
        binding.answer3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    binding.answer2.setChecked(!b);
                    binding.answer1.setChecked(!b);
                    binding.answer4.setChecked(!b);
                    binding.answer3.setButtonTintList(ColorStateList.valueOf(getColor(R.color.primaryTextColor)));
                }
            }
        });
        binding.answer4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    binding.answer2.setChecked(!b);
                    binding.answer3.setChecked(!b);
                    binding.answer1.setChecked(!b);
                    binding.answer4.setButtonTintList(ColorStateList.valueOf(getColor(R.color.primaryTextColor)));
                }
            }
        });
    }
}
