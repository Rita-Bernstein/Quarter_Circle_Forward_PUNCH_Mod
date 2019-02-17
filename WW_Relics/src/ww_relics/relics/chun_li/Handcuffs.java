package ww_relics.relics.chun_li;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;

public class Handcuffs extends CustomRelic {
	public static final String ID = "WW_Relics:Handcuffs";
	
	private static final int NUMBER_OF_USES_PER_FIGHT = 1;
	private static final int NUMBER_OF_STR_DOWN_DEBUFFS = 2;
	private static final int NUMBER_OF_DEX_DOWN_DEBUFFS = 2;
	private static final int NUMBER_OF_STUN_DEBUFFS = 1;
	
	public int number_of_uses_left_in_this_fight;
	public boolean handcuff_is_lost = false;

	public static final Logger logger = LogManager.getLogger(Handcuffs.class.getName());
	
	public Handcuffs() {
		super(ID, "abacus.png", //add method for textures here.
				RelicTier.UNCOMMON, LandingSound.CLINK);
	}
	
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + NUMBER_OF_STR_DOWN_DEBUFFS +
				DESCRIPTIONS[1] + NUMBER_OF_DEX_DOWN_DEBUFFS +
				DESCRIPTIONS[2] + NUMBER_OF_STUN_DEBUFFS +
				DESCRIPTIONS[3];
	}
	
	public void atPreBattle() {
		number_of_uses_left_in_this_fight = NUMBER_OF_USES_PER_FIGHT;
	}
	
	@Override
	public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
		
		boolean owner_not_null = info.owner != null;
		boolean normal_damage_type = info.type == DamageType.NORMAL;
		boolean can_be_used_in_this_fight = number_of_uses_left_in_this_fight > 0;
		
		logger.info(owner_not_null);
		logger.info(normal_damage_type);
		logger.info(can_be_used_in_this_fight);
		
		if ((owner_not_null) && (normal_damage_type) && (can_be_used_in_this_fight)) {
			
			 AbstractDungeon.actionManager.addToTop(
					 new ApplyPowerAction(target,
							 AbstractDungeon.player,
							 new StrengthPower(target, -1 * NUMBER_OF_STR_DOWN_DEBUFFS)));
			 
			 AbstractDungeon.actionManager.addToTop(
					 new ApplyPowerAction(target,
							 AbstractDungeon.player,
							 new DexterityPower(target, -1 * NUMBER_OF_DEX_DOWN_DEBUFFS)));
			
			 number_of_uses_left_in_this_fight--;
		}
		
	}

	public AbstractRelic makeCopy() { // always override this method to return a new instance of your relic
		return new Handcuffs();
	}
}