package peaksoft.lms_spring_boot.service.serviceImpl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.lms_spring_boot.entity.Task;
import peaksoft.lms_spring_boot.exceptions.MyException;
import peaksoft.lms_spring_boot.repository.TaskRepo;
import peaksoft.lms_spring_boot.service.TaskService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepo taskRepo;

    @Override
    public void saveTask(Task task) {
        taskRepo.save(task);
    }

    @Override
    public Task getTaskById(Long taskId) {
        Task task = null;
        try {
            task = taskRepo.findById(taskId).orElseThrow(
                    () -> new MyException("task not found!"));

        }catch (MyException e){
            System.out.println(e.getMessage());
        }
        return task;
    }

    @Override
    public List<Task> getAllTasksByLessonId(Long lessonId) {
        return taskRepo.getAllTasksByLessonId(lessonId);
    }

    @Override
    public void updateTaskById(Long id, Task newTask) {
        Task oldTask = getTaskById(id);
        oldTask.setTaskName(newTask.getTaskName());
        oldTask.setTaskText(newTask.getTaskText());
        oldTask.setDeadLine(newTask.getDeadLine());
        taskRepo.save(oldTask);
    }

    @Override
    public void deleteTaskById(Long taskId) {
        Task task = getTaskById(taskId);
        task.setLesson(null);
        taskRepo.delete(task);
    }
}
