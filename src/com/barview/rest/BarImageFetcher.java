package com.barview.rest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.barview.constants.BarviewConstants;
import com.barview.models.BarImage;
import com.barview.rest.RestClient.RequestMethod;
import com.barview.utilities.BarviewUtilities;
import com.barview.xml.BarImageXMLHandler;

public class BarImageFetcher extends AsyncTask<String, Integer, String> {

	String response = "";
	
	BarImage barImage;
	
	ImageView imageView;
	
	@Override
	protected String doInBackground(String... params) {
		RestClient client = new RestClient(BarviewUtilities.getBarImageURLForRunMode() + "/" + params[0]);
		
		try {
			client.Execute(RequestMethod.GET);
			response = client.getResponse();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			/* Get a SAXParser from the SAXPArserFactory. */
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();

            /* Get the XMLReader of the SAXParser we created. */
            XMLReader xr = sp.getXMLReader();
            /* Create a new ContentHandler and apply it to the XML-Reader*/
            BarImageXMLHandler myExampleHandler = new BarImageXMLHandler();
            xr.setContentHandler(myExampleHandler);
           
            /* Parse the xml-data from our URL. */
            xr.parse(new InputSource(new StringReader(response)));
            /* Parsing has finished. */

            /* Our ExampleHandler now provides the parsed data to us. */
            barImage = myExampleHandler.getBarImage();
		}
		catch(Exception e) {
			Log.e("doInBackground", e.getMessage());
		}
		
		return response;
	}
	
	protected void onPostExecute(String result) {
		InputStream is = new ByteArrayInputStream(barImage.getBarImageBytes());
		
		imageView.setImageDrawable(Drawable.createFromStream(is, ""));
		
		
	}

	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}

}
