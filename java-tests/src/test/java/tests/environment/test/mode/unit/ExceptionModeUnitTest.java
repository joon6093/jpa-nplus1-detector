package tests.environment.test.mode.unit;

import io.jeyong.nplus1detector.test.annotation.NPlusOneTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import tests.case4.repository.AddressRepository;
import tests.environment.test.mode.unit.config.TestInitDataConfig;

@NPlusOneTest(mode = NPlusOneTest.Mode.EXCEPTION)
@DataJpaTest
@Import(TestInitDataConfig.class)
class ExceptionModeUnitTest {

    @Autowired
    private AddressRepository addressRepository;

    @Disabled("TestExecutionListener를 통해 발생하는 예외는 테스트할 수 없다.")
    @Test
    @DisplayName("@DataJpaTest를 이용한 Repository를 호출하는 상황에서 EXCEPTION 모드가 동작한다.")
    void testExceptionModeInRepositoryCall() {
        addressRepository.findAll();  // N+1 문제 발생
    }
}
