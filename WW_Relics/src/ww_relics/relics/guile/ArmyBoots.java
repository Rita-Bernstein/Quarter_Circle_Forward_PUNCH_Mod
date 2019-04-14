package ww_relics.relics.guile;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import ww_relics.resources.relic_graphics.GraphicResources;

public class ArmyBoots extends CustomRelic  {
	public static final String ID = "WW_Relics:Army_Boots";
	
	private static List<String> powers_affected_by_relic;
	
	static Logger logger = LogManager.getLogger(ArmyBoots.class.getName());

	public ArmyBoots() {
		super(ID, GraphicResources.LoadRelicImage("White_Boots - steeltoe-boots - Lorc - CC BY 3.0.png"),
				RelicTier.COMMON, LandingSound.SOLID);
		
		powers_affected_by_relic.add("Frail");
		powers_affected_by_relic.add("Vulnerable");
	}
	
	public String getUpdatedDescription() {
		return "test";
	}
	
	@Override
	public void onBlockBroken(AbstractCreature m) {
		
		logger.info("1");
		if (m == AbstractDungeon.player) {
			logger.info("2");
			AbstractPlayer player = AbstractDungeon.player;
			
			for (String power: powers_affected_by_relic){
				if (player.hasPower(power)) {
					logger.info(power);
				}
			}
		}
		
	}
	
	public AbstractRelic makeCopy() {
		return new ArmyBoots();
	}	
}
