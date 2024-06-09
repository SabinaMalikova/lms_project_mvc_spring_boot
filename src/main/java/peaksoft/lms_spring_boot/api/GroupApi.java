package peaksoft.lms_spring_boot.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.lms_spring_boot.entity.Company;
import peaksoft.lms_spring_boot.entity.Course;
import peaksoft.lms_spring_boot.entity.Group;
import peaksoft.lms_spring_boot.repository.CompanyRepo;
import peaksoft.lms_spring_boot.service.CompanyService;
import peaksoft.lms_spring_boot.service.CourseService;
import peaksoft.lms_spring_boot.service.GroupService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupApi {

    private final GroupService groupService;
    private final CourseService courseService;
    private final CompanyService companyService;

    @GetMapping("/{courseId}")
    public String getAllGroupsByCourseId(@PathVariable Long courseId, Model model) {
        List<Group> groups = groupService.getAllGroupsByCourseId(courseId);
        model.addAttribute("groups", groups);
        model.addAttribute("courseId", courseId);
        return "allGroupsByCourseId";
    }


    @GetMapping("/getAll/{companyId}")
    public String getAllGroupsByCompanyId(@PathVariable Long companyId, Model model) {
        List<Group> groups = groupService.getAllGroupsByCompanyId(companyId);
        model.addAttribute("companiesGroups", groups);
        model.addAttribute("companyId", companyId);
        return "allGroups";
    }


    @GetMapping("/new/{companyId}")
    public String createGroupForm(@PathVariable Long companyId, Model model) {
        List<Course> courses = courseService.getAllCoursesByCompanyId(companyId);
        model.addAttribute("newGroup", new Group());
        model.addAttribute("companyId", companyId);
        model.addAttribute("courses", courses);
        return "newGroup";
    }


    @PostMapping("/save/{companyId}")
    public String saveGroup(@ModelAttribute("newGroup") Group group, @PathVariable Long companyId, @RequestParam List<Long> courseIds) {
        Company company = companyService.getById(companyId);
        group.setCompany(company);
        List<Course> courses = courseService.getCoursesByIds(courseIds);
        group.setCourses(courses);
        for (Course course : courses) {
            course.getGroups().add(group);
        }
        groupService.saveGroup(group);
        return "redirect:/groups/getAll/{companyId}";
    }


    @GetMapping("/{groupId}/update")
    public String getByIdToUpdate(Model model, @PathVariable Long groupId) {
        Group group = groupService.getById(groupId);
        List<Course> allCourses = courseService.getAllCoursesByCompanyId(group.getCompany().getId());
        model.addAttribute("allCourses", allCourses);
        model.addAttribute("upGroup", group);
        return "updateGroup";
    }


    @PostMapping("/{groupId}/updateGroup")
    public String update(@ModelAttribute("upGroup") Group group, @PathVariable Long groupId, @RequestParam List<Long> courseIds) {
        List<Course> courses = courseService.getCoursesByIds(courseIds);
        group.setCourses(courses);
        for (Course course : group.getCourses()) {
            course.getGroups().add(group);
            courseService.saveCourse(course);
        }
        groupService.updateById(groupId, group);
        return "redirect:/groups/getAll/{companyId}";
    }


    @GetMapping("/{groupId}/delete")
    public String deleteGroup(@PathVariable Long groupId){
        groupService.deleteGroupById(groupId);
        return "redirect:/groups/getAll/{companyId}";
    }

}

