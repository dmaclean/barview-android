package com.barview.xml;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.barview.constants.BarviewConstants;
import com.barview.models.Bar;

public class NearbyBarXMLHandler extends DefaultHandler {
	private String text = "";
	private ArrayList<Bar> bars;
	private Bar bar;
	
	public NearbyBarXMLHandler() {
		bars = new ArrayList<Bar>();
	}
	
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
		if(qName.equals(BarviewConstants.NEARBY_BAR_AGGREGATE))
			bar = new Bar();
	}
	
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
		if(qName.equals(BarviewConstants.NEARBY_BAR_ID))
			bar.setBarId(text);
		else if(qName.equals(BarviewConstants.NEARBY_BAR_NAME))
			bar.setName(text);
		else if(qName.equals(BarviewConstants.NEARBY_BAR_ADDRESS))
			bar.setAddress(text);
		else if(qName.equals(BarviewConstants.NEARBY_BAR_LAT))
			bar.setLat(Double.parseDouble(text)*BarviewConstants.GEOPOINT_MULT);
		else if(qName.equals(BarviewConstants.NEARBY_BAR_LNG))
			bar.setLng(Double.parseDouble(text)*BarviewConstants.GEOPOINT_MULT);
		else if(qName.equals(BarviewConstants.NEARBY_BAR_AGGREGATE))
			bars.add(bar);
		
		text = "";
	}
	
	public void characters(char ch[], int start, int length) {
		text += new String(ch, start, length);
	}
	
	public void startDocument() throws SAXException {
		
	}
	
	public ArrayList<Bar> getNearbyBars() {
		return bars;
	}
}
