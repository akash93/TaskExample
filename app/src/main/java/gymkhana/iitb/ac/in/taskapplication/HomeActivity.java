package gymkhana.iitb.ac.in.taskapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<String>{

    private static final String TAG = HomeActivity.class.getSimpleName();
    private TextView textView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        textView = (TextView) findViewById(R.id.text_view);
        getSupportLoaderManager().initLoader(0,null,this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<String> onCreateLoader(int i, Bundle bundle) {
        return new HeavyLoader(getApplicationContext(),"Some random url");

    }

    @Override
    public void onLoadFinished(Loader<String> loader, String s) {
        textView.setText(s);
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        //Clear data
    }

    public class HeavyTask extends AsyncTask<String,Integer,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String  url  = params[0];
            Log.i(TAG,"Do in background started with"+params[0]);
            String result= "";
            for (int i = 0; i <10 ; i++) {
                try {
                    Thread.sleep(1000);
                    publishProgress(i);
                    result +=  " " + i;
                } catch (InterruptedException e) {
                    Log.e(TAG, "doInBackground failed");
                }
            }
            return result;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.i(TAG, "Current progress is "+ values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            textView.setText(s);
        }
    }


    public static class HeavyLoader extends AsyncTaskLoader<String>{

        String url;
        //Will store the data if it was processed already
        String result;

        public HeavyLoader(Context context,String param) {
            super(context);
            this.url = param;
        }

        @Override
        protected void onStartLoading() {
            if (result!=null){
                deliverResult(result);
            }

            //Add other checks to see if data is needed again
            //then call {@link #forceLoad()}


        }

        @Override
        protected void onStopLoading() {
            cancelLoad();
        }

        @Override
        public void onCanceled(String data) {
            //Release data
            data = null;
        }

        @Override
        protected void onReset() {
            super.onReset();
            onStopLoading();
        }

        @Override
        public String loadInBackground() {
            Log.i(TAG,"Load in background started with"+url);
            for (int i = 0; i <10 ; i++) {
                try {
                    Thread.sleep(1000);
                    result +=  " " + i;
                } catch (InterruptedException e) {
                    Log.e(TAG, "LoadInBackground failed");
                }
            }
            return result;
        }

        @Override
        public void deliverResult(String data) {
            if (isStarted()){
                super.deliverResult(data);
            }

        }


    }
}
