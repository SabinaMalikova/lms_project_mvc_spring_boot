package peaksoft.lms_spring_boot.service;

import peaksoft.lms_spring_boot.entity.Student;

import java.util.List;

public interface StudentService {
    void saveStudent(Student Student);
    Student getById(Long id);
    List<Student> getAllStudents();
    List<Student> getAllStudentsByGroupId(Long groupId);
    void  updateById(Long id, Student newStudent);
    void deleteStudentById(Long id);
    void deleteStudentFromGroup(Long studentId, Long groupId);
    void assignStudentToGroup(Long studentId, Long groupId);
}
