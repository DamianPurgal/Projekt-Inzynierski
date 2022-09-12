package pl.damian.demor.service.definition;

import pl.damian.demor.DTO.blackboard.BlackboardAddContributorDTO;
import pl.damian.demor.DTO.blackboard.BlackboardDTO;
import pl.damian.demor.DTO.blackboard.BlackboardEditDTO;

import java.util.List;

public interface BlackboardService {

    BlackboardDTO createBlackboard(BlackboardDTO blackboardDTO, String ownerUsername);

    void addContributorToBlackboard(BlackboardAddContributorDTO request);

    List<BlackboardDTO> getAllBlackboards();

    BlackboardDTO editBlackboard(BlackboardEditDTO blackboardEditDTO, Long blackboardId);

    void deleteBlackboard(Long blackboardId, String ownerUsername);

    BlackboardDTO getBlackboardInformations(Long blackboardId, String ownerUsername);

    List<BlackboardDTO> getAllBlackboardsOfUser(String username);

}
