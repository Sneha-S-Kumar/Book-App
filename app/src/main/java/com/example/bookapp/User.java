package com.example.bookapp;

public class User {
    private String name;
    private  String email;
    private String username;
    private String password;
    private String mobileno;


    public User(String name, String username, String email, String password, String mobileno, String mobile)
    {
        this.name=name;
        this.email=email;
        this.username=username;
        this.password=password;
        this.mobileno=mobileno;


    }

    public User() {
    }

    public String getEmail() {
        return email;
    }
    public String getNamePerson()
    {
        return name;

    }
    public  String getUsername()
    {
       return username;
    }
    public String getPassword()
    {
        return password;

    }
    public String getMobileno()
    {
        return mobileno;
    }

}
