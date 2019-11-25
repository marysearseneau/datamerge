package com.mariner.datamerge.parse;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.mariner.datamerge.model.DataRecord;
import static com.mariner.datamerge.parse.Constants.*;

/**
 * The CSV Record Parser is responsible for iterating through an arbitrary
 * Comma-Separated List of records and parsing them into a List of
 * <code>DataRecord</code> objects as defined by the required
 * <code>parse()</code> method in it's <code>RecordParser</code> interface.
 * 
 * @author Maryse Arseneau
 *
 */
public class CsvRecordParser implements RecordParser {

	/**
	 * The location of the Csv file to be parsed
	 */
	private String location;

	/**
	 * Constructor that takes in a path location of the target Csv file
	 * 
	 * @param location The path of the target Csv file
	 */
	public CsvRecordParser(final String location) {
		this.location = location;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.mariner.datamerge.Parser#parse()
	 */
	@Override
	public List<DataRecord> parse() {

		List<DataRecord> records = new ArrayList<>();

		try (Reader reader = Files.newBufferedReader(Paths.get(location));
				CSVParser csvParser = new CSVParser(reader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withHeader(CLIENT_ADDRESS_NAME, CLIENT_GUID_NAME,
								REQUEST_TIME_NAME, SERVICE_GUID_NAME, RETRIES_REQUEST_NAME, PACKETS_REQUESTED_NAME,
								PACKETS_SERVICED_NAME, MAX_HOLE_SIZE_NAME));) {
			for (CSVRecord csvRecord : csvParser) {
				DataRecord record = new DataRecord();
				record.setMaxHoleSize(Integer.parseInt(csvRecord.get(MAX_HOLE_SIZE_NAME)));
				record.setPacketsServiced(Integer.parseInt(csvRecord.get(PACKETS_SERVICED_NAME)));
				record.setPacketsRequested(Integer.parseInt(csvRecord.get(PACKETS_REQUESTED_NAME)));
				record.setClientGuid(csvRecord.get(CLIENT_GUID_NAME));
				record.setClientAddress(csvRecord.get(CLIENT_ADDRESS_NAME));
				record.setRequestTime(csvRecord.get(REQUEST_TIME_NAME));
				record.setServiceGuid(csvRecord.get(SERVICE_GUID_NAME));
				record.setRetriesRequest(Integer.parseInt(csvRecord.get(RETRIES_REQUEST_NAME)));
				if (record.getPacketsServiced() != 0) {
					records.add(record);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return records;
	}
}
