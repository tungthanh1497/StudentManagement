package com.ttc.tungtt.sm.ui.updatestudent;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ttc.tungtt.sm.MainActivity;
import com.ttc.tungtt.sm.R;
import com.ttc.tungtt.sm.commons.Constants;
import com.ttc.tungtt.sm.commons.adapters.SimpleSpinnerAdapter;
import com.ttc.tungtt.sm.commons.adapters.TranscriptAdapter;
import com.ttc.tungtt.sm.commons.utils.StringUtils;
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
    public @interface BUNDLE_KEY {
        String STUDENT_ID = "student_id";
    }

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
    private String mUpdatingStudentId;
    private StudentEntity mStudentModel;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mUpdatingStudentId = bundle.getString(BUNDLE_KEY.STUDENT_ID, "");
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(UpdateStudentViewModel.class);

        initEditTexts();
        initSpinners();
        initRecyclerViews();
        initData();
        if (!StringUtils.isNullOrEmpty(mUpdatingStudentId)) {
            mViewModel.getStudentById(mUpdatingStudentId).observe(getViewLifecycleOwner(), studentEntity -> {
                mStudentModel = studentEntity;
                setDefaultData();
            });
        }

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
            mClassList.clear();
            mClassList.addAll(classList);
            mClassAdapter.notifyDataSetChanged();
            if (mClassList.size() > Constants.SIZE_EMPTY) {
                setDefaultClass();
            }
        });
    }

    private void initGenderList() {
        mViewModel.getAllGender().observe(getViewLifecycleOwner(), genderList -> {
            mGenderList.clear();
            mGenderList.addAll(genderList);
            mGenderAdapter.notifyDataSetChanged();
            if (mGenderList.size() > Constants.SIZE_EMPTY) {
                setDefaultGender();
            }
        });
    }

    private void setDefaultClass() {
        int selectedIndex = 0;
        if (mStudentModel != null) {
            for (int index = 0; index < mClassList.size(); index++) {
                SimpleModel classItem = mClassList.get(index);
                if (mStudentModel.getClassId() == classItem.getId()) {
                    selectedIndex = index;
                    break;
                }
            }
        }
        classSpinner.setSelection(selectedIndex);
        mSelectedClassIndex = selectedIndex;
    }

    private void setDefaultGender() {
        int selectedIndex = 0;
        if (mStudentModel != null) {
            for (int index = 0; index < mGenderList.size(); index++) {
                SimpleModel gender = mGenderList.get(index);
                if (mStudentModel.getGenderId() == gender.getId()) {
                    selectedIndex = index;
                    break;
                }
            }
        }
        genderSpinner.setSelection(selectedIndex);
        mSelectedGenderIndex = selectedIndex;
    }

    private void initSubjectList() {
        mViewModel.getAllSubject().observe(getViewLifecycleOwner(), subjectList -> {
            mSubjectList.clear();
            mSubjectList.addAll(subjectList);
            updateListTranscripts();
        });
    }

    private void updateListTranscripts() {
        mTranscriptList.clear();
        if (mStudentModel == null) {
            for (SubjectEntity item : mSubjectList) {
                mTranscriptList.add(new TranscriptModel(item));
            }
        } else {
            List<TranscriptModel> tempTranscriptList = mStudentModel.getTranscriptList();
            for (SubjectEntity item : mSubjectList) {
                boolean isExisted = false;
                for (TranscriptModel transcript : tempTranscriptList) {
                    if (transcript.getSubject().equals(item)) {
                        mTranscriptList.add(transcript);
                        isExisted = true;
                        break;
                    }
                }
                if (!isExisted) {
                    mTranscriptList.add(new TranscriptModel(item));
                }
            }
        }
        mTranscriptAdapter.notifyDataSetChanged();
    }


    private void initSpinners() {
        mGenderList = new ArrayList<>();
        mClassList = new ArrayList<>();

        mGenderAdapter = new SimpleSpinnerAdapter(getActivity(), mGenderList);
        mClassAdapter = new SimpleSpinnerAdapter(getActivity(), mClassList);

        bindAdapter(SPINNER_TYPE.GENDER, mGenderAdapter, genderSpinner);
        bindAdapter(SPINNER_TYPE.CLASS, mClassAdapter, classSpinner);
    }

    private void bindAdapter(String type,
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
                validateSubmitButton();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Log.w(TAG, "onNothingSelected: ");
            }
        });
    }

    @OnClick(R.id.btn_submit)
    public void onSubmitClicked() {
        String firstName = StringUtils.formatCapitalizeWithoutSpace(firstNameEditText);
        String lastName = StringUtils.formatCapitalize(lastNameEditText);
        if (mStudentModel == null) {
            String tempId = mViewModel.formatNameToTempStudentId(firstName, lastName);

            mViewModel.getListStudentLikeId(tempId).observe(getViewLifecycleOwner(), studentList -> {
                mViewModel.addStudent(new StudentEntity(
                        mViewModel.getMinimizeAvailableId(tempId, studentList),
                        firstName,
                        lastName,
                        mGenderList.get(mSelectedGenderIndex).getId(),
                        mClassList.get(mSelectedClassIndex).getId(),
                        mTranscriptList
                ));
                onBack();
            });
        } else {
            mViewModel.updateStudent(new StudentEntity(
                    mStudentModel.getId(),
                    firstName,
                    lastName,
                    mGenderList.get(mSelectedGenderIndex).getId(),
                    mClassList.get(mSelectedClassIndex).getId(),
                    mTranscriptList
            ));
            onBack();
        }


    }

    private void onBack() {
        if (getActivity() != null && getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).onBack();
        }
    }

    private void setDefaultData() {
        setDefaultName();
        setDefaultGender();
        setDefaultClass();
        setDefaultTranscripts();
    }

    private void setDefaultTranscripts() {
        updateListTranscripts();
    }

    private void setDefaultName() {
        firstNameEditText.setText(mStudentModel.getFirstName());
        firstNameEditText.setSelection(firstNameEditText.getText().toString().length());
        firstNameEditText.setEnabled(mStudentModel == null);
        lastNameEditText.setText(mStudentModel.getLastName());
        lastNameEditText.setSelection(lastNameEditText.getText().toString().length());
        lastNameEditText.setEnabled(mStudentModel == null);
    }

    private void initEditTexts() {
        TextWatcher validateTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validateSubmitButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        firstNameEditText.addTextChangedListener(validateTextWatcher);
        lastNameEditText.addTextChangedListener(validateTextWatcher);
    }

    private void validateSubmitButton() {
        submitButton.setEnabled(!invalidField());
    }

    private boolean invalidField() {
        return StringUtils.isNullOrEmpty(firstNameEditText)
                || StringUtils.isNullOrEmpty(lastNameEditText)
                || mSelectedGenderIndex == -1
                || mSelectedClassIndex == -1;
    }
}