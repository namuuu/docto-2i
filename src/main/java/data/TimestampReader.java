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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TimestampReader {
    public PlanningJournee insertLine(String filename, BufferedReader br, String line, ReadData readData) throws IOException {
        Manager reponsibleManager = null;
        int rendezVousNb;
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
                    rendezVousNb = Integer.parseInt(data[0]);
                    break;
                case 2:
                    for (String professionalNumber : data) {
                        doctors.add(readData.getMedecins().stream().filter(
                                d -> d.getHealthProfessionalNumber() == Integer.parseInt(professionalNumber)
                        ).findFirst().orElse(null));
                    }
                    break;
                case 3:
                    for(String salleNumber : data) {
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

        LocalDate localDate = LocalDate.parse(filename.substring(0,8), DateTimeFormatter.BASIC_ISO_DATE);
        Date date = Date.from(localDate.atStartOfDay().toInstant(java.time.ZoneOffset.UTC));

        return new PlanningJournee(date, reponsibleManager, readData.getPlanning(), doctors, new ArrayList<>(), sallesDispo, rendezVousList);

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
