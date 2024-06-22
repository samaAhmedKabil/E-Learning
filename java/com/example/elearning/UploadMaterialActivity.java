package com.example.elearning;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.elearning.databinding.ActivityUploadMaterialBinding;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UploadMaterialActivity extends AppCompatActivity {
    ActivityUploadMaterialBinding binding ;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    StorageReference storage = FirebaseStorage.getInstance().getReference();
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUploadMaterialBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getMaterialClick();
    }
    private void getMaterialClick()
    {
        String courseId = getIntent().getStringExtra("id");
        ActivityResultLauncher<String>mGetContent = registerForActivityResult(new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        binding.pdfView.fromUri(uri).enableSwipe(true)
                                .enableDoubletap(true)
                                .defaultPage(0)
                                .enableAnnotationRendering(true)
                                .scrollHandle(null)
                                .enableDoubletap(true)
                                .load();
                        uploadPdfToStorage(uri , courseId);
                    }
                });
        binding.btnAddMaterial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGetContent.launch("application/pdf");
            }
        });
    }
    private void uploadPdfToStorage(Uri uri , String courseId)
    {
        binding.btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fileName = binding.fileName.getText().toString().trim();
                StorageReference ref = storageReference.child("pdf/").child(courseId + System.currentTimeMillis());
                ref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                sendFileToRealtime(uri , courseId , fileName) ;
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UploadMaterialActivity.this , e.getLocalizedMessage() , Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
    private void sendFileToRealtime (Uri uri , String courseId , String fileName)
    {
        validate(fileName);
        final String fileLink = uri.toString();
        ref.child(ConstData.COURSE_MATERIAL).child(courseId).push().setValue(new ModelMaterial(fileName , fileLink));
    }
    private void validate(String fileName)
    {
        if (fileName.isEmpty())
        {
            binding.fileName.setError(getString(R.string.Required));
        }
    }
}
