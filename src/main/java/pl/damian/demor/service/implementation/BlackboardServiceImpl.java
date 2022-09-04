package pl.damian.demor.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.damian.demor.DTO.Blackboard.BlackboardDTO;
import pl.damian.demor.mapper.BlackboardMapper;
import pl.damian.demor.model.AppUser;
import pl.damian.demor.model.Blackboard;
import pl.damian.demor.model.BlackboardContributor;
import pl.damian.demor.model.ContributorRole;
import pl.damian.demor.repository.AppUserRepository;
import pl.damian.demor.repository.BlackboardContributorRepository;
import pl.damian.demor.repository.BlackboardRepository;
import pl.damian.demor.service.definition.BlackboardService;

import java.util.UUID;

@Service
@AllArgsConstructor
public class BlackboardServiceImpl implements BlackboardService {

    private final BlackboardMapper blackboardMapper;

    private final BlackboardRepository blackboardRepository;

    private final BlackboardContributorRepository contributorRepository;

    private final AppUserRepository appUserRepository;

    @Override
    public BlackboardDTO createBlackboard(BlackboardDTO blackboardDTO, String ownerUsername) {
        AppUser user = appUserRepository.findByEmail(ownerUsername)
                .orElseThrow(
                        () -> new UsernameNotFoundException(String.format("User %s not found", ownerUsername))
                );

        Blackboard blackboardToAdd = blackboardMapper.mapBlackboardDtoToBlackboard(
                blackboardDTO
        );
        blackboardToAdd.setLinkId(UUID.randomUUID().toString());

        Blackboard blackboard = blackboardRepository.save(blackboardToAdd);


        BlackboardContributor contributor =  BlackboardContributor.builder()
                .blackboard(blackboard)
                .user(user)
                .role(ContributorRole.OWNER)
                .build();

        contributor = contributorRepository.save(contributor);

        return blackboardMapper.mapBlackboardToBlackboardDto(
                contributor.getBlackboard()
        );
    }
}
