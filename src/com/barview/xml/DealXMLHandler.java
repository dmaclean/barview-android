package com.barview.xml;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.barview.constants.BarviewConstants;
import com.barview.models.Deal;

public class DealXMLHandler extends DefaultHandler {

	private String text = "";
	private Deal deal;
	private ArrayList<Deal> deals;
	
	public DealXMLHandler() {
		deals = new ArrayList<Deal>();
	}
	
	/**
	 * In here we only really care about when a new <event> aggregate is encountered because
	 * we have to instantiate a new Deal object.  For the fields themselves, the characters()
	 * method will take care of them.
	 */
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
		String element = (qName.equals("")) ? localName : qName;
		if(element.equals(BarviewConstants.EVENT_AGGREGATE))
			deal = new Deal();
	}
	
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
		String element = (qName.equals("")) ? localName : qName;
		
		if(element.equals(BarviewConstants.EVENT_BARNAME))
			deal.setBarName(text);
		else if(element.equals(BarviewConstants.EVENT_DETAIL))
			deal.setDetail(text);
		else if(element.equals(BarviewConstants.EVENT_AGGREGATE))
			deals.add(deal);
		
		text = "";
	}
	
	public void characters(char ch[], int start, int length) {
		text += new String(ch, start, length);
	}
	
	public void startDocument() throws SAXException {
		// We don't use this.
	}
	
	public ArrayList<Deal> getDeals() {
		return deals;
	}
}
