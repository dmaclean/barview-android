package com.barview;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.barview.constants.BarviewConstants;
import com.barview.models.BarImage;
import com.barview.rest.RestClient;
import com.barview.rest.RestClient.RequestMethod;
import com.barview.xml.BarImageXMLHandler;

public class DetailActivity extends Activity {
	
	private BarImage barImage;
	
	private ImageView imageView;
	
	private String barId;
	private String barName;
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.detail);
	}
	
	public void onResume() {
		super.onResume();
		
		Log.i("DetailActivity", "in onResume");
		
		Bundle extras = getIntent().getExtras();
		barId = extras.getString(BarviewConstants.BAR_ID);
		barName = extras.getString(BarviewConstants.BAR_NAME);
		
		TextView tv = (TextView) findViewById(R.id.detailTitle);
		tv.setText(barName);
		
		BarImageFetcher bif = new BarImageFetcher();
		bif.execute(barId);
	}
	
	/**
	 * An AsyncTask class that contacts the Barview server with the user id to
	 * get their favorites as an XML string.  Once retrieved, this string will
	 * be parsed by a SAX parser into individual Favorite objects.  Finally,
	 * we'll grab the ArrayList of Favorite objects so it can be put into the list.
	 * 
	 * @author dmaclean
	 *
	 */
	class BarImageFetcher extends AsyncTask<String, Integer, String> {

		String response = "";
		
		@Override
		protected String doInBackground(String... params) {
			RestClient client = new RestClient(BarviewConstants.BARIMAGES_URL_DEV + "/" + params[0]);
			
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
			
			imageView = (ImageView) findViewById(R.id.detailImage);
			imageView.setImageDrawable(Drawable.createFromStream(is, ""));
			
			
		}
	}
}
