package com.library.library_app.models;

public class Member {
    private int id;
    private String name;
    private String email;
    private String password;
    private Double fines;

    public Member() {}

    public Member(int id, String name, String email, String password, Double fines) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.fines = fines;
    }

    // Getters and Setters
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public Double getFines() {return fines;}
    public void setFines(Double fines) {this.fines = fines;}

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", fines=" + fines +
                '}';
    }
}
