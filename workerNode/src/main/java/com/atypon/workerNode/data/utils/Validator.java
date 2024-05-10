package com.atypon.workerNode.data.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Validator implements ValidationStrategy{
    public boolean validate(JSONObject schema, JSONObject document) throws JSONException {
        return validateObject(schema, document);
    }
    private static boolean validateObject(JSONObject schema, JSONObject document) throws JSONException {
        JSONArray names = schema.names();
        if (names == null) {
            return true;
        }
        for (int i = 0; i < names.length(); i++) {
            String property = names.getString(i);
            Object schemaValue = schema.get(property);
            if (!document.has(property)) {
                System.err.println("Error: Property '" + property + "' is missing in the document.");
                return false;
            }
            Object documentValue = document.get(property);
            if (!validateType(schemaValue, documentValue)) {
                System.err.println("Error: Property '" + property + "' has invalid type."+schemaValue+"-"+documentValue);
                return false;
            }
            if (schemaValue instanceof JSONObject) {
                if (!validateObject((JSONObject) schemaValue, (JSONObject) documentValue)) {
                    return false;
                }
            } else if (schemaValue instanceof JSONArray) {
                if (!validateArray((JSONArray) schemaValue, (JSONArray) documentValue)) {
                    return false;
                }
            }
        }
        return true;
    }
    private static boolean validateArray(JSONArray schemaArray, JSONArray documentArray) throws JSONException {
        if (schemaArray.length() != documentArray.length()) {
            System.err.println("Error: Array length mismatch.");
            return false;
        }
        for (int i = 0; i < schemaArray.length(); i++) {
            Object schemaElement = schemaArray.get(i);
            Object documentElement = documentArray.get(i);
            if (!validateType(schemaElement, documentElement)) {
                System.err.println("Error: Array element at index " + i + " has invalid type.");
                return false;
            }
            if (schemaElement instanceof JSONObject) {
                if (!validateObject((JSONObject) schemaElement, (JSONObject) documentElement)) {
                    return false;
                }
            } else if (schemaElement instanceof JSONArray) {
                if (!validateArray((JSONArray) schemaElement, (JSONArray) documentElement)) {
                    return false;
                }
            }
        }
        return true;
    }
    private static boolean validateType(Object schemaValue, Object documentValue) {
        if (schemaValue.equals("String")) {
            return documentValue instanceof String;
        } else if (schemaValue.equals("int")) {
            return documentValue instanceof Integer;
        } else if (schemaValue.equals("boolean")) {
            return documentValue instanceof Boolean;
        } else if (schemaValue.equals("JSONObject")) {
            return documentValue instanceof JSONObject;
        } else if (schemaValue.equals("JSONArray")) {
            return documentValue instanceof JSONArray;
        }
        return true; // Allow any other types by default
    }
}
