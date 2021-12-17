package com.assignment2.vaccinator.services;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


// This class for get the Data from the given Url
public class DownloadURL {

    // This method is used to fetched the data from remote Url and returning it as a String
    public String readUrl(String myUrl) throws IOException
    {
        String data = "";
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;

        try {
            URL url = new URL(myUrl);
            // Opening Http connection
            urlConnection=(HttpURLConnection) url.openConnection();
            urlConnection.connect();
            // InputStream for fetching data from connection
            inputStream = urlConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb = new StringBuffer();

            String line = "";
            // Reading the data from buffer
            while((line = br.readLine()) != null)
            {
                sb.append(line);
            }

            data = sb.toString();
            br.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            inputStream.close();
            urlConnection.disconnect();
        }
        Log.d("DownloadURL","Returning data= "+data);

        return data;
    }
}
