package com.example.studytracker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileFragment extends Fragment {

    TextView nameText;
    TextView facultyText;
    TextView specializationText;
    TextView facultyNumberText;
    TextView courseText;
    TextView enrollmentStatusText;
    TextView tuitionFeeTypeText;
    TextView egnText;
    TextView countryText;
    TextView successText;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        nameText = view.findViewById(R.id.nameText);
        facultyText = view.findViewById(R.id.facultyText);
        specializationText = view.findViewById(R.id.specializationText);
        facultyNumberText = view.findViewById(R.id.facultyNumberText);
        courseText = view.findViewById(R.id.courseText);
        enrollmentStatusText = view.findViewById(R.id.enrollmentStatusText);
        tuitionFeeTypeText = view.findViewById(R.id.tuitionFeeTypeText);
        egnText = view.findViewById(R.id.egnText);
        countryText = view.findViewById(R.id.countryText);
        successText = view.findViewById(R.id.successText);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://653bdd6ad5d6790f5ec78f61.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<StudentEntity> call = apiService.getStudent("1");
        call.enqueue(new Callback<StudentEntity>() {
            @Override
            public void onResponse(Call<StudentEntity> call, Response<StudentEntity> response) {
                if (response.isSuccessful()) {
                    StudentEntity studentEntity = response.body();
                    setStudentData(studentEntity);
                } else {
                    //error
                }
            }

            @Override
            public void onFailure(Call<StudentEntity> call, Throwable t) {
                //error
            }
        });

        return view;
    }

    private void setStudentData(StudentEntity studentEntity) {
        // Пример установки данных для полей
        nameText.setText(studentEntity.getName());
        facultyText.setText(studentEntity.getFaculty());
        specializationText.setText(studentEntity.getSpecialization());
        facultyNumberText.setText(String.valueOf(studentEntity.getFacultyNumber()));
        courseText.setText(String.valueOf(studentEntity.getCourse()));
        enrollmentStatusText.setText(studentEntity.getEnrollmentStatus());
        tuitionFeeTypeText.setText(studentEntity.getTuitionFeeType());
        egnText.setText(studentEntity.getEgn());
        countryText.setText(studentEntity.getCountry());
        successText.setText(String.valueOf(studentEntity.getSuccess()));
    }
}
