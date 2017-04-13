package com.firdavsiimajidzoda.myvideplayer.Models;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.firdavsiimajidzoda.myvideplayer.Controllers.MyAdapter;
import com.firdavsiimajidzoda.myvideplayer.Models.FeedItem;
import com.firdavsiimajidzoda.myvideplayer.R;
import com.firdavsiimajidzoda.myvideplayer.UI.MainActivity;
import com.firdavsiimajidzoda.myvideplayer.UI.VerticalSpace;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by firdavsiimajidzoda on 4/12/17.
 */

/**
 *  This asynctast class run off the UI to download and fetch RSS XML
 */

public class ReadRSS extends AsyncTask <Void, Void, String> {
    // Fields
    Context context;
    ProgressDialog progressDialog;
    String address = "http://www.snagfilms.com/feeds/";
    URL url;
    ArrayList<FeedItem> feedItems;
    RecyclerView recyclerView;
    String channelTitle;

    // Constructor
    public  ReadRSS(Context context, RecyclerView recyclerView){
        this.context = context;
        this.recyclerView = recyclerView;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
    }

    @Override
    protected void onPreExecute() {
        // Show loading
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) {
        // Process XML after getting data from WEB
        processXml(getData());
        return channelTitle;
    }

    @Override
    protected void onPostExecute(String aVoid) {
        super.onPostExecute(aVoid);
        // Dismiss loading message
        progressDialog.dismiss();

        // Setting adapter for recycler view
        MyAdapter adapter = new MyAdapter(context, feedItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new VerticalSpace(50));
        recyclerView.setAdapter(adapter);
    }

    /**
     * Process XML parse items inside it
     * @param data downloaded and to be Processed
     */
    private void processXml (Document data){
        if (data != null){
            // Item collector
            feedItems = new ArrayList<>();

            // Get RSS
            Element root = data.getDocumentElement();

            // Get Channel
            Node channel = root.getChildNodes().item(1);

            // Get items inside Channel
            NodeList items = channel.getChildNodes();

            // Go through each item
            for (int i = 0; i < items.getLength(); i++ ){
                //Create a channel node
                Node currentItem = items.item(i);

                // If item
                if (currentItem.getNodeName().equalsIgnoreCase("item")){
                    // New item
                    FeedItem item = new FeedItem();

                    // Get item node details
                    NodeList itemChilds = currentItem.getChildNodes();

                    //Go through each node detail
                    for (int j = 0; j < itemChilds.getLength(); j++){
                        // Detail node
                        Node current = itemChilds.item(j);
                        if (current.getNodeName().equalsIgnoreCase("title")){
                            // Set title of the item
                            item.setTitle(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase("description")){
                            // Set description of the item
                            item.setDescription(current.getTextContent());
                        } else if (current.getNodeName().equalsIgnoreCase("year")){
                            // Set published year of the item
                            item.setPubDate(current.getTextContent());
                        }  else if (current.getNodeName().equalsIgnoreCase("media:content")){
                            // Set video link of the item
                            String url = current.getAttributes().item(3).getTextContent();
                            item.setLink(url);

                            // Set duration of the item video
                            String durtion = current.getAttributes().item(0).getTextContent();
                            item.setDuration(durtion);
                        } else if (current.getNodeName().equalsIgnoreCase("media:thumbnail")){
                            // Set thumbnail picuter of the item
                            if (current.getAttributes().item(0).getTextContent().equalsIgnoreCase("thumbnail")){
                                String url = current.getAttributes().item(1).getTextContent();
                                item.setThumbnail(url);
                            }
                        }
                    }
                    feedItems.add(item);


                } else if (currentItem.getNodeName().equalsIgnoreCase("title")){
                    // Title of channel
                    channelTitle = currentItem.getTextContent();
                }
            }
        }

    }


    /**
     * Open URL connection to download and Document RSS XML
     * @return
     */
    public Document getData(){
        try {
            url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDoc = builder.parse(inputStream);
            return xmlDoc;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
