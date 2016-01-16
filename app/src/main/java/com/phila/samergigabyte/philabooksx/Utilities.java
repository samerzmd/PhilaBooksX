package com.phila.samergigabyte.philabooksx;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.etsy.android.grid.StaggeredGridView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by SamerGigaByte on 12/12/2015.
 */
public class Utilities {
    static ArrayList<File>pdfFiles=new ArrayList<File>();
    public static File[] walkdir(File dir) {
        String pdfPattern = ".pdf";

        File listFile[] = dir.listFiles();

        if (listFile != null) {

            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory()) {
                    walkdir(listFile[i]);
                } else {
                    if (listFile[i].getName().endsWith(pdfPattern)){
                        pdfFiles.add(listFile[i]);
                    }
                }
            }
        }
        File[] va=new File[pdfFiles.size()];
        for (int i=0;i<pdfFiles.size();i++) {
            va[i]=pdfFiles.get(i);
        }
        return va;
    }

    public static File[] getPdfFiles(){
        pdfFiles=new ArrayList<>();
        return walkdir(Environment.getExternalStorageDirectory());
    }

    public static void setListViewHeightBasedOnChildren(StaggeredGridView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        View listItem = listAdapter.getView(0, null, listView);
        totalHeight=(listAdapter.getCount()%2==0)?totalHeight:totalHeight+listItem.getMeasuredHeight();
        params.height = totalHeight/2+500;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
    public static void openFile(String minmeType, int requestCode, AppCompatActivity activity) {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(minmeType);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // special intent for Samsung file manager
        Intent sIntent = new Intent("com.sec.android.app.myfiles.PICK_DATA");
        // if you want any file type, you can skip next line
        sIntent.putExtra("CONTENT_TYPE", minmeType);
        sIntent.addCategory(Intent.CATEGORY_DEFAULT);

        Intent chooserIntent;
        if (activity.getPackageManager().resolveActivity(sIntent, 0) != null) {
            // it is device with samsung file manager
            chooserIntent = Intent.createChooser(sIntent, "Open file");
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{intent});
        } else {
            chooserIntent = Intent.createChooser(intent, "Open file");
        }

        try {
            activity.startActivityForResult(chooserIntent, requestCode);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(activity.getApplicationContext(), "No suitable File Manager was found.", Toast.LENGTH_SHORT).show();
        }
    }
}
