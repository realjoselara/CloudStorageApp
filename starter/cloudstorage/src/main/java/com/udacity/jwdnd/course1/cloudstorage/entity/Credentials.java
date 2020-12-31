package com.udacity.jwdnd.course1.cloudstorage.entity;


public class Credentials {
    private Long id;
    private String url;
    private String username;
    private String key;
    private Long userId;
    private String password;

    public Credentials(Long id, String url, String username, String key, String password, Long userId) {
        this.id = id;
        this.url = url;
        this.username = username;
        this.key = key;
        this.password = password;
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCredentialid() {
        return this.id;
    }

    public void setCredentialid(Long credentialsid) {
        this.id = credentialsid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}