package ww_relics.powers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class WeakestFightingStylePower extends AbstractPower {

	public static final String POWER_ID = "WW_Relics:Power_Weakest_Fighting_Style";
	private static final PowerStrings powerStrings = 
			CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	
	public static ArrayList<String> cards_id_to_spawn;
	
	public WeakestFightingStylePower(AbstractCreature owner, int amount)
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.type = AbstractPower.PowerType.BUFF;
		
		updateDescription();
		
		loadRegion("frail");
		
		cards_id_to_spawn = new ArrayList<String>();
		
		//Temporary for testing
		cards_id_to_spawn.add("Shiv");
	}
	
	public void updateDescription()
	{
		this.description = "test";
	}
	
	public void atStartOfTurnPostDraw() {
		
		
		
	}
	
	public void CreateCard(String id) {
		
	}
	
	
	
}
