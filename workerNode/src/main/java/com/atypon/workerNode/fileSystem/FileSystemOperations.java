package com.atypon.workerNode.fileSystem;

import com.atypon.workerNode.data.model.Document;
import org.json.JSONObject;

import java.io.*;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class FileSystemOperations implements FileSystem{
    @Override
    public List readListFromFile(String path) {
        List<JSONObject> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                JSONObject jsonObject = new JSONObject(line);
                list.add(jsonObject);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
        return list;
    }

    @Override
    public TreeMap readMapFromFile(String path) { //index table
        TreeMap<String,JSONObject> map = new TreeMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] mapLine = line.split("-");
                JSONObject jsonObject = new JSONObject(mapLine[1]);
                map.put(mapLine[0],jsonObject);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
        return map;
    }

    @Override
    public void writeToFile(List list, String path,boolean append) {
        for (Object line:list){
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, append))) {
                writer.write(line.toString());
                writer.newLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @Override
    public void writeToFile(String str, String path,boolean append) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, append))) {
            writer.write(str);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void writeToFile(TreeMap<Object,Document> map, String path,boolean append) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, append))) {
            map.forEach((key,value)-> {
                try {
                    writer.write("{" + key + ":" + value.getDocument().toString() + "}\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writeToFile(JSONObject document, String path, boolean append) {
        try (FileWriter fileWriter = new FileWriter(path,append)) {
            fileWriter.write(document.toString() + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


        @Override
    public boolean deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    @Override
    public boolean deleteDirectory(String path) {
        Path p = Paths.get(path);
        File dir=new File(path);
        try {
            Files.walk(p)
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException e) {
            return false;
        }
        return dir.delete();
    }

    @Override
    public JSONObject readDocument(String path) {
        JSONObject jsonObject = new JSONObject();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
              jsonObject = new JSONObject(line);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
        return jsonObject;
    }

    @Override
    public boolean createDirectory(String path) {
        File dir = new File(path);
        if(dir.exists()){
            return false;
        }
        return dir.mkdir();
    }

    @Override
    public boolean createFile(String path) {
        File file =new File(path);
        try {
            return file.createNewFile();
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public List readDirectories(String path) {
        List<String> directories = new ArrayList<>();
        try {
            Path dirPath = Paths.get(path);
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath)) {
                for (Path entry : stream) {
                    if (Files.isDirectory(entry)) {
                        directories.add(String.valueOf(entry.getFileName()));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return directories;
    }

    @Override
    public List readFiles(String path, String expression) {
        List<String> files = new ArrayList<>();
        try {
            Path dirPath = Paths.get(path);
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath, expression)) {
                for (Path entry : stream) {
                    if (Files.isRegularFile(entry)) {
                        files.add(String.valueOf(entry.getFileName()));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return files;
    }
}
