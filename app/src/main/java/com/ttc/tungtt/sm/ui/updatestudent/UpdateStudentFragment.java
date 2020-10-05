package com.ttc.tungtt.sm.ui.updatestudent;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.ttc.tungtt.sm.R;
import com.ttc.tungtt.sm.databases.entities.StudentEntity;

import java.util.List;

public class UpdateStudentFragment extends Fragment {

    private static final String TAG = UpdateStudentFragment.class.getSimpleName();
    private UpdateStudentViewModel mViewModel;

    public static UpdateStudentFragment newInstance() {
        return new UpdateStudentFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.update_student_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(UpdateStudentViewModel.class);
        // TODO: Use the ViewModel
        mViewModel.getAllStudent().observe(getViewLifecycleOwner(), new Observer<List<StudentEntity>>() {
            @Override
            public void onChanged(List<StudentEntity> studentEntities) {
                Log.d(TAG, "onChanged: " + studentEntities);
            }
        });
    }

}