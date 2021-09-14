package com.example.attendancemanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import org.w3c.dom.Text;

public class ClassAdapter extends FirestoreRecyclerAdapter<ClassModel, ClassAdapter.ClassViewHolder> {


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
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_item,parent,false);
        return new ClassViewHolder(v);
    }

    public void deleteClass(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class ClassViewHolder extends RecyclerView.ViewHolder {

        TextView txtclassname;
        TextView txtsubjectname;
        TextView txtpriority;

        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            txtclassname = itemView.findViewById(R.id.tv_classname);
            txtsubjectname = itemView.findViewById(R.id.tv_subjectname);
            txtpriority = itemView.findViewById(R.id.tv_priority);
        }
    }
}
