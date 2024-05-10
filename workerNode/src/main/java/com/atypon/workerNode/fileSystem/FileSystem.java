package com.atypon.workerNode.fileSystem;

import com.atypon.workerNode.data.model.Document;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public interface FileSystem {
    public List readListFromFile(String path);
    public TreeMap readMapFromFile(String path);
    public void writeToFile(List list, String path,boolean append);
    public void writeToFile(TreeMap<Object,Document> map, String path, boolean append);
    public void writeToFile(JSONObject document,String path,boolean append);
    public void writeToFile(String str, String path,boolean append);
    public boolean deleteFile(String path);
    public boolean deleteDirectory(String path);
    public JSONObject readDocument(String path);
    public boolean createDirectory(String path);
    public boolean createFile(String path);
    public List readDirectories(String path);
    public List readFiles(String path, String expression);
}
