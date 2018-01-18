package main;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.XMLEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws UnirestException, IOException, XMLStreamException {
        GetRequest request = Unirest.get("https://lenta.ru/rss/news");
        System.out.println(request.asString().getBody());

        List<Item> itemList = new ArrayList<>();
        /*new ByteArrayInputStream(Dummy.text.getBytes())*/
        try (StaxStreamProcessor processor = new StaxStreamProcessor(request.asBinary().getBody())) {
            XMLStreamReader reader = processor.getReader();

            while (reader.hasNext()) {
                int event = reader.next();
                if (event == XMLEvent.START_ELEMENT && "item".equals(reader.getLocalName())) {
                    Item item = new Item();
                    while (reader.hasNext()) {
                        event = reader.next();
                        if (event == XMLEvent.END_ELEMENT && "item".equals(reader.getLocalName())) {
                            itemList.add(item);
                            break;
                        }
                        if (event == XMLEvent.START_ELEMENT && "guid".equals(reader.getLocalName())) {
                            item.setGuid(reader.getElementText());
                        }

                        if (event == XMLEvent.START_ELEMENT && "title".equals(reader.getLocalName())) {
                            item.setTitle(reader.getElementText());
                        }

                        if (event == XMLEvent.START_ELEMENT && "description".equals(reader.getLocalName())) {
                            item.setDescription(reader.getElementText());
                        }

                        if (event == XMLEvent.START_ELEMENT && "pubDate".equals(reader.getLocalName())) {
                            item.setPubDate(reader.getElementText());
                        }
                    }
                }
            }
            itemList.stream().forEach(System.out::println);
        }
    }
}
