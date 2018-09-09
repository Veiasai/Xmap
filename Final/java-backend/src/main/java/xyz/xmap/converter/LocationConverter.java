package xyz.xmap.converter;

import org.neo4j.ogm.typeconversion.CompositeAttributeConverter;
import xyz.xmap.pojo.Location;

import java.util.HashMap;
import java.util.Map;

public class LocationConverter implements CompositeAttributeConverter<Location> {
    @Override
    public Map<String, ?> toGraphProperties(Location location) {
        Map<String, Double> properties = new HashMap<>();
        if (location != null)  {
            properties.put("latitude", location.getLatitude());
            properties.put("longitude", location.getLongitude());
        }
        return properties;
    }

    @Override
    public Location toEntityAttribute(Map<String, ?> map) {
        Double latitude = (Double) map.get("latitude");
        Double longitude = (Double) map.get("longitude");
        if (latitude != null && longitude != null) {
            return new Location(latitude, longitude);
        }
        return null;
    }

}