package tests.environment.test.mode.unit;

import io.jeyong.test.annotation.NPlusOneTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import tests.case4.repository.AddressRepository;
import tests.environment.test.mode.unit.config.TestInitDataConfig;

@NPlusOneTest(mode = NPlusOneTest.Mode.LOGGING)
@DataJpaTest(showSql = false)
@Import(TestInitDataConfig.class)
class LoggingModeUnitTest {

    @Autowired
    private AddressRepository addressRepository;

    @Test
    @DisplayName("@DataJpaTest를 이용한 Repository를 호출하는 상황에서 LOGGING 모드가 동작한다.")
    void testLoggingModeInRepositoryCall() {
        addressRepository.findAll();  // N+1 문제 발생
    }
}
