package dungeonmania.map;

import org.json.JSONObject;

import dungeonmania.entities.EntityFactory;

public class GraphNodeFactory {
    public static GraphNode createEntity(JSONObject jsonEntity, EntityFactory factory) {
        return constructEntity(jsonEntity, factory);
    }

    private static GraphNode constructEntity(JSONObject jsonEntity, EntityFactory factory) {
        switch (jsonEntity.getString("type")) {
        //add cases for every game object
        case "player":
        case "zombie_toast":
        case "zombie_toast_spawner":
        case "mercenary":
        case "assassin":
        case "wall":
        case "boulder":
        case "swamp_tile":
        case "switch":
        case "exit":
        case "treasure":
        case "wood":
        case "arrow":
        case "bomb":
        case "invisibility_potion":
        case "invincibility_potion":
        case "portal":
        case "sword":
        case "spider":
        case "door":
        case "light_bulb_off":
        case "light_bulb_on":
        case "switch_door":
        case "switch_door_open":
        case "wire":
        case "key":
        case "sun_stone":
            return new GraphNode(factory.createEntity(jsonEntity));
        default:
            return null;
        }
    }
}
