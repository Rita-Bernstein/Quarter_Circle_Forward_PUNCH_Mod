package ww_relics_case_tests.ryu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
public class FightingGlovesTest {

    @Test
    @DisplayName("My First Test")
    void myFirstTest(TestInfo testInfo) {
        Assertions.assertEquals(2, 1+0, "1 + 1 = 2");
        Assertions.assertEquals("My First Test", testInfo.getDisplayName(),
                                    () -> "TestInfo is injected correctly");
    }
	
}
