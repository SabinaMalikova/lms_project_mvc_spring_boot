package peaksoft.lms_spring_boot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "companies")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Company {
    @Id
    @GeneratedValue(generator = "com_gen", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "com_gen", sequenceName = "com_seq", allocationSize = 1)
    private Long id;
    private String companyName;
    private String country;
    private String address;
    private String phoneNumber;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    private List<Instructor>instructors;

    @OneToMany(mappedBy = "company",cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    private List<Course>courses;

    @OneToMany(mappedBy = "company", cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE})
    private List<Group> groups;
}
