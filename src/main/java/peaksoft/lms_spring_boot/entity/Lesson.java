package peaksoft.lms_spring_boot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "lessons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lesson {
    @Id
    @GeneratedValue(generator = "l_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "l_gen", sequenceName = "l_seq", allocationSize = 1)
    private Long id;
    private String lessonName;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private Course course;

    @OneToMany(mappedBy = "lesson",cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    private List<Task>tasks;
}
