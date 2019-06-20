package com.example.whowroteit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

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
            new FetchBook(Author, Title).execute(book);
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
}
