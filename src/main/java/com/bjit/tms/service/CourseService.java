package com.bjit.tms.service;

import com.bjit.tms.model.CourseCreateModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface CourseService {
    ResponseEntity<Object> createCourse(CourseCreateModel courseCreateModel);
    ResponseEntity<Object> assignCourse(Map<Integer, List<Integer>> batchCourseMap);
}
