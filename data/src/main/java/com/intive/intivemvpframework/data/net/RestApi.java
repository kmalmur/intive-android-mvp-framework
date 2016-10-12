package com.intive.intivemvpframework.data.net;

import com.intive.intivemvpframework.data.entity.UserEntity;

import java.util.List;

import rx.Observable;

/**
 * RestApi for retrieving data from the network.
 */
public interface RestApi {

    String API_BASE_URL = "http://www.android10.org/myapi/";

    /**
     * Retrieves an {@link rx.Observable} which will emit a List of {@link UserEntity}.
     */
    Observable<List<UserEntity>> getUserEntityList();

    /**
     * Retrieves an {@link rx.Observable} which will emit a {@link UserEntity}.
     *
     * @param userId The user id used to get user data.
     */
    Observable<UserEntity> getUserEntityById(final int userId);
}
