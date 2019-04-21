package ww_relics.relics.guile;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.PenNibPower;
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
	@Override
	public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction) {
		
		if (targetCard.type == targetCard.type.ATTACK) {
			havent_attacked_last_turn = false;
		}
	}
	
	@Override	
	public void atTurnStart() {
		if (firstTurnPassed()) {
			
			logger.info("gained_block_last_turn " + gained_block_last_turn);
			logger.info("havent_attacked_last_turn " + havent_attacked_last_turn);
			
			if (relicCanDoItsEffect()) {
				giveDoubleDamage();
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
	
	private void giveDoubleDamage() {
		 AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
	     AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PenNibPower(AbstractDungeon.player, 1), 1, true));
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
