package com.kutash.taxibuber.entity;

import java.util.Date;
import java.util.List;

public class User extends AbstractEntity {

    private float rating;
    private String name;
    private String surname;
    private String patronymic;
    private String email;
    private String password;
    private UserRole role;
    private Date birthday;
    private String photoPath;
    private String phone;
    private List<Comment> comments;

    public User(String name, String surname, String patronymic, String email, String password, UserRole role, Date birthday, String phone) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.email = email;
        this.password = password;
        this.role = role;
        this.birthday = birthday;
        this.phone = phone;
    }

    public User(String name, String surname, String patronymic, String email, String password,String phone) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public User(int id, float rating, String name, String surname, String patronymic, String email, String password, UserRole role, Date birthday, String photoPath, String phone) {
        super(id);
        this.rating = rating;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.email = email;
        this.password = password;
        this.role = role;
        this.birthday = birthday;
        this.photoPath = photoPath;
        this.phone = phone;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullName(){
        return surname+" "+name+" "+patronymic;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}