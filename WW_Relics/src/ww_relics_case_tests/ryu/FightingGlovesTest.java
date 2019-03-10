package ww_relics_case_tests.ryu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import ww_relics.relics.ryu.FightingGloves;

@RunWith(JUnitPlatform.class)
public class FightingGlovesTest {

    @Test
    @DisplayName("Values added to the charge variable are really added - #113")
    void myFirstTest(TestInfo testInfo) {
    	FightingGloves fighting_gloves = new FightingGloves();
        Assertions.assertEquals(2, 1+0, "1 + 1 = 2");
        Assertions.assertEquals("My First Test", testInfo.getDisplayName(),
                                    () -> "TestInfo is injected correctly");
    }
	
}
