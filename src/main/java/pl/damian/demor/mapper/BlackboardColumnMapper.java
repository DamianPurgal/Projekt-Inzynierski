package pl.damian.demor.mapper;

import org.mapstruct.Mapper;
import pl.damian.demor.DTO.blackboardColumn.BlackboardColumnAddDTO;
import pl.damian.demor.DTO.blackboardColumn.BlackboardColumnDTO;
import pl.damian.demor.model.BlackboardColumn;

@Mapper()
public interface BlackboardColumnMapper {

    BlackboardColumnDTO mapBlackboardColumnToBlackboardColumnDto(BlackboardColumn blackboardColumn);

    BlackboardColumn mapBlackboardColumnDtoToBlackboardColumn(BlackboardColumnDTO blackboardColumnDto);

    BlackboardColumn mapBlackboardColumnAddDtoBlackboardColumn(BlackboardColumnAddDTO blackboardColumnAddDTO);

}
