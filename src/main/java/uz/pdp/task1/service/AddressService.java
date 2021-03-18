package uz.pdp.task1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.task1.entity.Address;
import uz.pdp.task1.payLoad.AddressDto;
import uz.pdp.task1.payLoad.ApiResponse;
import uz.pdp.task1.repository.AddressRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    /**
     * get All address list page
     */
    public List<Address> getAddress() {
        return addressRepository.findAll();
    }

    /**
     * get address by id
     * if not found return null
     */
    public Address getAddressById(Integer id) {
        Optional<Address> optionalAddress = addressRepository.findById(id);
        return optionalAddress.orElse(null);
    }

    /**
     * add address
     */
    public ApiResponse addAddress(AddressDto addressDto) {
        boolean exists = addressRepository.existsByStreetAndHomeNumber
                (addressDto.getStreet(), addressDto.getHomeNumber());
        if (exists)
            return new ApiResponse("This address already exist !", false);
        Address address = new Address();
        address.setStreet(addressDto.getStreet());
        address.setHomeNumber(addressDto.getHomeNumber());
        addressRepository.save(address);
        return new ApiResponse("Address saved !",true);
    }

    /**
     * edit address by id
     */
    public ApiResponse editAddress(Integer id,AddressDto addressDto){
        Optional<Address> optionalAddress = addressRepository.findById(id);
        if (!optionalAddress.isPresent())
            return new ApiResponse("Address not found !", false);
        Address address = optionalAddress.get();
        address.setStreet(addressDto.getStreet());
        address.setHomeNumber(address.getHomeNumber());
        addressRepository.save(address);
        return new ApiResponse("Address edited !",true);
    }

    /**
     * delete address by id
     */
    public ApiResponse deleteAddress(Integer id){
        boolean exists = addressRepository.existsById(id);
        if (!exists)
            return new ApiResponse("Address not found !",false);
        addressRepository.deleteById(id);
        return new ApiResponse("Address deleted !",true);
    }
}
