package pl.damian.demor.mapper;

import org.mapstruct.Mapper;
import pl.damian.demor.DTO.blackboard.BlackboardAddDTO;
import pl.damian.demor.DTO.blackboard.BlackboardDTO;
import pl.damian.demor.DTO.blackboard.BlackboardDetailedDTO;
import pl.damian.demor.model.Blackboard;
import pl.damian.demor.model.ContributorRole;

@Mapper()
public interface BlackboardMapper {

    BlackboardDTO mapBlackBoardAddDtoToBlackboardDto(BlackboardAddDTO blackboardAddDTO);

    BlackboardDTO mapBlackboardToBlackboardDto(Blackboard blackboard);

    Blackboard mapBlackboardDtoToBlackboard(BlackboardDTO blackboardDTO);

    BlackboardDetailedDTO mapBlackboardToDetailedDto(Blackboard blackboard, ContributorRole role);
}
