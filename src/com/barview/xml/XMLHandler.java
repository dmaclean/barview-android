package com.barview.xml;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.barview.constants.BarviewConstants;
import com.barview.models.Favorite;

public class XMLHandler extends DefaultHandler {
	
	private String text = "";
	
	ArrayList<Favorite> favorites = new ArrayList<Favorite>();
	Favorite currFave;
	
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
		// We've reached the opening tag for a favorite aggregate.  (Re)initialize the Favorite object.
		if(qName.equals(BarviewConstants.FAVORITE_AGGREGATE))
			currFave = new Favorite();
	}
	
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
		if(qName.equals(BarviewConstants.FAVORITE_AGGREGATE))
			favorites.add(currFave);
		else if(qName.equals(BarviewConstants.FAVORITE_BAR_ID))
			currFave.setBarId(text);
		else if(qName.equals(BarviewConstants.FAVORITE_NAME))
			currFave.setBarName(text);
		else if(qName.equals(BarviewConstants.FAVORITE_ADDRESS))
			currFave.setAddress(text);
		
		text = "";
	}
	
	public void characters(char ch[], int start, int length) {
		text += new String(ch, start, length);
	}
	
	public void startDocument() throws SAXException {
        // Do some startup if needed
	}
	
	public ArrayList<Favorite> getParsedFavorites() {
		return favorites;
	}
}
