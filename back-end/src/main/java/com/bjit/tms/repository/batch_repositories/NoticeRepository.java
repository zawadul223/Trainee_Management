package com.bjit.tms.repository.batch_repositories;

import com.bjit.tms.entity.batch_entities.BatchEntity;
import com.bjit.tms.entity.batch_entities.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeEntity, Integer> {
    List<NoticeEntity> findNoticeEntitiesByBatchEntity(BatchEntity batchEntity);
}
