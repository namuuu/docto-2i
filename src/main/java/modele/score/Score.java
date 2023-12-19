package modele.score;

import modele.planning.Planning;

public class Score {

    private Planning planning;

    private Planning oldPlanning = null;

    private int scoreInitial = 0;

    private int scoreFinal = 0;

    /**
     * Creer le score d'un planning sans comparaison (généralement le planning initial)
     * @param planning
     */
    public Score(Planning planning) {
        this.planning = planning;
    }

    public Score(Planning planning, Planning oldPlanning) {
        this.planning = planning;
        this.oldPlanning = oldPlanning;
    }

    public void setScoreInitial(int scoreInitial) {
        this.scoreInitial = scoreInitial;
    }


    public Planning getPlanning() {
        return planning;
    }

    public Planning getOldPlanning() {
        return oldPlanning;
    }
}
