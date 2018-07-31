package com.example.conference.entity;

public enum ROLE {
    ADMIN ("Admin"),
    PRESENTER ("Presenter"),
    LISTENER ("Listener");

    private String role;

    ROLE(final String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

}
