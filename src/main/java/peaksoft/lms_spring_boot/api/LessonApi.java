package peaksoft.lms_spring_boot.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.lms_spring_boot.entity.Course;
import peaksoft.lms_spring_boot.entity.Lesson;
import peaksoft.lms_spring_boot.service.CourseService;
import peaksoft.lms_spring_boot.service.LessonService;

import java.util.List;

@Controller
@RequestMapping("/lessons")
@RequiredArgsConstructor
public class LessonApi {

    private final LessonService lessonService;
    private final CourseService courseService;

    @GetMapping("/{courseId}")
    public String getAllLessonsByCourseId(@PathVariable Long courseId, Model model) {
        List<Lesson> lessons = lessonService.getAllLessonsByCourseId(courseId);
        model.addAttribute("allLessons", lessons);
        return "allLessons";
    }


    @GetMapping("/newLesson/{courseId}")
    public String createLessonForm(@PathVariable Long courseId, Model model) {
        model.addAttribute("courseId", courseId);
        model.addAttribute("newLesson", new Lesson());
        return "newLesson";
    }


    @PostMapping("/save/{courseId}")
    public String saveLesson(@ModelAttribute("newLesson") Lesson lesson, @PathVariable Long courseId) {
        Course course = courseService.getById(courseId);
        lesson.setCourse(course);
        course.getLessons().add(lesson);
        lessonService.saveLesson(lesson);
        return "redirect:/lessons/{courseId}";
    }


    @GetMapping("/{lessonId}/delete")
    public String deleteLesson(@PathVariable Long lessonId) {
        lessonService.deleteLessonById(lessonId);
        return "redirect:/lessons/{courseId}";
    }


    @GetMapping("/{lessonId}/update")
    public String getByIdToUpdate(Model model, @PathVariable Long lessonId){
        model.addAttribute("upLesson", lessonService.getLessonById(lessonId));
        return "updateLesson";
    }


    @PostMapping("/{lessonId}/updateLesson")
    public String update(@ModelAttribute("upLesson") Lesson lesson, @PathVariable Long lessonId){
        lessonService.updateLessonById(lessonId,lesson);
        return "redirect:/lessons/{courseId}";
    }

}
