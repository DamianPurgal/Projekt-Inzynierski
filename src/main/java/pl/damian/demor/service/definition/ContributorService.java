package pl.damian.demor.service.definition;

import pl.damian.demor.DTO.contributor.ContributorAddDTO;
import pl.damian.demor.DTO.contributor.ContributorDTO;
import pl.damian.demor.DTO.contributor.ContributorDeleteDTO;

import java.util.List;
import java.util.UUID;

public interface ContributorService {

    ContributorDTO addContributorToBlackboard(ContributorAddDTO request);

    void deleteContributorOfBlackboard(ContributorDeleteDTO request);

    List<ContributorDTO> getAllContributorsOfBlackboard(String ownerUsername, UUID blackboardUUID);
}
