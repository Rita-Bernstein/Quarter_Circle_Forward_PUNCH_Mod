package ww_relics.relics.ryu;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.mod.stslib.relics.ClickableRelic;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;

import basemod.abstracts.CustomRelic;

import java.util.ArrayList;
import java.util.Random;

public class FightingGloves extends CustomRelic implements ClickableRelic {
	
	public static final Logger logger = LogManager.getLogger(FightingGloves.class.getName());
	
	public static final String ID = "WW_Relics:Fighting_Gloves";
	private static final int INITIAL_CHARGES = 1;
	private static int positive_charges = INITIAL_CHARGES;
	private static final int MULTIPLE_THAT_INCREASES_CHARGES = 4;
	private static int rooms_visited = 0;
	
	private static int number_of_cards_that_can_be_upgraded;
	
	private static boolean cards_upgraded_in_this_room = false;
	private static int number_of_cards_upgraded_in_this_room = 0;
	
	public FightingGloves() {
		super(ID, "abacus.png", //add method for textures here.
				RelicTier.RARE, LandingSound.SOLID);
		belowZeroCheck();
		counter = INITIAL_CHARGES;
	}
	
	public static int getCharges() {
		return positive_charges;
	}
	
	public static void setCharges(int value) {
		positive_charges = value;
		belowZeroCheck();
		logger.info("New value for positive_charges is " + positive_charges);
	}
	
	public static void belowZeroCheck() {
		if (positive_charges < 0) {
			logger.info("WARNING - For some reason, FightingGloves.positive_charges was with " +
					positive_charges + " value.");
			positive_charges = 0;
			logger.info("And it's now 0. Tell the developer about it? Thanks!");
		}
		
	}
	
	public static void addCharges(int value_to_add) {
		positive_charges += value_to_add;
		belowZeroCheck();
		logger.info("New value for positive_charges is " + positive_charges);
	}
	
	public static void removeCharges(int value_to_subtract) {
		positive_charges -= value_to_subtract;
		belowZeroCheck();
		logger.info("New value for positive_charges is " + positive_charges);
	}
	
	public String getUpdatedDescription() {
		String description = "Something wrong happened, please warn the programmer!";
	
		description = DESCRIPTIONS[0] + DESCRIPTIONS[1] + MULTIPLE_THAT_INCREASES_CHARGES +
					DESCRIPTIONS[2] + DESCRIPTIONS[3] + DESCRIPTIONS[4] +
					INITIAL_CHARGES + DESCRIPTIONS[5] + DESCRIPTIONS[6];

		return description;
		
	}
	
	public String getCardGridDescription() {
		String description = "Something wrong happened, please warn the programmer!";
		
		if (positive_charges > 1) {
			
			description = DESCRIPTIONS[7] + positive_charges +
					DESCRIPTIONS[9] + DESCRIPTIONS[10];
			
		} else {
			
			description = DESCRIPTIONS[7] + positive_charges +
					DESCRIPTIONS[8] + DESCRIPTIONS[10];
			
		}
		
		return description;

	}
	
	public void onEnterRoom(AbstractRoom room) {
		cards_upgraded_in_this_room = false;
		number_of_cards_upgraded_in_this_room = 0;
		rooms_visited++;
		logger.info("rooms_visited " + rooms_visited);
		if (rooms_visited % MULTIPLE_THAT_INCREASES_CHARGES == 0) {
			addCharges(1);
			flash();
		}
		counter = positive_charges;
	}
	
	@Override
	public void onRightClick() {
		
		if ((positive_charges > 0) && (getValidCardGroup().size() > 0)) {
			if (AbstractDungeon.getCurrRoom() instanceof RestRoom) {
				logger.info("Here the relic should work!");
				upgradingCards();
			}
		}
		
	}
	
	public void upgradingCards() {

		AbstractDungeon.dynamicBanner.hide();
		AbstractDungeon.overlayMenu.cancelButton.hide();
		AbstractDungeon.previousScreen = AbstractDungeon.screen;
		
		AbstractDungeon.getCurrRoom().phase = RoomPhase.INCOMPLETE;
		
		if (getValidCardGroup().size() >= positive_charges) {

			number_of_cards_that_can_be_upgraded = positive_charges;
			
		} else {
			
			number_of_cards_that_can_be_upgraded = getValidCardGroup().size();
			
		}
		
		AbstractDungeon.gridSelectScreen.open(getValidCardGroup(),
				number_of_cards_that_can_be_upgraded,
				getCardGridDescription(), false, false, false, false);
		
	}
	
