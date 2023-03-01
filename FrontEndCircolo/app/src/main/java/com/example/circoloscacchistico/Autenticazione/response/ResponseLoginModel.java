package com.example.circoloscacchistico.Autenticazione.response;

import com.example.circoloscacchistico.Autenticazione.Role;

public class ResponseLoginModel {

    private Integer id_user;
    private String token;
    private Role role;
    private String firstname;
    private String lastname;
    private String successo;

    public ResponseLoginModel(Integer id, String token, Role role,String firstname, String lastname,String successo) {
        this.id_user = id;
        this.token = token;
        this.role = role;
        this.successo = successo;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Integer getId() {
        return id_user;
    }

    public void setId(Integer id) {
        this.id_user = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getSuccesso() {
        return successo;
    }

    public void setSuccesso(String successo) {
        this.successo = successo;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
