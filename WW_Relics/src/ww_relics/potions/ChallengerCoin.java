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

import basemod.abstracts.CustomSavable;

import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import com.megacrit.cardcrawl.rooms.TreasureRoom;

import ww_relics.rooms.MonsterRoomEmeraldElite;
import ww_relics.WW_Relics_MiscelaneaCode;
import ww_relics.relics.chun_li.WhiteBoots;

import com.megacrit.cardcrawl.mod.replay.rooms.TeleportRoom;
import infinitespire.rooms.NightmareEliteRoom;


public class ChallengerCoin extends OutOfCombatPotion {

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
	
	public static int saved_map_x_position;
	public static int saved_map_y_position;
	public static String saved_map_room = "";
	
	public ChallengerCoin() {
		super(NAME, ID, RARITY, SIZE, COLOR);
		description = DESCRIPTIONS[0] + DESCRIPTIONS[1];
		this.isThrown = false;
		this.tips.add(new PowerTip(this.name, this.description));
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
		
		return true;
	}
	
	@Override
	public void use(AbstractCreature arg0) {
		
		ArrayList<ArrayList<MapRoomNode>> dungeon_map = AbstractDungeon.map;
		
		MapRoomNode current_room = AbstractDungeon.currMapNode;
		ArrayList<MapEdge> edges = current_room.getEdges();
		
		for (int i = 0; i < edges.size(); i++) {
			int x = edges.get(i).dstX;	int y = edges.get(i).dstY;
			
			changeRoom(dungeon_map, x, y);
		}
		
	}
	
	public static void changeRoom(ArrayList<ArrayList<MapRoomNode>> dungeon_map, int x, int y) {
		
		changeRoom(dungeon_map, x, y, null);
		
	}
	
	public static void changeRoom(ArrayList<ArrayList<MapRoomNode>> dungeon_map, int x, int y, String which_room) {
		
		MapRoomNode room_to_change = dungeon_map.get(y).get(x);
		AbstractRoom room = room_to_change.getRoom();

		if (CheckIfPotionCanBeUsed(room)) {
		
			AbstractRoom new_room;
			
			saved_map_x_position = x;
			saved_map_y_position = y;
			
			if (which_room == null) {
				if (checkIfEmeraldEliteOrEliteRoom(room_to_change)) {
					new_room = new MonsterRoomEmeraldElite();
					saved_map_room += "EmeraldElite";
				}
				else {
					new_room = new MonsterRoomElite();
					saved_map_room += "Elite";
				}
			} else {
				if (which_room == "EmeraldElite") {
					new_room = new MonsterRoomEmeraldElite();
					saved_map_room += "EmeraldElite";
				}
				else {
					new_room = new MonsterRoomElite();
					saved_map_room += "Elite";
				}
			}
			
			room_to_change.room = new_room;
		
		} else {
			
			//if it was a combat room, 
			//add an elite to a non-problematic space position to that room
			
		}
		
	}
	
	public static boolean CheckIfPotionCanBeUsed(AbstractRoom room) {
		
		boolean evaluation = !(room instanceof MonsterRoom) && 
				!(room instanceof MonsterRoomElite) &&
				!(room instanceof MonsterRoomBoss);
		
		if (WW_Relics_MiscelaneaCode.checkForMod(WW_Relics_MiscelaneaCode.replay_the_spire_class_code)) {
			
			evaluation &= !(room instanceof TeleportRoom);
			
		}
		
		if (WW_Relics_MiscelaneaCode.checkForMod(WW_Relics_MiscelaneaCode.infinite_spire_class_code)) {
			
			evaluation &= !(room instanceof NightmareEliteRoom);
			
		}
		
		return evaluation;
		
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
	
	public static void save(final SpireConfig config) {

        if (AbstractDungeon.player != null) {
        	logger.info("Started saving Challenger Coin information");
        	
        	config.setInt("Challenger_Coin_X", saved_map_x_position);
        	config.setInt("Challenger_Coin_Y", saved_map_y_position);
        	config.setString("Challenger_Coin_Room", saved_map_room);
        	
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
		if (config.has("Challenger_Coin_Room")){
                     
			ChallengerCoin.saved_map_x_position = config.getInt("Challenger_Coin_X");
			ChallengerCoin.saved_map_y_position = config.getInt("Challenger_Coin_Y");
			ChallengerCoin.saved_map_room = config.getString("Challenger_Coin_Room");
            
			ArrayList<ArrayList<MapRoomNode>> dungeon_map = AbstractDungeon.map;
			
			changeRoom(dungeon_map, saved_map_x_position, saved_map_y_position, saved_map_room);
			
            try {
				config.load();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            logger.info("Finished loading Challenger Coin info.");
        }
		
		else
		{
			logger.info("There's no info, setting variables accordingly.");

			logger.info("Finished setting Challenger Coin variables.");
		}
		
		
    }
		
	public static void clear(final SpireConfig config) {
		logger.info("Clearing Challenger Coin variables.");
    	
		config.remove("Challenger_Coin_X");
    	config.remove("Challenger_Coin_Y");
    	config.remove("Challenger_Coin_Room");
		
        logger.info("Finished clearing Challenger Coin variables.");
	}

	@Override
	public AbstractPotion makeCopy() {

		return new ChallengerCoin();
	}



}
