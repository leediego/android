package com.hfad.bitsandpizzas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;

public class StoreDetailActivity extends Activity {

    private ShareActionProvider shareActionProvider;
    public static final String EXTRA_STORENO = "storeNo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail);

        //Enable the Up button
        getActionBar().setDisplayHomeAsUpEnabled(true);

        //Display details of the store
        int storeNo = (Integer)getIntent().getExtras().get(EXTRA_STORENO);
        String storeName = Store.stores[storeNo].getName();
        TextView textView = (TextView)findViewById(R.id.store_text);
        textView.setText(storeName);
        int storeImage = Store.stores[storeNo].getImageResourceId();
        ImageView imageView = (ImageView)findViewById(R.id.store_image);
        imageView.setImageDrawable(getResources().getDrawable(storeImage));
        imageView.setContentDescription(storeName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        //Share the name of the store
        TextView textView = (TextView)findViewById(R.id.store_text);
        CharSequence storeName = textView.getText();
        MenuItem menuItem = menu .findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) menuItem.getActionProvider();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, storeName);
        shareActionProvider.setShareIntent(intent);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_order:
                Intent intent = new Intent(this, OrderActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
