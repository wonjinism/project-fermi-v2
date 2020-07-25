package kim.wonjin.fermi.common;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    public CommonTestUtil commonTestUtil() {
        return new CommonTestUtil();
    }

}
