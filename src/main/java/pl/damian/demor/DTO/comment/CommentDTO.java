package pl.damian.demor.DTO.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.damian.demor.DTO.appUser.AppUserDTO;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private UUID uuid;

    private AppUserDTO author;

    private String text;

    private LocalDateTime date;
}
