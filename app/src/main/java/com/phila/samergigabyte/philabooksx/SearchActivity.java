package com.phila.samergigabyte.philabooksx;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import com.etsy.android.grid.StaggeredGridView;
import com.phila.samergigabyte.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SearchActivity extends AppCompatActivity {
    ListView searchList;
    ArrayList<Book> books=new ArrayList<Book>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchList= (ListView) findViewById(R.id.booksList);
        booksSetup();

        EditText text= (EditText) findViewById(R.id.searchText);
        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                s.toString();
                ArrayList<Book>hasBooks=new ArrayList<Book>();
                for(Book b :books){
                    if(b.title.toLowerCase().toString().contains(s.toString().toLowerCase())){
                        hasBooks.add(b);
                    }
                }
                SearchBooksAdapter adapter=new SearchBooksAdapter(SearchActivity.this, hasBooks);
                searchList.setAdapter(adapter);
            }
        });
    }
    private void booksSetup() {

        Net.apiClient(SearchActivity.this).PDFs("",new Callback<PDFs>() {
            @Override
            public void success(PDFs pdFs, Response response) {

                for (PDFs.pdf x :pdFs.PDFs){
                    OnlineBook book=new OnlineBook();
                    book.bookUrl="79.134.150.46/PDF/uploads/pdfs/"+x.file+".pdf";
                    book.title=x.name;
                    books.add(book);
                }

                File[] va= Utilities.getPdfFiles();
                ArrayList<OfflineBook>offLibeBooks=new ArrayList<OfflineBook>();
                for (int i=0;i<va.length;i++){
                    OfflineBook book=new OfflineBook();
                    book.path=va[i].getPath();
                    book.title=va[i].getName();
                    books.add(book);
                }

                SearchBooksAdapter adapter=new SearchBooksAdapter(SearchActivity.this,books);
                searchList.setAdapter(adapter);



//                StaggeredGridView gridView1 = (StaggeredGridView) findViewById(R.id.grid_view1);
//
//                OfflineBooksGridAdapter offlineBooksGridAdapter=new OfflineBooksGridAdapter(offLibeBooks,SearchActivity.this);
//                gridView1.setAdapter(offlineBooksGridAdapter);
//
//                Utilities.setListViewHeightBasedOnChildren(gridView);
//                Utilities.setListViewHeightBasedOnChildren(gridView1);

            }

            @Override
            public void failure(RetrofitError error) {

            }
        });






    }
}
