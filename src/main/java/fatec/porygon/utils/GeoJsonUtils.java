package fatec.porygon.utils;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.io.geojson.GeoJsonReader;
import org.locationtech.jts.io.geojson.GeoJsonWriter;

public class GeoJsonUtils {
    
    private static final GeoJsonReader geoJsonReader = new GeoJsonReader();
    private static final GeoJsonWriter geoJsonWriter = new GeoJsonWriter();
    private static final WKTReader wktReader = new WKTReader();
    
    public static Geometry fromGeoJson(String geoJson) {
        if (geoJson == null || geoJson.trim().isEmpty()) {
            return null;
        }
        
        try {
            return geoJsonReader.read(geoJson);
        } catch (ParseException e) {
            throw new RuntimeException("Erro ao converter GeoJSON para Geometry: " + e.getMessage(), e);
        }
    }
    
    public static String toGeoJson(Geometry geometry) {
        if (geometry == null) {
            return null;
        }
        
        return geoJsonWriter.write(geometry);
    }
    
    public static Geometry fromWkt(String wkt) {
        if (wkt == null || wkt.trim().isEmpty()) {
            return null;
        }
        
        try {
            return wktReader.read(wkt);
        } catch (ParseException e) {
            throw new RuntimeException("Erro ao converter WKT para Geometry: " + e.getMessage(), e);
        }
    }
}
