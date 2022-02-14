package app.controllers;

import app.entities.DTOs.Nurse.NurseDTO;
import app.entities.Nurse;
import app.mapper.NurseMapper;
import app.repositories.NurseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/nurse")
public class NurseController {
    private final NurseRepository nurseRepository;
    private final NurseMapper nurseMapper;

    @GetMapping("/get-all-nurses")
    public List<NurseDTO> index() {
        return nurseMapper.NursesToNurseDTOs(nurseRepository.findAll());
    }

    @PostMapping("/add-nurse")
    public ResponseEntity Add(NurseDTO nurseDTO) {
        if (nurseRepository.findByPhone(nurseDTO.phone).size() != 0)
            return ResponseEntity.badRequest().body("phone must be different ");
        if (nurseDTO.name != "" && nurseDTO.phone != "") {
            nurseRepository.save(nurseMapper.NurseDTOToNurse(nurseDTO));
            return ResponseEntity.ok(nurseDTO);
        }
        return ResponseEntity.badRequest().body("incorrect nurse value : " + nurseDTO);
    }

    @DeleteMapping("/delete-nurse")
    public ResponseEntity Delete(int nurseId) {
        if (nurseRepository.existsById(nurseId)) {
            nurseRepository.deleteById(nurseId);
            return ResponseEntity.ok("successfully deleted");
        } else return ResponseEntity.badRequest().body("nurse doesn`t exist");
    }

    @PutMapping("/edit-nurse")
    public ResponseEntity Edit(int nurseId, NurseDTO nurseDTO) {
        if (!nurseRepository.existsById(nurseId)){
            return ResponseEntity.badRequest().body("not exist nurse id : " + nurseId);
        }
        Nurse nurse = nurseRepository.getById(nurseId);
        String error_message = "";
        if (nurseDTO.name != "" && nurse.getName() != nurseDTO.name)
            nurse.setName(nurseDTO.name);
        if (nurseRepository.findByPhone(nurseDTO.phone).toArray().length == 0) {
            if (nurseDTO.phone != "" && nurse.getPhone() != nurseDTO.phone)
                nurse.setPhone(nurseDTO.phone);
        } else error_message += ", but phone number wasn`t changed because nurse with it exist";
        nurseRepository.save(nurse);
        return ResponseEntity.ok("successfully edited" + error_message);
    }
}