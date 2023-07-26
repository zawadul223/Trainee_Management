package com.bjit.tms;

import com.bjit.tms.entity.batch_entities.BatchEntity;
import com.bjit.tms.entity.user_entities.TraineeEntity;
import com.bjit.tms.model.batch_models.BatchCreateModel;
import com.bjit.tms.repository.batch_repositories.BatchRepository;
import com.bjit.tms.repository.user_repositories.TraineeRepository;
import com.bjit.tms.service.implementation.BatchServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

    @Nested
    @DisplayName("batchCreate() method")
    class BatchCreateTests {
        @Test
        @DisplayName("When batch is created, should return success response with batch ID")
        public void testBatchCreate_Success() {
            // Arrange
            BatchEntity savedBatch = BatchEntity.builder()
                    .batchId(1)
                    .batchName("Test Batch")
                    .startDate(Date.valueOf("2023-07-01"))
                    .endDate(Date.valueOf("2023-12-31"))
                    .build();

            when(batchRepository.save(any())).thenReturn(savedBatch);

            // Act
            ResponseEntity<Object> response = batchService.batchCreate(batchCreateModel);

            // Assert
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isEqualTo(Map.of("success", true, "id", 1));
            verify(batchRepository, times(1)).save(any());
        }
    }

    @Nested
    @DisplayName("assignTrainee() method")
    class AssignTraineeTests {
        @Test
        @DisplayName("When batch exists and trainees are assigned, should return success response")
        public void testAssignTrainee_Success() {
            // Arrange
            int batchId = 1;
            List<Integer> traineeIds = List.of(1, 2);

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
            when(traineeRepository.findById(1)).thenReturn(Optional.of(trainee1));
            when(traineeRepository.findById(2)).thenReturn(Optional.of(trainee2));

            // Act
            ResponseEntity<Object> response = batchService.assignTrainee(batchId, traineeIds);

            // Assert
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isEqualTo("Assigned Successfully");

            assertThat(batchEntity.getTraineeEntityList()).containsExactlyInAnyOrder(trainee1, trainee2);
            assertThat(trainee1.isAssignedBatch()).isTrue();
            assertThat(trainee2.isAssignedBatch()).isTrue();

            verify(batchRepository, times(1)).findById(batchId);
            verify(traineeRepository, times(1)).findById(1);
            verify(traineeRepository, times(1)).findById(2);
            verify(batchRepository, times(1)).save(batchEntity);
        }

        @Test
        @DisplayName("When batch does not exist, should return not found response")
        public void testAssignTrainee_BatchNotFound() {
            // Arrange
            int batchId = 1;
            List<Integer> traineeIds = List.of(1, 2);

            when(batchRepository.findById(batchId)).thenReturn(Optional.empty());

            // Act
            ResponseEntity<Object> response = batchService.assignTrainee(batchId, traineeIds);

            // Assert
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            assertThat(response.getBody()).isNull();

            verify(batchRepository, times(1)).findById(batchId);
            verify(traineeRepository, never()).findById(any());
            verify(batchRepository, never()).save(any());
        }
    }
}
