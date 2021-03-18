package uz.pdp.task1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task1.entity.Company;
import uz.pdp.task1.payLoad.ApiResponse;
import uz.pdp.task1.payLoad.CompanyDto;
import uz.pdp.task1.service.CompanyService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    /**
     * All company list page
     */
    @GetMapping
    public ResponseEntity<List<Company>> getCompany(){
        return ResponseEntity.ok(companyService.getCompany());
    }

    /**
     * One company by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompanyById(@PathVariable Integer id){
        return ResponseEntity.ok(companyService.getCompanyById(id));
    }

    /**
     * Saving company to db
     */
    @PostMapping
    public ResponseEntity<ApiResponse> saveCompany(@Valid @RequestBody CompanyDto companyDto){
        ApiResponse apiResponse = companyService.addCompany(companyDto);
        return ResponseEntity.status(apiResponse.isSuccess()
                ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * Editing company
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editCompany(@PathVariable Integer id,
                                                   @Valid @RequestBody CompanyDto companyDto){
        ApiResponse apiResponse = companyService.editCompany(id, companyDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED
                : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * Deleting Company by id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCompany(@PathVariable Integer id){
        ApiResponse apiResponse = companyService.deleteCompany(id);
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
