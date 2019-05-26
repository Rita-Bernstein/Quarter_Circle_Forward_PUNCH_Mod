package ww_relics.relics.character_cameos.sakura;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.AbstractCard.CardColor;
import com.megacrit.cardcrawl.cards.AbstractCard.CardRarity;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardGroup.CardGroupType;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.abstracts.CustomRelic;
import ww_relics.resources.relic_graphics.GraphicResources;

public class SchoolBackpack extends CustomRelic {

	public static final String ID = "WW_Relics:School_Backpack";
	
	public static final int NUMBER_OF_EXTRA_CARDS = 2;
	public static final float CHANCE_OF_UPGRADED_CARDS = 0.1f;
	
	public static int number_of_cards_left = NUMBER_OF_EXTRA_CARDS;
	public static boolean battle_ended_before = false;
	public static boolean empty_relic = false;
	public static int floor_of_last_stored_reward = 0;
	public static RewardItem card_reward;
	public static ArrayList<String> card_reward_rarity = new ArrayList<String>();
	public static ArrayList<String> card_reward_id = new ArrayList<String>();
	public static ArrayList<Boolean> card_reward_upgrade = new ArrayList<Boolean>();
	
	public static String current_description;

	public static final Logger logger = LogManager.getLogger(SchoolBackpack.class.getName());
	
	public SchoolBackpack() {
		super(ID, GraphicResources.LoadRelicImage("White_Boots - steeltoe-boots - Lorc - CC BY 3.0.png"), 
				RelicTier.UNCOMMON, LandingSound.HEAVY);
		
		counter = NUMBER_OF_EXTRA_CARDS;
		
	}
	
	public String getUpdatedDescription() {
		if (current_description == null) return getCommonDescription();
		else return current_description;
	}
	
	public String getCommonDescription() {
		return DESCRIPTIONS[0] + NUMBER_OF_EXTRA_CARDS + DESCRIPTIONS[1];
	}

	public String getEmptyRelicDescription() {
		return DESCRIPTIONS[3];
	}
	
	@Override
	public void atBattleStart() {
		
		if (counter <= 0) {
			ChangeToEmptyRelicDescriptionAndToolTips();
		}
		
	}
	
	public void ChangeToEmptyRelicDescriptionAndToolTips() {
		current_description = getEmptyRelicDescription();
		this.tips.clear();
		this.tips.add(new PowerTip(this.name, current_description));
		initializeTips();
	}
	
	@Override
	public void atPreBattle() {
		boolean no_saved_reward = true;
		
		logger.info("NOW THIS HAS TO GO");
		logger.info(battle_ended_before);
		logger.info(AbstractDungeon.floorNum  == floor_of_last_stored_reward);
		
		if (battle_ended_before && AbstractDungeon.floorNum == floor_of_last_stored_reward) {
			AddSavedReward();
			ifEmptyVanishWithCounterNumber();
			no_saved_reward = false;
		}
		
		if (!empty_relic) {
			
			avoidCounterProblemsBetweenSaves();
			
			if (no_saved_reward && (number_of_cards_left > 0) && (AbstractDungeon.getCurrRoom().rewardAllowed)) {
				if (!battle_ended_before) {
					reduceNumberOfUsesByOne();
					ifEmptyVanishWithCounterNumber();
					AddReward();
					floor_of_last_stored_reward = AbstractDungeon.floorNum;
				}
			}
		}
			
		if (!empty_relic && counter <= 0) empty_relic = true;
		
	}
	
	public void avoidCounterProblemsBetweenSaves() {
		if (number_of_cards_left <= 0) {
			counter = number_of_cards_left;
		} else if ((counter != number_of_cards_left)){
			number_of_cards_left = NUMBER_OF_EXTRA_CARDS;
			counter = NUMBER_OF_EXTRA_CARDS;
		}
	}
	
	public void reduceNumberOfUsesByOne() {
		number_of_cards_left--;
		counter = number_of_cards_left;
	}
	
	public void ifEmptyVanishWithCounterNumber() {
		if (counter <= 0) {
			number_of_cards_left = -2;
			counter = -2;
		}
	}

