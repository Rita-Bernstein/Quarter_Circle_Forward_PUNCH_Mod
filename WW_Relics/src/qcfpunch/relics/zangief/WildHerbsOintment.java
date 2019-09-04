package qcfpunch.relics.zangief;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import qcfpunch.QCFPunch_MiscCode;
import qcfpunch.resources.relic_graphics.GraphicResources;

public class WildHerbsOintment extends CustomRelic  {

	public static final String ID = QCFPunch_MiscCode.returnPrefix() + "WildHerbsOintment";
	
	public final int AMOUNT_OF_MAX_HP_GAINED = 2;
	public final int PERCENTAGE_OF_MAX_HP_TO_LOSE = 40;
	
	public static boolean had_enough_hp_at_combat_start;
	
	public WildHerbsOintment() {
		super(ID, GraphicResources.LoadRelicImage("White_Boots - steeltoe-boots - Lorc - CC BY 3.0.png"),
				RelicTier.UNCOMMON, LandingSound.MAGICAL);
	}
	
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + PERCENTAGE_OF_MAX_HP_TO_LOSE + 
			    DESCRIPTIONS[1] + PERCENTAGE_OF_MAX_HP_TO_LOSE +
			    DESCRIPTIONS[2] + AMOUNT_OF_MAX_HP_GAINED +
			    DESCRIPTIONS[3];
	}
	
	
	@Override
	public void atBattleStartPreDraw() {
		
		if (hasEnoughHP()) {
			//have to check if powers are saved at the end of a combat
			//to avoid making this a relic that needs a save/load
		}
		
		
	}
	
	private boolean hasEnoughHP() {
		
		float current_hp = (float)AbstractDungeon.player.currentHealth;
		float current_max_hp = (float)AbstractDungeon.player.maxHealth;
		
		if (current_hp / current_max_hp * 100 > PERCENTAGE_OF_MAX_HP_TO_LOSE) {
			return true;
		} else return false;
		
	}
	
	public AbstractRelic makeCopy() {
		return new WildHerbsOintment();
	}
	
}
