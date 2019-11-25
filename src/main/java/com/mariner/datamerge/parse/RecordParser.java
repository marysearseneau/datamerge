package com.mariner.datamerge.parse;

import java.util.List;

import com.mariner.datamerge.model.DataRecord;

/**
 * The Record Parser interface defines parsing behavior that iterates through an
 * underlying data format, supplying a generalized List of
 * <code>DataRecord</code> objects to be consumed by calling classes.
 * 
 * The implemented parsing classes assume the responsibility of reading
 * underlying data formats, preparing/formatting record fields and creating the
 * resulting list of <code>DataRecord</code> objects.
 * 
 * @author Maryse Arseneau
 *
 */
public interface RecordParser {

	/**
	 * Iterate through and parse a collection of underlying records, formulating a
	 * resulting list of DataRecord objects.
	 * 
	 * @return The resulting List of DataRecords
	 */
	public List<DataRecord> parse();

}