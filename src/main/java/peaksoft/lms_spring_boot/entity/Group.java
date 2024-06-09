package peaksoft.lms_spring_boot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "groups")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Group {
    @Id
    @GeneratedValue(generator = "g_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "g_gen", sequenceName = "g_seq", allocationSize = 1)
    private Long id;
    private String groupName;
    private String imageLink;
    private String description;


    @ManyToMany(mappedBy = "groups",cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<Course>courses;

    @OneToMany(mappedBy = "group", cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<Student>students;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private Company company;
}
