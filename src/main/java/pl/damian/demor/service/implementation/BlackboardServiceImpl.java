package pl.damian.demor.service.implementation;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.damian.demor.DTO.blackboard.BlackboardAddContributorDTO;
import pl.damian.demor.DTO.blackboard.BlackboardDTO;
import pl.damian.demor.DTO.blackboard.BlackboardEditDTO;
import pl.damian.demor.exception.blackboard.BlackboardNotFoundException;
import pl.damian.demor.exception.blackboard.BlackboardPermissionDeniedException;
import pl.damian.demor.exception.blackboardContributor.BlackboardContributorArleadyExistsException;
import pl.damian.demor.mapper.BlackboardMapper;
import pl.damian.demor.model.AppUser;
import pl.damian.demor.model.Blackboard;
import pl.damian.demor.model.BlackboardContributor;
import pl.damian.demor.model.ContributorRole;
import pl.damian.demor.repository.AppUserRepository;
import pl.damian.demor.repository.BlackboardContributorRepository;
import pl.damian.demor.repository.BlackboardRepository;
import pl.damian.demor.service.definition.BlackboardService;

import javax.transaction.Transactional;
import java.util.List;
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
        AppUser user = findUser(ownerUsername);

        Blackboard blackboardToAdd = blackboardMapper.mapBlackboardDtoToBlackboard(
                blackboardDTO
        );

        blackboardToAdd.setUuid(UUID.randomUUID());

        Blackboard blackboard = blackboardRepository.save(blackboardToAdd);

        BlackboardContributor contributor =  BlackboardContributor.builder()
                .blackboard(blackboard)
                .user(user)
                .role(ContributorRole.OWNER)
                .build();

        contributor = contributorRepository.save(contributor);

        return mapContributionToBlackboardDTO(contributor);
    }

    @Override
    @Transactional
    public void addContributorToBlackboard(BlackboardAddContributorDTO request) {

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
                            throw new BlackboardContributorArleadyExistsException();
                        }
                );

        BlackboardContributor contribution = BlackboardContributor.builder()
                .blackboard(blackboard)
                .user(userToContribute)
                .role(ContributorRole.CONTRIBUTOR)
                .build();

        contributorRepository.save(contribution);
    }

    @Override
    public List<BlackboardDTO> getAllBlackboards() {
        return blackboardRepository.findAll()
                .stream()
                .map(blackboardMapper::mapBlackboardToBlackboardDto)
                .toList();
    }

    @Override
    @Transactional
    public BlackboardDTO editBlackboard(BlackboardEditDTO blackboardEditDTO, String ownerUsername, UUID blackboardUUID) {
        Blackboard blackboard = getBlackboardOfUserWithAnyRoleInList(
                findUser(
                        ownerUsername
                ),
                List.of(ContributorRole.OWNER),
                blackboardUUID
        );

        blackboard.setColor(blackboardEditDTO.getColor());
        blackboard.setName(blackboardEditDTO.getName());
        blackboard.setDescription(blackboardEditDTO.getDescription());

        BlackboardDTO blackboardDTO = blackboardMapper.mapBlackboardToBlackboardDto(
                blackboardRepository.save(blackboard)
        );

        blackboardDTO.setRole(ContributorRole.OWNER);

        return blackboardDTO;
    }

    @Override
    @Transactional
    public void deleteBlackboard(UUID blackboardUUID, String ownerUsername) {

        Blackboard blackboard = blackboardRepository.findByUuid(blackboardUUID)
                        .orElseThrow(BlackboardNotFoundException::new);

        blackboard.getContributors().stream()
                        .filter(
                                contributor -> contributor.getUser()
                                        .getEmail()
                                        .equals(ownerUsername)
                                &&
                                        contributor.getRole()
                                                .equals(ContributorRole.OWNER)
                        ).findFirst()
                        .ifPresentOrElse(
                                contributor -> blackboardRepository.delete(blackboard),
                                () -> {
                                    throw new BlackboardPermissionDeniedException();
                                }
                        );
    }

    @Override
    @Transactional
    public BlackboardDTO getBlackboardInformations(UUID blackboardUUID, String ownerUsername) {
        return getBlackboardDtoOfUser(
                findUser(ownerUsername),
                blackboardUUID
        );
    }

    @Override
    public List<BlackboardDTO> getAllBlackboardsOfUser(String username) {
        AppUser user = findUser(username);

        return user.getContributes()
                .stream()
                .map(this::mapContributionToBlackboardDTO)
                .toList();
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

    private BlackboardDTO getBlackboardDtoOfUser(AppUser owner, UUID blackboardUUID) {
        return mapContributionToBlackboardDTO(
                owner.getContributes()
                        .stream()
                        .filter(
                                contribution -> contribution.getBlackboard()
                                        .getUuid()
                                        .equals(
                                                blackboardUUID
                                        )
                        )
                        .findAny()
                        .orElseThrow(
                                BlackboardPermissionDeniedException::new
                        )
        );
    }

    private AppUser findUser(String username) {
        return appUserRepository.findByEmail(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(String.format("User %s not found", username))
                );
    }

    private BlackboardDTO mapContributionToBlackboardDTO(BlackboardContributor contribution) {
        BlackboardDTO blackboardDTO = blackboardMapper.mapBlackboardToBlackboardDto(
                contribution.getBlackboard()
        );

        blackboardDTO.setRole(contribution.getRole());

        return blackboardDTO;
    }
}
