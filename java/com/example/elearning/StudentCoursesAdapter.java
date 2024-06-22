package com.example.elearning;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elearning.databinding.ItemCourseBinding;

import java.util.ArrayList;

public class StudentCoursesAdapter extends RecyclerView.Adapter<StudentCoursesAdapter.Holder> {

    ArrayList<ModelMember> list = new ArrayList<>();
    OnItemClick onItemClick;

    public void setList(ArrayList<ModelMember> list) {
        this.list = list;
    }

    public void setOnItemClick(StudentCoursesAdapter.OnItemClick onItemClick){
        this.onItemClick = onItemClick;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCourseBinding binding = ItemCourseBinding.inflate(LayoutInflater.from(parent.getContext()) , parent , false);
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
        ItemCourseBinding binding ;
        public Holder(ItemCourseBinding binding) {
            super(binding.getRoot());
            this.binding = binding ;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClick!= null)
                        onItemClick.onClick(list.get(getLayoutPosition()));
                }
            });
        }
        public void bind (ModelMember modelMember)
        {
            binding.textName.setText(modelMember.getCourseName());
        }
    }
    interface OnItemClick{
        void onClick(ModelMember m);
    }
}
