package com.training.rledenev.dao.h2;

import com.training.rledenev.dao.AttachmentDao;
import com.training.rledenev.model.Attachment;
import org.springframework.stereotype.Repository;

@Repository
public class AttachmentDaoH2 extends CrudDaoH2<Attachment> implements AttachmentDao {

    public AttachmentDaoH2() {
        setClazz(Attachment.class);
    }
}
