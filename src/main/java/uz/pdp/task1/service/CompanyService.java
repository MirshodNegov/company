package uz.pdp.task1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.task1.entity.Address;
import uz.pdp.task1.entity.Company;
import uz.pdp.task1.payLoad.ApiResponse;
import uz.pdp.task1.payLoad.CompanyDto;
import uz.pdp.task1.repository.AddressRepository;
import uz.pdp.task1.repository.CompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {

    @Autowired
    AddressRepository addressRepository;
    @Autowired
    CompanyRepository companyRepository;

    /**
     * get all company list
     */
    public List<Company> getCompany() {
        return companyRepository.findAll();
    }

    /**
     * get company by id
     * if not found return null
     */
    public Company getCompanyById(Integer id) {
        Optional<Company> optionalCompany = companyRepository.findById(id);
        return optionalCompany.orElse(null);
    }

    /**
     * add company
     */
    public ApiResponse addCompany(CompanyDto companyDto) {
        boolean exists = companyRepository.existsByCorpName(companyDto.getCorpName());
        if (exists)
            return new ApiResponse("This Company already exist !", false);
        Company company=new Company();
        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());
        Optional<Address> optionalAddress = addressRepository.findById(companyDto.getAddressId());
        if (!optionalAddress.isPresent())
            return new ApiResponse("Address not found !",false);
        Address address = optionalAddress.get();
        company.setAddress(address);
        companyRepository.save(company);
        return new ApiResponse("Company saved !",true);
    }

    /**
     * edit company by id
     */
    public ApiResponse editCompany(Integer id,CompanyDto companyDto){
        Optional<Company> optionalCompany = companyRepository.findById(id);
        if (!optionalCompany.isPresent())
            return new ApiResponse("Company not found !", false);
        Company company = optionalCompany.get();
        company.setCorpName(companyDto.getCorpName());
        company.setDirectorName(companyDto.getDirectorName());
        Optional<Address> optionalAddress = addressRepository.findById(companyDto.getAddressId());
        if (!optionalAddress.isPresent())
            return new ApiResponse("Address not found !",false);
        Address address = optionalAddress.get();
        company.setAddress(address);
        companyRepository.save(company);
        return new ApiResponse("Company edited !",true);
    }

    /**
     * delete company by id
     */
    public ApiResponse deleteCompany(Integer id){
        boolean exists = companyRepository.existsById(id);
        if (!exists)
            return new ApiResponse("Company not found !",false);
        companyRepository.deleteById(id);
        return new ApiResponse("Company deleted !",true);
    }
}
