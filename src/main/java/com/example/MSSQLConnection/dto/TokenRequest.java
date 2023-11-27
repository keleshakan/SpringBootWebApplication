package com.example.MSSQLConnection.dto;

public class TokenRequest {

    private String username;

    private String password;

    private String provider;

    private boolean refresh;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public boolean isRefresh() {
        return refresh;
    }

    public void setRefresh(boolean resfresh) {
        this.refresh = resfresh;
    }
}
