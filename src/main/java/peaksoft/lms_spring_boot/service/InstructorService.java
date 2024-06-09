package peaksoft.lms_spring_boot.service;


import peaksoft.lms_spring_boot.entity.Instructor;
import peaksoft.lms_spring_boot.exceptions.MyException;

import java.util.List;

public interface InstructorService {
    void saveInstructor(Instructor instructor);
    Instructor getById(Long id);
    List<Instructor> getAllInstructors();
    List<Instructor> getAllInstructorsByCompanyId(Long id);
    List<Instructor> getAllInstructorsByCourseId(Long id);
    void  updateById(Long id, Instructor newInstructor);
    void deleteInstructorById(Long id);
    void deleteInstructorFromCourse(Long instructorId, Long courseId);
    void assignInstructorToCourse(Long courseId, Long instructorId);


}
