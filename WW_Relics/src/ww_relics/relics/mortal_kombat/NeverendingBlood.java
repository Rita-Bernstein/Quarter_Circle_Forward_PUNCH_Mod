package ww_relics.relics.mortal_kombat;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;

import basemod.abstracts.CustomRelic;
import ww_relics.resources.relic_graphics.GraphicResources;

public class NeverendingBlood extends CustomRelic {

	public static final String ID = "WW_Relics:Neverending_Blood";
	
	public static final int REGEN_AMOUNT = 1;
	public static final int COUNTER_MAX_VALUE = 100;
		
	public NeverendingBlood() {
		super(ID, GraphicResources.LoadRelicImage("White_Boots - steeltoe-boots - Lorc - CC BY 3.0.png"),
				RelicTier.SPECIAL, LandingSound.MAGICAL);
	}
	
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + REGEN_AMOUNT +
				DESCRIPTIONS[1] + DESCRIPTIONS[2] + DESCRIPTIONS[3];
	}
	
	@Override
	public void onLoseHp(int damageAmount) {
		
		if ((AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT) && 
			      (damageAmount > 0)) {
			
			flash();
			AbstractDungeon.actionManager.addToBottom(
					new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
							new RegenPower(AbstractDungeon.player, REGEN_AMOUNT), REGEN_AMOUNT));
		}
		
	}
	
	public AbstractRelic makeCopy() {
		return new NeverendingBlood();
	}
	
}
