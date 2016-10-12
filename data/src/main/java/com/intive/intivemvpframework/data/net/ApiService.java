package com.intive.intivemvpframework.data.net;

import com.intive.intivemvpframework.data.entity.UserEntity;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface ApiService {

    @GET("users.json")
    Observable<List<UserEntity>> getUsers();

    @GET("user_{id}.json")
    Observable<UserEntity> getUser(@Path("id") int userId);
}