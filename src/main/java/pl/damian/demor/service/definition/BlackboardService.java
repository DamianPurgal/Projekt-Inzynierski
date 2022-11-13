package pl.damian.demor.service.definition;

import pl.damian.demor.DTO.blackboard.BlackboardDTO;
import pl.damian.demor.DTO.blackboard.BlackboardDetailedDTO;
import pl.damian.demor.DTO.blackboard.BlackboardEditDTO;

import java.util.List;
import java.util.UUID;

public interface BlackboardService {

    BlackboardDTO createBlackboard(BlackboardDTO blackboardDTO, String ownerUsername);

    List<BlackboardDTO> getAllBlackboards();

    BlackboardDTO editBlackboard(BlackboardEditDTO blackboardEditDTO, String ownerUsername, UUID blackboardUUID);

    void deleteBlackboard(UUID blackboardUUID, String ownerUsername);

    BlackboardDTO getBlackboardInformations(UUID blackboardUUID, String ownerUsername);

    List<BlackboardDTO> getAllBlackboardsOfUser(String username);

    BlackboardDetailedDTO getBlackboardDetailed(UUID blackboardUUID, String ownerUsername);

}
