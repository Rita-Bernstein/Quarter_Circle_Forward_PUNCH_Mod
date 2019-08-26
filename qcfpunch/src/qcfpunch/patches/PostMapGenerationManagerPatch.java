package qcfpunch.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import qcfpunch.map_generation.PostMapGenerationManager;

@SpirePatch(clz= AbstractDungeon.class, method = "generateMap")
public class PostMapGenerationManagerPatch {
	
	public static void Postfix() {
		
		if (PostMapGenerationManager.initialized) {
			
			PostMapGenerationManager.doIfPossiblePostMapGenerationChangers();
			
		}
    }

}
