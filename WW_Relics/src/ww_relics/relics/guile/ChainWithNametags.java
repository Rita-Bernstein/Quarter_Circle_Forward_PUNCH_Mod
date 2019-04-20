package ww_relics.relics.guile;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;

import basemod.abstracts.CustomRelic;
import ww_relics.resources.relic_graphics.GraphicResources;

public class ChainWithNametags extends CustomRelic {
	public static final String ID = "WW_Relics:Chain_With_Nametags";
	
	public static final int AMOUNT_OF_TEMP_HP_GAINED = 1;
	
	public ChainWithNametags() {
		super(ID, GraphicResources.LoadRelicImage("White_Boots - steeltoe-boots - Lorc - CC BY 3.0.png"),
				RelicTier.UNCOMMON, LandingSound.CLINK);
	}
	
	public String getUpdatedDescription() {
		return "test";
	}
	
	public int onPlayerGainedBlock(float blockAmount) {
		
		AbstractDungeon.actionManager.addToBottom(
				new AddTemporaryHPAction(AbstractDungeon.player,
						AbstractDungeon.player, AMOUNT_OF_TEMP_HP_GAINED));
		
		return MathUtils.floor(blockAmount);
	}
	
	@Override
	public AbstractRelic makeCopy() {
		return new ChainWithNametags();
	}

}
