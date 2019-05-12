package ww_relics.relics.ken;

import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.rooms.MonsterRoom;

import basemod.abstracts.CustomRelic;
import ww_relics.powers.FlamingPower;
import ww_relics.resources.relic_graphics.GraphicResources;

public class UnceasingFlame extends CustomRelic implements ClickableRelic {

	public static final String ID = "WW_Relics:Unceasing_Flame";
	public static final int NUMBER_OF_ATTACKS_TO_TRIGGER_CHARGE_UP = 3;
	public static final int HOW_MUCH_CHARGE_INCREASES_PER_TRIGGER = 1;
	public static final int MAX_NUMBER_OF_CHARGES = 6;
	
	public static boolean is_player_turn = false;
	
	public UnceasingFlame() {
		super(ID, GraphicResources.LoadRelicImage("White_Boots - steeltoe-boots - Lorc - CC BY 3.0.png"),
				RelicTier.RARE, LandingSound.MAGICAL);
		
		setCounter(0);
	}
	
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}
	
	@Override
	public void atTurnStart() {
		is_player_turn = true;
	}
	
	@Override
	public void onPlayerEndTurn() {
		is_player_turn = false;
	}
	
	@Override
	public void onVictory() {
		is_player_turn = false;
	}
	 
	@Override
	public void onPlayCard(AbstractCard c, AbstractMonster m) {

		if ((c.type == CardType.ATTACK) && (counter < MAX_NUMBER_OF_CHARGES)) {
			counter++;
		}
		
	}
	
	@Override
	public void onRightClick() {
		
		if (AbstractDungeon.getCurrRoom() instanceof MonsterRoom) {
			if (effectCanBeTriggered())
				{
				
				if (counter == 6) {
					counter -= 6;
					AbstractDungeon.actionManager.addToBottom(
						new ApplyPowerAction(AbstractDungeon.player,
								AbstractDungeon.player,
							new FlamingPower(AbstractDungeon.player, 1),
							1));
				}
				
			}
			
			
		}			
	}
	
	private boolean effectCanBeTriggered() {
		boolean can_be_triggered = false;
		
		if (is_player_turn
				&& AbstractDungeon.getCurrRoom() instanceof MonsterRoom
				&& !AbstractDungeon.getCurrRoom().isBattleOver
				&& !AbstractDungeon.getCurrRoom().isBattleEnding()
				&& AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT){
			can_be_triggered = true;
		}
		
		return can_be_triggered;
	}
	
	public AbstractRelic makeCopy() {
		return new UnceasingFlame();
	}



}
