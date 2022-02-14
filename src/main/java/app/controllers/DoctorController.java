package app.controllers;

import app.entities.DTOs.Doctor.DoctorDTO;
import app.entities.Doctor;
import app.mapper.DoctorMapper;
import app.repositories.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/doctor")
public class DoctorController {
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

    @GetMapping("/get-all-doctors")
    public List<DoctorDTO> index() {
        return doctorMapper.DoctorsToDoctorDTOs(doctorRepository.findAll());
    }

    @PostMapping("/add-doctor")
    public ResponseEntity Add(DoctorDTO doctor) {
        if (doctorRepository.findByPhone(doctor.phone).toArray().length != 0)
            return ResponseEntity.badRequest().body(" phone must be different ");
        if (doctor.name != "" && doctor.phone != "") {
            doctorRepository.save(doctorMapper.DoctorDTOToDoctor(doctor));
            return ResponseEntity.ok(doctor);
        }
        return ResponseEntity.badRequest().body(" incorrect doctor value : " + doctor);
    }

    @DeleteMapping("/delete-doctor")
    public ResponseEntity Delete(int doctorId) {
        if (doctorRepository.existsById(doctorId)) {
            doctorRepository.deleteById(doctorId);
            return ResponseEntity.ok("Successfully deleted") ;
        } else return ResponseEntity.badRequest().body(", doctor doesn`t exist");
    }

    @PutMapping("/edit-doctor")
    public ResponseEntity Edit(int doctorId, DoctorDTO doctorDTO) {
        Doctor doctor = doctorRepository.getById(doctorId);
        String error_message = "";
        if (doctorDTO.name != "" && doctor.getName() != doctorDTO.name)
            doctor.setName(doctorDTO.name);
        if (doctorRepository.findByPhone(doctorDTO.phone).size()== 0) {
            if (doctorDTO.phone != "" && doctor.getPhone() != doctorDTO.phone)
                doctor.setPhone(doctorDTO.phone);
        } else error_message += " but phone number wasn`t changed because doctor with it exist";
        doctorRepository.save(doctor);
        return ResponseEntity.ok( "Successfully edited" + error_message);
    }
}