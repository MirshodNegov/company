package uz.pdp.task1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import uz.pdp.task1.entity.Worker;
import uz.pdp.task1.payLoad.ApiResponse;
import uz.pdp.task1.payLoad.WorkerDto;
import uz.pdp.task1.service.WorkerService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/worker")
public class WorkerController {

    @Autowired
    WorkerService workerService;

    /**
     * All worker list page
     */
    @GetMapping
    public ResponseEntity<List<Worker>> getWorker(){
        return ResponseEntity.ok(workerService.getWorker());
    }

    /**
     * One worker by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Worker> getWorkerById(@PathVariable Integer id){
        return ResponseEntity.ok(workerService.getWorkerById(id));
    }

    /**
     * Saving worker to db
     */
    @PostMapping
    public ResponseEntity<ApiResponse> saveWorker(@Valid @RequestBody WorkerDto workerDto){
        ApiResponse apiResponse = workerService.addWorker(workerDto);
        return ResponseEntity.status(apiResponse.isSuccess()
                ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * Editing worker
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> editWorker(@PathVariable Integer id,
                                                   @Valid @RequestBody WorkerDto workerDto){
        ApiResponse apiResponse = workerService.editWorker(id, workerDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.ACCEPTED
                : HttpStatus.CONFLICT).body(apiResponse);
    }

    /**
     * Deleting worker by id
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteWorker(@PathVariable Integer id){
        ApiResponse apiResponse = workerService.deleteWorker(id);
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
