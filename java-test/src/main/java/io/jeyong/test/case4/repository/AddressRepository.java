package io.jeyong.test.case4.repository;

import io.jeyong.test.case4.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
