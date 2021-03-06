package com.ttc.tungtt.sm.commons.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ttc.tungtt.sm.R;
import com.ttc.tungtt.sm.databases.entities.StudentEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ttcandroid a.k.a TungTT
 * On Mon, 05 Oct 2020 - 16:29
 */
public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    private Context mContext;
    private List<StudentEntity> mList;
    private OnStudentListener mListener;

    public StudentAdapter(Context context,
                          List<StudentEntity> list,
                          OnStudentListener listener) {
        this.mContext = context;
        this.mList = list;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StudentAdapter.ViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_student, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StudentEntity model = mList.get(position);
        holder.nameTextView.setText(model.getFullName());
        holder.genderTextView.setText(model.getGenderName());
        holder.classTextView.setText(model.getClassName());

        holder.transcriptRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        holder.transcriptRecyclerView.setItemAnimator(new DefaultItemAnimator());
        holder.transcriptRecyclerView.setAdapter(new TranscriptAdapter(mContext,
                model.getTranscriptList(),
                TranscriptAdapter.RESULT_VIEW_TYPE.TEXT_VIEW,
                null));

        holder.addButton.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onUpdateStudent(position);
            }
        });

        holder.deleteButton.setOnClickListener(view -> {
            if (mListener != null) {
                mListener.onDeleteStudent(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView nameTextView;
        @BindView(R.id.tv_gender)
        TextView genderTextView;
        @BindView(R.id.tv_class)
        TextView classTextView;
        @BindView(R.id.rv_transcript)
        RecyclerView transcriptRecyclerView;
        @BindView(R.id.btn_add)
        Button addButton;
        @BindView(R.id.btn_delete)
        Button deleteButton;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnStudentListener {
        void onUpdateStudent(int position);

        void onDeleteStudent(int position);
    }
}
