package main;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

public class DomParser {

    public static void main(String[] args) throws DocumentException, UnirestException {
        GetRequest request = Unirest.get("https://lenta.ru/rss/news");

        SAXReader reader = new SAXReader();
        Document document = reader.read(request.asBinary().getBody());

        List<Item> items = new ArrayList<>();
        List<Node> nodes = document.selectNodes("/rss/channel/item");
        nodes.stream().forEach(node -> items.add(new Item(node.selectSingleNode("guid").getStringValue(),
                node.selectSingleNode("title").getStringValue(),
                node.selectSingleNode("description").getStringValue(),
                node.selectSingleNode("pubDate").getStringValue())));
        items.stream().forEach(System.out::println);
    }
}
