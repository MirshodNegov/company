package uz.pdp.task1.payLoad;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkerDto {
    @NotNull(message = "Worker name can't be NULL")
    private String name;
    @NotNull(message = "Phone number can't be NULL")
    private String phoneNumber;
    @NotNull(message = "Address id can't be NULL")
    private Integer addressId;
    @NotNull(message = "Department id can't be NULL")
    private Integer departmentId;
}
