package peaksoft.lms_spring_boot.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.lms_spring_boot.entity.Company;
import peaksoft.lms_spring_boot.entity.Group;
import peaksoft.lms_spring_boot.entity.Student;
import peaksoft.lms_spring_boot.exceptions.MyException;
import peaksoft.lms_spring_boot.repository.GroupRepo;
import peaksoft.lms_spring_boot.repository.StudentRepo;
import peaksoft.lms_spring_boot.service.StudentService;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepo studentRepo;
    private final GroupRepo groupRepo;

    @Override
    public void saveStudent(Student Student) {
        studentRepo.save(Student);
    }

    @Override
    public Student getById(Long id) {
        Student student = null;
        try {
             student = studentRepo.findById(id).orElseThrow(
                    () -> new MyException("student not found!")
            );
        }catch (MyException e){
            System.out.println(e.getMessage());
        }
        return student;
    }

    @Override
    public List<Student> getAllStudents() {
        return studentRepo.findAll();
    }

    @Override
    public List<Student> getAllStudentsByGroupId(Long groupId) {
        return studentRepo.getAllStudentsByGroupId(groupId);
    }

    @Override
    public void updateById(Long id, Student newStudent) {
        Student oldStudent = getById(id);
        oldStudent.setFirstName(newStudent.getFirstName());
        oldStudent.setLastName(newStudent.getLastName());
        oldStudent.setEmail(newStudent.getEmail());
        oldStudent.setPhoneNumber(newStudent.getPhoneNumber());
        oldStudent.setStudyFormat(newStudent.getStudyFormat());
        studentRepo.save(oldStudent);
    }

    @Override
    public void deleteStudentById(Long id) {
        studentRepo.deleteById(id);
    }

    @Override
    public void deleteStudentFromGroup(Long studentId, Long groupId) {
        try {
            Student student = getById(studentId);
            Group group = groupRepo.findById(groupId).orElseThrow(
                    () -> new MyException("group not found!"));
            student.setGroup(null);
            group.getStudents().remove(student);
        }catch (MyException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void assignStudentToGroup(Long studentId, Long groupId) {
        try {
            Student student = getById(studentId);
            Group group = groupRepo.findById(groupId).orElseThrow(
                    () -> new MyException("group not found!"));
            student.setGroup(group);
            group.getStudents().add(student);

            studentRepo.save(student);
            groupRepo.save(group);
        }catch (MyException e){
            System.out.println(e.getMessage());
        }
    }
}
