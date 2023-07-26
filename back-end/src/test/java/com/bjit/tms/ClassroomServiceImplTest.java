package com.bjit.tms;

import com.bjit.tms.entity.classroom_entities.ClassroomEntity;
import com.bjit.tms.entity.classroom_entities.ClassroomPostEntity;
import com.bjit.tms.entity.user_entities.TrainerEntity;
import com.bjit.tms.model.classroom_models.PostListModel;
import com.bjit.tms.model.classroom_models.PostModel;
import com.bjit.tms.repository.classroom_repositories.ClassroomRepository;
import com.bjit.tms.repository.classroom_repositories.PostRepository;
import com.bjit.tms.repository.user_repositories.TrainerRepository;
import com.bjit.tms.service.implementation.ClassroomServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

//@SpringBootTest
//@RunWith(MockitoJUnitRunner.class)
public class ClassroomServiceImplTest {

    @Mock
    private ClassroomRepository classroomRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private TrainerRepository trainerRepository;

    @InjectMocks
    private ClassroomServiceImpl classroomService;

    private final int classroomId = 1;
    private final int trainerId = 1;
    private PostModel postModel;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        postModel = new PostModel();
        postModel.setMessage("Test post message");
    }

    @Test
    public void testCreatePost_Success() {
        // Mocking TrainerEntity
        TrainerEntity trainerEntity = new TrainerEntity();
        trainerEntity.setTrainerId(trainerId);

        when(trainerRepository.findById(trainerId)).thenReturn(Optional.of(trainerEntity));

        // Mocking ClassroomEntity
        ClassroomEntity classroomEntity = new ClassroomEntity();
        classroomEntity.setClassroomId(classroomId);

        when(classroomRepository.findById(classroomId)).thenReturn(Optional.of(classroomEntity));

        ResponseEntity<Object> response = classroomService.createPost(classroomId, trainerId, postModel);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(postRepository, times(1)).save(any());
    }

    @Test
    public void testCreatePost_TrainerNotFound() {
        when(trainerRepository.findById(trainerId)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = classroomService.createPost(classroomId, trainerId, postModel);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Trainer not found", response.getBody());
        verify(postRepository, never()).save(any());
    }

    @Test
    public void testCreatePost_ClassroomNotFound() {
        // Mocking TrainerEntity
        TrainerEntity trainerEntity = new TrainerEntity();
        trainerEntity.setTrainerId(trainerId);

        when(trainerRepository.findById(trainerId)).thenReturn(Optional.of(trainerEntity));
        when(classroomRepository.findById(classroomId)).thenReturn(Optional.empty());

        ResponseEntity<Object> response = classroomService.createPost(classroomId, trainerId, postModel);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Classroom not found", response.getBody());
        verify(postRepository, never()).save(any());
    }

    @Test
    public void testPostList_Success() {
        // Mocking ClassroomEntity
        ClassroomEntity classroomEntity = new ClassroomEntity();
        classroomEntity.setClassroomId(classroomId);

        when(classroomRepository.findById(classroomId)).thenReturn(Optional.of(classroomEntity));

        // Mocking ClassroomPostEntity list
        List<ClassroomPostEntity> postList = new ArrayList<>();
        ClassroomPostEntity post1 = new ClassroomPostEntity();
        post1.setMessage("Post 1 message");
        post1.setDate(Date.valueOf(LocalDate.now()));
        TrainerEntity trainer1 = new TrainerEntity();
        trainer1.setName("Trainer 1");
        post1.setTrainerEntity(trainer1);
        postList.add(post1);

        ClassroomPostEntity post2 = new ClassroomPostEntity();
        post2.setMessage("Post 2 message");
        post2.setDate(Date.valueOf(LocalDate.now().minusDays(1)));
        TrainerEntity trainer2 = new TrainerEntity();
        trainer2.setName("Trainer 2");
        post2.setTrainerEntity(trainer2);
        postList.add(post2);

        when(postRepository.findByClassroomEntity(classroomEntity)).thenReturn(postList);

        ResponseEntity<List<PostListModel>> response = classroomService.postList(classroomId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<PostListModel> postListModels = response.getBody();
        assertEquals(2, postListModels.size());
        assertEquals("Trainer 1", postListModels.get(0).getTrainerName());
        assertEquals("Post 1 message", postListModels.get(0).getMessage());
        assertEquals(Date.valueOf(LocalDate.now()), postListModels.get(0).getDate());
        assertEquals("Trainer 2", postListModels.get(1).getTrainerName());
        assertEquals("Post 2 message", postListModels.get(1).getMessage());
        assertEquals(Date.valueOf(LocalDate.now().minusDays(1)), postListModels.get(1).getDate());
    }

    @Test
    public void testPostList_ClassroomNotFound() {
        when(classroomRepository.findById(classroomId)).thenReturn(Optional.empty());

        ResponseEntity<List<PostListModel>> response = classroomService.postList(classroomId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(postRepository, never()).findByClassroomEntity(any());
    }
}

