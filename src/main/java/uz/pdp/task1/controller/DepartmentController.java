package uz.pdp.task1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task1.entity.Company;
import uz.pdp.task1.entity.Department;
import uz.pdp.task1.payLoad.ApiResponse;
import uz.pdp.task1.payLoad.CompanyDto;
import uz.pdp.task1.payLoad.DepartmentDto;
import uz.pdp.task1.service.CompanyService;
import uz.pdp.task1.service.DepartmentService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    /**
     * All department list page
     */
    @GetMapping
    public ResponseEntity<List<Department>> getDepartment(){
        return ResponseEntity.ok(departmentService.getDepartment());
    }

    /**
     * One department by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Integer id){
        return ResponseEntity.ok(departmentService.getDepartmentById(id));
    }

    /**
     * Saving department to db
     */
    @PostMapping
    public ResponseEntity<ApiResponse> saveDepartment(@Valid @RequestBody DepartmentDto departmentDto){
        ApiResponse apiResponse = departmentService.addDepartment(departmentDto);
        return ResponseEntity.status(apiResponse.isSuccess()
                ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * Editing company
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editDepartment(@PathVariable Integer id,
                                                   @Valid @RequestBody DepartmentDto departmentDto){
        ApiResponse apiResponse = departmentService.editDepartment(id, departmentDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED
                : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * Deleting Company by id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteDepartment(@PathVariable Integer id){
        ApiResponse apiResponse = departmentService.deleteDepartment(id);
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
