package qcfpunch.relics.zangief;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import qcfpunch.QCFPunch_MiscCode;
import qcfpunch.powers.WildHerbsOintmentPower;
import qcfpunch.resources.relic_graphics.GraphicResources;

public class WildHerbsOintment extends CustomRelic  {

	public static final String ID = QCFPunch_MiscCode.returnPrefix() + "Wild_Herbs_Ointment";
	
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
			
			AbstractPlayer player = AbstractDungeon.player;
			int HP_to_have_at_most_in_victory =
					player.currentHealth - 
						(int)(player.maxHealth * (PERCENTAGE_OF_MAX_HP_TO_LOSE));
					
			
			WildHerbsOintmentPower wild_herbs_power =
					new WildHerbsOintmentPower(player,
							AMOUNT_OF_MAX_HP_GAINED,
							HP_to_have_at_most_in_victory);
			
			AbstractDungeon.actionManager.addToBottom(
					new ApplyPowerAction(player, player, wild_herbs_power));
			
		}
		
	}
	
	private boolean hasEnoughHP() {
		
		float current_hp = (float)AbstractDungeon.player.currentHealth;
		float current_max_hp = (float)AbstractDungeon.player.maxHealth;
		
		if (current_hp / current_max_hp * 100 > PERCENTAGE_OF_MAX_HP_TO_LOSE) {
			
			int value_to_lose = (int)(current_max_hp * (PERCENTAGE_OF_MAX_HP_TO_LOSE));
			if (current_hp - value_to_lose > 0)
				return true;
			else return false;
			
		} else return false;
		
	}
	
	public AbstractRelic makeCopy() {
		return new WildHerbsOintment();
	}
	
}
