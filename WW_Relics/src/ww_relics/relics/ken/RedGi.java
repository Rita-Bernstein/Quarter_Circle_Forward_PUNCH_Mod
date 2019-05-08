package ww_relics.relics.ken;

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
	public static final int REDUCE_ATTACK_COST_BY = 1;
	
	public static final AbstractPower POWER_TO_APPLY =
			new VulnerablePower(AbstractDungeon.player, 1, false);
	
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
	public void onPlayCard(AbstractCard card, AbstractMonster monster) {
		if (card.type == CardType.ATTACK) {
			this.counter++;
			
		}
		
		if (counter == 2) {
			this.counter -= 2;
			if (counter < 0) counter = 0;
			DrawEffect();
		}
	}
	
	public void DrawEffect() {
		draw_effect = true;
		AbstractDungeon.player.draw();
	}
	
	@Override
	public void onCardDraw(AbstractCard drawnCard) {
		if (draw_effect) {
			draw_effect = false;
			AbstractDungeon.player.heal(1);
		}
		
	}
	
	public AbstractRelic makeCopy() {
		return new RedGi();
	}
}
