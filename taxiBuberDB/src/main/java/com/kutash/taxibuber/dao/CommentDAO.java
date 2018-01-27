package com.kutash.taxibuber.dao;

import com.kutash.taxibuber.entity.Comment;
import com.kutash.taxibuber.exception.DAOException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommentDAO extends AbstractDAO<Comment> {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final String FIND_COMMENT_BY_ID = "SELECT id_comment,text,id_user,date,id_reviewer,mark FROM comment WHERE id_comment = ?";
    private static final String FIND_COMMENT_BY_USER_ID = "SELECT c.id_comment,c.id_user,c.text,c.date,c.id_reviewer,c.mark,u.name,u.photo_path\n" +
            "FROM comment AS c INNER JOIN user AS u ON c.id_reviewer = u.id_user WHERE c.id_user = ?";
    private static final String DELETE_COMMENT_BY_USER_ID = "DELETE FROM comment WHERE id_user = ?";
    private static final String DELETE_COMMENT_BY_ID = "DELETE FROM comment WHERE id_comment = ?";
    private static final String CREATE_COMMENT = "INSERT INTO comment (text,id_user,date,id_reviewer,mark) VALUES (?,?,?,?,?)";
    private static final String UPDATE_COMMENT = "UPDATE comment  SET text=?,id_user=?,date=?,id_reviewer=?,mark=? WHERE id_comment=?";


    @Override
    public List<Comment> findAll() throws DAOException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Comment findEntityById(int id) throws DAOException {
        LOGGER.log(Level.INFO,"finding comment by id {}",id);
        Comment comment = null;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getPreparedStatement(FIND_COMMENT_BY_ID);
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                comment = getComment(resultSet);
            }
        }catch (SQLException e){
            throw new DAOException("Exception while finding comment by id {}",e);
        }finally {
            close(preparedStatement);
        }
        return comment;
    }

    public List<Comment> findEntityByUserId(int userId) throws DAOException {
        LOGGER.log(Level.INFO,"finding address by user id {}",userId);
        List<Comment> comments = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getPreparedStatement(FIND_COMMENT_BY_USER_ID);
            preparedStatement.setInt(1,userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Comment comment = getComment(resultSet);
                comments.add(comment);
            }
        }catch (SQLException e){
            throw new DAOException("Exception while finding comments by user id {}",e);
        }finally {
            close(preparedStatement);
        }
        return comments;
    }

    @Override
    public int delete(int id) throws DAOException {
        LOGGER.log(Level.INFO,"deleting comment by id {}",id);
        int result;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getPreparedStatement(DELETE_COMMENT_BY_ID);
            preparedStatement.setInt(1,id);
            result = preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new DAOException("Exception while deleting comment by id {}",e);
        }finally {
            close(preparedStatement);
        }
        return result;
    }

    public int deleteByUserId(int userId) throws DAOException {
        LOGGER.log(Level.INFO,"deleting comment where id user {}",userId);
        int result;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getPreparedStatement(DELETE_COMMENT_BY_USER_ID);
            preparedStatement.setInt(1,userId);
            result = preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new DAOException("Exception while deleting comment by user id {}",e);
        }finally {
            close(preparedStatement);
        }
        return result;
    }

    @Override
    public int create(Comment entity) throws DAOException {
        LOGGER.log(Level.INFO,"creating comment");
        int result;
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getPreparedStatement(CREATE_COMMENT);
            preparedStatement = setValues(preparedStatement,entity);
            result = preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new DAOException("Exception while creating comment {}",e);
        }finally {
            close(preparedStatement);
        }
        return result;
    }

    @Override
    public Comment update(Comment entity) throws DAOException {
        LOGGER.log(Level.INFO,"updating comment");
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = getPreparedStatement(UPDATE_COMMENT);
            preparedStatement = setValues(preparedStatement,entity);
            preparedStatement.setInt(6,entity.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            throw new DAOException("Exception while updating comment {}",e);
        }finally {
            close(preparedStatement);
        }
        return findEntityById(entity.getId());
    }

    private Comment getComment(ResultSet resultSet) throws DAOException {
        Comment comment;
        try {
            int idComment = resultSet.getInt("id_comment");
            String text = resultSet.getString("text");
            int reviewerId = resultSet.getInt("id_reviewer");
            int userId = resultSet.getInt("id_user");
            Date date = resultSet.getDate("date");
            byte mark = resultSet.getByte("mark");
            String reviewerName = resultSet.getString("name");
            String reviewerPhoto = resultSet.getString("photo_path");
            comment = new Comment(idComment,text,userId,reviewerId,date,mark,reviewerName,reviewerPhoto);
        }catch (SQLException e){
            throw new DAOException("Exception while getting comment from resultSet",e);
        }
        return comment;
    }

    private PreparedStatement setValues(PreparedStatement preparedStatement, Comment entity) throws DAOException {
        try {
            preparedStatement.setString(1, entity.getText());
            preparedStatement.setInt(2, entity.getUserId());
            preparedStatement.setDate(3, new java.sql.Date(entity.getDate().getTime()));
            preparedStatement.setInt(4, entity.getReviewerId());
            preparedStatement.setByte(5, entity.getMark());
        }catch (SQLException e){
            throw new DAOException("Exception while set values to prepareStatement {}",e);
        }
        return preparedStatement;
    }
}
