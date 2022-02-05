package app.entities.DTOs.Doctor;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DoctorDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String phone;
//    DoctorMapper
//    public Doctor toDoctor(){
//        Doctor _doctor = new Doctor();
//        _doctor.setName(this.name);
//        _doctor.setPhone(this.phone);
//        return _doctor;
//    }
}
