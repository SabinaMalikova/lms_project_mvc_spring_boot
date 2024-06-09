package peaksoft.lms_spring_boot.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.lms_spring_boot.entity.Company;
import peaksoft.lms_spring_boot.entity.Course;
import peaksoft.lms_spring_boot.entity.Instructor;
import peaksoft.lms_spring_boot.exceptions.MyException;
import peaksoft.lms_spring_boot.repository.CompanyRepo;
import peaksoft.lms_spring_boot.repository.CourseRepo;
import peaksoft.lms_spring_boot.repository.InstructorRepo;
import peaksoft.lms_spring_boot.service.InstructorService;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepo instructorRepo;
    private final CourseRepo courseRepo;
    private final CompanyRepo companyRepo;

    @Override
    public void saveInstructor(Instructor instructor) {
        instructorRepo.save(instructor);
    }

    @Override
    public Instructor getById(Long id) {
        Instructor instructor = null;
        try {
            instructor = instructorRepo.findById(id).orElseThrow(
                    () -> new MyException("instructor not found!")
            );
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
        return instructor;
    }

    @Override
    public List<Instructor> getAllInstructors() {
        return instructorRepo.findAll();
    }

    @Override
    public List<Instructor> getAllInstructorsByCompanyId(Long id) {
        return instructorRepo.getAllInstructorsByCompanyId(id);
    }

    @Override
    public List<Instructor> getAllInstructorsByCourseId(Long id) {
        return instructorRepo.getAllInstructorsByCourseId(id);
    }

    @Override
    public void updateById(Long id, Instructor newInstructor) {
        Instructor oldInstructor = getById(id);
        oldInstructor.setFirstName(newInstructor.getFirstName());
        oldInstructor.setLastName(newInstructor.getLastName());
        oldInstructor.setPhoneNumber(newInstructor.getPhoneNumber());
        oldInstructor.setSpecialization(newInstructor.getSpecialization());
        instructorRepo.save(oldInstructor);
    }

    @Override
    public void deleteInstructorById(Long id) {
        Instructor instructor = getById(id);
        for (Company company : instructor.getCompanies()) {
            company.getInstructors().remove(instructor);
        }
        instructor.getCompanies().clear();
        instructorRepo.delete(instructor);
    }

    @Override
    public void deleteInstructorFromCourse(Long instructorId, Long courseId) {
        try {
            Instructor instructor = getById(instructorId);
            Course course = courseRepo.findById(courseId).orElseThrow(
                    () -> new MyException("course not found!"));
            Company company = course.getCompany();
            company.getInstructors().remove(instructor);
            instructor.getCompanies().remove(company);
            instructor.setCourse(null);
            course.getInstructors().remove(instructor);
            instructorRepo.save(instructor);
            courseRepo.save(course);
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void assignInstructorToCourse(Long courseId, Long instructorId) {
        try {
            Instructor instructor = getById(instructorId);
            Course course = courseRepo.findById(courseId).orElseThrow(
                    () -> new MyException("course not found!"));

            instructor.setCourse(course);
            course.getInstructors().add(instructor);

            Company company = course.getCompany();
            instructor.getCompanies().add(company);
            if (!company.getInstructors().contains(instructor)) {
                company.getInstructors().add(instructor);
                companyRepo.save(company);
            }
            instructorRepo.save(instructor);
            courseRepo.save(course);
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
    }
}
