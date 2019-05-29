package ww_relics.relics.ryu;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.colorless.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.*;

import basemod.abstracts.CustomRelic;
import ww_relics.relics.character_cameos.sakura.SchoolBackpack;
import ww_relics.resources.relic_graphics.GraphicResources;

public class DuffelBag extends CustomRelic {
	
	public static final String ID = "WW_Relics:Duffel_Bag";
	private static final int NUMBER_OF_STATIC_CARDS = 2;

	private static final int NUMBER_OF_RANDOM_COMMON_RELICS = 2;

	private static final int PRETENDED_NUMBER_OF_EXTRA_REWARDS = NUMBER_OF_STATIC_CARDS +
			NUMBER_OF_RANDOM_COMMON_RELICS;
	
	private ArrayList<AbstractCard> reward_cards;
	
	private int number_of_rewards_left;
	
	private boolean has_relic_been_used_this_battle = false;
	
	public static final Logger logger = LogManager.getLogger(SchoolBackpack.class.getName());
	
	public DuffelBag() {
		super(ID, GraphicResources.LoadRelicImage("Duffel_Bag - swap-bag - Lorc - CC BY 3.0.png"),
				RelicTier.RARE, LandingSound.FLAT);
		reward_cards = new ArrayList<AbstractCard>();
		
		reward_cards.add(new Panacea());
		reward_cards.add(new BandageUp());
		
		SetNumberofRewards(PRETENDED_NUMBER_OF_EXTRA_REWARDS);
	}

	private void SetNumberofRewards(int new_value) {
		number_of_rewards_left = new_value;
		SetCounter(number_of_rewards_left);
	}
	
	private void SetCounter(int number_of_rewards_left) {
		if (number_of_rewards_left > 0) this.counter = number_of_rewards_left;
		else this.counter = -2;
	}
    
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + NUMBER_OF_RANDOM_COMMON_RELICS +
				DESCRIPTIONS[1];
	}
	
	@Override
	public void atPreBattle() {
		
		if (has_relic_been_used_this_battle) {
			has_relic_been_used_this_battle = false;
		}
		
		if ((currentRoomIsAMonsterOrMonsterEliteRoom()) &&
				number_of_rewards_left > 0 && 
				AbstractDungeon.getCurrRoom().rewardAllowed){
			
			AddReward();
			AddNumberOfRewards(-1);
			has_relic_been_used_this_battle = true;
		}
	}
	
	public boolean currentRoomIsAMonsterOrMonsterEliteRoom() {
		return AbstractDungeon.getCurrRoom() instanceof MonsterRoom ||
				AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite;
	}
	
	private void AddNumberOfRewards(int added) {
		number_of_rewards_left += added;
		SetCounter(number_of_rewards_left);
	}
	
	@Override
	public void atBattleStart() {
		if (this.counter == -2)
		{
			ChangeToSecondDescription();
		}
	}
	
	private void ChangeToSecondDescription(){
		this.counter = -3;
		this.description = this.DESCRIPTIONS[2];
		this.tips.clear();
		this.tips.add(new PowerTip(this.name, this.description));
		initializeTips();
	}
	
	public void onUsePotion() {
		
		if ((AbstractDungeon.player.isEscaping) && (has_relic_been_used_this_battle)) {
			AddNumberOfRewards(1);
		}
		
		if (this.counter > 0)
		{
			ChangeToFirstDescription();
		}
		
	}
	
	private void ChangeToFirstDescription() {
		this.description = DESCRIPTIONS[0] + NUMBER_OF_RANDOM_COMMON_RELICS +
				DESCRIPTIONS[1];
		this.tips.clear();
		this.tips.add(new PowerTip(this.name, this.description));
		initializeTips();
	}
	
	private void AddReward() {
		
		if (number_of_rewards_left - reward_cards.size() > 0) {
			
			int card_position = number_of_rewards_left - reward_cards.size();
			
			RewardItem card_reward = new RewardItem();
			card_reward.cards.clear();
			card_reward.cards.add(reward_cards.get(card_position - 1));
			AbstractDungeon.getCurrRoom().addCardReward(card_reward);
			flash();
			
		} else if (number_of_rewards_left > 0) {
			
			AbstractRelic relic = AbstractDungeon.returnRandomRelic(RelicTier.COMMON);
			AbstractDungeon.getCurrRoom().addRelicToRewards(relic);
			flash();
			
		}
		
	}
	
	public static void save(final SpireConfig config) {

        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(ID)) {
    		logger.info("Started saving Duffel Bag information");

    		if (AbstractDungeon.isDungeonBeaten || AbstractDungeon.player.isDead) {
    			clear(config);
    		} 
    		else {
    					
                try {
    				config.save();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
                
    		}

            logger.info("Finished saving Duffel Bag information");
        }
        else {
        	clear(config);
        }

    }
	
		public static void load(final SpireConfig config) {
		
		logger.info("Loading Duffel Bag info.");
		if (AbstractDungeon.player.hasRelic(ID) && config.has("duffel_bag_1")) {
			
            try {
				config.load();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            logger.info("Finished loading Duffel Bag info.");
        }
		
		else
		{
			logger.info("There's no info, setting variables accordingly.");

			logger.info("Finished setting Duffel Bag variables.");
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
		logger.info("Clearing Duffel Bag variables.");
        
        logger.info("Finished clearing Duffel Bag variables.");
	}
	
	public AbstractRelic makeCopy() { // always override this method to return a new instance of your relic
		return new DuffelBag();
	}	
}
