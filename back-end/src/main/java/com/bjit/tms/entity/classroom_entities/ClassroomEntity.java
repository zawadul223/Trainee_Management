package com.bjit.tms.entity.classroom_entities;

import com.bjit.tms.entity.batch_entities.BatchEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="classroom")
public class ClassroomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer classroomId;

    @OneToOne
    @JoinColumn(name = "batch_id")
    private BatchEntity batchEntity;
}
