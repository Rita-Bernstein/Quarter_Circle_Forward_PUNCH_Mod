package ww_relics.relics.guile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import ww_relics.resources.relic_graphics.GraphicResources;

public class CombatFatigues extends CustomRelic {

	public static final String ID = "WW_Relics:Combat_Fatigues";
	
	public static boolean gained_block_last_turn = false;
	public static boolean havent_attacked_last_turn = true; 
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
	
	public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
		
		havent_attacked_last_turn = false;
		
	}
	
	@Override	
	public void atTurnStart() {
		if (firstTurnPassed()) {
			
			logger.info("gained_block_last_turn " + gained_block_last_turn);
			logger.info("havent_attacked_last_turn " + havent_attacked_last_turn);
			
			if (relicCanDoItsEffect()) {
				logger.info("effect would happen here");
			}
			
		} else is_first_turn = false;
		
		resetConditionChecks();
	}
	
	private boolean firstTurnPassed() {
		return !is_first_turn;
	}
	
	private boolean relicCanDoItsEffect() {
		return gained_block_last_turn && havent_attacked_last_turn;
	}
	
	private void resetConditionChecks() {
		gained_block_last_turn = false;
		havent_attacked_last_turn = true;
	}
	
	
	
	@Override
	public AbstractRelic makeCopy() {
		return new CombatFatigues();
	}
	
}
