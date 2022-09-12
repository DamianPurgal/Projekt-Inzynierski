package pl.damian.demor.model;

import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="blackboards")
public class Blackboard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name = "link_id", nullable = false)
    private String linkId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "color", nullable = false)
    private String color;

    @OneToMany(mappedBy = "blackboard", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<BlackboardColumn> columns = new LinkedHashSet<>();

    @OneToMany(mappedBy = "blackboard", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BlackboardContributor> contributors = new LinkedHashSet<>();

}
