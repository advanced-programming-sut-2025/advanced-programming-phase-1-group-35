package Model.TypeAdapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import Model.Tile;
import Model.FarmStuff.Rock;
import Model.enums.TileType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TileAdapter extends TypeAdapter<Tile> {

    @Override
    public void write(JsonWriter out, Tile tile) throws IOException {
        if (tile == null) {
            out.nullValue();
            return;
        }

        out.beginObject();
        out.name("x").value(tile.getCoordination().x);
        out.name("y").value(tile.getCoordination().y);
        out.name("symbol").value(tile.getSymbol());
        out.name("tileType").value(tile.getTileType().name());
        out.name("walkable").value(tile.isWalkable());

        // Handle contents carefully
        out.name("contents").beginArray();
        if (tile.getContents() != null) {
            for (Object content : tile.getContents()) {
                if (content instanceof Rock) {
                    out.beginObject();
                    out.name("type").value("Rock");
                    out.name("symbol").value(((Rock)content).getSymbol());
                    out.endObject();
                }
                // Add other content types as needed
            }
        }
        out.endArray();

        out.endObject();
    }

    @Override
    public Tile read(JsonReader in) throws IOException {
        Tile tile = new Tile();
        in.beginObject();

        while (in.hasNext()) {
            String fieldName = in.nextName();
            switch (fieldName) {
                case "x":
                    tile.getCoordination().x = in.nextInt();
                    break;
                case "y":
                    tile.getCoordination().y = in.nextInt();
                    break;
                case "symbol":
                    tile.setSymbol(in.nextString().charAt(0));
                    break;
                case "tileType":
                    tile.setTileType(TileType.valueOf(in.nextString()));
                    break;
                case "walkable":
                    tile.setWalkable(in.nextBoolean());
                    break;
                case "contents":
                    in.beginArray();
                    ArrayList<Object> contents = new ArrayList<>();
                    while (in.hasNext()) {
                        in.beginObject();
                        while (in.hasNext()) {
                            String contentField = in.nextName();
                            if (contentField.equals("type") && in.nextString().equals("Rock")) {
                                contents.add(new Rock());
                            } else {
                                in.skipValue();
                            }
                        }
                        in.endObject();
                    }
                    in.endArray();
                    tile.setContents(contents);
                    break;
                default:
                    in.skipValue();
            }
        }

        in.endObject();
        return tile;
    }
}
