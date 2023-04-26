package com.training.rledenev.dao.h2;

import com.training.rledenev.dao.FeedbackDao;
import com.training.rledenev.model.Feedback;
import org.springframework.stereotype.Repository;

@Repository
public class FeedbackDaoH2 extends CrudDaoH2<Feedback> implements FeedbackDao {

    public FeedbackDaoH2() {
        setClazz(Feedback.class);
    }
}
