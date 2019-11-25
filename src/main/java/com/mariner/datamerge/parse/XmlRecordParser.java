package com.mariner.datamerge.parse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.mariner.datamerge.model.DataRecord;

import static com.mariner.datamerge.parse.Constants.*;

/**
 * The XML Record Parser is responsible for iterating through an arbitrary
 * Comma-Separated List of records and parsing them into a List of
 * <code>DataRecord</code> objects as defined by the required
 * <code>parse()</code> method in it's <code>RecordParser</code> interface.
 * 
 * @author Maryse Arseneau
 *
 */
public class XmlRecordParser implements RecordParser {

	/**
	 * The location of the Xml file to be parsed
	 */
	private String location;

	
	/**
	 * Constructor that takes in a path location of the target Xml file
	 * 
	 * @param location The path of the target Xml file
	 */
	public XmlRecordParser(final String location) {
		this.location = location;
	}

	/* (non-Javadoc)
	 * @see com.mariner.datamerge.parse.RecordParser#parse()
	 */
	@Override
	public List<DataRecord> parse() {
		List<DataRecord> records = new ArrayList<>();
		File xmlFile = new File(location);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getElementsByTagName("report");
			for (int i = 0; i < nodeList.getLength(); i++) {
				DataRecord record = parseRecord(nodeList.item(i));
				if (record.getPacketsServiced() != 0) {
					records.add(record);
				}
			}

		} catch (SAXException | ParserConfigurationException | IOException e1) {
			e1.printStackTrace();
		}
		return records;
	}

	private DataRecord parseRecord(Node node) {
		DataRecord record = new DataRecord();
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			Element element = (Element) node;
			record.setMaxHoleSize(Integer.parseInt(tagValue(MAX_HOLE_SIZE_NAME, element)));
			record.setPacketsServiced(Integer.parseInt(tagValue(PACKETS_SERVICED_NAME, element)));
			record.setPacketsRequested(Integer.parseInt(tagValue(PACKETS_REQUESTED_NAME, element)));
			record.setClientGuid(tagValue(CLIENT_GUID_NAME, element));
			record.setClientAddress(tagValue(CLIENT_ADDRESS_NAME, element));
			record.setRequestTime(tagValue(REQUEST_TIME_NAME, element));
			record.setServiceGuid(tagValue(SERVICE_GUID_NAME, element));
			record.setRetriesRequest(Integer.parseInt(tagValue(RETRIES_REQUEST_NAME, element)));
		}

		return record;
	}

	private String tagValue(String tag, Element element) {
		NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodeList.item(0);
		return node.getNodeValue();
	}

}
