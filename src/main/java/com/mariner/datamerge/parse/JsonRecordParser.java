package com.mariner.datamerge.parse;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.mariner.datamerge.model.DataRecord;
import static com.mariner.datamerge.parse.Constants.*;

/**
 * The JSON Record Parser is responsible for iterating through an arbitrary
 * Comma-Separated List of records and parsing them into a List of
 * <code>DataRecord</code> objects as defined by the required
 * <code>parse()</code> method in it's <code>RecordParser</code> interface.
 * 
 * @author Maryse Arseneau
 *
 */
public class JsonRecordParser implements RecordParser {

	/**
	 * The location of the Json file to be parsed
	 */
	private String location;

	/**
	 * Constructor that takes in a path location of the target Json file
	 * 
	 * @param location The path of the target Json file
	 */
	public JsonRecordParser(final String location) {
		this.location = location;
	}
	
	/* (non-Javadoc)
	 * @see com.mariner.datamerge.parse.RecordParser#parse()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DataRecord> parse() {
		List<DataRecord> records = new ArrayList<>();
		JSONParser jsonParser = new JSONParser();
		try (FileReader reader = new FileReader(location)) {
			// Read JSON file
			Object obj = jsonParser.parse(reader);
			JSONArray recordList = (JSONArray) obj;
			
			// Iterate over records
			recordList.forEach(jsonRecord -> {
				DataRecord record = parseRecord((JSONObject) jsonRecord);
				if(record.getPacketsServiced() != 0) {
					records.add(record);
				}});

		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return records;
	}

	private DataRecord parseRecord(JSONObject jsonRecord) {
		String clientAddress = (String) jsonRecord.get(CLIENT_ADDRESS_NAME);
		String clientGuid = (String) jsonRecord.get(CLIENT_GUID_NAME);		
		Long requestTime = (Long) jsonRecord.get(REQUEST_TIME_NAME);		
		String serviceGuid = (String) jsonRecord.get(SERVICE_GUID_NAME);		
		Long retriesRequest = (Long) jsonRecord.get(RETRIES_REQUEST_NAME);		
		Long packetsRequested = (Long) jsonRecord.get(PACKETS_REQUESTED_NAME);		
		Long packetsServiced = (Long) jsonRecord.get(PACKETS_SERVICED_NAME);		
		Long maxHoleSize = (Long) jsonRecord.get(MAX_HOLE_SIZE_NAME);
		
		DataRecord record = new DataRecord();
		record.setMaxHoleSize(maxHoleSize.intValue());
		record.setPacketsServiced(packetsServiced.intValue());
		record.setPacketsRequested(packetsRequested.intValue());
		record.setClientGuid(clientGuid);
		record.setClientAddress(clientAddress);
		record.setRequestTime(requestTime);
		record.setServiceGuid(serviceGuid);
		record.setRetriesRequest(retriesRequest.intValue());
		return record;
	}

}
