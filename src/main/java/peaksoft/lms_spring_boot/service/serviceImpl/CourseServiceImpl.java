package peaksoft.lms_spring_boot.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.lms_spring_boot.entity.Company;
import peaksoft.lms_spring_boot.entity.Course;
import peaksoft.lms_spring_boot.entity.Group;
import peaksoft.lms_spring_boot.entity.Instructor;
import peaksoft.lms_spring_boot.exceptions.MyException;
import peaksoft.lms_spring_boot.repository.CourseRepo;
import peaksoft.lms_spring_boot.service.CourseService;


import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepo courseRepo;

    @Override
    public void saveCourse(Course course) {
        courseRepo.save(course);
    }

    @Override
    public Course getById(Long id) {
        Course course = null;
        try {
            course = courseRepo.findById(id).orElseThrow(
                    () -> new MyException("course not found!")
            );
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
        return course;
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepo.findAll();
    }

    @Override
    public List<Course> getAllCoursesByCompanyId(Long id) {
        return courseRepo.getAllCoursesByCompanyId(id);
    }

    @Override
    public void updateById(Long id, Course newCourse) {
        Course oldCourse = getById(id);
        oldCourse.setCourseName(newCourse.getCourseName());
        oldCourse.setDateOfStart(newCourse.getDateOfStart());
        oldCourse.setDescription(newCourse.getDescription());
        courseRepo.save(oldCourse);
    }


    @Override
    public void deleteCourseById(Long id) {
        Course course = getById(id);
        if (course != null) {
            for (Instructor instructor : course.getInstructors()) {
                instructor.setCourse(null);
                for (Company company : instructor.getCompanies()) {
                    company.getInstructors().remove(instructor);
                }
                instructor.getCompanies().clear();
            }
            for (Group group : course.getGroups()) {
                group.getCourses().remove(course);
            }
            course.getGroups().clear();
            course.getInstructors().clear();
            courseRepo.delete(course);
        }
    }

    @Override
    public List<Course> getCoursesByIds(List<Long> coursesIds) {
        return courseRepo.findAllByIdIn(coursesIds);
    }
}
