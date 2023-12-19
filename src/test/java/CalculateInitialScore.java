import jakarta.persistence.*;
import modele.planning.PlanningJournee;
import modele.score.Score;

import java.util.List;

public class CalculateInitialScore {

    public static void main(String[] args) {
        Score score = new Score(1);
        score.calculateInitialScore();
    }
}
