package tests.case4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tests.case4.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
