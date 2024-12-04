package persistence;


import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/*
Object to write GameHistory object to JSON file.
 */
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String fileName;

    /*
    EFFECTS: constructs writer to write to destination file
     */
    public JsonWriter(String fileName) {
        this.fileName = fileName;
    }

    /*
    MODIFIES: this
    EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    be opened for writing
    */
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(fileName));
    }

    /*
    MODIFIES: this
    EFFECTS: writes GameHistory object as json to file
     */
    public void write(Writeable gh) {
        JSONObject json = gh.toJson();
        saveToFile(json.toString(TAB));
    }

    /*
    MODIFIES: this
    EFFECTS: closes file writer
     */
    public void close() {
        writer.close();
    }

    /*
    MODIFIES: this
    EFFECTS: writes string to file
     */
    private void saveToFile(String json) {
        writer.print(json);
    }
}
