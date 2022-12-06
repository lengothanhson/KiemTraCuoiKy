package com.example.kiemtracuoiky.models;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kiemtracuoiky.R;

import java.util.ArrayList;

public class PBAdapter extends RecyclerView.Adapter<PBAdapter.UserViewHolder>{
    ArrayList<PB> lstUser;
    Context context;
    PBAdapter.UserCallback userCallback;

    public PBAdapter(ArrayList<PB> lstUser) {
        this.lstUser = lstUser;
    }

    public void setCallback(PBAdapter.UserCallback callback) {
        this.userCallback = callback;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View userView = inflater.inflate(R.layout.layoutitemtp, parent, false);
        PBAdapter.UserViewHolder viewHolder = new PBAdapter.UserViewHolder(userView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, @SuppressLint("RecyclerView") int position) {
        PB item = lstUser.get(position);

        holder.tvName.setText(item.getName());

        holder.ivDelete.setOnClickListener(view -> userCallback.onItemDeleteClicked(item, position));
        holder.ivEdit.setOnClickListener(view -> userCallback.onItemEditClicked(item, position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userCallback.onClick(item, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstUser.size();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView ivEdit;
        ImageView ivDelete;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvNameTP);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            ivDelete = itemView.findViewById(R.id.ivDelete);
        }
    }

    public interface UserCallback {
        void onClick(PB pb, int position);
        void onItemDeleteClicked(PB pb, int position);
        void onItemEditClicked(PB pb, int position);
    }
}
