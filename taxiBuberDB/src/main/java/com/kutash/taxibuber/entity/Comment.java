package com.kutash.taxibuber.entity;

import java.util.Date;

public class Comment extends AbstractEntity {

    private String text;
    private int userId;
    private int reviewerId;
    private Date date;
    private byte mark;
    private String reviewerName;
    private String reviewerPhoto;

    public Comment(String text, int userId, int reviewerId, Date date, byte mark) {
        this.text = text;
        this.userId = userId;
        this.reviewerId = reviewerId;
        this.date = date;
        this.mark = mark;
    }

    public Comment(int id, String text, int userId, int reviewerId, Date date, byte mark, String reviewerName, String reviewerPhoto) {
        super(id);
        this.text = text;
        this.userId = userId;
        this.reviewerId = reviewerId;
        this.date = date;
        this.mark = mark;
        this.reviewerName = reviewerName;
        this.reviewerPhoto = reviewerPhoto;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(int reviewerId) {
        this.reviewerId = reviewerId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public byte getMark() {
        return mark;
    }

    public void setMark(byte mark) {
        this.mark = mark;
    }

    public String getReviewerName() {
        return reviewerName;
    }

    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    public String getReviewerPhoto() {
        return reviewerPhoto;
    }

    public void setReviewerPhoto(String reviewerPhoto) {
        this.reviewerPhoto = reviewerPhoto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Comment comment = (Comment) o;

        if (userId != comment.userId) return false;
        if (reviewerId != comment.reviewerId) return false;
        if (mark != comment.mark) return false;
        if (text != null ? !text.equals(comment.text) : comment.text != null) return false;
        if (date != null ? !date.equals(comment.date) : comment.date != null) return false;
        if (reviewerName != null ? !reviewerName.equals(comment.reviewerName) : comment.reviewerName != null)
            return false;
        return reviewerPhoto != null ? reviewerPhoto.equals(comment.reviewerPhoto) : comment.reviewerPhoto == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + userId;
        result = 31 * result + reviewerId;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (int) mark;
        result = 31 * result + (reviewerName != null ? reviewerName.hashCode() : 0);
        result = 31 * result + (reviewerPhoto != null ? reviewerPhoto.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "text='" + text + '\'' +
                ", userId=" + userId +
                ", reviewerId=" + reviewerId +
                ", date=" + date +
                ", mark=" + mark +
                ", reviewerName='" + reviewerName + '\'' +
                ", reviewerPhoto='" + reviewerPhoto + '\'' +
                "} " + super.toString();
    }
}
