package ww_relics.relics.mortal_kombat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;

import basemod.abstracts.CustomRelic;
import ww_relics.resources.relic_graphics.GraphicResources;

public class NeverendingBlood extends CustomRelic {

	public static final String ID = "WW_Relics:Neverending_Blood";
	
	public static final float REGEN_PERCENTAGE_OF_DAMAGE_RECEIVED = 0.34f;
	public static final int MINIMUM_AMOUNT_OF_REGEN_ADDED = 1; 
	public static final int MINIMUM_AMOUNT_OF_HP_LOST = 3;
	
	public static final int COUNTER_INITIAL_VALUE = 7;
	public static final int COUNTER_MAX_VALUE = 100;
	public static final int COUNTER_USED_FOR_EFFECT = 1;
	public static final int COUNTER_RECOVERED_IF_HEALED_OUTSIDE_OF_BATTLE = 2;
	
	public static final Logger logger = LogManager.getLogger(NeverendingBlood.class.getName());
		
	public NeverendingBlood() {
		super(ID, GraphicResources.LoadRelicImage("White_Boots - steeltoe-boots - Lorc - CC BY 3.0.png"),
				RelicTier.SPECIAL, LandingSound.MAGICAL);
		
		this.counter = COUNTER_INITIAL_VALUE;
	}
	
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + COUNTER_INITIAL_VALUE + DESCRIPTIONS[1] +
				DESCRIPTIONS[2] + MINIMUM_AMOUNT_OF_HP_LOST + 
				DESCRIPTIONS[3] + COUNTER_USED_FOR_EFFECT +
				DESCRIPTIONS[4] + MINIMUM_AMOUNT_OF_REGEN_ADDED +
				DESCRIPTIONS[5] +
				DESCRIPTIONS[6] + COUNTER_RECOVERED_IF_HEALED_OUTSIDE_OF_BATTLE +
				DESCRIPTIONS[7];
	}
	
	@Override
	public void onLoseHp(int damage_amount) {

		if ((AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT) && 
			      (damage_amount >= MINIMUM_AMOUNT_OF_HP_LOST) && (this.counter > 0)) {
			
			int regen_to_receive;
			
			int total_regen_to_have_at_the_end =
					(int)Math.floor(damage_amount * REGEN_PERCENTAGE_OF_DAMAGE_RECEIVED);
			
			if (AbstractDungeon.player.hasPower(RegenPower.POWER_ID)) {
				
				int regen_amount_already_there = AbstractDungeon.player.getPower(RegenPower.POWER_ID).amount;

				if (total_regen_to_have_at_the_end > regen_amount_already_there) {
					regen_to_receive = total_regen_to_have_at_the_end - regen_amount_already_there;
				} else return;
				
			} else regen_to_receive = total_regen_to_have_at_the_end;
			
			if (regen_to_receive < MINIMUM_AMOUNT_OF_REGEN_ADDED) {
				regen_to_receive = MINIMUM_AMOUNT_OF_REGEN_ADDED; 
			}
			
			flash();
			AbstractDungeon.actionManager.addToBottom(
					new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
							new RegenPower(AbstractDungeon.player, regen_to_receive),
								regen_to_receive));
			
			this.counter -= COUNTER_USED_FOR_EFFECT;
			if (counter < 0) counter = 0;
			
		}
		
	}
	
	@Override
	public int onPlayerHeal(int healAmount) {
		
		if (AbstractDungeon.getCurrRoom().phase != RoomPhase.COMBAT) {
			flash();
			this.counter += COUNTER_RECOVERED_IF_HEALED_OUTSIDE_OF_BATTLE;
		}
		
		return super.onPlayerHeal(healAmount);
	}
	
	@Override
	
	public AbstractRelic makeCopy() {
		return new NeverendingBlood();
	}
	
}
