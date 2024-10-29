package com.sanhait.springfilesave.file;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Util {
    public static final String ROOT_PATH = "C:\\WINGS\\rms\\room\\monitoring\\";
//    public static LocalDate date = LocalDate.now();

    public static Path getRootPath() {
        return getRootPath(LocalDate.now(), null);
    }

    public static Path getRootPath(String... path) {
        String dateSelected = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        if (path == null) {
            return Paths.get(ROOT_PATH, dateSelected);
        } else {
            return Paths.get(ROOT_PATH + dateSelected + "\\", path);
        }
    }

    public static Path getRootPath(LocalDate dateRoom) {
        return getRootPath(dateRoom, null);
    }

    public static Path getRootPath(LocalDate date, String... path) {
        String dateSelected = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        if (path == null) {
            return Paths.get(ROOT_PATH, dateSelected);
        } else {
            return Paths.get(ROOT_PATH + dateSelected + "\\", path);
        }
    }

    public static Path getRoot() {
        return Paths.get(ROOT_PATH);
    }
}