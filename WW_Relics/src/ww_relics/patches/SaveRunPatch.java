 package ww_relics.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import ww_relics.QCFPunch_Mod;

public class SaveRunPatch {
	@SpirePatch(cls = "com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue", method = "save")
    public static class SaveGame
    {
        public static void Prefix(final SaveFile save) {
            QCFPunch_Mod.saveRunData();
        }
    }
    
    @SpirePatch(cls = "com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue", method = "deleteSave")
    public static class DeleteSave
    {
        public static void Prefix(final AbstractPlayer p) {
        	QCFPunch_Mod.clearRunData();
        }
    }
}
