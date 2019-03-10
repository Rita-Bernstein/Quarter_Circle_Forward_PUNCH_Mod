package ww_relics_case_tests.ryu;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.helpers.ShaderHelper;
import com.megacrit.cardcrawl.helpers.TipTracker;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import ww_relics.relics.ryu.FightingGloves;

@RunWith(JUnitPlatform.class)
public class FightingGlovesTest {

    @SuppressWarnings("static-access")
	@Test
    @DisplayName("Values added to the charge variable are really added - #113")
    void addChargesAdds1(TestInfo testInfo) {
    		  	
        AbstractCard.initialize();
        GameDictionary.initialize();
        ImageMaster.initialize();
        AbstractPower.initialize();
        FontHelper.initialize();
        AbstractCard.initializeDynamicFrameWidths();
        UnlockTracker.initialize();
        CardLibrary.initialize();
        RelicLibrary.initialize();
        InputHelper.initialize();
        TipTracker.initialize();
        ModHelper.initialize();
        ShaderHelper.initializeShaders();
        UnlockTracker.retroactiveUnlock();
    	
    	FightingGloves fighting_gloves = new FightingGloves();
    	
    	int initial_value = fighting_gloves.getCharges();
    	fighting_gloves.addCharges(1);
        Assertions.assertEquals(initial_value+1, fighting_gloves.getCharges(), "1 plus confirmed");
    }
	
}
