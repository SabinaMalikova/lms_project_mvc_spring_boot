package peaksoft.lms_spring_boot.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.lms_spring_boot.entity.Instructor;
import peaksoft.lms_spring_boot.service.CompanyService;
import peaksoft.lms_spring_boot.service.InstructorService;


@Controller
@RequestMapping("/instructors")
@RequiredArgsConstructor
public class InstructorApi {

    private final InstructorService instructorService;
    private final CompanyService companyService;

    @GetMapping
    public String getAllInstructors(Model model){
        model.addAttribute("allInstructors", instructorService.getAllInstructors());
        return "allInstructors";
    }


    @GetMapping("/{id}/getInstructorsByCourseId")
    public String getAllInstructorsByCourseId(Model model, @PathVariable("id") Long courseId) {
        model.addAttribute("courseInstructors", instructorService.getAllInstructorsByCourseId(courseId));
        return "allCoursesInstructors";
    }


    @GetMapping("/{id}/getInstructorsByCompanyId")
    public String getAllInstructorsByCompanyId(Model model, @PathVariable("id") Long companyId) {
        model.addAttribute("companyInstructors", instructorService.getAllInstructorsByCompanyId(companyId));
        return "allCompaniesInstructors";
    }


    @GetMapping("/new")
    public String addInstructor(Model model) {
        model.addAttribute("newInstructor", new Instructor());
        return "/newInstructor";
    }


    @PostMapping("/save")
    private String saveInstructor(@ModelAttribute("newInstructor") Instructor instructor) {
        instructorService.saveInstructor(instructor);
        return "redirect:/instructors";
    }


    @GetMapping("/{id}/delete")
    public String deleteInstructor(@PathVariable("id") Long instructorId){
        instructorService.deleteInstructorById(instructorId);
        return "redirect:/instructors";
    }


    @PostMapping("/{instructorId}/deleteFromCourse/{courseId}")
    public String deleteInstructorFromCourse(@PathVariable Long instructorId, @PathVariable Long courseId) {
        instructorService.deleteInstructorFromCourse(instructorId, courseId);
        return "redirect:/courses/{id}/getInstructorsByCompanyId";
    }


    @GetMapping("/{id}/update")
    public String getByIdToUpdate(Model model, @PathVariable("id") Long instructorId) {
        model.addAttribute("upInstructor", instructorService.getById(instructorId));
        return "updateInstructor";
    }

    @PostMapping("/{id}/updateInstructor")
    public String update(@ModelAttribute("upInstructor") Instructor instructor, @PathVariable Long id) {
        instructorService.updateById(id, instructor);
        return "redirect:/instructors";
    }

    @GetMapping("/assignToCourse/{courseId}")
    public String showAssignInstructorForm(@PathVariable Long courseId, Model model) {
        model.addAttribute("courseId", courseId);
        model.addAttribute("instructors", instructorService.getAllInstructors());
//        model.addAttribute("companies", companyService.getAllCompany());
        return "assignInstructorForm";
    }


    @PostMapping("/assignToCourse/{courseId}")
    public String assignInstructorToCourse(@PathVariable Long courseId, @RequestParam Long instructorId) {
        instructorService.assignInstructorToCourse(courseId, instructorId);
        return "redirect:/courses/{courseId}/getInstructorsByCompanyId";
    }


}





