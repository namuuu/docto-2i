package data;

import modele.Creneau;
import modele.RendezVous;
import modele.Salle;
import modele.people.Doctor;
import modele.people.Manager;
import modele.people.Patient;
import modele.planning.PlanningJournee;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TimestampReader {
    public PlanningJournee insertLine(String filename, BufferedReader br, String line, ReadData readData) throws IOException {
        Manager reponsibleManager = null;
        List<Doctor> doctors = new ArrayList<>();
        List<Salle> sallesDispo = new ArrayList<>();
        List<RendezVous> rendezVousList = new ArrayList<>();

        int i = 0; // number of the line

        while(line != null) {
            String[] data = line.split(";");

            switch (i) {
                case 0:
                    int cadreResponsableId = Integer.parseInt(data[0]);
                    reponsibleManager = readData.getManagers().get(cadreResponsableId);
                    break;
                case 1:
                    break;
                case 2:
                    for (String professionalNumber : data) {
                        doctors.add(readData.getMedecins().stream().filter(
                                d -> d.getHealthProfessionalNumber() == Integer.parseInt(professionalNumber)
                        ).findFirst().orElse(null));
                    }
                    break;
                case 3:
                    for (String salleNumber : data) {
                        sallesDispo.add(readData.getSalles().get(Integer.parseInt(salleNumber)));
                    }
                    break;
                default:
                    rendezVousList.add(readRendezVous(readData, data));
                    break;
            }

            line = br.readLine();
            i++;
        }

        return new PlanningJournee(filename.substring(0,8), reponsibleManager, readData.getPlanning(), doctors, new ArrayList<>(), sallesDispo, rendezVousList);

    }

    private RendezVous readRendezVous(ReadData readData, String[] data) {
        Doctor doctor = readData.getMedecins().stream().filter(
                d -> d.getHealthProfessionalNumber() == Integer.parseInt(data[0])
        ).findFirst().orElse(null);
        Salle salle = readData.getSalles().get(Integer.parseInt(data[1]));
        int startHour = Integer.parseInt(data[2]);
        String patientFirstName = data[3];
        String patientName = data[4];
        Date birthdate = new Date(data[5]);
        Integer patientNumber = Integer.parseInt(data[6]);
        boolean special = Boolean.parseBoolean(data[7]);

        Patient patient = new Patient(patientFirstName, patientName, birthdate, special, patientNumber);
        return new RendezVous(patient, new Creneau(new Timestamp(startHour)), salle, doctor);
    }
}
