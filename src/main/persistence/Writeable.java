package persistence;

import org.json.JSONObject;

/*
Subclasses based off of:
https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
 */
public interface Writeable {
    /*
    EFFECTS: converts Writeable to JSONObject
     */
    JSONObject toJson();
}
