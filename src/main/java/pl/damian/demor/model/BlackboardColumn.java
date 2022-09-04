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
@Table(name="BlackboardColumns")
public class BlackboardColumn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "blackboard_id", nullable = false)
    private Blackboard blackboard;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "position", nullable = false)
    private Integer position;

    @OneToMany(mappedBy = "column")
    private Set<Ticket> tickets = new LinkedHashSet<>();

}
