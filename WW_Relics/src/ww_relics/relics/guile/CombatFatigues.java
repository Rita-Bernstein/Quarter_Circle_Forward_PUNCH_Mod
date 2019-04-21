package ww_relics.relics.guile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import ww_relics.resources.relic_graphics.GraphicResources;

public class CombatFatigues extends CustomRelic {

	public static final String ID = "WW_Relics:Combat_Fatigues";
	
	public static boolean gained_block_last_turn = false;
	public static boolean is_first_turn = true;
	
	public static final Logger logger = LogManager.getLogger(CombatFatigues.class.getName()); 
	
	public CombatFatigues() {
		super(ID, GraphicResources.LoadRelicImage("White_Boots - steeltoe-boots - Lorc - CC BY 3.0.png"),
				RelicTier.RARE, LandingSound.FLAT);
	}
	
	public String getUpdatedDescription() {
		return "test";
	}
	
	public int onPlayerGainedBlock(float blockAmount) {
		
		gained_block_last_turn = true;
		
		return MathUtils.floor(blockAmount);
	}
	
	@Override	
	public void atTurnStart() {
		if (firstTurnPassed()) {
			
			logger.info(gained_block_last_turn);
			
		} else is_first_turn = false;
		
		resetConditionChecks();
	}
	
	private boolean firstTurnPassed() {
		return !is_first_turn;
	}
	
	private void resetConditionChecks() {
		gained_block_last_turn = false;
	}
	
	
	
	@Override
	public AbstractRelic makeCopy() {
		return new CombatFatigues();
	}
	
}
