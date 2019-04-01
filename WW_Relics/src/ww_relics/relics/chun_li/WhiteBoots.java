package ww_relics.relics.chun_li;

import java.io.IOException;

import org.apache.logging.log4j.*;

import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import ww_relics.resources.relic_graphics.GraphicResources;

public class WhiteBoots extends CustomRelic {
	public static final String ID = "WW_Relics:White_Boots";
	/*private static final int CONSTANT_DAMAGE = 1;
	private static final int DAMAGE_FOR_EACH_UPGRADE = 1;
	private static final int CARDS_DREW_FOR_MULTIPLIER = 3;
	private static final int SIZE_OF_MULTIPLIER = 2;*/
	
	private static int number_of_cards_drew;
	
	public int original_cost;
	
	public int[] copied_cards_x_position = {100, 120, 120, 100};
	public int[] copied_cards_y_position = {100, 80, -80, -100};
	
	public boolean effect_happened = false;
	
	public static final Logger logger = LogManager.getLogger(WhiteBoots.class.getName());
	
	public WhiteBoots() {
		super(ID, GraphicResources.LoadRelicImage("White_Boots - steeltoe-boots - Lorc - CC BY 3.0.png"),
			RelicTier.UNCOMMON, LandingSound.SOLID);
		
		/*white_boots_texture = GraphicResources.LoadRelicImage("White_Boots - steeltoe-boots - Lorc - CC BY 3.0.png");
		spent_white_boots_texture = GraphicResources.LoadRelicImage("White_Boots spent - steeltoe-boots - Lorc - CC BY 3.0.png");*/

	}
	
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + DESCRIPTIONS[1] + DESCRIPTIONS[2]; /*+ NUMBER_OF_CHOSEN_ATTACKS +
				DESCRIPTIONS[3] + DESCRIPTIONS[4] + NUMBER_OF_COPIES + DESCRIPTIONS[5] +
				EFFECT_ON_COST_READABLE + DESCRIPTIONS[6];*/
	}

	public boolean canSpawn()
	{
		return AbstractDungeon.player.masterDeck.getAttacks().size() > 0;
	}
	
	public static void save(final SpireConfig config) {

        if (AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(ID)) {
    		logger.info("Started saving White Boots information");
        	final WhiteBoots relic = (WhiteBoots)AbstractDungeon.player.getRelic(ID);

            /*config.setInt("White_Boots_number_of_uses",
            		relic.number_of_uses_left_in_this_fight);*/
            
            try {
				config.save();
			} catch (IOException e) {
				e.printStackTrace();
			}
            logger.info("Finished saving White Boots info.");
        }
        else {
        	clear(config);
        }

    }
	
	public static void load(final SpireConfig config) {
		
		logger.info("Loading White Boots info.");
		if (AbstractDungeon.player.hasRelic(ID) && config.has("White_Boots_number_of_uses")){

            final WhiteBoots relic = (WhiteBoots)AbstractDungeon.player.getRelic(ID);
            final int number_of_uses = config.getInt("White_Boots_number_of_uses");
                     
        	/*relic.number_of_uses_left_in_this_fight = number_of_uses;
            
        	relic.checkAndSetNotUsedRelic();
        	relic.checkAndSetUsedRelic();   */
            
            try {
				config.load();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            logger.info("Finished loading White Boots info.");
        }
		
		else
		{
			logger.info("There's no info, setting variables accordingly.");

			logger.info("Finished setting White Boots variables.");
		}
		
		
    }
		
	public static void clear(final SpireConfig config) {
		logger.info("Clearing White Boots variables.");      
        config.remove("White_Boots_number_of_uses");
        logger.info("Finished clearing White Boots variables.");
	}

	public AbstractRelic makeCopy() { // always override this method to return a new instance of your relic
		return new WhiteBoots();
	}
}