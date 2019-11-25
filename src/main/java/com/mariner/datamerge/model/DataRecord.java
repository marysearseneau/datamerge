package com.mariner.datamerge.model;

import static com.mariner.datamerge.parse.Constants.DATE_FORMAT;

import java.text.ParseException;
import java.util.Date;

/**
 * The DataRecord model object encapsulates a unified representation of records
 * found in each file format. ( JSON, CSV, and XML ). Typically, this object
 * would follow the Data transfer object ( DTO ) design pattern, as it does not
 * have any specific behavior defined except for storage, retrieval, formatting,
 * serialization and deserialization of its own data.
 * 
 * See <a href="https://en.wikipedia.org/wiki/Data_transfer_object">Data Transfer Object</a>
 * 
 * @author Maryse Arseneau
 *
 */
public class DataRecord {

	private int maxHoleSize;

	private int packetsServiced;

	private int packetsRequested;

	private String clientGuid;

	private String clientAddress;

	private Date requestTime;

	private String serviceGuid;

	private int retriesRequest;

	public DataRecord() {
	}

	public DataRecord(int maxHoleSize, int packetsServiced, int packetsRequested, String clientGuid,
			String clientAddress, Date requestTime, String serviceGuid, int retriesRequest) {
		super();
		this.maxHoleSize = maxHoleSize;
		this.packetsServiced = packetsServiced;
		this.packetsRequested = packetsRequested;
		this.clientGuid = clientGuid;
		this.clientAddress = clientAddress;
		this.requestTime = requestTime;
		this.serviceGuid = serviceGuid;
		this.retriesRequest = retriesRequest;
	}

	public int getMaxHoleSize() {
		return maxHoleSize;
	}

	public void setMaxHoleSize(int maxHoleSize) {
		this.maxHoleSize = maxHoleSize;
	}

	public int getPacketsServiced() {
		return packetsServiced;
	}

	public void setPacketsServiced(int packetsServiced) {
		this.packetsServiced = packetsServiced;
	}

	public int getPacketsRequested() {
		return packetsRequested;
	}

	public void setPacketsRequested(int packetsRequested) {
		this.packetsRequested = packetsRequested;
	}

	public String getClientGuid() {
		return clientGuid;
	}

	public void setClientGuid(String clientGuid) {
		this.clientGuid = clientGuid;
	}

	public String getClientAddress() {
		return clientAddress;
	}

	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Long longTime) {
		this.requestTime = new Date(longTime);
	}

	public void setRequestTime(String requestTime) {
		this.requestTime = toDate(requestTime);
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	public String getServiceGuid() {
		return serviceGuid;
	}

	public void setServiceGuid(String serviceGuid) {
		this.serviceGuid = serviceGuid;
	}

	public int getRetriesRequest() {
		return retriesRequest;
	}

	public void setRetriesRequest(int retriesRequest) {
		this.retriesRequest = retriesRequest;
	}

	private Date toDate(String requestTime) {
		Date date = new Date();
		try {
			date = DATE_FORMAT.parse(requestTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return date;
	}

	@Override
	public String toString() {
		return "Record [maxHoleSize=" + maxHoleSize + ", packetsServiced=" + packetsServiced + ", packetsRequested="
				+ packetsRequested + ", clientGuid=" + clientGuid + ", clientAddress=" + clientAddress
				+ ", requestTime=" + requestTime + ", serviceGuid=" + serviceGuid + ", retriesRequest=" + retriesRequest
				+ "]";
	}

}
