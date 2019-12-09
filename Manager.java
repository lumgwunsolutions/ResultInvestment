package group.lsg.resultinvestmentapp.Class;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public abstract class Manager {
    private String filePath;

    public Manager(File file) throws IOException {
        filePath = file.getPath();
        if (file.exists()) {
            uploadInfo(filePath);
        } else {
            file.createNewFile();
        }
    }
    public abstract void uploadInfo(String path);

    public void writeOutToFile() {
        try {
            String content = toString();
            FileWriter writer = new FileWriter(filePath);
            BufferedWriter buffer = new BufferedWriter(writer);
            buffer.write(content);
            buffer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



