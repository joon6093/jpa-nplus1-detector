package io.jeyong.test.environment.test.mode.unit;

import io.jeyong.detector.annotation.NPlusOneTest;
import io.jeyong.test.case4.repository.AddressRepository;
import io.jeyong.test.environment.test.mode.unit.config.TestInitDataConfig;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@NPlusOneTest(mode = NPlusOneTest.Mode.EXCEPTION)
@DataJpaTest
@Import(TestInitDataConfig.class)
class ExceptionModeUnitTest {

    @Autowired
    private AddressRepository addressRepository;

    @Disabled("BeforeEach 메서드에서 발생하는 예외는 테스트할 수 없다.")
    @Test
    @DisplayName("단위 테스트에서 Repository를 호출하는 상황에서 EXCEPTION 모드가 동작한다.")
    void testExceptionModeInRepositoryCall() {
        addressRepository.findAll();  // N+1 문제 발생
    }
}
