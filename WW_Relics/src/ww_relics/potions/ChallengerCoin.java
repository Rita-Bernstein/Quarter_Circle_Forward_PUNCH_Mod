package ww_relics.potions;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.shrines.WeMeetAgain;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapRoomNode;

import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;

import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import com.megacrit.cardcrawl.rooms.TreasureRoom;

import ww_relics.rooms.MonsterRoomEmeraldElite;
import ww_relics.WW_Relics_MiscelaneaCode;
import ww_relics.interfaces.IPostMapGenerationAddStuff;
import ww_relics.map_generation.PostMapGenerationChange;
import ww_relics.map_generation.PostMapGenerationManager;

import com.megacrit.cardcrawl.mod.replay.rooms.TeleportRoom;
import infinitespire.rooms.NightmareEliteRoom;


public class ChallengerCoin extends OutOfCombatPotion implements IPostMapGenerationAddStuff {

	public static final String ID = "WW_Relics:Challenger_Coin";
	
	private static final PotionStrings potionStrings = CardCrawlGame.
			languagePack.getPotionString(ID);
	
	public static final String NAME = potionStrings.NAME;
	public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;
	
	//Here it says Rare, but it's event or relic only.
	//It won't be sold at shops, at least not the base game shop.
	public static final PotionRarity RARITY = PotionRarity.RARE;
	
	//Size apparently creates the recipient of the potion.
	//Since this potion is a coin, I will have to make my own files for that.
	public static final PotionSize SIZE = PotionSize.SPHERE;
	public static final PotionColor COLOR = PotionColor.ATTACK;
	
	public static final String INFINITE_SPIRE_NIGHTMARE_ELITE_ROOM_SYMBOL = "NM";
	public static final String REPLAY_THE_SPIRE_TELEPORT_ROOM_SYMBOL = "PTL";
	
	public static final Logger logger = LogManager.getLogger(ChallengerCoin.class.getName());
	
	public static ArrayList<Integer> saved_act;
	public static ArrayList<Integer> saved_map_x_position;
	public static ArrayList<Integer> saved_map_y_position;
	public static ArrayList<String> saved_map_room;
	public static ArrayList<Integer> saved_post_map_gen_use_priority;
	
	//YES. I know this is a Mcguyverism and this part of the code seriously needs
	// improvement. I will do that, one day.
	//Also, if you are looking to this code thinking to use it as a base for your own changes in map,
	// this is NOT A GOOD CODE TO USE AS AN EXAMPLE.
	// Thanks for your understanding.
	private int post_gen_priority;
	
	public ChallengerCoin() {
		super(NAME, ID, RARITY, SIZE, COLOR);
		description = DESCRIPTIONS[0] + DESCRIPTIONS[1];
		this.isThrown = false;
		this.tips.add(new PowerTip(this.name, this.description));
		
		if (saved_act == null) saved_act = new ArrayList<Integer>();
		if (saved_map_x_position == null) saved_map_x_position = new ArrayList<Integer>();
		if (saved_map_y_position == null) saved_map_y_position = new ArrayList<Integer>();
		if (saved_map_room == null) saved_map_room = new ArrayList<String>();
		if (saved_post_map_gen_use_priority == null) saved_post_map_gen_use_priority = new ArrayList<Integer>();
	}
	
	@Override
	public int getPotency(int ascensionLevel) {
		return 0;
	}

	@Override
	public boolean canUse() {
		if ((AbstractDungeon.actionManager.turnHasEnded) && 
				(AbstractDungeon.getCurrRoom().phase == RoomPhase.COMBAT)) {
				      return false;
		}
		if ((AbstractDungeon.getCurrRoom().event != null) && 
				((AbstractDungeon.getCurrRoom().event instanceof WeMeetAgain))) {
				      return false;
		}
		
		return theresARoomToUseThePotion();
	}
	
	private boolean theresARoomToUseThePotion() {
		
		ArrayList<ArrayList<MapRoomNode>> dungeon_map = AbstractDungeon.map;
		
		MapRoomNode current_room = AbstractDungeon.currMapNode;
		ArrayList<MapEdge> edges = current_room.getEdges();
		
		for (int i = 0; i < edges.size(); i++) {
			int x = edges.get(i).dstX;	int y = edges.get(i).dstY;

			MapRoomNode room_to_check = dungeon_map.get(y).get(x);
			AbstractRoom room = room_to_check.getRoom();
			
			if (CheckIfPotionCanBeUsed(room)) return true;
		}
		
		return false;
		
	}
	
