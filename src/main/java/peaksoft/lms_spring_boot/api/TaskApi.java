package peaksoft.lms_spring_boot.api;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.lms_spring_boot.entity.Course;
import peaksoft.lms_spring_boot.entity.Lesson;
import peaksoft.lms_spring_boot.entity.Task;
import peaksoft.lms_spring_boot.service.LessonService;
import peaksoft.lms_spring_boot.service.TaskService;

import java.util.List;

@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskApi {

    private final TaskService taskService;
    private final LessonService lessonService;

    @GetMapping("/{lessonId}/allTasks")
    public String getAllTasksByLessonId(@PathVariable Long lessonId, Model model){
        List<Task> tasks = taskService.getAllTasksByLessonId(lessonId);
        model.addAttribute("allTasks", tasks);
        return "allTasks";
    }


    @GetMapping("/newTask/{lessonId}")
    public String createTaskForm(@PathVariable Long lessonId, Model model){
        model.addAttribute("lessonId", lessonId);
        model.addAttribute("newTask", new Task());
        return "newTask";
    }


    @PostMapping("/save/{lessonId}")
    public String saveTask(@ModelAttribute("newTask") Task task, @PathVariable Long lessonId){
        Lesson lesson = lessonService.getLessonById(lessonId);
        task.setLesson(lesson);
        lesson.getTasks().add(task);
        taskService.saveTask(task);
        return "redirect:/tasks/{lessonId}";
    }


    @GetMapping("/{taskId}/delete")
    public String deleteTask(@PathVariable Long taskId){
        taskService.deleteTaskById(taskId);
        return "redirect:/tasks/{lessonId}";
    }


    @GetMapping("/{taskId}/update")
    public String getByIdToUpdate(Model model, @PathVariable Long taskId){
        model.addAttribute("upTask", taskService.getTaskById(taskId));
        return "updateTask";
    }


    @PostMapping("/{taskId}/updateTask")
    public String update(@ModelAttribute("upTask") Task task, @PathVariable Long taskId){
        taskService.updateTaskById(taskId, task);
        return "redirect:/tasks/{lessonId}";
    }


}
