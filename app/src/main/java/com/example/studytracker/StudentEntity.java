package com.example.studytracker;

public class StudentEntity {
    private String name;
    private String faculty;
    private String specialization;
    private long facultyNumber;
    private int course;
    private String enrollmentStatus;
    private String tuitionFeeType;
    private String egn;
    private String country;
    private double success;

    public StudentEntity(String name, String faculty, String specialization, long facultyNumber, int course, String enrollmentStatus, String tuitionFeeType, String egn, String country, double success) {
        this.name = name;
        this.faculty = faculty;
        this.specialization = specialization;
        this.facultyNumber = facultyNumber;
        this.course = course;
        this.enrollmentStatus = enrollmentStatus;
        this.tuitionFeeType = tuitionFeeType;
        this.egn = egn;
        this.country = country;
        this.success = success;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public long getFacultyNumber() {
        return facultyNumber;
    }

    public void setFacultyNumber(long facultyNumber) {
        this.facultyNumber = facultyNumber;
    }

    public int getCourse() {
        return course;
    }

    public void setCourse(int course) {
        this.course = course;
    }

    public String getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public void setEnrollmentStatus(String enrollmentStatus) {
        this.enrollmentStatus = enrollmentStatus;
    }

    public String getTuitionFeeType() {
        return tuitionFeeType;
    }

    public void setTuitionFeeType(String tuitionFeeType) {
        this.tuitionFeeType = tuitionFeeType;
    }

    public String getEgn() {
        return egn;
    }

    public void setEgn(String egn) {
        this.egn = egn;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getSuccess() {
        return success;
    }

    public void setSuccess(double success) {
        this.success = success;
    }
}

