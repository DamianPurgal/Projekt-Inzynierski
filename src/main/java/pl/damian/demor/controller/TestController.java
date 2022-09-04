package pl.damian.demor.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.damian.demor.model.AppUser;
import pl.damian.demor.model.Blackboard;
import pl.damian.demor.model.BlackboardContributor;
import pl.damian.demor.model.ContributorRole;
import pl.damian.demor.repository.*;
import pl.damian.demor.security.UserRole;

import javax.transaction.Transactional;

@RestController
@RequestMapping("api/test")
@AllArgsConstructor
public class TestController {

    AppUserRepository userRepository;
    BlackboardColumnRepository columnRepository;
    BlackboardRepository blackboardRepository;
    BlackboardContributorRepository contributorRepository;
    CommentRepository commentRepository;
    TicketRepository ticketRepository;


    @GetMapping()
    @PreAuthorize("permitAll()")
    @Transactional
    public BlackboardContributor xd(){
        AppUser user = userRepository.save(AppUser.builder()
                        .email("DamianDamian@gmail.com")
                        .firstname("Damian")
                        .lastname("Purgal")
                        .password("abc123")
                        .userRole(UserRole.BASIC_USER)
                        .enabled(true)
                        .locked(false)
                        .build()
                );
        Blackboard blackboard = blackboardRepository.save(
                Blackboard.builder()
                        .color("red")
                        .name("nazwa")
                        .linkId("LINK_ID#1")
                        .description("test description")
                        .build()
        );

        BlackboardContributor contributor = BlackboardContributor.builder()
                .blackboard(blackboard)
                .user(user)
                .role(ContributorRole.OWNER)
                .build();

        contributor = contributorRepository.save(contributor);

        return contributor;
    }
}
