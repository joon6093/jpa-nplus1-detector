package io.jeyong.test.case4.controller;

import io.jeyong.test.case4.entity.Address;
import io.jeyong.test.case4.service.AddressService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    // @formatter:off
    /**
     * Address와 Person은 일대일(1:1) 관계
     * 모든 Address을 조회한 후 각 Address에 대해 즉시로딩으로 Person를 조회
     * 1:1 관계에서 연관관계의 주인이 아닌 일(1)을 조회하는 상황에서 감지하는 것을 검증
     */
    // @formatter:on
    @GetMapping
    public List<Address> getAllAddresses() {
        return addressService.findAllAddresses();
    }
}
