package pl.damian.demor.mapper;

import org.mapstruct.Mapper;
import pl.damian.demor.DTO.contributor.ContributorDTO;
import pl.damian.demor.model.BlackboardContributor;

@Mapper()
public interface BlackboardContributorMapper {

    ContributorDTO mapBlackboardContributorToDto(BlackboardContributor blackboardContributor);
}