	private CardGroup getValidCardGroup() {
		
		CardGroup valid_card_group = new CardGroup(CardGroupType.UNSPECIFIED);
		CardGroup master_deck = AbstractDungeon.player.masterDeck;
		
		
		for (AbstractCard c : master_deck.group){
			if (c.canUpgrade()) {
				valid_card_group.addToTop(c);
			}
		}
		
		return valid_card_group;
		
	}
	
	public void update()
	{
		super.update();
		
		/*logger.info("cards " + !cards_upgraded_in_this_room);
		logger.info("rest " + (AbstractDungeon.getCurrRoom() instanceof RestRoom));
		logger.info("grid + " + (AbstractDungeon.gridSelectScreen.selectedCards.size() ==
					number_of_cards_that_can_be_upgraded));*/
		
		if (isTimeToUpgradeTheChosenCards())
	    {
			logger.info("Step 1");
			
            flash();
			
			ArrayList<AbstractCard> cards_chosen = getCardsToUpgrade();
			
			upgradeChosenCards(cards_chosen);
			
			addCharges(-number_of_cards_that_can_be_upgraded);
			
			counter = positive_charges;
			
			logger.info("It now has " + positive_charges + " charges.");
			
			if (number_of_cards_upgraded_in_this_room == number_of_cards_that_can_be_upgraded)
				cards_upgraded_in_this_room = true;
			
			AbstractDungeon.gridSelectScreen.selectedCards.clear();
			
			AbstractDungeon.overlayMenu.hideBlackScreen();
			AbstractDungeon.dynamicBanner.appear();
			AbstractDungeon.isScreenUp = false;
			
	    }
	}
	
	private static boolean isTimeToUpgradeTheChosenCards() {
		
		boolean relic_did_not_upgrade_cards_here = !cards_upgraded_in_this_room;
		boolean i_am_in_a_rest_room = AbstractDungeon.getCurrRoom() instanceof RestRoom;
		boolean all_cards_to_upgrade_have_been_chosen = 
				AbstractDungeon.gridSelectScreen.selectedCards.size() ==
					number_of_cards_that_can_be_upgraded;
		
		return relic_did_not_upgrade_cards_here && i_am_in_a_rest_room && 
				all_cards_to_upgrade_have_been_chosen;
	}
	
	private static ArrayList<AbstractCard> getCardsToUpgrade() {
		return AbstractDungeon.gridSelectScreen.selectedCards;
	}
	
	private static void upgradeChosenCards(ArrayList<AbstractCard> chosen_cards) {
		
		float x = Settings.WIDTH;
        float y = Settings.HEIGHT;
        Random random = new Random();
        float random_x;
        float random_y;
		
		for (AbstractCard c: chosen_cards) {
    		c.upgrade();
			logger.info("Upgraded " + c.name);
    		
    		random_x = random.nextFloat() / 2 + 0.25f;
    		random_y = random.nextFloat() / 4 + 0.25f;
    		
            AbstractDungeon.effectList.add(new ShowCardBrieflyEffect(
    				c.makeStatEquivalentCopy(), random_x * x, random_y * y));
            
            number_of_cards_upgraded_in_this_room++;
    	}
	}
	
	public static void save(final SpireConfig config) {

        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(ID)) {
    		logger.info("Started saving Fighting Gloves information");

            config.setInt("fighting_gloves_1", rooms_visited);
            config.setInt("fighting_gloves_2", positive_charges);
            
            try {
				config.save();
			} catch (IOException e) {
				e.printStackTrace();
			}
            logger.info("Finished saving Fighting Gloves info.");
        }
        else {
        	clear(config);
        }

    }
	
	public static void load(final SpireConfig config) {
		
		logger.info("Loading Fighting Gloves info.");
		if (AbstractDungeon.player.hasRelic(ID) && config.has("fighting_gloves_1")) {

			rooms_visited = config.getInt("fighting_gloves_1");
			setCharges(config.getInt("fighting_gloves_2"));
			
            try {
				config.load();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            logger.info("Finished loading Fighting Gloves info.");
        }
		
		else
		{
			logger.info("There's no info, setting variables accordingly.");

			logger.info("Finished setting Fighting Gloves variables.");
		}
		
		
    }
	
	public static void clear(final SpireConfig config) {
		logger.info("Clearing Fighting Gloves variables.");
        config.remove("fighting_gloves_1");
        config.remove("fighting_gloves_2");
        logger.info("Finished clearing Fighting Gloves variables.");
	}

	public AbstractRelic makeCopy() { // always override this method to return a new instance of your relic
		return new FightingGloves();
	}

}