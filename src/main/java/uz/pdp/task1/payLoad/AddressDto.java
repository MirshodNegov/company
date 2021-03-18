package uz.pdp.task1.payLoad;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressDto {
    @NotNull(message = "Street name can't be NULL")
    private String street;
    @NotNull(message = "HomeNumber can't be NULL")
    private Integer homeNumber;
}
