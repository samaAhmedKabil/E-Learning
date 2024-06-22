package com.example.elearning;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.elearning.databinding.ActivitySolveQuizBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SolveQuizActivity extends AppCompatActivity {
    ActivitySolveQuizBinding binding ;
    // what does MutableLiveData do ? .. listens to the updates immediately;
    MutableLiveData<List<ModelQuiz>> quizLiveData = new MutableLiveData<>();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    List<ModelQuiz> list = new ArrayList<>();
    private int rightAnswer = 0 ;
    private int grade = 0 ;
    private int i = 0 ;
    private String courseId , quizId ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySolveQuizBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        courseId = getIntent().getStringExtra("courseId");
        quizId = getIntent().getStringExtra("quizId");
        getData(courseId , quizId);
        confirmClick();
        // observers is not understandable ;
        observer();
    }
    private void getData(String courseId , String quizId)
    {
        ref.child(ConstData.COURSE_QUIZ).child(courseId).child(quizId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren())
                {
                    list.add(snapshot1.getValue(ModelQuiz.class));
                }
                quizLiveData.setValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void confirmClick()
    {
        binding.buttonConfirmToSolveNextQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rightAnswer != 0) {
                    checkRightAnswer();
                    bindView();
                } else {
                    Toast.makeText(SolveQuizActivity.this, "Choose Answer", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.chooseOneInSolveQuze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rightAnswer = 1;
                binding.chooseOneInSolveQuze.setBackgroundColor(ContextCompat.getColor(SolveQuizActivity.this, R.color.babyBlue));
                binding.chooseTwoInSolveQuze.setBackgroundColor(ContextCompat.getColor(SolveQuizActivity.this, R.color.white));
                binding.chooseThreeInSolveQuze.setBackgroundColor(ContextCompat.getColor(SolveQuizActivity.this, R.color.white));
                binding.chooseFoureInSolveQuze.setBackgroundColor(ContextCompat.getColor(SolveQuizActivity.this, R.color.white));


            }
        });
        binding.chooseTwoInSolveQuze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rightAnswer = 2;
                binding.chooseTwoInSolveQuze.setBackgroundColor(ContextCompat.getColor(SolveQuizActivity.this, R.color.babyBlue));
                binding.chooseOneInSolveQuze.setBackgroundColor(ContextCompat.getColor(SolveQuizActivity.this, R.color.white));
                binding.chooseThreeInSolveQuze.setBackgroundColor(ContextCompat.getColor(SolveQuizActivity.this, R.color.white));
                binding.chooseFoureInSolveQuze.setBackgroundColor(ContextCompat.getColor(SolveQuizActivity.this, R.color.white));

            }
        });
        binding.chooseThreeInSolveQuze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rightAnswer = 3;
                binding.chooseThreeInSolveQuze.setBackgroundColor(ContextCompat.getColor(SolveQuizActivity.this, R.color.babyBlue));
                binding.chooseOneInSolveQuze.setBackgroundColor(ContextCompat.getColor(SolveQuizActivity.this, R.color.white));
                binding.chooseTwoInSolveQuze.setBackgroundColor(ContextCompat.getColor(SolveQuizActivity.this, R.color.white));
                binding.chooseFoureInSolveQuze.setBackgroundColor(ContextCompat.getColor(SolveQuizActivity.this, R.color.white));
            }
        });
        binding.chooseFoureInSolveQuze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rightAnswer = 4;
                binding.chooseFoureInSolveQuze.setBackgroundColor(ContextCompat.getColor(SolveQuizActivity.this, R.color.babyBlue));
                binding.chooseOneInSolveQuze.setBackgroundColor(ContextCompat.getColor(SolveQuizActivity.this, R.color.white));
                binding.chooseThreeInSolveQuze.setBackgroundColor(ContextCompat.getColor(SolveQuizActivity.this, R.color.white));
                binding.chooseTwoInSolveQuze.setBackgroundColor(ContextCompat.getColor(SolveQuizActivity.this, R.color.white));
            }
        });
    }

    private void checkRightAnswer() {
        if (list.get(i).getRightAnswer() == rightAnswer) {
            grade++;
        }
        if (i == (list.size()-1))
        {
            finish();
            uploadStudentAnswers();
        }
        else {
            i++;
        }
        resetAnswer();
    }
    private void uploadStudentAnswers()
    {
        courseId = getIntent().getStringExtra("courseId");
        quizId = getIntent().getStringExtra("quizId");
        ref.child(ConstData.USERS).child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ModelUser m = snapshot.getValue(ModelUser.class);
                String studentName = m.getUserName();
                String studentEmail = m.getUserEmail();
                ref.child(ConstData.COURSE_QUIZ_ANSWER).child(courseId).child(quizId).child(auth.getUid()).setValue(new ModelStudentQuizAnswer(auth.getUid() , studentEmail , grade , studentName));
                uploadStudentGrade();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void uploadStudentGrade()
    {
        courseId = getIntent().getStringExtra("courseId");
        ref.child(ConstData.COURSES).child(courseId).child(ConstData.MEMBERS).child(auth.getUid()).child("quizGrade").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int oldGrade = snapshot.getValue(Integer.class);
                        ref.child(ConstData.COURSES).child(courseId).child(ConstData.MEMBERS).child(auth.getUid()).child("quizGrade").setValue(oldGrade + grade);
                    }
                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    private void resetAnswer ()
    {
        rightAnswer = 0 ;
        binding.chooseOneInSolveQuze.setBackgroundColor(ContextCompat.getColor(SolveQuizActivity.this, R.color.white));
        binding.chooseFoureInSolveQuze.setBackgroundColor(ContextCompat.getColor(SolveQuizActivity.this, R.color.white));
        binding.chooseThreeInSolveQuze.setBackgroundColor(ContextCompat.getColor(SolveQuizActivity.this, R.color.white));
        binding.chooseTwoInSolveQuze.setBackgroundColor(ContextCompat.getColor(SolveQuizActivity.this, R.color.white));
    }
    private void bindView() {
        if (i == (list.size() - 1)){
            binding.buttonConfirmToSolveNextQuestion.setText("Finish");
        }
        binding.questionContentInSolveQuze.setText(list.get(i).getQuestion());
        binding.chooseOneInSolveQuze.setText(list.get(i).getAnswer1());
        binding.chooseTwoInSolveQuze.setText(list.get(i).getAnswer2());
        binding.chooseThreeInSolveQuze.setText(list.get(i).getAnswer3());
        binding.chooseFoureInSolveQuze.setText(list.get(i).getAnswer4());

    }

    private void observer (){

        quizLiveData.observe(SolveQuizActivity.this, new Observer<List<ModelQuiz>>() {
            @Override
            public void onChanged(List<ModelQuiz> modelQuizes) {
                list = modelQuizes ;
                bindView();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null ;
    }
}
