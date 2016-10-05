/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intive.intivemvpframework.data.repository;

import com.intive.intivemvpframework.data.entity.UserEntity;
import com.intive.intivemvpframework.data.entity.mapper.UserEntityDataMapper;
import com.intive.intivemvpframework.data.net.RestApi;
import com.intive.intivemvpframework.domain.User;
import com.intive.intivemvpframework.domain.repository.UserRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import rx.Observable;
import rx.functions.Func1;

/**
 * {@link UserRepository} for retrieving user data.
 */
@Singleton
public class UserDataRepository implements UserRepository {

    private final RestApi mRestApi;

    private final UserEntityDataMapper mUserEntityDataMapper;

    /**
     * Constructs a {@link UserRepository}.
     *
     * @param restApi              A factory to construct different data source implementations.
     * @param userEntityDataMapper {@link UserEntityDataMapper}.
     */
    @Inject
    public UserDataRepository(final RestApi restApi, final UserEntityDataMapper userEntityDataMapper) {
        mRestApi = restApi;
        this.mUserEntityDataMapper = userEntityDataMapper;
    }

    @Override
    public Observable<List<User>> users() {
        return mRestApi.userEntityList().map(new Func1<List<UserEntity>, List<User>>() {
            @Override
            public List<User> call(final List<UserEntity> userEntities) {
                return mUserEntityDataMapper.transform(userEntities);
            }
        });
    }

    @Override
    public Observable<User> user(final int userId) {
        return mRestApi.userEntityById(userId)
                .map(new Func1<UserEntity, User>() {
                    @Override
                    public User call(final UserEntity userEntities) {
                        return mUserEntityDataMapper.transform(userEntities);
                    }
                });
    }
}
