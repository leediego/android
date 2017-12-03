package com.hfad.bitsandpizzas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ShareActionProvider;
import android.widget.TextView;

public class PastaDetailActivity extends Activity {

    private ShareActionProvider shareActionProvider;
    public static final String EXTRA_PASTANO = "pastaNo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pasta_detail);

        //Enable the Up button
        getActionBar().setDisplayHomeAsUpEnabled(true);

        //Display details of the pizza
        int pastaNo = (Integer)getIntent().getExtras().get(EXTRA_PASTANO);
        String pastaName = Pasta.pastas[pastaNo].getName();
        TextView textView = (TextView)findViewById(R.id.pasta_text);
        textView.setText(pastaName);
        int pastaImage = Pasta.pastas[pastaNo].getImageResourceId();
        ImageView imageView = (ImageView)findViewById(R.id.pasta_image);
        imageView.setImageDrawable(getResources().getDrawable(pastaImage));
        imageView.setContentDescription(pastaName);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        //Share the name of the pizza
        TextView textView = (TextView)findViewById(R.id.pasta_text);
        CharSequence pastaName = textView.getText();
        MenuItem menuItem = menu .findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) menuItem.getActionProvider();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, pastaName);
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
