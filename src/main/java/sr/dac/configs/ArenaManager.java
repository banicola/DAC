package sr.dac.configs;

import sr.dac.main.Arena;

import java.util.HashMap;
import java.util.Map;

public class ArenaManager {
    private Map<String, Arena> arenas = new HashMap<String, Arena>();

    public void createArena(String name) {
        arenas.put(name, new Arena(name, null, null, null, 0, 0));
    }

    public void removeArena(String name) {
        arenas.remove(name);
    }

    public Arena getArena(String name) {
        return arenas.get(name);
    }

    public boolean exists(String name) {
        return arenas.get(name) != null;
    }

    public void load() {

    }

    public void save() {

    }
}
