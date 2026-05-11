package util;

import java.io.FileInputStream;
import java.util.Properties;

public class Config {

    private static Properties props = new Properties();

    static {
        // Usiamo FileInputStream per leggere il file NELLA CARTELLA del programma
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            props.load(fis);
        } catch (Exception e) {
            System.err.println("ERRORE: File config.properties non trovato nella cartella principale!");
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }
}