package pl.damian.demor.service.definition;

import pl.damian.demor.DTO.Blackboard.BlackboardDTO;

public interface BlackboardService {

    BlackboardDTO createBlackboard(BlackboardDTO blackboardDTO, String ownerUsername);


}
