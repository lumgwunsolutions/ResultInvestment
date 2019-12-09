package group.lsg.resultinvestmentapp.Class;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

public class PasswordManager  extends Manager implements Serializable {

    public PasswordManager(File file) throws IOException {
        super(file);
    }

    @Override
    public String toString() {
        String content = "";
        for (String email : Databank.passwords.keySet()) {
            content += email + ";" + Databank.passwords.get(email) + "\n";
        }
        return content;
    }

    public void uploadInfo(String path) {
        try (Scanner sc = new Scanner(new File(path))) {
            while (sc.hasNextLine()) {
                String nextLine = sc.nextLine();
                String[] info = nextLine.split(";");
                String email = info[0];
                String password = info[1];
                Databank.passwords.put(email, password);
            }
        } catch (FileNotFoundException e1) {
            return;
        }
    }
}