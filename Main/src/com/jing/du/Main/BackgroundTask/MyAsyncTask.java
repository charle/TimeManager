package com.jing.du.Main.BackgroundTask;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import com.jing.du.Main.Adapter.CategoyAdapter;
import com.jing.du.Main.Model.Category;
import com.jing.du.Main.R;
import com.jing.du.Main.ViewHolder.CategoyViewHolder;
import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by charle-chen on 15/6/26.
 */
public class MyAsyncTask extends AsyncTask<Void, Void, Void> {

    private ListView listView;
    private List<Category> categoryList;
    private CategoyViewHolder viewHolder;
    private CategoyAdapter categoyAdapter;
    private Context mContext;

    public MyAsyncTask(ListView listView,Context context) {
        this.listView = listView;
        this.mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        listView.setAdapter(categoyAdapter);
    }

    @Override
    protected Void doInBackground(Void... params) {
        categoryList = DataSupport.findAll(Category.class,true);
        viewHolder = new CategoyViewHolder();
        categoyAdapter = new CategoyAdapter(mContext, categoryList, viewHolder, R.layout.category_item);
        return null;
    }
}
