package uz.pdp.task1.payLoad;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.task1.entity.Address;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {
    @NotNull(message = "Corp name can't be NULL")
    private String corpName;
    @NotNull(message = "Director name can't be NULL")
    private String directorName;
    @NotNull(message = "Address can't be NULL")
    private Integer addressId;
}