	private void AddReward() {
		
		PlayerClass reward_class = getRandomBaseGameNotYoursPlayerClass();
		
		card_reward = createReward(reward_class);
		AbstractDungeon.getCurrRoom().addCardReward(card_reward);
		
		flash();
		
	}
	
	private PlayerClass getRandomBaseGameNotYoursPlayerClass() {
		
		ArrayList<PlayerClass> basegame_classes = new ArrayList<PlayerClass>();
		
		for (PlayerClass base_game_player_class : PlayerClass.values()) {
			
			if (base_game_player_class != AbstractDungeon.player.chosenClass) {
				basegame_classes.add(base_game_player_class);
			}
			
		}
		
		return basegame_classes.get(AbstractDungeon.cardRng.random(basegame_classes.size() - 1));

	}
	
	public RewardItem createReward(PlayerClass reward_class) {
		RewardItem reward_created = new RewardItem();
		reward_created.cards.clear();
		reward_created.cards = createCardsFromOtherClassForReward(reward_class);
		reward_created.text = DESCRIPTIONS[2];
		return reward_created;
	}
	
	private ArrayList<AbstractCard> createCardsFromOtherClassForReward(PlayerClass a_class) {
		
		ArrayList<AbstractCard> basic_array_of_cards = new ArrayList<AbstractCard>();
	    
	    int num_cards = 3;
	    num_cards = circunstancesThatChangeCardNumber(num_cards);
	    
	    AbstractCard.CardRarity rarity;
	    for (int i = 0; i < num_cards; i++)
	    {
	    	rarity = AbstractDungeon.rollRarity();
	    	AbstractCard card = null;
	    	
	    	applyRandomRarityToCard(rarity);
	    	
	    	card = getCardAvoidingDuplicates(basic_array_of_cards, rarity, a_class);
	    	
	        if (card != null) {
	        	basic_array_of_cards.add(card);
	        }
	    }
	    ArrayList<AbstractCard> array_of_cards_with_possible_upgrades = new ArrayList<AbstractCard>();
	    for (AbstractCard c : basic_array_of_cards) {
	    	array_of_cards_with_possible_upgrades.add(c.makeCopy());
	    }
	    for (AbstractCard c : array_of_cards_with_possible_upgrades) {
	    	if ((c.rarity != AbstractCard.CardRarity.RARE) && 
	    			(AbstractDungeon.cardRng.randomBoolean(CHANCE_OF_UPGRADED_CARDS)) && (c.canUpgrade())) {
	    		c.upgrade();
	    	} else if ((c.type == AbstractCard.CardType.ATTACK) && 
	    			(AbstractDungeon.player.hasRelic("Molten Egg 2"))) {
	    		c.upgrade();
	    	} else if ((c.type == AbstractCard.CardType.SKILL) &&
	    			(AbstractDungeon.player.hasRelic("Toxic Egg 2"))) {
	    		c.upgrade();
	    	} else if ((c.type == AbstractCard.CardType.POWER) &&
	    			(AbstractDungeon.player.hasRelic("Frozen Egg 2"))) {
		        c.upgrade();
	    	}
	    }
	    return array_of_cards_with_possible_upgrades;
	}
	
	public void applyRandomRarityToCard(CardRarity rarity) {
		switch (rarity)
    	{
    		case RARE: 
    			AbstractDungeon.cardBlizzRandomizer = AbstractDungeon.cardBlizzStartOffset;
    		break;
    		case UNCOMMON: 
    			break;
    		case COMMON: 
    			AbstractDungeon.cardBlizzRandomizer -= AbstractDungeon.cardBlizzGrowth;
		        if (AbstractDungeon.cardBlizzRandomizer <= AbstractDungeon.cardBlizzMaxOffset) {
		        	AbstractDungeon.cardBlizzRandomizer = AbstractDungeon.cardBlizzMaxOffset;
		        }
		        break;
    		default: 
    			logger.info("Paraphrasing the base game code: WTF?");
    	}
	}
	
	private int circunstancesThatChangeCardNumber(int num_cards) {
		if (AbstractDungeon.player.hasRelic("Question Card")) 	num_cards++;
		if (AbstractDungeon.player.hasRelic("Busted Crown")) 	num_cards -= 2;
		if (ModHelper.isModEnabled("Binary")) 					num_cards--;
	
		return num_cards;
	}

