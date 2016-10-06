package com.intive.intivemvpframework.model;

/**
 * Class that represents a user in the presentation layer.
 */
public class UserModel {

    private final int userId;

    private String coverUrl;

    private String fullName;

    private String email;

    private String description;

    private int followers;

    public UserModel(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("***** User Model Details *****\n");
        stringBuilder.append("id=" + this.getUserId() + "\n");
        stringBuilder.append("cover url=" + this.getCoverUrl() + "\n");
        stringBuilder.append("fullname=" + this.getFullName() + "\n");
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

    public String getFullName() {
        return fullName;
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

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }
}
