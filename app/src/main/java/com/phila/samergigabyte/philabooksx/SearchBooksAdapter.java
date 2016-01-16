package com.phila.samergigabyte.philabooksx;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.phila.samergigabyte.R;

import java.util.ArrayList;

/**
 * Created by SamerGigaByte on 16/01/2016.
 */
public class SearchBooksAdapter implements ListAdapter {
    Context mContext;
    ArrayList<Book>mBooks;
    public SearchBooksAdapter(Context context, ArrayList<Book> books){
        mContext=context;
        mBooks=books;
    }


    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return mBooks.size();
    }

    @Override
    public Book getItem(int position) {
        return mBooks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        ViewHolder holder;
        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            rowView = inflater.inflate(R.layout.book_search_result,null,false);
            // configure view holder
            holder = new ViewHolder();
            holder.title = (TextView) rowView.findViewById(R.id.bookTitle);
            rowView.setTag(holder);
        }

        // fill data
        else
            holder = (ViewHolder) rowView.getTag();
        if(mBooks.size()>0) {

                final Book book = mBooks.get(position);

            holder.title.setText(book.title);
            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(book instanceof OfflineBook) {

                        Intent o = new Intent(mContext, PdfViewActivity.class);
                        o.putExtra("path", ((OfflineBook)book).path);
                        mContext.startActivity(o);
                    }
                    else{
                        Intent o=new Intent(mContext,PdfWebViewer.class);
                        if (((OnlineBook)book).bookUrl==null)
                            o.putExtra("url","www.esilicon.com/wp-content/uploads/lesson2.pdf");
                        else
                            o.putExtra("url",((OnlineBook)book).bookUrl);
                        mContext.startActivity(o);
                    }
                }
            });
        }
        return rowView;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
    class ViewHolder{
        TextView title;
    }
}
