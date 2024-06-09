package peaksoft.lms_spring_boot.service;

import peaksoft.lms_spring_boot.entity.Lesson;
import peaksoft.lms_spring_boot.entity.Task;
import peaksoft.lms_spring_boot.repository.TaskRepo;

import java.util.List;

public interface TaskService {
    void saveTask(Task task);
    Task getTaskById(Long taskId);
    List<Task> getAllTasksByLessonId(Long lessonId);
    void updateTaskById(Long id, Task newTask);
    void deleteTaskById(Long taskId);
}
