package com.example.whowroteit;

import android.os.AsyncTask;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class FetchBook extends AsyncTask<String,Void,String> {

    private WeakReference<TextView> Author;
    private WeakReference<TextView> Title;

    public FetchBook(TextView author, TextView title) {
        Author = new WeakReference<>(author);
        Title = new WeakReference<>(title);
    }

    @Override
    protected String doInBackground(String... strings) {
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
