package util;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class Config {

    private static Properties props = new Properties();

    static {
        // Usiamo il nuovo nome file che hai indicato
        String fileName = "credenziali_database.properties";
        
        // Puntiamo alla cartella dove il programma viene lanciato
        File configFile = new File(fileName);

        // Questo log ti apparirà nel terminale e risolverà ogni dubbio
        System.out.println("DEBUG: Cerco il file in: " + configFile.getAbsolutePath());

        if (configFile.exists()) {
            try (FileInputStream fis = new FileInputStream(configFile)) {
                props.load(fis);
                System.out.println("DEBUG: Caricamento riuscito!");
            } catch (Exception e) {
                System.err.println("ERRORE: Impossibile leggere il file trovato.");
                e.printStackTrace();
            }
        } else {
            System.err.println("ERRORE: Il file '" + fileName + "' non è stato trovato!");
            System.err.println("Assicurati che sia nella stessa cartella del JAR.");
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}