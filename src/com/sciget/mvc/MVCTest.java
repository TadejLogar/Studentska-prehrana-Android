package com.sciget.mvc;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import si.feri.projekt.studentskaprehrana.activity.DetailsActivity;

import android.os.Environment;
import android.test.AndroidTestCase;

public class MVCTest extends AndroidTestCase {

    public void setUp() throws Exception {
    }

    public void testDownloadToFile() throws IOException {
        String zip = DetailsActivity.FILE_URL + "imgs.zip";
        
        File file1 = new File(Environment.getExternalStorageDirectory() + "/StudentMeals/a.zip");
        //File file2 = new File(Environment.getExternalStorageDirectory() + "/StudentMeals/b.zip");
        
        MVC.downloadToFile(zip, file1);
        //MVC.downloadToFile2(zip, file2);
        
        System.out.println("velikost: " + file1.length());
        //System.out.println("velikost: " + file2.length());
    }

}
