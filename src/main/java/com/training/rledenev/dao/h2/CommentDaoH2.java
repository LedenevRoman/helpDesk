package com.training.rledenev.dao.h2;

import com.training.rledenev.dao.CommentDao;
import com.training.rledenev.model.Comment;
import org.springframework.stereotype.Repository;

@Repository
public class CommentDaoH2 extends CrudDaoH2<Comment> implements CommentDao {

    public CommentDaoH2() {
        setClazz(Comment.class);
    }
}
