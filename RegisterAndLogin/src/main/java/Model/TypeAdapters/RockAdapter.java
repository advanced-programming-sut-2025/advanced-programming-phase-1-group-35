package Model.TypeAdapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import Model.FarmStuff.Rock;

public class RockAdapter extends TypeAdapter<Rock> {

    @Override
    public void write(JsonWriter out, Rock rock) throws IOException {
        if (rock == null) {
            out.nullValue();
            return;
        }

        out.beginObject();
        out.name("type").value("Rock");
        out.name("symbol").value(rock.getSymbol());
        out.endObject();
    }

    @Override
    public Rock read(JsonReader in) throws IOException {
        in.beginObject();
        Rock rock = new Rock();
        while (in.hasNext()) {
            in.nextName(); // skip field names
            in.skipValue();
        }
        in.endObject();
        return rock;
    }
}
