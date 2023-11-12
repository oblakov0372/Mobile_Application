package com.example.studytracker;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("https://653bdd6ad5d6790f5ec78f61.mockapi.io/api/studyTracker/students/{id}")
    Call<StudentEntity> getStudent(@Path("id") String id);
}
