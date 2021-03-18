package uz.pdp.task1.payLoad;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDto {
    @NotNull(message = "Department name can't be NULL")
    private String name;
    @NotNull(message = "Company id can't be NULL")
    private Integer companyId;
}
