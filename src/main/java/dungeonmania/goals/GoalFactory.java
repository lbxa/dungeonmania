package dungeonmania.goals;

import org.json.JSONArray;
import org.json.JSONObject;

public class GoalFactory {
    public static Goal createGoal(JSONObject jsonGoal, JSONObject config) {
        JSONArray subgoals; // open factoring

        switch (jsonGoal.getString("goal")) {
        case "AND":
            subgoals = jsonGoal.getJSONArray("subgoals");
            return new Goal(
                "AND",
                createGoal(subgoals.getJSONObject(0), config),
                createGoal(subgoals.getJSONObject(1), config)
            );
        case "OR":
            subgoals = jsonGoal.getJSONArray("subgoals");
            return new Goal(
                "OR",
                createGoal(subgoals.getJSONObject(0), config),
                createGoal(subgoals.getJSONObject(1), config)
            );
        case "exit":
            return new Goal("exit");
        case "boulders":
            return new Goal("boulders");
        case "treasure":
            int treasureGoal = config.optInt("treasure_goal", 1);
            return new Goal("treasure", treasureGoal);
        case "enemies":
            int enemiesGoal = config.optInt("enemy_goal", 1);
            return new Goal("enemies", enemiesGoal);
        default:
            return null;
        }
    }
}
