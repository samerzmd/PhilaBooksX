package com.phila.samergigabyte.philabooksx;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.etsy.android.grid.StaggeredGridView;
import com.phila.samergigabyte.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String attachmentPath;
    private File selectedFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.openFile("*/*",0,MainActivity.this);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        booksSetup();


    }

    private void booksSetup() {

        Net.apiClient(MainActivity.this).PDFs("",new Callback<PDFs>() {
            @Override
            public void success(PDFs pdFs, Response response) {
                ArrayList books=new ArrayList();
                for (PDFs.pdf x :pdFs.PDFs){
                    OnlineBook book=new OnlineBook();
                    book.bookUrl="79.134.150.46/PDF/uploads/pdfs/"+x.file+".pdf";
                    book.title=x.name;
                    books.add(book);
                }
                OnlineBooksGridAdapter adapter=new OnlineBooksGridAdapter(books,MainActivity.this);

                StaggeredGridView gridView = (StaggeredGridView) findViewById(R.id.grid_view);
                gridView.setAdapter(adapter);

                File[] va= Utilities.getPdfFiles();
                ArrayList<OfflineBook>offLibeBooks=new ArrayList<OfflineBook>();
                for (int i=0;i<va.length;i++){
                    OfflineBook book=new OfflineBook();
                    book.path=va[i].getPath();
                    book.title=va[i].getName();
                    offLibeBooks.add(book);
                }
                StaggeredGridView gridView1 = (StaggeredGridView) findViewById(R.id.grid_view1);

                OfflineBooksGridAdapter offlineBooksGridAdapter=new OfflineBooksGridAdapter(offLibeBooks,MainActivity.this);
                gridView1.setAdapter(offlineBooksGridAdapter);

                Utilities.setListViewHeightBasedOnChildren(gridView);
                Utilities.setListViewHeightBasedOnChildren(gridView1);
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });






    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (0 == requestCode) {
            if (RESULT_OK == resultCode) {
                attachmentPath = FileUtils.getPath(MainActivity.this, data.getData());
                if (false == TextUtils.isEmpty(attachmentPath)) {
                    selectedFile = new File(attachmentPath);
                    final TypedFile typedFile = new TypedFile("multipart/form-data", new File(attachmentPath));

                    Net.apiClient(this).uploadPDF(typedFile, new Callback<PDF>() {
                        @Override
                        public void success(PDF s, Response response) {
                            if(s.PDF!=null){
                                Net.apiClient(MainActivity.this).savePdf(((MyAppClass) getApplicationContext()).mCurrentUser.Users.id.toString(), typedFile.fileName(), s.PDF, new Callback<Object>() {
                                    @Override
                                    public void success(Object o, Response response) {
                                        Log.d("shiiiiiiiii",o.toString());
                                        booksSetup();
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Log.d("lol", error.toString());
                        }
                    });

                } else {
                    attachmentPath = null;
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);

        }
    }
}
