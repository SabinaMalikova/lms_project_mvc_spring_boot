package peaksoft.lms_spring_boot.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.lms_spring_boot.entity.Student;
import peaksoft.lms_spring_boot.service.StudentService;


@Controller
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentApi {

    private final StudentService studentService;

    @GetMapping
    public String getAllStudents(Model model){
        model.addAttribute("allStudents", studentService.getAllStudents());
        return "allStudents";
    }


    @GetMapping("/{id}/getStudentsByGroupId")
    public String getAllStudentsByGroupId(Model model, @PathVariable("id") Long groupId){
        model.addAttribute("companyId", groupId);
        model.addAttribute("groupStudents", studentService.getAllStudentsByGroupId(groupId));
        return "allGroupsStudents";
    }


    @GetMapping("/new")
    public String addStudent(Model model) {
        model.addAttribute("newStudent", new Student());
        return "/newStudent";
    }


    @PostMapping("/save")
    private String saveStudent(@ModelAttribute("newStudent") Student Student) {
        studentService.saveStudent(Student);
        return "redirect:/students";
    }


    @GetMapping("/{id}/delete")
    public String deleteStudent(@PathVariable("id") Long studentId) {
        studentService.deleteStudentById(studentId);
        return "redirect:/students";
    }


    @PostMapping("/{studentId}/deleteFromGroup/{groupId}")
    public String deleteStudentFromGroup(@PathVariable Long studentId, @PathVariable Long groupId){
        studentService.deleteStudentFromGroup(studentId, groupId);
        return "redirect:/students/{id}/getStudentsByGroupId";
    }


    @GetMapping("/{id}/update")
    public String showUpdateForm(@PathVariable("id") Long studentId, Model model) {
        Student student = studentService.getById(studentId);
        model.addAttribute("upStudent", student);
        return "updateStudent";
    }


    @PostMapping("/{id}/updateStudent")
    public String update(@ModelAttribute("student") Student student, @PathVariable Long id) {
        studentService.updateById(id, student);
        return "redirect:/students";
    }


    @GetMapping("/assignToGroup/{groupId}")
    public String showAssignStudentForm(@PathVariable Long groupId, Model model){
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("groupId", groupId);
        return "assignStudentForm";
    }


    @PostMapping("/assignToGroup/{groupId}")
    public String assignStudentToGroup(@PathVariable Long groupId, @RequestParam Long studentId) {
        studentService.assignStudentToGroup(studentId, groupId);
        return "redirect:/groups/{id}/getStudentsByGroupId";
    }

}
