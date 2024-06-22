package com.example.elearning;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elearning.databinding.ItemCourseBinding;

import java.util.ArrayList;

public class StudentQuizAdapter extends RecyclerView.Adapter<StudentQuizAdapter.Holder>{

    OnItemClick onClick ;
    ArrayList<String> list = new ArrayList<String>();
    public void setList(ArrayList<String> list) {
        this.list = list;
    }
    public void setOnItemClick(OnItemClick onItemClick){
        this.onClick = onItemClick;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCourseBinding binding = ItemCourseBinding.inflate(LayoutInflater.from(parent.getContext()) , parent , false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.binding.textName.setText("Quiz"+ " " +(position+1));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    class Holder extends RecyclerView.ViewHolder
    {
        ItemCourseBinding binding ;
        public Holder(ItemCourseBinding binding) {
            super(binding.getRoot());
            this.binding = binding ;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onClick!= null)
                        onClick.onClick(list.get(getLayoutPosition()));
                }
            });
        }
    }
    interface OnItemClick{
        void onClick(String m);
    }
}
