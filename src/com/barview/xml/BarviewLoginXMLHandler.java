package com.barview.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.barview.constants.BarviewConstants;
import com.barview.mobile.BarviewMobileUser;

public class BarviewLoginXMLHandler extends DefaultHandler {
	private String text = "";
	private BarviewMobileUser user;
	
	public BarviewLoginXMLHandler() {
		
	}
	
	public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
//		String element = (qName.equals("")) ? localName : qName;
//		if(element.equals(BarviewConstants.BARVIEW_USER))
//			user = new BarviewMobileUser();
	}
	
	public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
		String element = (qName.equals("")) ? localName : qName;
		
		if(element.equals(BarviewConstants.BARVIEW_CITY))
			user.setCity(text);
		else if(element.equals(BarviewConstants.BARVIEW_DOB))
			user.setDob(text);
		else if(element.equals(BarviewConstants.BARVIEW_EMAIL))
			user.setUserId(text);
		else if(element.equals(BarviewConstants.BARVIEW_FIRST_NAME))
			user.setFirstName(text);
		else if(element.equals(BarviewConstants.BARVIEW_LAST_NAME))
			user.setLastName(text);
		else if(element.equals(BarviewConstants.BARVIEW_STATE))
			user.setState(text);
		else if(element.equals(BarviewConstants.BARVIEW_TOKEN))
			user.setToken(text);
		
		text = "";
	}
	
	public void characters(char ch[], int start, int length) {
		text += new String(ch, start, length);
	}
	
	public void startDocument() throws SAXException {
		
	}
	
	public BarviewMobileUser getUser() {
		return user;
	}
	
	public void setUser(BarviewMobileUser user) {
		this.user = user;
	}
}
