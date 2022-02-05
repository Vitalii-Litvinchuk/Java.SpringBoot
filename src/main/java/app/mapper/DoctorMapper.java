package app.mapper;

import app.entities.DTOs.Doctor.DoctorDTO;
import app.entities.Doctor;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    Doctor DoctorDTOToDoctor(DoctorDTO doctorDTO);
    DoctorDTO DoctorToDoctorDTO(Doctor doctor);
    List<DoctorDTO> DoctorsToDoctorDTOs(List<Doctor> doctors);
}
