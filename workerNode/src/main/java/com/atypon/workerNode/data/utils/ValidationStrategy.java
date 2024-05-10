package com.atypon.workerNode.data.utils;

import org.json.JSONException;
import org.json.JSONObject;

public interface ValidationStrategy {
    public boolean validate(JSONObject schema, JSONObject document) throws JSONException;
}
