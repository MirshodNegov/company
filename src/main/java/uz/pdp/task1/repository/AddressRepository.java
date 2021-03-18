package uz.pdp.task1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.task1.entity.Address;

public interface AddressRepository extends JpaRepository<Address,Integer> {
    boolean existsByStreetAndHomeNumber(String street, Integer homeNumber);
    boolean existsById(Integer id);
}
