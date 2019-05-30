package ww_relics.potions;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen;
import com.megacrit.cardcrawl.events.shrines.WeMeetAgain;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.FruitJuice;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;

public class ChallengerCoin extends FruitJuice {

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
	
	public static final Logger logger = LogManager.getLogger(ChallengerCoin.class.getName()); // lets us log output
	
	public ChallengerCoin() {
		super();
		this.name = NAME;
		this.potency = getPotency();
		description = DESCRIPTIONS[0] + DESCRIPTIONS[1];
		this.isThrown = false;
		this.tips.clear();
		this.tips.add(new PowerTip(this.name, this.description));
	}
	
	@Override
	public int getPotency(int ascensionLevel) {
		return 1;
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
		
		ArrayList<MapRoomNode> rooms_found = new ArrayList<MapRoomNode>();
		MapRoomNode current_room = AbstractDungeon.currMapNode;
		
		String room_symbol = current_room.getRoomSymbol(false);
		
		for (int i = 0; i < dungeon_map.size(); i++) {
			
			for (int j = 0; j < dungeon_map.get(i).size(); j++) {
				
				MapRoomNode check_room = dungeon_map.get(i).get(j);
				if (check_room.isConnectedTo(current_room)) {
					ArrayList<MapRoomNode> parents = check_room.getParents();
					
					for (int k = 0; k < parents.size(); k++) {
						if (parents.get(k) == current_room) rooms_found.add(check_room); 
					}
				}
			}
			
		}
		
		logger.info("Number of rooms found = " + rooms_found.size());
		
		for (int i = 0; i < rooms_found.size(); i++) {
			
			if ((rooms_found.get(i).getRoom().getMapSymbol() != "M") ||
				(rooms_found.get(i).getRoom().getMapSymbol() != "B") ||
				(rooms_found.get(i).getRoom().getMapSymbol() != "E")) {
				
				rooms_found.get(i).setRoom(new MonsterRoomElite());
				logger.info("Did it");
			}
			
		}
		
		//Change next room entered to an Elite room, if it was already, 
		//add elite to a non-problematic space position in the next room
	}
	
	@Override
	public AbstractPotion makeCopy() {

		return new ChallengerCoin();
	}



}
