package ww_relics.relics.ken;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import ww_relics.relics.guile.ArmyBoots;
import ww_relics.resources.relic_graphics.GraphicResources;

public class RedGi extends CustomRelic {
	public static final String ID = "WW_Relics:Red_Gi";
	
	
	public RedGi() {
		super(ID, GraphicResources.LoadRelicImage("White_Boots - steeltoe-boots - Lorc - CC BY 3.0.png"),
				RelicTier.COMMON, LandingSound.FLAT);
	}
	
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}
	
	private void RelicVisualEffects() {
		flash();
		AbstractDungeon.actionManager.addToBottom(new RelicAboveCreatureAction(AbstractDungeon.player, this));
	}
	
	public AbstractRelic makeCopy() {
		return new ArmyBoots();
	}

}
