package pl.damian.demor.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.damian.demor.DTO.contributor.ContributorAddDTO;
import pl.damian.demor.DTO.contributor.ContributorDTO;
import pl.damian.demor.DTO.contributor.ContributorDeleteDTO;
import pl.damian.demor.exception.blackboard.BlackboardPermissionDeniedException;
import pl.damian.demor.exception.blackboardContributor.ContributorArleadyExistsException;
import pl.damian.demor.exception.blackboardContributor.ContributorNotFoundException;
import pl.damian.demor.exception.user.UserNotFoundException;
import pl.damian.demor.mapper.BlackboardContributorMapper;
import pl.damian.demor.model.AppUser;
import pl.damian.demor.model.Blackboard;
import pl.damian.demor.model.BlackboardContributor;
import pl.damian.demor.model.ContributorRole;
import pl.damian.demor.repository.AppUserRepository;
import pl.damian.demor.repository.BlackboardContributorRepository;
import pl.damian.demor.service.definition.ContributorService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContributorServiceImpl implements ContributorService {

    private final BlackboardContributorMapper contributorMapper;

    private final AppUserRepository appUserRepository;

    private final BlackboardContributorRepository contributorRepository;

    @Override
    @Transactional
    public ContributorDTO addContributorToBlackboard(ContributorAddDTO request) {

        AppUser userToContribute = findUser(request.getContributorUsername());

        Blackboard blackboard = getBlackboardOfUserWithAnyRoleInList(
                findUser(
                        request.getOwnerUsername()
                ),
                List.of(ContributorRole.OWNER),
                request.getBlackboardUUID()
        );

        blackboard.getContributors()
                .stream()
                .map(BlackboardContributor::getUser)
                .filter(user -> user.getId().equals(
                        userToContribute.getId()
                )).findAny()
                .ifPresent(
                        user -> {
                            throw new ContributorArleadyExistsException();
                        }
                );

        BlackboardContributor contribution = BlackboardContributor.builder()
                .blackboard(blackboard)
                .user(userToContribute)
                .role(ContributorRole.CONTRIBUTOR)
                .build();

        return contributorMapper.mapBlackboardContributorToDto(
                contributorRepository.save(contribution)
        );
    }

    @Override
    @Transactional
    public void deleteContributorOfBlackboard(ContributorDeleteDTO request) {

        Blackboard blackboard = getBlackboardOfUserWithAnyRoleInList(
                findUser(
                        request.getOwnerUsername()
                ),
                List.of(ContributorRole.OWNER),
                request.getBlackboardUUID()
        );

        BlackboardContributor contributor = blackboard.getContributors()
                .stream()
                .filter(contr -> contr.getUser()
                        .getEmail()
                        .equals(
                                request.getContributorUsername()
                        )
                ).findFirst()
                .orElseThrow(
                    ContributorNotFoundException::new
                );

        if (contributor.getRole().equals(ContributorRole.OWNER)) {
            throw new ContributorNotFoundException();
        }

        contributor.setBlackboard(null);
        contributor.setUser(null);
        contributorRepository.delete(contributor);

    }

    @Override
    @Transactional
    public List<ContributorDTO> getAllContributorsOfBlackboard(String ownerUsername, UUID blackboardUUID) {

        Blackboard blackboard = getBlackboardOfUserWithAnyRoleInList(
                findUser(
                        ownerUsername
                ),
                List.of(ContributorRole.OWNER, ContributorRole.CONTRIBUTOR),
                blackboardUUID
        );

        return blackboard.getContributors().stream()
                .map(contributorMapper::mapBlackboardContributorToDto)
                .toList();
    }

    private AppUser findUser(String username) {
        return appUserRepository.findByEmail(username)
                .orElseThrow(UserNotFoundException::new);
    }

    private Blackboard getBlackboardOfUserWithAnyRoleInList(AppUser owner, List<ContributorRole> roles, UUID blackboardUUID) {
        return owner.getContributes()
                .stream()
                .filter(
                        contribution -> roles.contains(
                                contribution.getRole()
                        )
                )
                .map(BlackboardContributor::getBlackboard)
                .filter(board -> board.getUuid()
                        .equals(blackboardUUID)
                )
                .findFirst()
                .orElseThrow(
                        BlackboardPermissionDeniedException::new
                );
    }
}
