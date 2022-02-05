package app.controllers;

import app.entities.DTOs.Doctor.DoctorDTO;
import app.entities.Doctor;
import app.mapper.DoctorMapper;
import app.repositories.DoctorRepository;
import app.repositories.NurseRepository;
import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import java.lang.reflect.Array;
//import java.util.ArrayList;
//import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/doctor")
public class DoctorController {
    private final DoctorRepository doctorRepository;
    private final DoctorMapper doctorMapper;

//    @RequiredArgsConstructor
//    @Autowired
//    public HomeController(DoctorRepository doctorRepository) {
//        this.doctorRepository = doctorRepository;
//    }

    @GetMapping("/get-all-doctors")
    public List<DoctorDTO> index() {
//        List<Doctor> doctors = doctorRepository.findAll();
//        List<DoctorDTO> doctorDTOs = new ArrayList<>();
//        for (int i = 0 ; i < doctors.size() ; i++)
//            doctorDTOs.add(doctorMapper.DoctorToDoctorDTO(doctors.get(i)));

        return doctorMapper.DoctorsToDoctorDTOs(doctorRepository.findAll());
    }

    @PostMapping("/add-doctor")
    public String Add(DoctorDTO doctor) {
        if (doctorRepository.findByPhone(doctor.phone).toArray().length != 0)
            return ResponseEntity.badRequest().body(" phone must be different ").toString();
        if (doctor.name != "" && doctor.phone != "") {
            doctorRepository.save(doctorMapper.DoctorDTOToDoctor(doctor));
            return ResponseEntity.ok(doctor).toString();
        }
        return ResponseEntity.badRequest().body(" incorrect doctor value : " + doctor).toString();
    }

    @DeleteMapping("/delete-doctor")
    public String Delete(int doctorId) {
        if (doctorRepository.existsById(doctorId)) {
            doctorRepository.deleteById(doctorId);
            return "Successfully deleted";
        } else return ResponseEntity.badRequest().body(", doctor doesn`t exist").toString();
    }

    @PutMapping("/edit-doctor")
    public String Edit(int doctorId, DoctorDTO doctorDTO) {
        Doctor doctor = doctorRepository.getById(doctorId);
        String error_message = "";
        if (doctorDTO.name != "" && doctor.getName() != doctorDTO.name)
            doctor.setName(doctorDTO.name);
        if (doctorRepository.findByPhone(doctorDTO.phone).size()== 0) {
            if (doctorDTO.phone != "" && doctor.getPhone() != doctorDTO.phone)
                doctor.setPhone(doctorDTO.phone);
        } else error_message += " but phone number wasn`t changed because doctor with it exist";
        doctorRepository.save(doctor);
        return "Successfully edited" + error_message;
    }
}