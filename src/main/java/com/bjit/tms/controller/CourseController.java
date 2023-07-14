package com.bjit.tms.controller;

import com.bjit.tms.model.CourseCreateModel;
import com.bjit.tms.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;

    @PostMapping("/create")
    public ResponseEntity<Object> courseCreate(@RequestBody CourseCreateModel courseCreateModel){
        return courseService.createCourse(courseCreateModel);
    }

    @PostMapping("/assign/batch")
    public ResponseEntity<Object> courseAssign(@RequestBody Map<Integer, List<Integer>> batchCourseMap){
        return courseService.assignCoursetoBatch(batchCourseMap);
    }

    @PostMapping("/assign/trainer/{trainerId}")
    public ResponseEntity<Object> trainerAssign(@PathVariable Integer trainerId, @RequestBody List<Integer> courseList){
        return courseService.assignCoursetoTrainer(trainerId, courseList);
    }
}
