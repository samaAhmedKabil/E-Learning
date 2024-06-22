package com.example.elearning;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.elearning.databinding.ItemGradesBinding;

import java.util.ArrayList;

public class CourseGradesAdapter extends RecyclerView.Adapter<CourseGradesAdapter.Holder>{

    ArrayList<ModelMember> list = new ArrayList<>();
    public void setList(ArrayList<ModelMember> list) {
        this.list = list;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemGradesBinding binding = ItemGradesBinding.inflate(LayoutInflater.from(parent.getContext()) , parent , false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class Holder extends RecyclerView.ViewHolder
    {
        ItemGradesBinding binding ;
        public Holder(ItemGradesBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(ModelMember m)
        {
            binding.textQuiz.setText(m.getQuizGrade()+"");
            binding.textAttendance.setText(m.getAttendanceGrade()+"");
            binding.textId.setText(m.getStudentId());
        }
    }
}
