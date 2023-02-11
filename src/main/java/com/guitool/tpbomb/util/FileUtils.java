package com.guitool.tpbomb.util;

import com.guitool.tpbomb.Main;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

public class FileUtils {
    public static String getContentsFromFile(String filePath) {
        StringBuilder stringBuffer = new StringBuilder();
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            String path = Objects.requireNonNull(Main.class.getClassLoader().getResource(filePath)).getPath();
            path.replaceAll("%20", " ");
            File file = new File(path);

            if (file.isFile() && file.exists()) {
                inputStreamReader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
                bufferedReader = new BufferedReader(inputStreamReader);
                String lineContent = null;
                while ((lineContent = bufferedReader.readLine()) != null) {
                    if (lineContent == null) {
                        continue;
                    }
                    stringBuffer.append(lineContent).append("\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return stringBuffer.toString();
    }

    public static ArrayList<String> getUrlsFromFile(String filePath) {
        ArrayList<String> urlArrayList = new ArrayList<>();
        try {
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String lineContent;
                while ((lineContent = bufferedReader.readLine()) != null) {
                    urlArrayList.add(lineContent);
                }
                bufferedReader.close();
                inputStreamReader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return urlArrayList;
    }
}
