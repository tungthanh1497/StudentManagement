package com.ttc.tungtt.sm.ui.updatestudent;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.ttc.tungtt.sm.R;
import com.ttc.tungtt.sm.commons.Constants;
import com.ttc.tungtt.sm.databases.entities.StudentEntity;
import com.ttc.tungtt.sm.databases.models.SubjectModel;
import com.ttc.tungtt.sm.databases.models.TranscriptModel;

import java.util.ArrayList;

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

        observeLiveData();

        mViewModel.getAllStudent();

        ArrayList<TranscriptModel> transcriptModels = new ArrayList<>();
        transcriptModels.add(new TranscriptModel(new SubjectModel(1, "math"), 6));
        transcriptModels.add(new TranscriptModel(new SubjectModel(2, "physics"), 9));
        transcriptModels.add(new TranscriptModel(new SubjectModel(3, "chem"), 7));
        mViewModel.addStudent(new StudentEntity("tung",
                "trinh thanh",
                Constants.GENDER.MALE,
                1,
                transcriptModels));

        new Handler().postDelayed(() -> mViewModel.getAllStudent(), 2000);

    }

    private void observeLiveData() {
        mViewModel.getStudentLiveData().observe(getViewLifecycleOwner(),
                studentList -> Log.d(TAG, "onChanged: studentList = " + studentList));
    }

}