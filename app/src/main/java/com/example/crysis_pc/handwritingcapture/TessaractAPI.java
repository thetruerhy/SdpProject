package com.example.crysis_pc.handwritingcapture;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by CRYSIS-PC on 03-Nov-17.
 */

class TessaractAPI {
    private static TessaractAPI uniqueInstance = null;
    private TessBaseAPI tessApi;
    private String datapath;
    private Context currentContext;

    static TessaractAPI getInstance() {
        if(uniqueInstance == null)
            uniqueInstance = new TessaractAPI();
        return uniqueInstance;
    }

    private TessaractAPI() {
        tessApi = new TessBaseAPI();
        datapath="";
        currentContext=null;
    }

    public String Interpret(Bitmap image,Context context){
        String language="eng";
        currentContext = context;
        datapath = currentContext.getFilesDir()+"/tessaract/";
        checkFiles(new File(datapath + "tessdata/"));
        tessApi.init(datapath,language);
        tessApi.setImage(image);
        return tessApi.getUTF8Text();
    }

    private void checkFiles(File dir){
        if (!dir.exists()&& dir.mkdirs()){
            copyFiles();
        }
        if(dir.exists()) {
            String datafilepath = datapath+ "/tessdata/eng.traineddata";
            File datafile = new File(datafilepath);

            if (!datafile.exists()) {
                copyFiles();
            }
        }
    }

    private void copyFiles(){
        try {
            String filepath = datapath + "/tessdata/eng.traineddata";
            AssetManager assetManager = currentContext.getAssets();

            InputStream instream = assetManager.open("tessdata/eng.traineddata");
            OutputStream outstream = new FileOutputStream(filepath);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = instream.read(buffer)) != -1) {
                outstream.write(buffer, 0, read);
            }


            outstream.flush();
            outstream.close();
            instream.close();

            File file = new File(filepath);
            if (!file.exists()) {
                throw new FileNotFoundException();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
