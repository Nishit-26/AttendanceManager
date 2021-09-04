package com.example.attendancemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassViewholder> {

    private OnItemClickListener onItemClickListener;
    public  interface OnItemClickListener{
        void onClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    Context ct;
    public ClassAdapter(Context context, ArrayList<ClassItem> classItems) {
        this.classItems = classItems;
        ct = context;
    }

    ArrayList<ClassItem> classItems;

    @NonNull
    @Override
    public ClassViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.class_item, parent, false);
        return new ClassViewholder(view,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewholder holder, int position) {
        holder.classname.setText(classItems.get(position).getClassname());
        holder.subjectname.setText(classItems.get(position).getSubjectname());


    }

    @Override
    public int getItemCount() {
        return classItems.size();
    }

    public static class ClassViewholder extends RecyclerView.ViewHolder {

        TextView classname;
        TextView subjectname;

        public ClassViewholder(@NonNull View itemView,OnItemClickListener onItemClickListener) {
            super(itemView);
            classname = itemView.findViewById(R.id.tv_classname);
            subjectname = itemView.findViewById(R.id.tv_subjectname);
            itemView.setOnClickListener(view -> onItemClickListener.onClick(getAdapterPosition()));

        }

    }
}
