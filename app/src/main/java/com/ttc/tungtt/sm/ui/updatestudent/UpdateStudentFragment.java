package com.ttc.tungtt.sm.ui.updatestudent;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.ttc.tungtt.sm.R;
import com.ttc.tungtt.sm.commons.Constants;
import com.ttc.tungtt.sm.commons.adapters.SimpleSpinnerAdapter;
import com.ttc.tungtt.sm.databases.entities.StudentEntity;
import com.ttc.tungtt.sm.models.SimpleModel;
import com.ttc.tungtt.sm.models.SubjectModel;
import com.ttc.tungtt.sm.models.TranscriptModel;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateStudentFragment extends Fragment {

    private static final String TAG = UpdateStudentFragment.class.getSimpleName();

    @Retention(RetentionPolicy.SOURCE)
    public @interface SPINNER_TYPE {
        String GENDER = "gender";
        String CLASS = "class";
    }


    @BindView(R.id.spn_gender)
    Spinner genderSpinner;
    @BindView(R.id.spn_class)
    Spinner classSpinner;


    private UpdateStudentViewModel mViewModel;

    private List<SimpleModel> mGenderList;
    private List<SimpleModel> mClassList;

    private SimpleSpinnerAdapter mGenderAdapter;
    private SimpleSpinnerAdapter mClassAdapter;

    private int mSelectedGenderIndex = -1;
    private int mSelectedClassIndex = -1;


    public static UpdateStudentFragment newInstance() {
        return new UpdateStudentFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.update_student_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(UpdateStudentViewModel.class);

        observeLiveData();
        initSpinners();
        initData();

        mViewModel.getAllStudent();

        ArrayList<TranscriptModel> transcriptModels = new ArrayList<>();
        transcriptModels.add(new TranscriptModel(new SubjectModel(1, "math"), 6));
        transcriptModels.add(new TranscriptModel(new SubjectModel(2, "physics"), 9));
        transcriptModels.add(new TranscriptModel(new SubjectModel(3, "chem"), 7));
        mViewModel.addStudent(new StudentEntity("tung",
                "trinh thanh",
                1,
                1,
                transcriptModels));

        new Handler().postDelayed(() -> mViewModel.getAllStudent(), 2000);

    }

    private void initData() {
        initGenderList();
        initClassList();
    }

    private void initClassList() {
        mViewModel.getAllClass().observe(getViewLifecycleOwner(), classList -> {
            mClassList.addAll(classList);
            mClassAdapter.notifyDataSetChanged();
            if (mClassList.size() > Constants.SIZE_EMPTY) {
                classSpinner.setSelection(0);
                mSelectedClassIndex = 0;
            }
        });
    }

    private void initGenderList() {
        mViewModel.getAllGender().observe(getViewLifecycleOwner(), genderList -> {
            mGenderList.addAll(genderList);
            mGenderAdapter.notifyDataSetChanged();
            if (mGenderList.size() > Constants.SIZE_EMPTY) {
                genderSpinner.setSelection(0);
                mSelectedGenderIndex = 0;
            }
        });
    }


    private void initSpinners() {
        mGenderList = new ArrayList<>();
        mClassList = new ArrayList<>();

        mGenderAdapter = new SimpleSpinnerAdapter(getActivity(), mGenderList);
        mClassAdapter = new SimpleSpinnerAdapter(getActivity(), mClassList);

        bindAdapter(SPINNER_TYPE.GENDER, mGenderList, mGenderAdapter, genderSpinner);
        bindAdapter(SPINNER_TYPE.CLASS, mClassList, mClassAdapter, classSpinner);
    }

    private void bindAdapter(String type,
                             List<SimpleModel> list,
                             SimpleSpinnerAdapter adapter,
                             Spinner spinner) {
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch (type) {
                    case SPINNER_TYPE.GENDER:
                        mSelectedGenderIndex = position;
                        break;
                    case SPINNER_TYPE.CLASS:
                        mSelectedClassIndex = position;
                        break;
                }
                Toast.makeText(getActivity(), "onItemSelected: " + list.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.w(TAG, "onNothingSelected: ");
            }
        });
    }

    private void observeLiveData() {
        mViewModel.getStudentLiveData().observe(getViewLifecycleOwner(),
                studentList -> Log.d(TAG, "onChanged: studentList = " + studentList));
    }

}