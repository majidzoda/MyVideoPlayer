package com.firdavsiimajidzoda.myvideplayer;

import android.app.Instrumentation;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.webkit.WebView;

import com.firdavsiimajidzoda.myvideplayer.Models.ReadRSS;
import com.firdavsiimajidzoda.myvideplayer.UI.MainActivity;
import com.firdavsiimajidzoda.myvideplayer.UI.VideoDetail;

/**
 * Created by firdavsiimajidzoda on 4/14/17.
 */


/**
 * This test class Test UI for MainActivity and performs a click to open VideoDetailActivity and test UI for VideoDetailActivity
 */

public class TestMyVideoPlayer extends android.test.ActivityInstrumentationTestCase2<MainActivity> {

    // Fields
    MainActivity mainActivity;
    RecyclerView mRecyclerView;
    RecyclerView.Adapter adapter;
    public static final int INITIAL_POSITION = 0;
    ReadRSS readRSS;
    Instrumentation.ActivityMonitor activityMonitor;
    VideoDetail videoDetail;


    public TestMyVideoPlayer() {
        super("com.firdavsiimajidzoda.myvideplayer.UI.MainActivity", MainActivity.class);
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Set activity monitro for child activity (VideoDetailA activity)
        activityMonitor = getInstrumentation().addMonitor(VideoDetail.class.getName(), null, false);

        // On touch on devide disable
        setActivityInitialTouchMode(false);

        // Initialize Main activity
        mainActivity = getActivity();

        // Initialize recyclerview for main activity
        mRecyclerView = (RecyclerView) mainActivity.findViewById(R.id.recyclerView);

        // Initialize adapter for recycler view
        adapter = mRecyclerView.getAdapter();
    }

    public void testPreConditions() {
        // Test preconditions for not null for recycler view and adapter
        assertTrue(mRecyclerView != null);
        assertTrue(adapter != null);
    }

    public void testMainActivity() {
        // Run thread
        mainActivity.runOnUiThread(
                new Runnable() {
                    public void run() {
                        // Initialize ReadRSS
                        readRSS = new ReadRSS(mainActivity.getApplicationContext(), mRecyclerView);

                        // Test ReadRSS for not null
                        assertNotNull(readRSS);

                        // Initialize first item view from recucler view;
                        View v = mRecyclerView.findViewHolderForAdapterPosition(0).itemView;

                        // Test the item view for not null
                        assertNotNull(v);

                        // Perform click on item view
                        v.callOnClick();
                    }
                }
        );

        // Initialize VideoDetail Activity
        VideoDetail videoDetailActivity = (VideoDetail) getInstrumentation().waitForMonitorWithTimeout(activityMonitor, 5000);

        // Test VideoDetail activity for not null
        assertNotNull(videoDetailActivity);

        // Initialize WebView for VideoDetail Activity
        WebView webView = (WebView)videoDetailActivity.findViewById(R.id.webView);

        // Test WebView for not null
        assertNotNull(webView);

        // Finish VideoDetail Activity
        videoDetailActivity.finish();

        // Finish MainActivity
        mainActivity.finish();
    }
}
