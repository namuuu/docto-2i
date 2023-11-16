package data;


import java.io.*;

public class DataReader {

    public void readFiles(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        try {
            if (files == null) {
                throw new NullPointerException();
            }
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".txt")) {
                    FileReader fr = new FileReader(file.getPath());
                    BufferedReader br = new BufferedReader(fr);

                    String line = br.readLine();

                    while(line != null) {
                        switch (file.getName()) {
                            case "cadres.txt":
                                CadresReader cr = new CadresReader();
                                cr.insertLine(line.split(";"));
                                break;
                            case "medecins.txt":
                                break;
                            case "salles.txt":
                                SallesReader sr = new SallesReader();
                                sr.insertLine(line.split(";"));
                                break;
                            default:
                                System.out.println(file.getName());
                        }

                        line = br.readLine();
                    }
                }
            }
        } catch (NullPointerException e) {
            System.err.println("ERR: Folder" + folderPath + " does not exist");
        } catch (IOException e) {
            throw new RuntimeException(e);
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
