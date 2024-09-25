package com.sanhait.springfilesave.file;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Util {
    public static final String ROOT_PATH = "C:\\log\\";

    private Path getRootPath() {
        return getRootPath(null);
    }

    private Path getRootPath(String... path) {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        if (path == null) {
            return Paths.get(ROOT_PATH, now);
        } else {
            return Paths.get(ROOT_PATH + now + "\\", path);
        }
    }
}
