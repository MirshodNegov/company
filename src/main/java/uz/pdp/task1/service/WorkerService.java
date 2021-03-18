package uz.pdp.task1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.task1.entity.Address;
import uz.pdp.task1.entity.Department;
import uz.pdp.task1.entity.Worker;
import uz.pdp.task1.payLoad.ApiResponse;
import uz.pdp.task1.payLoad.WorkerDto;
import uz.pdp.task1.repository.AddressRepository;
import uz.pdp.task1.repository.DepartmentRepository;
import uz.pdp.task1.repository.WorkerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkerService {

    @Autowired
    WorkerRepository workerRepository;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    AddressRepository addressRepository;

    /**
     * get all workers list
     */
    public List<Worker> getWorker() {
        return workerRepository.findAll();
    }

    /**
     * get worker by id
     * if not found return null
     */
    public Worker getWorkerById(Integer id) {
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        return optionalWorker.orElse(null);
    }

    /**
     * add worker
     */
    public ApiResponse addWorker(WorkerDto workerDto) {
        boolean exists = workerRepository.existsByPhoneNumber(workerDto.getPhoneNumber());
        if (exists)
            return new ApiResponse("This phone number already registered !", false);
        Worker worker=new Worker();
        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        Optional<Address> optionalAddress = addressRepository.findById(workerDto.getAddressId());
        if (!optionalAddress.isPresent())
            return new ApiResponse("Address not found !",false);
        Address address = optionalAddress.get();
        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (!optionalDepartment.isPresent())
            return new ApiResponse("Department not found !",false);
        Department department = optionalDepartment.get();
        worker.setAddress(address);
        worker.setDepartment(department);
        workerRepository.save(worker);
        return new ApiResponse("Worker saved !",true);
    }

    /**
     * edit worker by id
     */
    public ApiResponse editWorker(Integer id,WorkerDto workerDto){
        Optional<Worker> optionalWorker = workerRepository.findById(id);
        if (!optionalWorker.isPresent())
            return new ApiResponse("Worker not found !", false);
        Worker worker = optionalWorker.get();
        worker.setName(workerDto.getName());
        worker.setPhoneNumber(workerDto.getPhoneNumber());
        Optional<Address> optionalAddress = addressRepository.findById(workerDto.getAddressId());
        if (!optionalAddress.isPresent())
            return new ApiResponse("Address not found !",false);
        Address address = optionalAddress.get();
        Optional<Department> optionalDepartment = departmentRepository.findById(workerDto.getDepartmentId());
        if (!optionalDepartment.isPresent())
            return new ApiResponse("Department not found !",false);
        Department department = optionalDepartment.get();
        worker.setAddress(address);
        worker.setDepartment(department);
        workerRepository.save(worker);
        return new ApiResponse("Worker edited !",true);
    }

    /**
     * delete worker by id
     */
    public ApiResponse deleteWorker(Integer id){
        boolean exists = workerRepository.existsById(id);
        if (!exists)
            return new ApiResponse("Worker not found !",false);
        workerRepository.deleteById(id);
        return new ApiResponse("Worker deleted !",true);
    }
}
