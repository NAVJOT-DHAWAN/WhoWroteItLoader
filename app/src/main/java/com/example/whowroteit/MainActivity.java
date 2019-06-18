package com.example.whowroteit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
    }
}
