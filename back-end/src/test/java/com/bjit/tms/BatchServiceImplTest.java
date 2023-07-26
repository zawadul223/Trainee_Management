package com.bjit.tms;

import com.bjit.tms.entity.BatchEntity;
import com.bjit.tms.entity.TraineeEntity;
import com.bjit.tms.model.batch_models.BatchCreateModel;
import com.bjit.tms.repository.BatchRepository;
import com.bjit.tms.repository.TraineeRepository;
import com.bjit.tms.service.implementation.BatchServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BatchServiceImplTest {

    @Mock
    private BatchRepository batchRepository;

    @Mock
    private TraineeRepository traineeRepository;

    @InjectMocks
    private BatchServiceImpl batchService;

    private BatchCreateModel batchCreateModel;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        batchCreateModel = BatchCreateModel.builder()
                .batchName("Test Batch")
                .startDate(Date.valueOf("2023-07-01"))
                .endDate(Date.valueOf("2023-12-31"))
                .build();
    }

    @Test
    public void testBatchCreate_Success() {
        BatchEntity savedBatch = BatchEntity.builder()
                .batchId(1)
                .batchName("Test Batch")
                .startDate(Date.valueOf("2023-07-01"))
                .endDate(Date.valueOf("2023-12-31"))
                .build();

        when(batchRepository.save(any())).thenReturn(savedBatch);

        ResponseEntity<Object> response = batchService.batchCreate(batchCreateModel);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(Map.of("success", true, "id", 1));
        verify(batchRepository, times(1)).save(any());
    }

    @Test
    public void testAssignTrainee_Success() {
        int batchId = 1;
        List<String> traineeNames = List.of("Trainee 1", "Trainee 2");

        BatchEntity batchEntity = BatchEntity.builder()
                .batchId(batchId)
                .batchName("Test Batch")
                .startDate(Date.valueOf("2023-07-01"))
                .endDate(Date.valueOf("2023-12-31"))
                .build();

        TraineeEntity trainee1 = TraineeEntity.builder()
                .traineeId(1)
                .name("Trainee 1")
                .build();
        TraineeEntity trainee2 = TraineeEntity.builder()
                .traineeId(2)
                .name("Trainee 2")
                .build();

        when(batchRepository.findById(batchId)).thenReturn(Optional.of(batchEntity));
        when(traineeRepository.findByName("Trainee 1")).thenReturn(trainee1);
        when(traineeRepository.findByName("Trainee 2")).thenReturn(trainee2);

        ResponseEntity<Object> response = batchService.assignTrainee(batchId, traineeNames);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Assigned Successfully");

        assertThat(batchEntity.getTraineeEntityList()).containsExactlyInAnyOrder(trainee1, trainee2);
        assertThat(trainee1.isAssignedBatch()).isTrue();
        assertThat(trainee2.isAssignedBatch()).isTrue();

        verify(batchRepository, times(1)).findById(batchId);
        verify(traineeRepository, times(1)).findByName("Trainee 1");
        verify(traineeRepository, times(1)).findByName("Trainee 2");
        verify(batchRepository, times(1)).save(batchEntity);
    }

    @Test
    public void testAssignTrainee_BatchNotFound() {
        int batchId = 1;
        List<String> traineeNames = List.of("Trainee 1", "Trainee 2");

        when(batchRepository.findById(batchId)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = batchService.assignTrainee(batchId, traineeNames);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();

        verify(batchRepository, times(1)).findById(batchId);
        verify(traineeRepository, never()).findByName(any());
        verify(batchRepository, never()).save(any());
    }
}


