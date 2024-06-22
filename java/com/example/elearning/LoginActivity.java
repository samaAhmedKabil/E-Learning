package com.example.elearning;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Toast;

import com.example.elearning.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding ;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    boolean isPasswordVisible = false ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        showPassword();
        goToRegister();
        loginClick();
    }
    private void showPassword()
    {
        binding.showPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                     isPasswordVisible = !isPasswordVisible;
                    if (isPasswordVisible) {
                        binding.editTextPassword.setTransformationMethod(null);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                binding.editTextPassword.setTransformationMethod(new PasswordTransformationMethod());
                                isPasswordVisible = false;
                            }
                        }, 2000);
                    } else {
                        binding.editTextPassword.setTransformationMethod(new PasswordTransformationMethod());
                    }
                }
        });
    }
    private void goToRegister()
    {
        binding.goToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this , RegisterActivity.class));
            }
        });
    }
    private void validate(String email , String password)
    {
        if (email.isEmpty())
        {
            binding.editTextEmail.setError(getString(R.string.Required));
        }
        if (password.isEmpty())
        {
            binding.editTextPassword.setError(getString(R.string.Required));
        }
        if (password.isEmpty() && email.isEmpty())
        {
            binding.editTextPassword.setError(getString(R.string.Required));
            binding.editTextEmail.setError(getString(R.string.Required));
        }
        if (password.length()<6)
        {
            binding.editTextPassword.setError(getString(R.string.passwordLessThanSix));
        }
        else
        {
            checkAuth(email, password);
        }
    }
    private void checkAuth(String email , String password)
    {
        binding.progress.setVisibility(View.VISIBLE);
        auth.signInWithEmailAndPassword(email , password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                binding.progress.setVisibility(View.GONE);
                if (ConstData.userType.equals(ConstData.STUDENT_TYPE))
                {
                    startActivity(new Intent(LoginActivity.this , StudentCoursesActivity.class));
                }
                else if (ConstData.userType.equals(ConstData.INSTRUCTOR_TYPE))
                {
                    startActivity(new Intent(LoginActivity.this , InstructorCoursesActivity.class));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                binding.progress.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this , e.getLocalizedMessage().toString() , Toast.LENGTH_LONG).show();
            }
        });
    }
    private void loginClick()
    {
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.editTextEmail.getText().toString().trim();
                String password = binding.editTextPassword.getText().toString().trim();
                validate(email , password);
                binding.editTextEmail.setText("");
                binding.editTextPassword.setText("");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null ;
    }
}