package com.bjit.tms.repository;

import com.bjit.tms.entity.BatchEntity;
import com.bjit.tms.entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeEntity, Integer> {
    List<NoticeEntity> findNoticeEntitiesByBatchEntity(BatchEntity batchEntity);
}
