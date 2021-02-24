package Correo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class ControllerXML {
    public ControllerXML() {}


    public static String getCorreo(){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        String correo = "";

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document documento = builder.parse(new File("App.config"));
            NodeList listaEmail = documento.getElementsByTagName("AppConfig");

            for(int i = 0; i < listaEmail.getLength(); i++) {
                Node node = listaEmail.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    NodeList nodeList = element.getChildNodes();
                    for(int x = 0; x < nodeList.getLength(); x++) {
                        Node nodeChild = nodeList.item(x);
                        if(nodeChild.getNodeType() == Node.ELEMENT_NODE) {
                            correo = nodeChild.getTextContent();
                        }
                    }
                }
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return correo;
    }


}
