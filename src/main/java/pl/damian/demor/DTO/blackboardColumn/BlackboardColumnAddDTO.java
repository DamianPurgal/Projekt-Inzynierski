package pl.damian.demor.DTO.blackboardColumn;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlackboardColumnAddDTO {

    private String name;

    private String color;

}
