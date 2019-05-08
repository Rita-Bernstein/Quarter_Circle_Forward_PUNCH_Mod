package ww_relics.relics.ken;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
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
	
	public RedGi() {
		super(ID, GraphicResources.LoadRelicImage("White_Boots - steeltoe-boots - Lorc - CC BY 3.0.png"),
				RelicTier.UNCOMMON, LandingSound.FLAT);
	}	
	
	public String getUpdatedDescription() {
		return "test";
	}
	
	public AbstractRelic makeCopy() {
		return new RedGi();
	}
}
