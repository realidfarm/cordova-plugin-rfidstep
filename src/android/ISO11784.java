package com.realidtek.rfid;

import android.annotation.SuppressLint;

public class ISO11784{
	long m_AID = 0x8000000000000000L;
	boolean hdx=false;

	public ISO11784() {

	}

	public ISO11784(short iCountry, long iNational) {
		//this.Country = iCountry;
		setCountry(iCountry);
		// this.National = iNational;
		setNational(iNational);
		//this.Reserved1996 = 0;
		setReserved1996((short)0);
	}

	public ISO11784(short iCountry, long iNational, short iReserved1996) {
		setCountry(iCountry);
		setNational(iNational);
		setReserved1996(iReserved1996);
	}

	public ISO11784(long nAId) {
		m_AID = nAId;
	}

	public ISO11784(ISO11784 oAId) {
		m_AID = oAId.getAID();
		m_trailer[0] = oAId.m_trailer[0];
		m_trailer[1] = oAId.m_trailer[1];
		m_trailer[2] = oAId.m_trailer[2];
	}

	public boolean isHdx() {
		return hdx;
	}

	public void setHdx(boolean hdx) {
		this.hdx = hdx;
	}

	@SuppressLint("DefaultLocale")
	public String getId() {
		return String.format("%1$03d%2$012d", getCountry(), getNational());
	}
	@Override
	public String toString(){
		if(hdx){
			return "HDX:" + getId();
		}else{
			return "FDX-B:" + getId();
		}
	}
	public long toLong(){
		return m_AID;
	}

	public short getCountry() {
		return (short)((m_AID>>38)& 0x3FF);
	}

	public void setCountry(short value) {
		long isoData;
		isoData = (long)(value & 0x3FF);
		isoData <<= 38;
		isoData |= (m_AID & 0xFFFF003FFFFFFFFFL);
		m_AID = isoData;
	}

	public long getNational() {
		return (m_AID& 0x3FFFFFFFFFL);
	}

	public void setNational(long value) {
		long isoData;
		isoData = (long)(value & 0x3FFFFFFFFFL);
		isoData |= (m_AID & 0xFFFFFFC000000000L);
		m_AID = isoData;
	}

	public short getReserved1996() {
		return (short)( (m_AID>>49)&0x3FFF);
	}

	public void setReserved1996(short value) {
		long isoData;
		isoData = (long)(value & 0x3FFF);
		isoData <<= 49;
		isoData |= (m_AID & 0x8001FFFFFFFFFFFFL);
		m_AID = isoData;
	}

	public Boolean getAnimalTag() {
		if ((m_AID & 0x8000000000000000L) != 0)
			return true;
		else
			return false;
	}

	public void setAnimalTag(Boolean AnimalTag_value) {
		if (AnimalTag_value) {
			m_AID |= 0x8000000000000000L;
		} else {
			m_AID &= 0x7FFFFFFFFFFFFFFFL;
		}
	}

	public Boolean getExtDataTag() {
		if ((m_AID & 0x0001000000000000L) != 0)
			return true;
		else
			return false;
	}

	public void setExtDataTag(Boolean extDataFlag) {
		if (extDataFlag) {
			m_AID |= 0x0001000000000000L;
		} else {
			m_AID &= 0xFFFEFFFFFFFFFFFFL;
		}
	}

	public int getRetaggingCount() {
		return (int)( (m_AID>>60)&0x7);
	}

	public void setRetaggingCount(int value) {
		long isoData;
		isoData = (long)(value & 0x7);
		isoData <<= 60;

		isoData |= (m_AID & 0x8FFFFFFFFFFFFFFFL);
		m_AID = isoData;

	}

	public int getUserInfo() {
		return (int)( (m_AID>>55)&0x1F);
	}

	public void setUserInfo(int value) {
		long isoData;
		isoData = (long)(value & 0x1F);
		isoData <<= 55;
		isoData |= (m_AID & 0xF07FFFFFFFFFFFFFL);
		m_AID = isoData;
	}

	public int getReserved2004() {
		return (int)( (m_AID>>49)&0x3F);
	}

	public void setReserved2004(int value) {
		long isoData;
		isoData = (long)(value & 0x3F);
		isoData <<= 49;

		isoData |= (m_AID & 0xFF81FFFFFFFFFFFFL);
		m_AID = isoData;
	}

	public long getAID() {
		return this.m_AID;
	}

	public void setAID(long value) {
		this.m_AID = value;
	}

	public void reset() {
		m_AID = 0x8000000000000000L;
		m_trailer[0] = 0;
		m_trailer[1] = 0;
		m_trailer[2] = 0;
	}

	public static ISO11784 parseAID(String strId) {
		short iCountry = Short.parseShort(strId.substring(0, 3));
		long iNational = Long.parseLong(strId.substring(3, 15));
		return new ISO11784(iCountry, iNational);
	}

	int[] m_trailer = new int[] { 0, 0, 0 };
	public int[] getTrailer() {
		return m_trailer;
	}

}
