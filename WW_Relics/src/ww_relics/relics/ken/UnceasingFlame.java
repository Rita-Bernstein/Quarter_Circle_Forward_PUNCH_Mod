package ww_relics.relics.ken;

import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DoubleDamagePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.rooms.MonsterRoom;

import basemod.abstracts.CustomRelic;
import ww_relics.resources.relic_graphics.GraphicResources;

public class UnceasingFlame extends CustomRelic implements ClickableRelic {

	public static final String ID = "WW_Relics:Unceasing_Flame";
	public static final int NUMBER_OF_ATTACKS_TO_TRIGGER_CHARGE_UP = 3;
	public static final int HOW_MUCH_CHARGE_INCREASES_PER_TRIGGER = 1;
	public static final int MAX_NUMBER_OF_CHARGES = 6;
	
	public UnceasingFlame() {
		super(ID, GraphicResources.LoadRelicImage("White_Boots - steeltoe-boots - Lorc - CC BY 3.0.png"),
				RelicTier.RARE, LandingSound.MAGICAL);
		
		setCounter(6);
	}
	
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}
	
	@Override
	public void onRightClick() {
		
		if (AbstractDungeon.getCurrRoom() instanceof MonsterRoom) {
			if ((!AbstractDungeon.getCurrRoom().isBattleOver) &&
					(AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT)) {
				
				if (counter == 6) {
					counter -= 6;
					AbstractDungeon.actionManager.addToBottom(
						new ApplyPowerAction(AbstractDungeon.player,
								AbstractDungeon.player,
							new DoubleDamagePower(AbstractDungeon.player, 1, false),
							1));
				}
				
			}
			
			
		}			
	}
	
	public AbstractRelic makeCopy() {
		return new UnceasingFlame();
	}



}
