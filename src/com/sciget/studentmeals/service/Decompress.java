package com.sciget.studentmeals.service;

import android.util.Log;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Decompress {
    private File _zipFile;
    private String _location;

    public Decompress(File zipFile, String location) {
        _zipFile = zipFile;
        _location = location;
        _dirChecker("");
    }

    public void unzip() {
        try {
            FileInputStream fin = new FileInputStream(_zipFile);
            ZipInputStream zin = new ZipInputStream(fin);
            ZipEntry ze = null;
            while ((ze = zin.getNextEntry()) != null) {
                Log.v("Decompress", "Unzipping " + ze.getName());

                if (ze.isDirectory()) {
                    _dirChecker(ze.getName());
                } else {
                    File file = new File(_location + ze.getName());
                    if (!file.exists()) {
                        FileOutputStream fout = new FileOutputStream(file);
                        byte data[] = new byte[1024];
                        int count;
                        while ((count = zin.read(data, 0, 1024)) != -1) {
                            fout.write(data, 0, count);
                        }
                        fout.close();
                    }
                    zin.closeEntry();
                }

            }
            zin.close();
        } catch (Exception e) {
            Log.e("Decompress", "unzip", e);
        }
    }

    private void _dirChecker(String dir) {
        File f = new File(_location + dir);
        if (!f.isDirectory()) {
            f.mkdirs();
        }
    }
}
