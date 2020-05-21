package sr.dac.main;

import java.io.File;

public class Lang {
    private static File en_US = new File(Main.getPlugin().getDataFolder(),"/langs/en_US.yml");
    private static File fr_FR = new File(Main.getPlugin().getDataFolder(),"/langs/fr_FR.yml");
    public static void checkLang(){
        if(!en_US.exists()){
            Main.getPlugin().saveResource("langs/en_US.yml", false);
            System.out.println("[DAC] en_US file created");
        }
        if(!fr_FR.exists()){
            Main.getPlugin().saveResource( "langs/fr_FR.yml", false);
            System.out.println("[DAC] fr_FR file created");
        }
        Main.lang = Config.selectedLang();
    }
}