	private static AbstractCard getCardAvoidingDuplicates(ArrayList<AbstractCard> array_of_cards_to_check, 
			CardRarity rarity, PlayerClass a_class) {
		
		AbstractCard card = null;
		
		boolean contains_duplicate = true;
		while (contains_duplicate)
		{
    		contains_duplicate = false;
    		card = getCard(rarity, a_class);

    		for (AbstractCard c : array_of_cards_to_check) {
	        	if (c.cardID.equals(card.cardID))
	        	{
	        		contains_duplicate = true;
	        		break;
	        	}
	      	}
    	}
		
		return card;
	}	
	
	@SuppressWarnings("incomplete-switch")
	public static AbstractCard getCard(AbstractCard.CardRarity rarity, PlayerClass a_class)
	{
		CardGroup rare_class_CardPool = new CardGroup(CardGroupType.UNSPECIFIED);
		CardGroup uncommon_class_CardPool = new CardGroup(CardGroupType.UNSPECIFIED);
		CardGroup common_class_CardPool = new CardGroup(CardGroupType.UNSPECIFIED);
		
		CardColor class_color = getColorOfBaseGameClass(a_class);

		for (Map.Entry<String, AbstractCard> a_card : CardLibrary.cards.entrySet()) {
			AbstractCard one_more_card = a_card.getValue();
			
			if (cardIsOfChosenColor(one_more_card, class_color)) {
				
				CardRarity this_card_rarity = ((AbstractCard)one_more_card).rarity;
			
				switch (this_card_rarity) {
					case RARE:
						rare_class_CardPool.addToBottom((AbstractCard)one_more_card);
						break;
				    case UNCOMMON: 
				    	uncommon_class_CardPool.addToBottom((AbstractCard)one_more_card);
				    	break;
				    case COMMON:
				    	common_class_CardPool.addToBottom((AbstractCard)one_more_card);
				    	break;
				}
					
			}
		}		
		
		switch (rarity)
		{
			case SPECIAL:  return rare_class_CardPool.getRandomCard(true);
		    case RARE:     return rare_class_CardPool.getRandomCard(true);
		    case UNCOMMON: return uncommon_class_CardPool.getRandomCard(true);
		    case COMMON:   return common_class_CardPool.getRandomCard(true);
		    case CURSE:    return common_class_CardPool.getRandomCard(true);
		    case BASIC:    return common_class_CardPool.getRandomCard(true);
	    }
	    logger.info("Paraphrasing the base code comment: No rarity on getCard in Abstract Dungeon");
	    return null;
	}
	
	public static CardColor getColorOfBaseGameClass(PlayerClass a_class) {
		
		CardColor class_color = CardColor.COLORLESS;
		
		if (a_class == PlayerClass.DEFECT) 				class_color = CardColor.BLUE;
		else if (a_class == PlayerClass.THE_SILENT) 	class_color = CardColor.GREEN;
		else if (a_class == PlayerClass.IRONCLAD) 		class_color = CardColor.RED;
		
		return class_color;
	}
	
	public static boolean cardIsOfChosenColor(AbstractCard one_card, CardColor class_color) {
		
		AbstractCard card = (AbstractCard)one_card;
		
		return (card.color == class_color) && (card.type != AbstractCard.CardType.STATUS) &&
				(card.type != AbstractCard.CardType.CURSE);
		
	}
	
	public void AddSavedReward() {
		
		card_reward = new RewardItem();
		card_reward.cards = new ArrayList<AbstractCard>();
				
		int size = card_reward_id.size();
		
		for (int i = 0; i < size; i++) {
			
			AbstractCard reward_card;
						
			reward_card = CardLibrary.cards.get(card_reward_id.get(i)).makeCopy();
			
			logger.info(reward_card.name);
			
			if (card_reward_upgrade.get(i)) {
				reward_card.upgrade();
			}
			
			card_reward.text = DESCRIPTIONS[1];
			card_reward.cards.add(reward_card);	
		
		}
			
		AbstractDungeon.getCurrRoom().addCardReward(card_reward);
		flash();
		
	}

	@Override
	public void onEnterRoom(AbstractRoom room) {
		logger.info("AAAA");
	}
	
