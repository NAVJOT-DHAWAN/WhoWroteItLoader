package com.example.whowroteit;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

        return NetworkUtils.getBookInfo(strings[0]);

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        int i = 0;
        String title = null;
        String authors = null;
        try{
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            while (i<itemsArray.length()&&(authors == null && title == null)){
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                try{
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                }catch(Exception e){
                    e.printStackTrace();
                }
                i++;
            }
            if(title != null && authors != null){
                Title.get().setText(title);
                Author.get().setText(authors);
            }
            else
            {
                Title.get().setText(R.string.no_results);
                Author.get().setText("");
            }

        }catch (JSONException e){
            Title.get().setText(R.string.no_results);
            Author.get().setText("");
            e.printStackTrace();
        }
    }
}
