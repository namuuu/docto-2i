package data;


import jakarta.persistence.EntityManager;
import modele.Salle;
import modele.people.Manager;
import modele.people.Medecin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DataReader {

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

                    // TODO: Inverse le switch et le while, on fait des multiples itérations du même check
                    int i = 0;
                    while(line != null) {
                        switch (file.getName()) {
                            case "cadres.txt":
                                ManagerReader cr = new ManagerReader();
                                readData.addManager(cr.insertLine(line.split(";")));
                                break;
                            case "medecins.txt":
                                MedecinReader mr = new MedecinReader();
                                readData.addMedecin(mr.insertLine(line.split(";")));
                                break;
                            case "salles.txt":
                                SallesReader sr = new SallesReader();
                                readData.addSalle(sr.insertLine(line.split(";")));
                                break;
                            default:

                                //System.out.println(file.getName());
                                break;
                        }

                        i++;
                        line = br.readLine();
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

    public void importData(EntityManager em, ReadData data) {
        for(Manager manager: data.getManagers()) {
            em.persist(manager.getLogin());
            em.persist(manager.getPerson());
            em.persist(manager);
        }

        for(Medecin medecin: data.getMedecins()) {
            em.persist(medecin.getLogin());
            em.persist(medecin.getPerson());
            em.persist(medecin);
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
