package data;


import jakarta.persistence.EntityManager;
import modele.Salle;
import modele.people.Manager;
import modele.people.Doctor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DataReader {

    /**
     * Read all files in a specified folder
     * @param folderPath path to the folder
     * @return readData, an object containing all the data read
     */
    public ReadData readFiles(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();
        ReadData readData = new ReadData();

        try {
            if (files == null) {
                throw new NullPointerException();
            }
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".txt")) {
                    FileReader fr = new FileReader(file.getPath());
                    BufferedReader br = new BufferedReader(fr);

                    String line = br.readLine();

                    switch (file.getName()) {
                        case "cadres.txt":
                            while (line != null) {
                                readData.addManager((new ManagerReader()).insertLine(line.split(";")));
                                line = br.readLine();
                            }
                            break;
                        case "medecins.txt":
                            while (line != null) {
                                readData.addMedecin((new DoctorReader()).insertLine(line.split(";")));
                                line = br.readLine();
                            }
                            break;
                        case "salles.txt":
                            while (line != null) {
                                readData.addSalle((new SallesReader()).insertLine(line.split(";")));
                                line = br.readLine();
                            }
                            break;
                        default:
                            //System.out.println(file.getName());
                            int i = 0;
                            while (line != null) {
                                i++;
                                line = br.readLine();
                            }
                            break;
                    }
                }
            }
        } catch (NullPointerException e) {
            System.err.println("ERR: Folder" + folderPath + " does not exist");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return readData;
    }

    /**
     * Import data into the MySQL database
     * @param em EntityManager, used to persist the data
     * @param data ReadData, contains all the data to be persisted
     */
    public void importData(EntityManager em, ReadData data) {
        for(Manager manager: data.getManagers()) {
            em.persist(manager);
        }

        for(Doctor doctor : data.getMedecins()) {
            em.persist(doctor);
        }

        for(Salle salle: data.getSalles()) {
            em.persist(salle);
        }
    }

    public static void main(String[] args) {
        DataReader dr = new DataReader();
        // Wrong read (nothing)
        dr.readFiles("./src/main/resources/instances/empty");

        // Wrong read (folder)
        dr.readFiles("./src/main/resources");

        // Wrong read (file)
        dr.readFiles("./src/main/resources/instances/cadres.txt");

        // Correct read
        dr.readFiles("./src/main/resources/instances");

    }
}
