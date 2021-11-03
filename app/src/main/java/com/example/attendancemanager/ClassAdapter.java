package com.example.attendancemanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ClassAdapter extends FirestoreRecyclerAdapter<ClassModel, ClassAdapter.ClassViewHolder> {

//    public void setOnItemCLickListener(OnItemCLickListener onItemCLickListener) {
//        this.onItemCLickListener = onItemCLickListener;
//    }
//
//    public OnItemCLickListener onItemCLickListener;
//    public interface OnItemCLickListener{
//        void onClick(int position);
//    }

    public ClassAdapter(FirestoreRecyclerOptions<ClassModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ClassViewHolder holder, int position, ClassModel classModel) {

        holder.txtclassname.setText(classModel.getClassname());
        holder.txtsubjectname.setText(classModel.getSubjectname());
        holder.txtpriority.setText(String.valueOf(classModel.getPriority()));
    }

    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_item, parent, false);
        return new ClassViewHolder(v);
    }

    //to Delete Class
    public void deleteClass(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    //ClassViewHolder
    class ClassViewHolder extends RecyclerView.ViewHolder {

        TextView txtclassname;
        TextView txtsubjectname;
        TextView txtpriority;

        //Constructor of ClassViewHolder
        public ClassViewHolder(@NonNull View itemView /*OnItemCLickListener onItemCLickListener*/) {
            super(itemView);
            txtclassname = itemView.findViewById(R.id.tv_classname);
            txtsubjectname = itemView.findViewById(R.id.tv_subjectname);
            txtpriority = itemView.findViewById(R.id.tv_priority);
            //itemView.setOnClickListener(view -> onItemCLickListener.onClick(getAdapterPosition()));
        }
    }
}
