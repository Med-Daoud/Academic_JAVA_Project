package projet_java.package2.services;

import projet_java.package2.models.Professionnel;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PersonnelService {

    private static final String FILE_PERSONNEL = "data/personnel.txt";

    public List<Professionnel> chargerProfessionnels() {
        List<Professionnel> persList = new ArrayList<>();
        File file = new File(FILE_PERSONNEL);
        if (!file.exists()) return persList;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 10) {
                    Professionnel p = new Professionnel(
                        parts[0], 
                        parts[1], 
                        parts[2], 
                        parts[3], 
                        parts[4], 
                        parts[5].isEmpty() ? null : LocalDate.parse(parts[5]), 
                        null, 
                        null, 
                        parts[8], 
                        Double.parseDouble(parts[9]) 
                    );
                    persList.add(p);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return persList;
    }
}
