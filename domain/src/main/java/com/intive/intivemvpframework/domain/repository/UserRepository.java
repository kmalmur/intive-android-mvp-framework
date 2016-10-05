package com.intive.intivemvpframework.domain.repository;

import com.intive.intivemvpframework.domain.User;

import java.util.List;

import rx.Observable;

/**
 * Interface that represents a Repository for getting {@link User} related data.
 */
public interface UserRepository {

    /**
     * Get an {@link rx.Observable} which will emit a List of {@link User}.
     */
    Observable<List<User>> users();

    /**
     * Get an {@link rx.Observable} which will emit a {@link User}.
     *
     * @param userId The user id used to retrieve user data.
     */
    Observable<User> user(final int userId);
}
