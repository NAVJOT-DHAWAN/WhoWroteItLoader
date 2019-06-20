package com.example.whowroteit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    private EditText BookInput;
    private TextView Author;
    private TextView Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BookInput = findViewById(R.id.bookInput);
        Author = findViewById(R.id.authorText);
        Title = findViewById(R.id.titleText);

        if(getSupportLoaderManager().getLoader(0)!=null){
            getSupportLoaderManager().initLoader(0,null,this);
        }
    }

    public void searchBooks(View view) {

        String book = BookInput.getText().toString();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if(connMgr != null){
            networkInfo = connMgr.getActiveNetworkInfo();
        }


        if(networkInfo != null && networkInfo.isConnected() && book.length() != 0) {

            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString",book);
            getSupportLoaderManager().restartLoader(0,queryBundle,this);
            Author.setText("");
            Title.setText(R.string.loading);
        }
        else{
            if(book.length() == 0){
                Author.setText("");
                Title.setText(R.string.no_search_term);
            }
            else{
                Author.setText("");
                Title.setText(R.string.no_network);
            }
        }

    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        String queryString="";
        if(bundle != null){
            queryString = bundle.getString("queryString");
        }
        return new BookLoader(this,queryString);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {

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
                Title.setText(title);
                Author.setText(authors);
            }
            else
            {
                Title.setText(R.string.no_results);
                Author.setText("");
            }

        }catch (JSONException e){
            Title.setText(R.string.no_results);
            Author.setText("");
            e.printStackTrace();
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
