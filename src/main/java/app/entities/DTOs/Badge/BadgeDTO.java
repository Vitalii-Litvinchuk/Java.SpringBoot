package app.entities.DTOs.Badge;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class BadgeDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String name;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Date date;
}
