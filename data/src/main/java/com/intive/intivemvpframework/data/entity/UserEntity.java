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
package com.intive.intivemvpframework.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * User Entity used in the data layer.
 */
public class UserEntity {

    @SerializedName("id")
    private int userId;

    @SerializedName("cover_url")
    private String coverUrl;

    @SerializedName("full_name")
    private String fullname;

    @SerializedName("description")
    private String description;

    @SerializedName("followers")
    private int followers;

    @SerializedName("email")
    private String email;

    public UserEntity() {
        //empty
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("***** User Entity Details *****\n");
        stringBuilder.append("id=" + this.getUserId() + "\n");
        stringBuilder.append("cover url=" + this.getCoverUrl() + "\n");
        stringBuilder.append("fullname=" + this.getFullname() + "\n");
        stringBuilder.append("email=" + this.getEmail() + "\n");
        stringBuilder.append("description=" + this.getDescription() + "\n");
        stringBuilder.append("followers=" + this.getFollowers() + "\n");
        stringBuilder.append("*******************************");

        return stringBuilder.toString();
    }

    public int getUserId() {
        return userId;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public String getFullname() {
        return fullname;
    }

    public String getEmail() {
        return email;
    }

    public String getDescription() {
        return description;
    }

    public int getFollowers() {
        return followers;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
