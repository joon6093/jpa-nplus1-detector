package tests.case4.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tests.case4.entity.Address;
import tests.case4.repository.AddressRepository;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    @Transactional
    public List<Address> findAllAddresses() {
        return addressRepository.findAll();
    }
}
