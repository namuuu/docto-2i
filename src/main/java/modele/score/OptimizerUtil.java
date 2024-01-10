package modele.score;

import modele.RendezVous;
import modele.Salle;
import modele.people.Doctor;
import modele.planning.RDVBrick;

import java.util.ArrayList;
import java.util.List;

/**
 * OptimizerUtil is a class that contains all the methods used by the Optimizer class
 */
public class OptimizerUtil {

    public void fillRDVBricks(RDVBrick brick, ArrayList<RendezVous> rdvList) {
        RendezVous firstRDV = null;

        while(true) {
            firstRDV = findFirstRDVAvailable(rdvList, brick.getEndHour());

            if(firstRDV == null || brick.getRendezVous().size() == 8) {
                return;
            }

            brick.addRendezVous(firstRDV);
            if(brick.getRendezVous().size() == 1)
                brick.setStartHour(firstRDV.getCreneau().getStartHour());
            brick.setEndHour(firstRDV.getCreneau().getStartHour() + 1);
            if(firstRDV.getPatient().isSpecialized())
                brick.setSpecialized(true);
            rdvList.remove(firstRDV);
        }
    }

    private RendezVous findFirstRDVAvailable(ArrayList<RendezVous> rdvList, int previousHour) {
        RendezVous bestRDV = null;

        for(RendezVous rdv : rdvList) {
            if(rdv.getCreneau().getStartHour() == previousHour || previousHour == 0) {
                if(rdv.getPatient().isSpecialized())
                    return rdv;
                bestRDV = rdv;
            }
        }

        return bestRDV;
    }

    // Find the best doctor for the brick
    public Doctor findFirstDoctorAvailable(ArrayList<RDVBrick> brickList, List<Doctor> doctors, RDVBrick brick) {
        Doctor bestDoctor = null;
        int difference = 0;

        for (Doctor doctor : doctors) {
            if (brick.isSpecialized() && doctor.isIntern()) {
                continue;
            }
            int lastHour = getDoctorLastHour(brickList, doctor);
            if (lastHour < brick.getStartHour()) {
                if (lastHour == 0 || lastHour - brick.getStartHour() < difference) {
                    bestDoctor = doctor;
                    difference = lastHour - brick.getStartHour();
                }
            }
        }

        return bestDoctor;
    }

    public Salle findFirstSalleAvailable(ArrayList<RDVBrick> brickList, List<Salle> salles, RDVBrick brick) {
        Salle bestSalle = null;
        int difference = 0;

        for (Salle salle : salles) {
            int lastHour = getSallesLastHour(brickList, salle);
            if(getDoctorLastSalle(brickList, brick.getDoctor()) != null
                    && getDoctorLastSalle(brickList, brick.getDoctor()).equals(salle)
                    && lastHour == brick.getStartHour()) {
                return salle;
            }
            if (lastHour <= brick.getStartHour()) {
                if (lastHour == 0 || lastHour - brick.getStartHour() <= difference) {
                    bestSalle = salle;
                    difference = lastHour - brick.getStartHour();
                }
            }
            difference = 0;
        }

        return bestSalle;
    }

    public int getDoctorLastHour(ArrayList<RDVBrick> brickList, Doctor doctor) {
        int lastHour = 0;

        for(RDVBrick brick : brickList) {
            if(brick.getDoctor() != null && brick.getDoctor().equals(doctor) && brick.getEndHour() > lastHour) {
                lastHour = brick.getEndHour();
            }
        }

        return lastHour;
    }

    public Salle getDoctorLastSalle(ArrayList<RDVBrick> brickList, Doctor doctor) {
        Salle lastSalle = null;

        for(RDVBrick brick : brickList) {
            if(brick.getDoctor() != null && brick.getDoctor().equals(doctor)) {
                lastSalle = brick.getSalle();
            }
        }

        return lastSalle;
    }

    public int getSallesLastHour(ArrayList<RDVBrick> brickList, Salle salle) {
        int lastHour = 0;

        for(RDVBrick brick : brickList) {
            if(brick.getSalle() != null && brick.getSalle().equals(salle) && brick.getEndHour() > lastHour) {
                lastHour = brick.getEndHour();
            }
        }

        return lastHour;
    }
}
