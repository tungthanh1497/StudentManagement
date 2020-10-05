package com.ttc.tungtt.sm.ui.updatestudent;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ttc.tungtt.sm.R;
import com.ttc.tungtt.sm.commons.Constants;
import com.ttc.tungtt.sm.commons.adapters.SimpleSpinnerAdapter;
import com.ttc.tungtt.sm.commons.adapters.TranscriptAdapter;
import com.ttc.tungtt.sm.databases.entities.StudentEntity;
import com.ttc.tungtt.sm.databases.entities.SubjectEntity;
import com.ttc.tungtt.sm.models.SimpleModel;
import com.ttc.tungtt.sm.models.TranscriptModel;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateStudentFragment extends Fragment {

    private static final String TAG = UpdateStudentFragment.class.getSimpleName();

    @Retention(RetentionPolicy.SOURCE)
    public @interface SPINNER_TYPE {
        String GENDER = "gender";
        String CLASS = "class";
    }


    @BindView(R.id.et_first_name)
    EditText firstNameEditText;
    @BindView(R.id.et_last_name)
    EditText lastNameEditText;
    @BindView(R.id.spn_gender)
    Spinner genderSpinner;
    @BindView(R.id.spn_class)
    Spinner classSpinner;
    @BindView(R.id.rv_transcript)
    RecyclerView transcriptRecyclerView;
    @BindView(R.id.btn_submit)
    Button submitButton;


    private UpdateStudentViewModel mViewModel;

    private List<SimpleModel> mGenderList;
    private List<SimpleModel> mClassList;
    private List<SubjectEntity> mSubjectList;
    private ArrayList<TranscriptModel> mTranscriptList;

    private SimpleSpinnerAdapter mGenderAdapter;
    private SimpleSpinnerAdapter mClassAdapter;
    private TranscriptAdapter mTranscriptAdapter;

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

        initSpinners();
        initRecyclerViews();
        initData();

    }

    private void initRecyclerViews() {
        mSubjectList = new ArrayList<>();
        mTranscriptList = new ArrayList<>();
        mTranscriptAdapter = new TranscriptAdapter(getContext(),
                mTranscriptList,
                TranscriptAdapter.RESULT_VIEW_TYPE.EDIT_TEXT,
                (position, result) -> mTranscriptList.get(position).setResult(result));
        transcriptRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        transcriptRecyclerView.setItemAnimator(new DefaultItemAnimator());
        transcriptRecyclerView.setAdapter(mTranscriptAdapter);
    }

    private void initData() {
        initGenderList();
        initClassList();
        initSubjectList();
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

    private void initSubjectList() {
        mViewModel.getAllSubject().observe(getViewLifecycleOwner(), subjectList -> {
            mSubjectList.addAll(subjectList);
            mTranscriptList.clear();
            for (SubjectEntity item : mSubjectList) {
                mTranscriptList.add(new TranscriptModel(item));
            }
            mTranscriptAdapter.notifyDataSetChanged();
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

    @OnClick(R.id.btn_submit)
    public void onSubmitClicked() {
        StudentEntity studentModel = new StudentEntity(
                firstNameEditText.getText().toString().trim(),
                lastNameEditText.getText().toString().trim(),
                mGenderList.get(mSelectedGenderIndex).getId(),
                mClassList.get(mSelectedClassIndex).getId(),
                mTranscriptList
        );
        mViewModel.addStudent(studentModel);
    }

}