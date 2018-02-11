package com.kutash.taxibuber.entity;

import java.util.Date;

/**
 * The type Comment.
 */
public class Comment extends AbstractEntity {

    private String text;
    private int userId;
    private int reviewerId;
    private Date date;
    private byte mark;
    private String reviewerName;
    private String reviewerPhoto;

    /**
     * Instantiates a new Comment.
     *
     * @param text       the text
     * @param userId     the user id
     * @param reviewerId the reviewer id
     * @param date       the date
     * @param mark       the mark
     */
    public Comment(String text, int userId, int reviewerId, Date date, byte mark) {
        this.text = text;
        this.userId = userId;
        this.reviewerId = reviewerId;
        this.date = date;
        this.mark = mark;
    }

    /**
     * Instantiates a new Comment.
     *
     * @param id            the id
     * @param text          the text
     * @param userId        the user id
     * @param reviewerId    the reviewer id
     * @param date          the date
     * @param mark          the mark
     * @param reviewerName  the reviewer name
     * @param reviewerPhoto the reviewer photo
     */
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

    /**
     * Gets text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Sets text.
     *
     * @param text the text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Gets reviewer id.
     *
     * @return the reviewer id
     */
    public int getReviewerId() {
        return reviewerId;
    }

    /**
     * Sets reviewer id.
     *
     * @param reviewerId the reviewer id
     */
    public void setReviewerId(int reviewerId) {
        this.reviewerId = reviewerId;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets mark.
     *
     * @return the mark
     */
    public byte getMark() {
        return mark;
    }

    /**
     * Sets mark.
     *
     * @param mark the mark
     */
    public void setMark(byte mark) {
        this.mark = mark;
    }

    /**
     * Gets reviewer name.
     *
     * @return the reviewer name
     */
    public String getReviewerName() {
        return reviewerName;
    }

    /**
     * Sets reviewer name.
     *
     * @param reviewerName the reviewer name
     */
    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }

    /**
     * Gets reviewer photo.
     *
     * @return the reviewer photo
     */
    public String getReviewerPhoto() {
        return reviewerPhoto;
    }

    /**
     * Sets reviewer photo.
     *
     * @param reviewerPhoto the reviewer photo
     */
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
