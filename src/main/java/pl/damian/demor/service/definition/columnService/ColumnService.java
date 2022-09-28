package pl.damian.demor.service.definition.columnService;

import pl.damian.demor.DTO.blackboardColumn.BlackboardColumnAddDTO;
import pl.damian.demor.DTO.blackboardColumn.BlackboardColumnDTO;
import pl.damian.demor.DTO.blackboardColumn.BlackboardColumnEditDTO;
import pl.damian.demor.service.definition.columnService.model.ColumnPath;

import java.util.List;
import java.util.UUID;

public interface ColumnService {

    BlackboardColumnDTO addColumnToBlackboard(String ownerUsername, BlackboardColumnAddDTO addColumnDTO, UUID blackboardUUID);

    BlackboardColumnDTO editColumn(String ownerUsername, ColumnPath columnPath, BlackboardColumnEditDTO editBlackboardColumnDTO);

    void deleteColumn(String ownerUsername, ColumnPath columnPath);

    List<BlackboardColumnDTO> getAllColumnsOfBlackboard(String ownerUsername, UUID blackboardUUID);

    BlackboardColumnDTO changeColumnPosition(String ownerUsername, ColumnPath columnPath, Integer newPosition);

}
