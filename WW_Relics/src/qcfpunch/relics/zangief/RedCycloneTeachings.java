package qcfpunch.relics.zangief;

import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardType;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import qcfpunch.QCFPunch_MiscCode;
import qcfpunch.resources.relic_graphics.GraphicResources;

public class RedCycloneTeachings extends CustomRelic  {
	
	public static final String ID = QCFPunch_MiscCode.returnPrefix() + "Red_Cyclone_Teachings";
	
	public static final int MINIMUM_COST_TO_STUN = 3;
	
	public RedCycloneTeachings() {
		super(ID, GraphicResources.LoadRelicImage("White_Boots - steeltoe-boots - Lorc - CC BY 3.0.png"),
				RelicTier.RARE, LandingSound.HEAVY);
	}
	
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + MINIMUM_COST_TO_STUN + 
			    DESCRIPTIONS[1];
	}
	
	@Override
	public void onPlayCard(AbstractCard c, AbstractMonster m) {
		if ((c.type == CardType.ATTACK) &&
			(c.costForTurn >= MINIMUM_COST_TO_STUN)) {
			
			c.exhaust = true;
			AbstractDungeon.actionManager.addToTop(
					new StunMonsterAction(m, AbstractDungeon.player, 1));
		}
		
	}
	
	public AbstractRelic makeCopy() {
		return new RedCycloneTeachings();
	}

}
