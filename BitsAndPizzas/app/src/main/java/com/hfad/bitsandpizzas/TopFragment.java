package com.hfad.bitsandpizzas;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;



public class TopFragment extends Fragment {


    public TopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RelativeLayout layout = (RelativeLayout)
                                inflater.inflate(R.layout.fragment_top, container, false);

        // Pizza preview
        RecyclerView pizzaRecycler = (RecyclerView)layout.findViewById(R.id.pizza_recycler);
        String[] pizzaNames = new String[2];
        for (int i = 0; i < 2; i++) {
            pizzaNames[i] = Pizza.pizzas[i].getName();
        }
        int[] pizzaImages = new int[2];
        for (int i = 0; i < 2; i++) {
            pizzaImages[i] = Pizza.pizzas[i].getImageResourceId();
        }
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        pizzaRecycler.setLayoutManager(layoutManager);
        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(pizzaNames, pizzaImages);
        pizzaRecycler.setAdapter(adapter);
        adapter.setListener(new CaptionedImagesAdapter.Listener() {
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), PizzaDetailActivity.class);
                intent.putExtra(PizzaDetailActivity.EXTRA_PIZZANO, position);
                getActivity().startActivity(intent);
            }
        });

        // Pasta preview
        RecyclerView pastaRecycler = (RecyclerView)layout.findViewById(R.id.pasta_recycler);
        String[] pastaNames = new String[2];
        for (int i = 0; i < 2; i++) {
            pastaNames[i] = Pasta.pastas[i].getName();
        }
        int[] pastaImages = new int[2];
        for (int i = 0; i < 2; i++) {
            pastaImages[i] = Pasta.pastas[i].getImageResourceId();
        }
        GridLayoutManager layoutManagerPasta = new GridLayoutManager(getActivity(), 2);
        pastaRecycler.setLayoutManager(layoutManagerPasta);
        CaptionedImagesAdapter adapterPasta = new CaptionedImagesAdapter(pastaNames, pastaImages);
        pastaRecycler.setAdapter(adapterPasta);
        adapterPasta.setListener(new CaptionedImagesAdapter.Listener() {
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), PastaDetailActivity.class);
                intent.putExtra(PastaDetailActivity.EXTRA_PASTANO, position);
                getActivity().startActivity(intent);
            }
        });

        return layout;
    }
}
