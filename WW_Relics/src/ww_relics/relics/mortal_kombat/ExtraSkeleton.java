package ww_relics.relics.mortal_kombat;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.BufferPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import ww_relics.resources.relic_graphics.GraphicResources;

public class ExtraSkeleton extends CustomRelic {

	public static final String ID = "WW_Relics:Extra_Skeleton";
	
	public static final int COUNTER_INITIAL_VALUE = 0;
	public static final int COUNTER_INCREASE_VALUE = 1;
	public static final int COUNTER_MAX_VALUE = 5;
	
	public static final int BUFFER_POWER_AMOUNT_ADDED = 1;
	
	public ExtraSkeleton() {
		super(ID, GraphicResources.LoadRelicImage("White_Boots - steeltoe-boots - Lorc - CC BY 3.0.png"),
				RelicTier.SPECIAL, LandingSound.MAGICAL);
		
		this.counter = COUNTER_INITIAL_VALUE;
	}
	
	@Override
	public void atTurnStart() {
		
		this.counter++;
		if (counter >= COUNTER_MAX_VALUE) {
			
			flash();
			AbstractDungeon.actionManager.addToBottom(
					new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
							new BufferPower(AbstractDungeon.player, BUFFER_POWER_AMOUNT_ADDED)));
			this.counter = 0;
			
		}
		
		super.atTurnStart();
	}
	
	@Override
	public AbstractRelic makeCopy() {
		return new ExtraSkeleton();
	}
}
