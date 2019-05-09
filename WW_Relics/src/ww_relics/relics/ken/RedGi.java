package ww_relics.relics.ken;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import basemod.abstracts.CustomRelic;
import ww_relics.resources.relic_graphics.GraphicResources;

public class RedGi extends CustomRelic {
	
	public static final String ID = "WW_Relics:Red_Gi";
	
	public static final int CARDS_TO_DRAW = 2;
	public static final int INCREASE_ATTACK_COST_BY = -1;

	
	public static boolean draw_effect = false;
	
	public RedGi() {
		super(ID, GraphicResources.LoadRelicImage("White_Boots - steeltoe-boots - Lorc - CC BY 3.0.png"),
				RelicTier.UNCOMMON, LandingSound.FLAT);
		
		setCounter(0);
	}	
	
	public String getUpdatedDescription() {
		return "test";
	}
	
	@Override
	public void onPlayerEndTurn() {
		this.counter = 0;
	}
	
	@Override
	public void onPlayCard(AbstractCard card, AbstractMonster monster) {
		if (card.type == CardType.ATTACK) {
			this.counter++;
		}
		else this.counter = 0;
		
		if (counter == 2) {
			this.counter -= 2;
			if (counter < 0) counter = 0;
			for (int i = 0; i < CARDS_TO_DRAW; i++) {
				DrawEffect();
			}
			
		}
	}
	
	public void DrawEffect() {
		draw_effect = true;
		flash();
		AbstractDungeon.player.draw();
	}
	
	@Override
	public void onCardDraw(AbstractCard drawnCard) {
		if (draw_effect) {
			draw_effect = false;
			if (drawnCard.type == CardType.ATTACK)
				drawnCard.modifyCostForTurn(INCREASE_ATTACK_COST_BY);
		}
	}
	
	public AbstractRelic makeCopy() {
		return new RedGi();
	}
}
