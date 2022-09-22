package pl.damian.demor.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.damian.demor.DTO.blackboardColumn.BlackboardColumnAddDTO;
import pl.damian.demor.DTO.blackboardColumn.BlackboardColumnDTO;
import pl.damian.demor.DTO.blackboardColumn.BlackboardColumnEditDTO;
import pl.damian.demor.exception.blackboard.BlackboardNotFoundException;
import pl.damian.demor.mapper.BlackboardColumnMapper;
import pl.damian.demor.model.AppUser;
import pl.damian.demor.model.Blackboard;
import pl.damian.demor.model.BlackboardColumn;
import pl.damian.demor.model.BlackboardContributor;
import pl.damian.demor.repository.AppUserRepository;
import pl.damian.demor.repository.BlackboardColumnRepository;
import pl.damian.demor.repository.BlackboardRepository;
import pl.damian.demor.service.definition.ColumnService;

import javax.transaction.Transactional;
import java.util.List;
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

        BlackboardColumn column = blackboardColumnMapper.mapBlackboardColumnAddDtoBlackboardColumn(
                addColumnDTO
        );

        column.setBlackboard(
                findBlackboardColumnOfUser(ownerUsername, blackboardUUID)
        );
        column.setUuid(UUID.randomUUID());

        return blackboardColumnMapper.mapBlackboardColumnToBlackboardColumnDto(
                columnRepository.save(column)
        );
    }

    @Override
    public BlackboardColumnDTO editColumn(String ownerUsername, BlackboardColumnEditDTO editBlackboardColumnDTO) {
        return null;
    }

    @Override
    public void deleteColumn(String ownerUsername, UUID columnUUID) {

    }

    @Override
    public List<BlackboardColumnDTO> getAllColumnsOfBlackboard(String ownerUsername, UUID blackboardUUID) {
        return null;
    }

    @Override
    public BlackboardColumnDTO changeColumnPosition(String ownerUsername, UUID columnUUID, Integer newPosition) {
        return null;
    }

    private Blackboard findBlackboardColumnOfUser(String ownerUsername, UUID blackboardUUID) {
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
