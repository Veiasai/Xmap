package xyz.veiasai.neo4j.converter;

import org.neo4j.ogm.typeconversion.CompositeAttributeConverter;
import xyz.veiasai.neo4j.pojo.Content;

import java.util.*;

public class ContentConverter implements CompositeAttributeConverter<List<Content>> {
    @Override
    public Map<String, ?> toGraphProperties(List<Content> contents) {
        Map<String, List<String>> properties = new HashMap<>();
        Iterator<Content> iterator = contents.listIterator();
        List<String> types = new ArrayList<>();
        List<String> messages = new ArrayList<>();
        while (iterator.hasNext())
        {
            Content t = iterator.next();
            types.add(t.getType());
            messages.add(t.getMessage());
        }
        properties.put("types", types);
        properties.put("messages", messages);
        return properties;
    }

    @Override
    public List<Content> toEntityAttribute(Map<String, ?> map) {
        List<String> types = (List<String>) map.get("types");
        List<String> messages = (List<String>) map.get("messages");
        if (types != null && messages != null && types.size() == messages.size())
        {
            List<Content> contents = new ArrayList<>();
            for (int i = 0; i<types.size(); i++)
                contents.add(new Content(types.get(i), messages.get(i)));
            return contents;
        }
        return null;
    }
}
