package dev.dfonline.getactiondump;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileManager {
    public static Path Path(){
        return GetActionDump.MC.runDirectory.toPath().resolve(GetActionDump.ModId);
    }

    public static void WriteFile(Path path, String content) throws IOException {
        Files.writeString(path,content);
    }
}