	public static void save(final SpireConfig config) {

        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(ID)) {
    		logger.info("Started saving School Backpack information");

    		if (AbstractDungeon.isDungeonBeaten || AbstractDungeon.player.isDead) {
    			clear(config);
    		} 
    		else {
    			
    			if (AbstractDungeon.getCurrRoom().isBattleOver) {
    				config.setBool("school_backpack_2", true);
    			} else config.setBool("school_backpack_2", false);
    			
    			//No, I don't understand how these next four lines fixed a bug. Go figure.
    			//AND this is the possible culprit, changing it now
    			if (AbstractDungeon.getCurrRoom().isBattleOver && number_of_cards_left > 0) {
        			config.setInt("school_backpack_1", number_of_cards_left + 1);
        		}
        		else {
        			config.setInt("school_backpack_1", number_of_cards_left);
        		}
    			

                
                config.setInt("school_backpack_3", floor_of_last_stored_reward);
                
                config.setBool("school_backpack_4", empty_relic);
                
                storeCardRewardCreated(config, card_reward);
    			
                try {
    				config.save();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}

            logger.info("Finished saving School Backpack information");
        }
        else {
        	clear(config);
        }

    }
	
	public static void storeCardRewardCreated(final SpireConfig config, RewardItem card_reward) {
		
		if (card_reward == null) {
        	config.setInt("school_backpack_reward_size", 0);
        } else config.setInt("school_backpack_reward_size", card_reward.cards.size());
        
        if (card_reward != null) {
        	for (int i = 0; i < card_reward.cards.size(); i++) {
            	config.setString("school_backpack_reward_" + String.valueOf(i),
            			card_reward.cards.get(i).cardID);
            	config.setString("school_backpack_reward_rarity_" + String.valueOf(i),
            			card_reward.cards.get(i).rarity.toString());
            	config.setBool("school_backpack_reward_upgrade_" + String.valueOf(i),
            			card_reward.cards.get(i).upgraded);
            }
        }
		
	}
	
	public static void load(final SpireConfig config) {
		
		logger.info("Loading School Backpack info.");
		if (AbstractDungeon.player.hasRelic(ID) && config.has("school_backpack_1")) {

			number_of_cards_left = config.getInt("school_backpack_1");
			
			battle_ended_before = config.getBool("school_backpack_2");
			
			loadCardRewardStored(config);
			
			floor_of_last_stored_reward = config.getInt("school_backpack_3");
			
			empty_relic = config.getBool("school_backpack_4");
			
            try {
				config.load();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            logger.info("Finished loading School Backpack info.");
        }
		
		else
		{
			logger.info("There's no info, setting variables accordingly.");

			logger.info("Finished setting School Backpack variables.");
		}
    }
	
	public static void loadCardRewardStored(final SpireConfig config) {
		
		int size = config.getInt("school_backpack_reward_size");
		
		for (int i = 0; i < size; i++) {
			
			card_reward_rarity.add(config.getString("school_backpack_reward_rarity_" + String.valueOf(i)));			
			card_reward_id.add(config.getString("school_backpack_reward_" + String.valueOf(i)));
			card_reward_upgrade.add(config.getBool("school_backpack_reward_upgrade_" + String.valueOf(i)));
		
		}
	}
	
	public static void clear(final SpireConfig config) {
		logger.info("Clearing School Backpack variables.");
        config.remove("school_backpack_1");
        config.remove("school_backpack_2");
        config.remove("school_backpack_3");
        config.remove("school_backpack_4");
        
        if (config.has("school_backpack_reward_size")) {
        	clearCardRewardStored(config);
        }
        
        logger.info("Finished clearing School Backpack variables.");
	}
	
	public static void clearCardRewardStored(final SpireConfig config) {
		
		int size = config.getInt("school_backpack_reward_size");
        
		for (int i = 0; i < size; i++) {
			
			config.remove("school_backpack_reward_rarity_" + String.valueOf(i));			
			config.remove("school_backpack_reward_" + String.valueOf(i));
			config.remove("school_backpack_reward_upgrade_" + String.valueOf(i));
		
		}
		
		config.remove("school_backpack_reward_size");
		
	}
	
	@Override
	public CustomRelic makeCopy() {
		return new SchoolBackpack();
	}

}
