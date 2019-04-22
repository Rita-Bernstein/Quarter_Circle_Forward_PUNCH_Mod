package ww_relics.powers;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import ww_relics.cards.dan.Strike_Dan_Weakest;

public class WeakestFightingStylePower extends AbstractPower {

	public static final String POWER_ID = "WW_Relics:Power_Weakest_Fighting_Style";
	private static final PowerStrings powerStrings = 
			CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
	public static final String NAME = powerStrings.NAME;
	public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
	
	public static ArrayList<AbstractCard> cards_to_spawn;
	
	static Logger logger = LogManager.getLogger(WeakestFightingStylePower.class.getName());
	
	public WeakestFightingStylePower(AbstractCreature owner, int amount)
	{
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.type = AbstractPower.PowerType.BUFF;
		
		updateDescription();
		
		loadRegion("frail");
		
		cards_to_spawn = new ArrayList<AbstractCard>();
		
		//Temporary for testing
		cards_to_spawn.add(new Strike_Dan_Weakest());
	}
	
	public void updateDescription()
	{
		this.description = "test";
	}
	
	@Override
	public void onInitialApplication() {
		
		AbstractCard card_to_spawn = chooseRandomlyWhichCardFromTheArrayListToSpawn();
		
		createCard(card_to_spawn);
		
	}
	
	@Override
	public void atStartOfTurnPostDraw() {
		
		AbstractCard card_to_spawn = chooseRandomlyWhichCardFromTheArrayListToSpawn();
		
		createCard(card_to_spawn);
		
	}
	
	public AbstractCard chooseRandomlyWhichCardFromTheArrayListToSpawn() {
		int random = AbstractDungeon.aiRng.random(cards_to_spawn.size());
		if (random >= cards_to_spawn.size()) {
			random = cards_to_spawn.size() - 1;
			logger.info("Yes, you have to change it to cards_to_spawn.size() - 1");
		}
		return cards_to_spawn.get(random);
	}
	
	public void createCard(AbstractCard card) {
		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card));
	}
	
	
	
}
