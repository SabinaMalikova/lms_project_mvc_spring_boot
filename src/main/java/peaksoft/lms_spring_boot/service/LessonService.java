package peaksoft.lms_spring_boot.service;

import peaksoft.lms_spring_boot.entity.Group;
import peaksoft.lms_spring_boot.entity.Lesson;

import java.util.List;

public interface LessonService {
    void saveLesson(Lesson lesson);
    Lesson getLessonById(Long id);
    List<Lesson> getAllLessonsByCourseId(Long id);
    void updateLessonById(Long id, Lesson newLesson);
    void deleteLessonById(Long id);
}
