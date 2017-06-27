package com.example.manojpun.camc.data.remote;

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "https://lambda-face-recognition.p.mashape.com/recognize/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}