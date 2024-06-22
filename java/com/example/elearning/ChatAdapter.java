package com.example.elearning;

import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.elearning.databinding.ItemChatMeBinding;
import com.example.elearning.databinding.ItemChatOtherBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_MESSAGE_SENT = 0;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 1;
    ArrayList<ModelChat> list = new ArrayList<>();
    FirebaseAuth auth = FirebaseAuth.getInstance();

    public void setList(ArrayList<ModelChat> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemChatMeBinding binding ;
        ItemChatOtherBinding bindingOther ;
        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            binding = ItemChatMeBinding.inflate(LayoutInflater.from(parent.getContext()) , parent , false);
            return new SentMessageHolder(binding);
        } else {
            bindingOther = ItemChatOtherBinding.inflate(LayoutInflater.from(parent.getContext()) , parent , false);
            return new ReceivedMessageHolder(bindingOther);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ModelChat message = (ModelChat) list.get(position);
        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
        }
    }

    @Override
    public int getItemViewType(int position) {
        ModelChat message = (ModelChat) list.get(position);

        if (message.getSenderId().equals(auth.getUid())) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
    private class SentMessageHolder extends RecyclerView.ViewHolder {
        ItemChatMeBinding binding ;
        SentMessageHolder(ItemChatMeBinding binding) {
            super(binding.getRoot());
            this.binding = binding ;
        }
        void bind(ModelChat message) {
            binding.textUserName.setVisibility(View.GONE);
            binding.textGchatMessageMe.setText(message.getMassage());
        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        ItemChatOtherBinding binding ;
        ReceivedMessageHolder(ItemChatOtherBinding binding) {
            super(binding.getRoot());
            this.binding = binding ;
        }

        void bind(ModelChat message) {
            binding.textUserName.setText(message.getUserName());
            binding.textUserName.setVisibility(View.VISIBLE);
            binding.textGchatMessageOther.setText(message.getMassage());
        }
    }
}
