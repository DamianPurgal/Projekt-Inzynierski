package pl.damian.demor.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.damian.demor.repository.*;
import javax.transaction.Transactional;

@RestController
@RequestMapping("api/test")
@AllArgsConstructor
@CrossOrigin
public class TestController {

    AppUserRepository userRepository;
    BlackboardColumnRepository columnRepository;
    BlackboardRepository blackboardRepository;
    BlackboardContributorRepository contributorRepository;
    CommentRepository commentRepository;
    TicketRepository ticketRepository;


    @PostMapping()
    @PreAuthorize("permitAll()")
    @Transactional
    public String xd() {
        return "UDALO SIE";
    }
}
