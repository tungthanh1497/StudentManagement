package com.ttc.tungtt.sm.ui.main;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.ttc.tungtt.sm.commons.adapters.StudentAdapter;
import com.ttc.tungtt.sm.databases.entities.ClassEntity;
import com.ttc.tungtt.sm.databases.entities.GenderEntity;
import com.ttc.tungtt.sm.databases.entities.StudentEntity;
import com.ttc.tungtt.sm.ui.updatestudent.UpdateStudentFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainFragment extends Fragment {

    @BindView(R.id.rv_student)
    RecyclerView studentRecyclerView;

    private MainViewModel mViewModel;

    private List<StudentEntity> mStudentList;
    private List<GenderEntity> mGenderList;
    private List<ClassEntity> mClassList;

    private StudentAdapter mStudentAdapter;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
        initRecyclerViews();
        observeLiveData();
    }

    private void initRecyclerViews() {
        mStudentList = new ArrayList<>();
        mClassList = new ArrayList<>();
        mGenderList = new ArrayList<>();
        mStudentAdapter = new StudentAdapter(getContext(), mStudentList, new StudentAdapter.OnStudentListener() {
            @Override
            public void onUpdateStudent(int position) {
                openUpdateStudentScreen(mStudentList.get(position));
            }

            @Override
            public void onDeleteStudent(int position) {
                openDialogConfirm(mStudentList.get(position));
            }
        });
        studentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        studentRecyclerView.setItemAnimator(new DefaultItemAnimator());
        studentRecyclerView.setAdapter(mStudentAdapter);
    }

    private void observeLiveData() {
        mViewModel.getAllGenders().observe(getViewLifecycleOwner(), genderList -> {
            mGenderList.clear();
            mGenderList.addAll(genderList);
            updateGenderNames();
        });
        mViewModel.getAllClasses().observe(getViewLifecycleOwner(), classList -> {
            mClassList.clear();
            mClassList.addAll(classList);
            updateClassNames();
        });
        mViewModel.getAllStudents().observe(getViewLifecycleOwner(), studentList -> {
            mStudentList.clear();
            mStudentList.addAll(studentList);
            for (StudentEntity item : mStudentList) {
                item.setFullName(item.getFirstName() + " " + item.getLastName());
            }
            updateClassNames();
            updateGenderNames();
        });
    }

    private void updateClassNames() {
        for (StudentEntity student : mStudentList) {
            for (ClassEntity classItem : mClassList) {
                if (student.getClassId() == classItem.getId()) {
                    student.setClassName(classItem.getName());
                }
            }
        }
        mStudentAdapter.notifyDataSetChanged();
    }

    private void updateGenderNames() {
        for (StudentEntity student : mStudentList) {
            for (GenderEntity gender : mGenderList) {
                if (student.getGenderId() == gender.getId()) {
                    student.setGenderName(gender.getName());
                }
            }
        }
        mStudentAdapter.notifyDataSetChanged();
    }

    private void openUpdateStudentScreen(StudentEntity updatingStudent) {
        if (getActivity() != null && getActivity() instanceof MainActivity) {
            Bundle bundle = new Bundle();
            bundle.putString(UpdateStudentFragment.BUNDLE_KEY.STUDENT_ID, updatingStudent.getId());
            ((MainActivity) getActivity()).addScreen(UpdateStudentFragment.newInstance(),
                    Constants.SCREEN_TAG.UPDATE_STUDENT,
                    bundle);
        }
    }

    private void openDialogConfirm(StudentEntity deletingStudent) {
        AlertDialog dialogConfirm = new AlertDialog.Builder(getContext())
                .setTitle("Dialog Confirm")
                .setMessage("Do you really want to delete student " + deletingStudent.getFullName() + "?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(R.string.btn_delete, (dialog, whichButton) -> {
                    mViewModel.deleteStudent(deletingStudent);
                })
                .setNegativeButton(R.string.btn_no, null)
                .create();
        dialogConfirm.setOnShowListener(arg0 -> {
            dialogConfirm.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorAccent));
            dialogConfirm.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        });

        dialogConfirm.show();
    }

    @OnClick(R.id.btn_add)
    public void onAddClicked() {
        if (getActivity() != null && getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).addScreen(UpdateStudentFragment.newInstance(), Constants.SCREEN_TAG.UPDATE_STUDENT);
        }
    }
}