	public static boolean CheckIfPotionCanBeUsed(AbstractRoom room) {
		
		boolean evaluation = !(room instanceof MonsterRoom) && 
				!(room instanceof MonsterRoomElite) &&
				!(room instanceof MonsterRoomBoss);
		
		if (WW_Relics_MiscelaneaCode.silentlyCheckForMod(WW_Relics_MiscelaneaCode.replay_the_spire_class_code)) {
			
			evaluation &= !(room instanceof TeleportRoom);
			
		}
		
		if (WW_Relics_MiscelaneaCode.silentlyCheckForMod(WW_Relics_MiscelaneaCode.infinite_spire_class_code)) {
			
			evaluation &= !(room instanceof NightmareEliteRoom);
			
		}
		
		return evaluation;
		
	}
	
	@Override
	public void use(AbstractCreature arg0) {
		
		ArrayList<ArrayList<MapRoomNode>> dungeon_map = AbstractDungeon.map;
		
		MapRoomNode current_room = AbstractDungeon.currMapNode;
		ArrayList<MapEdge> edges = current_room.getEdges();
		
		for (int i = 0; i < edges.size(); i++) {
			int x = edges.get(i).dstX;	int y = edges.get(i).dstY;
			
			if (changeRoom(dungeon_map, x, y, null, true)) {
				saved_post_map_gen_use_priority.add(PostMapGenerationManager.getPriorityCounter());
				logger.info("Made new room at " + x + " " + y);
				logger.info("HERE " + saved_post_map_gen_use_priority);
			}
		}
		
	}
	
	public static boolean changeRoom(ArrayList<ArrayList<MapRoomNode>> dungeon_map, int x, int y) {
		
		return changeRoom(dungeon_map, x, y, null, true);
		
	}
	
	public static boolean changeRoom(ArrayList<ArrayList<MapRoomNode>> dungeon_map, int x, int y, String which_room,
										boolean map_changes_arent_being_loaded) {
		
		MapRoomNode room_to_change = dungeon_map.get(y).get(x);
		AbstractRoom room = room_to_change.getRoom();
		
		if (CheckIfPotionCanBeUsed(room)) {
		
			AbstractRoom new_room;
			
			saved_act.add(AbstractDungeon.actNum);
			if (map_changes_arent_being_loaded) {
				saved_map_x_position.add(x);
				saved_map_y_position.add(y);
			}
			
			
			if (which_room == null) {
				if (checkIfEmeraldEliteOrEliteRoom(room_to_change)) {
					new_room = new MonsterRoomEmeraldElite();
					if (map_changes_arent_being_loaded) saved_map_room.add("EmeraldElite");
				}
				else {
					new_room = new MonsterRoomElite();
					if (map_changes_arent_being_loaded) saved_map_room.add("Elite");
				}
			} else {
				if (which_room == "EmeraldElite") {
					new_room = new MonsterRoomEmeraldElite();
					if (map_changes_arent_being_loaded) saved_map_room.add("EmeraldElite");
				}
				else {
					new_room = new MonsterRoomElite();
					if (map_changes_arent_being_loaded) saved_map_room.add("Elite");
				}
			}
			
			room_to_change.room = new_room;
			return true;

		
		} else {
			
			//if it was a combat room, 
			//add an elite to a non-problematic space position to that room
			return false;
		}
		
	}
	
	public static boolean checkIfEmeraldEliteOrEliteRoom(MapRoomNode room_to_change) {
		
		if (room_to_change.getRoom() instanceof TreasureRoom
			&& (Settings.isFinalActAvailable)
			&& (!Settings.hasSapphireKey)) {
			
			return true;
		} else {
			return false;
		}
		
	}

	@Override
	public boolean canDoAfterMapGeneration() {
		int position = saved_post_map_gen_use_priority.indexOf(post_gen_priority);
		
		if (position == -1) return false;
		else {
			
			if (saved_act.get(position) != AbstractDungeon.actNum) return false;
			else return true;

		}
			
	}
	
	@Override
	public void doAfterMapGeneration() {
		
		ArrayList<ArrayList<MapRoomNode>> dungeon_map = AbstractDungeon.map;
		
		int position = saved_post_map_gen_use_priority.indexOf(post_gen_priority);
		
		if (position != -1) {
			
			changeRoom(dungeon_map, saved_map_x_position.get(position),
						saved_map_y_position.get(position), saved_map_room.get(position), false);
			
		}
		
	}

	public static void save(final SpireConfig config) {

        if (AbstractDungeon.player != null) {
        	logger.info("Started saving Challenger Coin information");
        	
        	if (saved_map_x_position != null) {
        		
        		int quant = saved_map_x_position.size();
        		logger.info("saved_map_x_position.size = " + saved_map_x_position.size());
        		logger.info("saved_post_map_gen_use_priority.size = " + saved_post_map_gen_use_priority.size());
            	config.setInt("Challenger_Coin_Number_Of_Rooms_Made", quant);
                
            	
            	
            	for (int i = 0; i < quant; i++) {
            		
            		config.setInt("Challenge_Coin_Saved_Act_" + i, saved_act.get(i));
            		config.setInt("Challenger_Coin_X_" + i, saved_map_x_position.get(i));
                	config.setInt("Challenger_Coin_Y_" + i, saved_map_y_position.get(i));
                	config.setString("Challenger_Coin_Room_" + i, saved_map_room.get(i));
                	config.setInt("Challenger_Coin_priority_" + i, saved_post_map_gen_use_priority.get(i));
            	}
            	
        	}
        	
            try {
				config.save();
			} catch (IOException e) {
				e.printStackTrace();
			}
            logger.info("Finished saving Challenger Coin info.");
        }
        else {
        	clear(config);
        }

    }
	
