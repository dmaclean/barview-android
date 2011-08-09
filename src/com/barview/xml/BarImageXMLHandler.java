package com.barview.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Base64;

import com.barview.constants.BarviewConstants;
import com.barview.models.BarImage;

public class BarImageXMLHandler extends DefaultHandler {
	private String text = "";
	private BarImage barImage;
	
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
		if(qName.equals(BarviewConstants.BARIMAGE_AGGREGATE))
			barImage = new BarImage();
	}
	
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
		if(qName.equals(BarviewConstants.BARIMAGE_BAR_ID))
			barImage.setBarId(text);
		else if(qName.equals(BarviewConstants.BARIMAGE_IMAGE))
			barImage.setBarImageBytes(Base64.decode(text, Base64.DEFAULT));
		
		text = "";
	}
	
	public void characters(char ch[], int start, int length) {
		text += new String(ch, start, length);
	}
	
	public void startDocument() throws SAXException {
		
	}

	public BarImage getBarImage() {
		return barImage;
	}
}
