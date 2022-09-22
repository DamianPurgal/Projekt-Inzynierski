package pl.damian.demor.service.definition;

import pl.damian.demor.DTO.blackboardColumn.BlackboardColumnAddDTO;
import pl.damian.demor.DTO.blackboardColumn.BlackboardColumnDTO;
import pl.damian.demor.DTO.blackboardColumn.BlackboardColumnEditDTO;

import java.util.List;
import java.util.UUID;

public interface ColumnService {

    BlackboardColumnDTO addColumnToBlackboard(String ownerUsername, BlackboardColumnAddDTO addColumnDTO, UUID blackboardUUID);

    BlackboardColumnDTO editColumn(String ownerUsername, BlackboardColumnEditDTO editBlackboardColumnDTO);

    void deleteColumn(String ownerUsername, UUID columnUUID);

    List<BlackboardColumnDTO> getAllColumnsOfBlackboard(String ownerUsername, UUID blackboardUUID);

    BlackboardColumnDTO changeColumnPosition(String ownerUsername, UUID columnUUID, Integer newPosition);

}