	public static void load(final SpireConfig config) {
		
		logger.info("Loading Challenger Coin info.");
		if (config.has("Challenger_Coin_Number_Of_Rooms_Made")){
                     
			int quant = config.getInt("Challenger_Coin_Number_Of_Rooms_Made");
			
			cleanArrayLists();
			
			for (int i = 0; i < quant; i++) {
				ChallengerCoin.saved_act.add(config.getInt("Challenge_Coin_Saved_Act_" + i));
				ChallengerCoin.saved_map_x_position.add(config.getInt("Challenger_Coin_X_" + i));
				ChallengerCoin.saved_map_y_position.add(config.getInt("Challenger_Coin_Y_" + i));
				ChallengerCoin.saved_map_room.add(config.getString("Challenger_Coin_Room_" + i));
				ChallengerCoin.saved_post_map_gen_use_priority.add(config.getInt("Challenger_Coin_priority_" + i));
				
				PostMapGenerationChange post_map_gen_changer = new PostMapGenerationChange();
				
				ChallengerCoin object = new ChallengerCoin();
				object.post_gen_priority = config.getInt("Challenger_Coin_priority_" + i);
				
				post_map_gen_changer.post_map_gen_changer_object =
						(IPostMapGenerationAddStuff) object;	
				
				post_map_gen_changer.counter = ChallengerCoin.saved_post_map_gen_use_priority.get(i);
				
				logger.info("Adding post map generation change");
				PostMapGenerationManager.addPostMapGenerationChange(post_map_gen_changer);
			}
			
            try {
				config.load();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            logger.info("ChallengerCoin.saved_act " + ChallengerCoin.saved_act.size());
            logger.info("ChallengerCoin.saved_map_x_position " + ChallengerCoin.saved_map_x_position.size());
            logger.info("ChallengerCoin.saved_map_y_position " + ChallengerCoin.saved_map_y_position.size());
            logger.info("ChallengerCoin.saved_map_room " + ChallengerCoin.saved_map_room.size());
            logger.info("ChallengerCoin.saved_post_map_gen_use_priority " + 
            				ChallengerCoin.saved_post_map_gen_use_priority.size());
            
            logger.info("Finished loading Challenger Coin info.");
        }
		
		else
		{
			logger.info("There's no info, setting variables accordingly.");

			logger.info("Finished setting Challenger Coin variables.");
		}
		
		
    }
	
	private static void cleanArrayLists() {
		if (ChallengerCoin.saved_act != null) saved_act.clear();
		if (ChallengerCoin.saved_map_x_position != null) saved_map_x_position.clear();
		if (ChallengerCoin.saved_map_y_position != null) saved_map_y_position.clear();
		if (ChallengerCoin.saved_map_room != null) saved_map_room.clear();
		if (ChallengerCoin.saved_post_map_gen_use_priority != null) saved_post_map_gen_use_priority.clear();
	}
		
	public static void clear(final SpireConfig config) {
		logger.info("Clearing Challenger Coin variables.");
    	
		if (config.has("Challenger_Coin_Number_Of_Rooms_Made")) {
			
			int count = config.getInt("Challenger_Coin_Number_Of_Rooms_Made");
			
			config.remove("Challenger_Coin_Number_Of_Rooms_Made");
			
			for (int i = 0; i < count; i++) {
				config.remove("Challenge_Coin_Saved_Act_" + i);
				config.remove("Challenger_Coin_X_" + i);
		    	config.remove("Challenger_Coin_Y_" + i);
		    	config.remove("Challenger_Coin_Room_" + i);
		    	config.remove("Challenger_Coin_priority_" + i);
			}
			
			saved_act.clear(); saved_act = new ArrayList<Integer>();
			saved_map_x_position.clear(); saved_map_x_position = new ArrayList<Integer>();
			saved_map_y_position.clear(); saved_map_y_position = new ArrayList<Integer>();
			saved_map_room.clear(); saved_map_room = new ArrayList<String>();
			saved_post_map_gen_use_priority.clear(); saved_post_map_gen_use_priority = new ArrayList<Integer>();
			
		}		
		
        logger.info("Finished clearing Challenger Coin variables.");
	}

	@Override
	public AbstractPotion makeCopy() {

		return new ChallengerCoin();
	}

}
