package com.example.manojpun.camc.data.remote;

import com.example.manojpun.camc.data.Post;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIService {
    @POST("/posts")
    @FormUrlEncoded
    Call<Post> savePost(@Field("confidence") Double confidence,
                        @Field("prediction") String prediction,
                        @Field("uid") String uid);
}