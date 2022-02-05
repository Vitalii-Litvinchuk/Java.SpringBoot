package app.entities.DTOs.Nurse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NurseDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String phone;
}

