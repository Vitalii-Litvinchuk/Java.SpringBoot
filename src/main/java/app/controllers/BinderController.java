package app.controllers;

import app.entities.Doctor;
import app.entities.Nurse;
import app.repositories.DoctorRepository;
import app.repositories.NurseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/binder")
public class BinderController {
    private final DoctorRepository doctorRepository;
    private final NurseRepository nurseRepository;

    @PostMapping("/nurse-and-doctor-relationship")
    public ResponseEntity NurseAndDoctorRelationship(int doctorId, int nurseId){
        if (!doctorRepository.existsById(doctorId))
            return ResponseEntity.badRequest().body(" not found doctor by id : " + doctorId);

        if (!nurseRepository.existsById(nurseId))
            return ResponseEntity.badRequest().body(" not found nurse by id : " + nurseId);

        Doctor doctor = doctorRepository.getById(doctorId);
        Nurse nurse = nurseRepository.getById(nurseId);

        try {
            Set<Nurse> nurses = doctor.getNurses();
            nurses.add(nurse);
//            doctor.setNurses(nurses);
            nurse.setDoctor(doctor);
            doctorRepository.save(doctor);
            nurseRepository.save(nurse);
        }   catch (Exception ex) { return ResponseEntity.badRequest().body("Bound error");}

        return ResponseEntity.ok().body("Successful bound doctor : " + doctorId + " and nurse : " + nurseId);
    }
}
