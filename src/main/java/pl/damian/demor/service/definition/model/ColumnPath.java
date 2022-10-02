package pl.damian.demor.service.definition.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColumnPath {

    private UUID blackboardUUID;

    private UUID columnUUID;
}
