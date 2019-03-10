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

    @SuppressWarnings("static-access")
	@Test
    @DisplayName("Values added to the charge variable are really added - #113")
    void addChargesAdds1(TestInfo testInfo) {
    	FightingGloves fighting_gloves = new FightingGloves();
    	int initial_value = fighting_gloves.getCharges();
    	fighting_gloves.addCharges(1);
        Assertions.assertEquals(initial_value+1, fighting_gloves.getCharges(), "1 plus confirmed");
    }
	
}
