package main.java.fatec.porygon.utils;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.geojson.GeoJsonReader;
import org.locationtech.jts.io.geojson.GeoJsonWriter;

public class ConvertGeoJsonUtils {

    public Geometry convertGeoJsonToGeometry(String geoJson) {
        GeoJsonReader geoJsonReader = new GeoJsonReader();

        if (geoJson == null || geoJson.trim().isEmpty()) {
            return null;
        }

        try {
            return geoJsonReader.read(geoJson);
        } catch (ParseException e) {
            throw new RuntimeException("Erro ao converter GeoJSON para Geometry: " + e.getMessage(), e);
        }

    }

    public String convertGeometryToGeoJson(Geometry geometry) {

        GeoJsonWriter geoJsonWriter = new GeoJsonWriter();

        if (geometry == null) {
            return null;
        }

        return geoJsonWriter.write(geometry);
    }

}
