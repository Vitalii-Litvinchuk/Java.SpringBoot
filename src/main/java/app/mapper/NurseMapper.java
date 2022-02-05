package app.mapper;

import app.entities.DTOs.Nurse.NurseDTO;
import app.entities.Nurse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NurseMapper {
    Nurse NurseDTOToNurse(NurseDTO nurseDTO);
    NurseDTO NurseToNurseDTO(Nurse Nurse);
    List<NurseDTO> NursesToNurseDTOs(List<Nurse> Nurses);
}