package peaksoft.lms_spring_boot.service;


import peaksoft.lms_spring_boot.entity.Course;

import java.util.List;

public interface CourseService {
    void saveCourse(Course course);
    Course getById(Long id);
    List<Course> getAllCourses();
    List<Course> getAllCoursesByCompanyId(Long id);
    void  updateById(Long id, Course newCourse);
    void deleteCourseById(Long id);
    List<Course> getCoursesByIds(List<Long> coursesIds);
}
