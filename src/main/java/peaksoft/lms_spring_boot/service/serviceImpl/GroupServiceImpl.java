package peaksoft.lms_spring_boot.service.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import peaksoft.lms_spring_boot.entity.Course;
import peaksoft.lms_spring_boot.entity.Group;
import peaksoft.lms_spring_boot.entity.Student;
import peaksoft.lms_spring_boot.exceptions.MyException;
import peaksoft.lms_spring_boot.repository.CompanyRepo;
import peaksoft.lms_spring_boot.repository.CourseRepo;
import peaksoft.lms_spring_boot.repository.GroupRepo;
import peaksoft.lms_spring_boot.service.GroupService;


import java.util.List;

@Controller
@Transactional
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepo groupRepo;
    private final CourseRepo courseRepo;

    @Override
    public void saveGroup(Group group) {
        groupRepo.save(group);
    }

    @Override
    public Group getById(Long id) {
        Group group = null;
        try {
            group = groupRepo.findById(id).orElseThrow(
                    () -> new MyException("group not found!"));
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
        return group;
    }

    @Override
    public List<Group> getAllGroupsByCourseId(Long id) {
        return groupRepo.getAllGroupsByCourseId(id);
    }

    @Override
    public List<Group> getAllGroupsByCompanyId(Long id) {
        return groupRepo.getAllGroupsByCompanyId(id);
    }

    @Override
    public void updateById(Long id, Group newGroup) {
        Group oldGroup = getById(id);
        oldGroup.setGroupName(newGroup.getGroupName());
        oldGroup.setImageLink(newGroup.getImageLink());
        oldGroup.setDescription(newGroup.getDescription());
        oldGroup.setCourses(newGroup.getCourses());
        groupRepo.save(oldGroup);
    }

    @Override
    public void deleteGroupById(Long id) {
        Group group = getById(id);
        for (Course course : group.getCourses()) {
            course.getGroups().remove(group);
//            courseRepo.save(course);
        }
        for (Student student : group.getStudents()) {
            student.setGroup(null);
        }
        group.getStudents().clear();
        group.setCompany(null);
        groupRepo.delete(group);
    }

}
