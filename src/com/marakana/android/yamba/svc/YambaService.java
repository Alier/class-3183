/* $Id: $
   Copyright 2013, G. Blake Meike

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package com.marakana.android.yamba.svc;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.marakana.android.yamba.BuildConfig;
import com.marakana.android.yamba.R;
import com.marakana.android.yamba.clientlib.YambaClient;


/**
 *
 * @version $Revision: $
 * @author <a href="mailto:blake.meike@gmail.com">G. Blake Meike</a>
 */
public class YambaService extends IntentService {
    private static final String TAG = "YambaService";
    private static final String KEY_STATUS = "YambaService.STATUS";

    public static void post(Context ctxt, String status) {
        Intent i = new Intent(ctxt, YambaService.class);
        i.putExtra(KEY_STATUS, status);
        ctxt.startService(i);
    }

    public YambaService() { super(TAG); }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (BuildConfig.DEBUG) { Log.d(TAG, "on start"); }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        doPost(intent.getStringExtra(KEY_STATUS));
    }

    private void doPost(String status) {
        if (BuildConfig.DEBUG) { Log.d(TAG, "Posting: " + status); }

        int msg = R.string.post_failed;
        try {
            new YambaClient(
                    "student",
                    "password",
                    "http://yamba.marakana.com/api")
            .updateStatus(status);
            msg = R.string.post_succeeded;
        }
        catch (Exception e) { Log.e(TAG, "Post failed!", e); }

        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
}
