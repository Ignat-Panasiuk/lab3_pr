package com.lab3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ArchiveHandler {
    public static void zipFile(String filePath, String zipPath) {
        try (FileOutputStream fos = new FileOutputStream(zipPath);
             ZipOutputStream zos = new ZipOutputStream(fos);
             FileInputStream fis = new FileInputStream(filePath)) {
            
            ZipEntry zipEntry = new ZipEntry(new File(filePath).getName());
            zos.putNextEntry(zipEntry);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) >= 0) {
                zos.write(buffer, 0, length);
            }

            zos.closeEntry();
            System.out.println("Файл успешно упакован в ZIP архив!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}