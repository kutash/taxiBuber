package com.kutash.taxibuber.entity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * The type User.
 */
public class User extends AbstractEntity {

    private float rating;
    private String name;
    private String surname;
    private String patronymic;
    private String email;
    private byte[] password;
    private UserRole role;
    private Date birthday;
    private String photoPath;
    private String phone;
    private Status status;
    private List<Comment> comments;

    /**
     * Instantiates a new User.
     *
     * @param name       the name
     * @param surname    the surname
     * @param patronymic the patronymic
     * @param email      the email
     * @param password   the password
     * @param role       the role
     * @param birthday   the birthday
     * @param phone      the phone
     * @param status     the status
     */
    public User(String name, String surname, String patronymic, String email, byte[] password, UserRole role, Date birthday, String phone, Status status) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.email = email;
        this.password = password;
        this.role = role;
        this.birthday = birthday;
        this.phone = phone;
        this.status = status;
    }

    /**
     * Instantiates a new User.
     *
     * @param name       the name
     * @param surname    the surname
     * @param patronymic the patronymic
     * @param email      the email
     * @param password   the password
     * @param phone      the phone
     */
    public User(String name, String surname, String patronymic, String email, byte[] password, String phone) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    /**
     * Instantiates a new User.
     *
     * @param id         the id
     * @param rating     the rating
     * @param name       the name
     * @param surname    the surname
     * @param patronymic the patronymic
     * @param email      the email
     * @param password   the password
     * @param role       the role
     * @param birthday   the birthday
     * @param photoPath  the photo path
     * @param phone      the phone
     * @param status     the status
     */
    public User(int id, float rating, String name, String surname, String patronymic, String email, byte[] password, UserRole role, Date birthday, String photoPath, String phone, Status status) {
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
        this.status = status;
    }

    /**
     * Gets rating.
     *
     * @return the rating
     */
    public float getRating() {
        return rating;
    }

    /**
     * Sets rating.
     *
     * @param rating the rating
     */
    public void setRating(float rating) {
        this.rating = rating;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets surname.
     *
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets surname.
     *
     * @param surname the surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets patronymic.
     *
     * @return the patronymic
     */
    public String getPatronymic() {
        return patronymic;
    }

    /**
     * Sets patronymic.
     *
     * @param patronymic the patronymic
     */
    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get password byte [ ].
     *
     * @return the byte [ ]
     */
    public byte[] getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(byte[] password) {
        this.password = password;
    }

    /**
     * Gets role.
     *
     * @return the role
     */
    public UserRole getRole() {
        return role;
    }

    /**
     * Sets role.
     *
     * @param role the role
     */
    public void setRole(UserRole role) {
        this.role = role;
    }

    /**
     * Gets birthday.
     *
     * @return the birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * Sets birthday.
     *
     * @param birthday the birthday
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * Gets photo path.
     *
     * @return the photo path
     */
    public String getPhotoPath() {
        return photoPath;
    }

    /**
     * Sets photo path.
     *
     * @param photoPath the photo path
     */
    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    /**
     * Gets phone.
     *
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets phone.
     *
     * @param phone the phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Gets comments.
     *
     * @return the comments
     */
    public List<Comment> getComments() {
        return comments;
    }

    /**
     * Sets comments.
     *
     * @param comments the comments
     */
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    /**
     * Get full name string.
     *
     * @return the string
     */
    public String getFullName(){
        return surname+" "+name+" "+patronymic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        if (Float.compare(user.rating, rating) != 0) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (surname != null ? !surname.equals(user.surname) : user.surname != null) return false;
        if (patronymic != null ? !patronymic.equals(user.patronymic) : user.patronymic != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (password != null ? !Arrays.equals(password, user.password) : user.password != null) return false;
        if (role != user.role) return false;
        if (birthday != null ? !birthday.equals(user.birthday) : user.birthday != null) return false;
        if (photoPath != null ? !photoPath.equals(user.photoPath) : user.photoPath != null) return false;
        if (phone != null ? !phone.equals(user.phone) : user.phone != null) return false;
        return status == user.status;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (rating != +0.0f ? Float.floatToIntBits(rating) : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (patronymic != null ? patronymic.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? Arrays.hashCode(password) : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (photoPath != null ? photoPath.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "rating=" + rating +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", birthday=" + birthday +
                ", photoPath='" + photoPath + '\'' +
                ", phone='" + phone + '\'' +
                ", status=" + status +
                "} " + super.toString();
    }
}