package pl.damian.demor.mapper;

import org.mapstruct.Mapper;
import pl.damian.demor.DTO.comment.CommentAddDTO;
import pl.damian.demor.DTO.comment.CommentDTO;
import pl.damian.demor.model.Comment;

@Mapper()
public interface CommentMapper {

    CommentDTO mapCommentToDto(Comment comment);

    Comment mapCommentAddDtoToComment(CommentAddDTO commentAddDTO);

}
