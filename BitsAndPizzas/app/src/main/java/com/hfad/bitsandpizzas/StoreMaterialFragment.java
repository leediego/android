package com.hfad.bitsandpizzas;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class StoreMaterialFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView storeRecycler = (RecyclerView)inflater.inflate(
                R.layout.fragment_store_material, container, false);
        String[] storeNames = new String[Store.stores.length];
        for (int i = 0; i < storeNames.length; i++) {
            storeNames[i] = Store.stores[i].getName();
        }

        int[] storeImages = new int[Store.stores.length];
        for (int i = 0; i < storeImages.length; i++) {
            storeImages[i] = Store.stores[i].getImageResourceId();
        }

        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(storeNames, storeImages);
        storeRecycler.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        storeRecycler.setLayoutManager(layoutManager);

        adapter.setListener(new CaptionedImagesAdapter.Listener() {
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), StoreDetailActivity.class);
                intent.putExtra(StoreDetailActivity.EXTRA_STORENO, position);
                getActivity().startActivity(intent);
            }
        });

        return storeRecycler;
    }

}
