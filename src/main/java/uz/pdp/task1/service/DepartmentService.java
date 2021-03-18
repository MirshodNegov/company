package uz.pdp.task1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.task1.entity.Company;
import uz.pdp.task1.entity.Department;
import uz.pdp.task1.payLoad.ApiResponse;
import uz.pdp.task1.payLoad.DepartmentDto;
import uz.pdp.task1.repository.CompanyRepository;
import uz.pdp.task1.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    CompanyRepository companyRepository;

    /**
     * get all department list
     */
    public List<Department> getDepartment() {
        return departmentRepository.findAll();
    }

    /**
     * get department by id
     * if not found return null
     */
    public Department getDepartmentById(Integer id) {
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        return optionalDepartment.orElse(null);
    }

    /**
     * add department
     */
    public ApiResponse addDepartment(DepartmentDto departmentDto) {
        boolean exists = departmentRepository.existsByName(departmentDto.getName());
        if (exists)
            return new ApiResponse("This department already exist !", false);
        Department department=new Department();
        department.setName(departmentDto.getName());
        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (!optionalCompany.isPresent())
            return new ApiResponse("Company not found !",false);
        Company company = optionalCompany.get();
        department.setCompany(company);
        departmentRepository.save(department);
        return new ApiResponse("Department saved !",true);
    }

    /**
     * edit department by id
     */
    public ApiResponse editDepartment(Integer id,DepartmentDto departmentDto){
        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (!optionalDepartment.isPresent())
            return new ApiResponse("Department not found !", false);
        Department department = optionalDepartment.get();
        department.setName(departmentDto.getName());
        Optional<Company> optionalCompany = companyRepository.findById(departmentDto.getCompanyId());
        if (!optionalCompany.isPresent())
            return new ApiResponse("Company not found !",false);
        Company company = optionalCompany.get();
        department.setCompany(company);
        departmentRepository.save(department);
        return new ApiResponse("Company edited !",true);
    }

    /**
     * delete department by id
     */
    public ApiResponse deleteDepartment(Integer id){
        boolean exists = departmentRepository.existsById(id);
        if (!exists)
            return new ApiResponse("Department not found !",false);
        departmentRepository.deleteById(id);
        return new ApiResponse("Department deleted !",true);
    }
}
