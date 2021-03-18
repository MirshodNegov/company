package uz.pdp.task1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task1.entity.Address;
import uz.pdp.task1.payLoad.AddressDto;
import uz.pdp.task1.payLoad.ApiResponse;
import uz.pdp.task1.service.AddressService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/address")
public class AddressController {

    @Autowired
    AddressService addressService;

    /**
     * All address list page
     */
    @GetMapping
    public ResponseEntity<List<Address>> getAddress(){
        return ResponseEntity.ok(addressService.getAddress());
    }

    /**
     * One address by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Integer id){
        return ResponseEntity.ok(addressService.getAddressById(id));
    }

    /**
     * Saving address to db
     */
    @PostMapping
    public ResponseEntity<ApiResponse> saveAddress(@Valid @RequestBody AddressDto addressDto){
        ApiResponse apiResponse = addressService.addAddress(addressDto);
        return ResponseEntity.status(apiResponse.isSuccess()
                ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * Editing address
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editAddress(@PathVariable Integer id,
                                                   @Valid @RequestBody AddressDto addressDto){
        ApiResponse apiResponse = addressService.editAddress(id, addressDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED
                : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * Deleting address by id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteAddress(@PathVariable Integer id){
        ApiResponse apiResponse = addressService.deleteAddress(id);
        return ResponseEntity.status(apiResponse.isSuccess()?202:409).body(apiResponse);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
