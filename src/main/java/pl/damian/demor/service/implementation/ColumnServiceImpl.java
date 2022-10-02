package pl.damian.demor.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.damian.demor.DTO.blackboardColumn.BlackboardColumnAddDTO;
import pl.damian.demor.DTO.blackboardColumn.BlackboardColumnDTO;
import pl.damian.demor.DTO.blackboardColumn.BlackboardColumnEditDTO;
import pl.damian.demor.exception.blackboard.BlackboardNotFoundException;
import pl.damian.demor.exception.blackboardColumn.BlackboardColumnNotFoundException;
import pl.damian.demor.mapper.BlackboardColumnMapper;
import pl.damian.demor.model.AppUser;
import pl.damian.demor.model.Blackboard;
import pl.damian.demor.model.BlackboardColumn;
import pl.damian.demor.model.BlackboardContributor;
import pl.damian.demor.repository.AppUserRepository;
import pl.damian.demor.repository.BlackboardColumnRepository;
import pl.damian.demor.repository.BlackboardRepository;
import pl.damian.demor.service.definition.columnService.ColumnService;
import pl.damian.demor.service.definition.columnService.model.ColumnPath;

import javax.transaction.Transactional;
import java.util.List;
import java.util.OptionalInt;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ColumnServiceImpl implements ColumnService {

    private final BlackboardColumnRepository columnRepository;

    private final AppUserRepository userRepository;

    private final BlackboardRepository blackboardRepository;

    private final BlackboardColumnMapper blackboardColumnMapper;

    @Override
    @Transactional
    public BlackboardColumnDTO addColumnToBlackboard(String ownerUsername, BlackboardColumnAddDTO addColumnDTO, UUID blackboardUUID) {

        Blackboard blackboard = findBlackboardOfUser(ownerUsername, blackboardUUID);
        BlackboardColumn column = blackboardColumnMapper.mapBlackboardColumnAddDtoBlackboardColumn(
                addColumnDTO
        );

        column.setPosition(
                findNewColumnPositionInBlackboard(blackboard)
        );

        column.setBlackboard(
                blackboard
        );
        column.setUuid(UUID.randomUUID());

        return blackboardColumnMapper.mapBlackboardColumnToBlackboardColumnDto(
                columnRepository.save(column)
        );
    }

    @Override
    @Transactional
    public BlackboardColumnDTO editColumn(String ownerUsername, ColumnPath columnPath, BlackboardColumnEditDTO editBlackboardColumnDTO) {
        BlackboardColumn column = findColumnOfUser(ownerUsername, columnPath);

        column.setName(editBlackboardColumnDTO.getName());
        column.setColor(editBlackboardColumnDTO.getColor());

        return blackboardColumnMapper.mapBlackboardColumnToBlackboardColumnDto(
                columnRepository.save(column)
        );
    }

    @Override
    @Transactional
    public void deleteColumn(String ownerUsername, ColumnPath columnPath) {
        Blackboard blackboard = findBlackboardOfUser(ownerUsername, columnPath.getBlackboardUUID());
        BlackboardColumn blackboardColumnToDelete = findColumnOfBlackboard(blackboard, columnPath.getColumnUUID());
        List<BlackboardColumn> columnsToChangePosition = blackboard.getColumns().stream()
                .filter(column -> column.getPosition() > blackboardColumnToDelete.getPosition())
                .map(column ->
                        {
                            column.setPosition(column.getPosition() - 1);
                            return column;
                        }
                ).toList();

        columnRepository.delete(
                blackboardColumnToDelete
        );

        columnRepository.saveAll(columnsToChangePosition);
    }

    @Override
    public List<BlackboardColumnDTO> getAllColumnsOfBlackboard(String ownerUsername, UUID blackboardUUID) {
        Blackboard blackboard = findBlackboardOfUser(
                ownerUsername,
                blackboardUUID
        );

        return blackboard.getColumns()
                .stream()
                .map(blackboardColumnMapper::mapBlackboardColumnToBlackboardColumnDto)
                .toList();
    }

    @Override
    @Transactional
    public BlackboardColumnDTO changeColumnPosition(String ownerUsername, ColumnPath columnPath, Integer newPosition) {
        Blackboard blackboard = findBlackboardOfUser(ownerUsername, columnPath.getBlackboardUUID());

        BlackboardColumn columnToChange = findColumnOfBlackboard(blackboard, columnPath.getColumnUUID());

        BlackboardColumn columnToSwapPosition = blackboard.getColumns().stream()
                .filter(column -> column.getPosition().equals(newPosition))
                .findFirst()
                .orElseThrow(
                        BlackboardColumnNotFoundException::new
                );

        columnToSwapPosition.setPosition(columnToChange.getPosition());
        columnToChange.setPosition(newPosition);

        columnRepository.save(columnToSwapPosition);
        return blackboardColumnMapper.mapBlackboardColumnToBlackboardColumnDto(
                columnRepository.save(columnToChange)
        );
    }

    private BlackboardColumn findColumnOfUser(String ownerUsername, ColumnPath columnPath) {
        Blackboard blackboard = findBlackboardOfUser(
                ownerUsername,
                columnPath.getBlackboardUUID()
        );

        return blackboard.getColumns().stream()
                .filter(
                        column -> column.getUuid()
                        .equals(
                                columnPath.getColumnUUID()
                        )
                ).findFirst()
                .orElseThrow(
                        BlackboardColumnNotFoundException::new
                );
    }

    private Integer findNewColumnPositionInBlackboard(Blackboard blackboard) {
        OptionalInt position = blackboard.getColumns().stream()
                .mapToInt(BlackboardColumn::getPosition)
                .max();
        if (position.isPresent()) {
            return position.getAsInt() + 1;
        } else {
            return 0;
        }
    }

    private BlackboardColumn findColumnOfBlackboard(Blackboard blackboard, UUID columnUUID) {
        return blackboard.getColumns().stream()
                .filter(column -> column.getUuid().equals(columnUUID))
                .findFirst()
                .orElseThrow(
                        BlackboardColumnNotFoundException::new
                );
    }

    private Blackboard findBlackboardOfUser(String ownerUsername, UUID blackboardUUID) {
        AppUser owner = findUserByUsername(ownerUsername);

        return owner.getContributes().stream()
                .map(BlackboardContributor::getBlackboard)
                .filter(
                        blackboard -> blackboard.getUuid().equals(blackboardUUID)
                ).findFirst()
                .orElseThrow(
                        BlackboardNotFoundException::new
                );
    }

    private AppUser findUserByUsername(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(String.format("User %s not found", username))
                );
    }
}
