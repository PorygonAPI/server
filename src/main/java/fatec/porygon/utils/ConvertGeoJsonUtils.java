package fatec.porygon.utils;

import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.geojson.GeoJsonReader;
import org.springframework.stereotype.Component;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.io.ParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ConvertGeoJsonUtils {

    private static final int DEFAULT_SRID = 4326; // WGS 84

    public Geometry convertGeoJsonToGeometry(String geoJson) {
        try {
            // Remove or fix CRS information if present
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(geoJson);
            
            // Remove CRS node if it exists
            if (rootNode.has("crs")) {
                ((com.fasterxml.jackson.databind.node.ObjectNode) rootNode).remove("crs");
            }
            
            // Create GeometryFactory with default SRID
            GeometryFactory geometryFactory = new GeometryFactory();
            GeoJsonReader reader = new GeoJsonReader(geometryFactory);
            
            // Convert back to string and parse
            String cleanedGeoJson = mapper.writeValueAsString(rootNode);
            return reader.read(cleanedGeoJson);
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter GeoJSON para Geometry: " + e.getMessage(), e);
        }
    }

    public String convertGeometryToGeoJson(Geometry geometry) {
        try {
            if (geometry == null) {
                return null;
            }
            org.locationtech.jts.io.geojson.GeoJsonWriter writer = 
                new org.locationtech.jts.io.geojson.GeoJsonWriter();
            return writer.write(geometry);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao converter Geometry para GeoJSON", e);
        }
    }

    public Geometry convertWKTToGeometry(String wkt) throws ParseException {
        WKTReader reader = new WKTReader();
        return reader.read(wkt);
    }
}
