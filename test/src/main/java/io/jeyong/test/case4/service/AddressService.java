package io.jeyong.test.case4.service;

import io.jeyong.test.case4.entity.Address;
import io.jeyong.test.case4.repository.AddressRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    @Transactional
    public List<Address> findAllAddresses() {
        return addressRepository.findAll();
    }
}
