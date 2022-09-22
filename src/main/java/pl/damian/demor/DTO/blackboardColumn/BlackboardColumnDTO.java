package pl.damian.demor.DTO.blackboardColumn;

import lombok.*;

import java.util.UUID;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlackboardColumnDTO {

    private UUID uuid;

    private String name;

    private String color;

    private Integer position;

}
