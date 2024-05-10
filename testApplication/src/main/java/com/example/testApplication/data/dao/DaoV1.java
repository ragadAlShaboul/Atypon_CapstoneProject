package com.example.testApplication.data.dao;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class DaoV1 implements DAO {
    private String username="ragad";
    private String password="ragad";
    private String myWorkerNode=createNewUser();
    public DaoV1(){
        System.out.println(myWorkerNode);
    }
    @Override
    public String createNewUser() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8000/assignNewUser?username="+username+"&password="+password))
                    .POST(HttpRequest.BodyPublishers.ofString(""))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            myWorkerNode=response.body();
        } catch (URISyntaxException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
        return myWorkerNode;
    }
    @Override
    public boolean createConnection() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(myWorkerNode+"/createConnection"))
                    .POST(HttpRequest.BodyPublishers.ofString(""))
                    .header("username",username)
                    .header("password",password)
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.body().equals(false))
                throw new RuntimeException("user doesn't exist");
            return true;
        } catch (URISyntaxException | InterruptedException | IOException e) {
            throw new RuntimeException("something wrong happened!");
        }
    }
    @Override
    public boolean createDatabase(String dbName, JSONObject schema) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(myWorkerNode+"/createConnection/createDatabase"))
                    .header("content-type","application/json")
                    .POST(HttpRequest.BodyPublishers.ofString("{\"username\":\""+username+"\""+
                            ", \"password\":\""+password+"\""+
                            ",\"dbName\":\""+dbName+"\"" +
                            ",\"schema\":\""+schema+"}"))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.body().equals(false))
                throw new RuntimeException("couldn't create database");
            return true;
        } catch (URISyntaxException | InterruptedException | IOException e) {
            throw new RuntimeException("something wrong happened!");
        }
    }

    @Override
    public boolean deleteDatabase(String dbName) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(myWorkerNode+"/createConnection/deleteDatabase"))
                    .header("content-type","application/json")
                    .POST(HttpRequest.BodyPublishers.ofString("{\"username\":\""+username+"\""+
                                                                ", \"password\":\""+password+"\""+
                                                                ",\"dbName\":\""+dbName+"\"}"))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.body().equals(false))
                throw new RuntimeException("couldn't delete database");
            return true;
        } catch (URISyntaxException | InterruptedException | IOException e) {
            throw new RuntimeException("something wrong happened!");
        }
    }

    @Override
    public boolean createDocument(String dbName,JSONObject document,JSONObject documentSchema) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(myWorkerNode+"/createConnection/createDocument"))
                    .header("content-type","application/json")
                    .POST(HttpRequest.BodyPublishers.ofString("{\"username\":\""+username+"\""+
                            ", \"password\":\""+password+"\""+
                            ",\"dbName\":\""+dbName+"\""+
                            ",\"document\":"+document+
                            ",\"schema\":"+documentSchema+"}"))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.body().equals(false))
                throw new RuntimeException("couldn't create Document");
            return true;
        } catch (URISyntaxException | InterruptedException | IOException e) {
            throw new RuntimeException("something wrong happened!");
        }
    }
    @Override
    public boolean deleteDocumentWhere(String key,String dbName) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(myWorkerNode+"/createConnection/deleteDocumentWhere"))
                    .header("content-type","application/json")
                    .POST(HttpRequest.BodyPublishers.ofString("{\"username\":\""+username+"\""+
                    ", \"password\":\""+password+"\""+
//                    ",\"dbName\":\""+dbName+"\""+
                    ",\"key\":\""+key+"\"}"))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.body().equals(false))
                throw new RuntimeException("couldn't delete document");
            return response.body().equals(true);
        } catch (URISyntaxException | InterruptedException | IOException e) {
            throw new RuntimeException("something wrong happened!");
        }
    }

    @Override
    public boolean insertPropertyWhere(String dbName,String key,String value,String newKey,String newValue) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(myWorkerNode+"/createConnection/insertPropertyWhere"))
                    .header("content-type","application/json")
                    .POST(HttpRequest.BodyPublishers.ofString("{\"username\":\""+username+"\""+
                            ", \"password\":\""+password+"\""+
                            ",\"dbName\":\""+dbName+"\""+
                            ",\"key\":\""+key+"\""+
                            ",\"value\":\""+value+"\""+
                            ",\"newKey\":\""+newKey+"\""+
                            ",\"newValue\":\""+newValue+"\"}"))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.body().equals(false))
                throw new RuntimeException("couldn't insert property");
            return response.body().equals(true);
        } catch (URISyntaxException | InterruptedException | IOException e) {
            throw new RuntimeException("something wrong happened!");
        }
    }

    @Override
    public boolean removePropertyWhere(String dbName,String key,String value) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(myWorkerNode+"/createConnection/deletePropertyWhere"))
                    .header("content-type","application/json")
                    .POST(HttpRequest.BodyPublishers.ofString("{\"username\":\""+username+"\""+
                            ", \"password\":\""+password+"\""+
                            ",\"dbName\":\""+dbName+"\""+
                            ",\"key\":\""+key+"\""+
                            ",\"value\":\""+value+"\"}"))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.body().equals(false))
                throw new RuntimeException("couldn't delete property");
            return response.body().equals(true);
        } catch (URISyntaxException | InterruptedException | IOException e) {
            throw new RuntimeException("something wrong happened!");
        }
    }

    @Override
    public boolean updatePropertyWhere(String dbName,String key,Object value,String newKey,Object newValue) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(myWorkerNode+"/createConnection/writePropertyWhere"))
                    .header("content-type","application/json")
                    .POST(HttpRequest.BodyPublishers.ofString("{\"username\":\""+username+"\""+
                            ", \"password\":\""+password+"\""+
                            ",\"dbName\":\""+dbName+"\""+
                            ",\"key\":\""+key+"\""+
                            ",\"newKey\":\""+newKey+"\""+
                            ",\"value\":"+(value instanceof String? ("\""+value+"\""):value)+
                            ",\"newValue\":"+(newValue instanceof String? ("\""+newValue+"\""):newValue)+"}"))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.body().equals(false))
                throw new RuntimeException("couldn't update property");
            return response.body().equals(true);
        } catch (URISyntaxException | InterruptedException | IOException e) {
            throw new RuntimeException("something wrong happened!");
        }
    }

    @Override
    public List getPropertyWhere(String dbName, String key, String value, String keyToRead) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(myWorkerNode+"/createConnection/readPropertyWhere"))
                    .header("content-type","application/json")
                    .POST(HttpRequest.BodyPublishers.ofString("{\"username\":\""+username+"\""+
                            ", \"password\":\""+password+"\""+
                            ",\"dbName\":\""+dbName+"\""+
                            ",\"key\":\""+key+"\""+
                            ",\"value\":\""+value+"\""+
                            ",\"keyToRead\":\""+keyToRead+"\"}"))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.body().equals(false))
                throw new RuntimeException("couldn't read property");
            return handleResponse(response.body());
        } catch (URISyntaxException | InterruptedException | IOException e) {
            throw new RuntimeException("something wrong happened!");
        }
    }

    @Override
    public List getDocumentsWhere(String dbName,String key,String value) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(myWorkerNode+"/createConnection/readDocumentWhere"))
                    .header("content-type","application/json")
                    .POST(HttpRequest.BodyPublishers.ofString("{\"username\":\""+username+"\""+
                            ", \"password\":\""+password+"\""+
                            ",\"dbName\":\""+dbName+"\""+
                            ",\"key\":\""+key+"\""+
                            ",\"value\":\""+value+"\"}"))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.body().equals("bad credentials!!"))
                throw new RuntimeException("couldn't update property");
            return handleResponse(response.body());
        } catch (URISyntaxException | InterruptedException | IOException e) {
            throw new RuntimeException("something wrong happened!");
        }
    }

    @Override
    public List getAllDocuments(String dbName) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = null;
        try {
            request = HttpRequest.newBuilder()
                    .uri(new URI(myWorkerNode+"/createConnection/getAllDocuments"))
                    .header("content-type","application/json")
                    .POST(HttpRequest.BodyPublishers.ofString("{\"username\":\""+username+"\""+
                            ", \"password\":\""+password+"\""+
                            ",\"dbName\":\""+dbName+"\"}"))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if(response.body().equals("bad credentials!!"))
                throw new RuntimeException("couldn't update property");
            return handleResponse(response.body());
        } catch (URISyntaxException | InterruptedException | IOException e) {
            throw new RuntimeException("something wrong happened!");
        }
    }
    public List<String> handleResponse(String responseBody) throws IOException {
        List<String> values = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new StringReader(responseBody))) {
            Object line;
            while ((line = reader.readLine()) != null) {
                values.add((String) line);
            }
        }
        return values;
    }
}
