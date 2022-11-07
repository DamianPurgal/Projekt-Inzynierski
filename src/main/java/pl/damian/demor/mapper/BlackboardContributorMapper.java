package pl.damian.demor.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.damian.demor.DTO.contributor.ContributorDTO;
import pl.damian.demor.model.BlackboardContributor;

@Mapper()
public interface BlackboardContributorMapper {

    @Mapping(target = "email", expression = "java(blackboardContributor.getUser().getEmail())")
    ContributorDTO mapBlackboardContributorToDto(BlackboardContributor blackboardContributor);
}
