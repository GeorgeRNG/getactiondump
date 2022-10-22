package dev.dfonline.getactiondump;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class FileManager {
    public static Path Path(){
        Path path = GetActionDump.MC.runDirectory.toPath().resolve(GetActionDump.ModId);
        path.toFile().mkdir();
        return path;
    }

    public static void WriteFile(String fileName, String content) throws IOException {
        File file = Path().resolve(fileName).toFile();
        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write(content);
        fileWriter.close();
    }
}
