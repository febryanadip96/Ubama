package com.example.biyan.ubama;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends Fragment {
    @BindView(R.id.recycler_favoritToko)
    RecyclerView recyclerFavoritToko;
    Unbinder unbinder;
    private RecyclerView mRecyclerViewFavoritToko;
    private RecyclerView.Adapter mAdapterFavoritToko;
    private RecyclerView.LayoutManager mLayoutManagerFavoritToko;
    private List<Toko> tokoList;
    RequestQueue queue;

    public static FeedFragment newInstance() {
        // Required empty public constructor
        FeedFragment feedFragment = new FeedFragment();
        return feedFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_feed, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        queue = Volley.newRequestQueue(getActivity());

        mRecyclerViewFavoritToko = (RecyclerView) rootView.findViewById(R.id.recycler_favoritToko);
        mLayoutManagerFavoritToko = new GridLayoutManager(getActivity(), 1);
        mRecyclerViewFavoritToko.setLayoutManager(mLayoutManagerFavoritToko);

        getFeed();
        return rootView;
    }

    private void getFeed() {
        queue = Volley.newRequestQueue(getActivity());
        String url = UrlUbama.UserFeed;
        JsonArrayRequest feedRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                tokoList = new Gson().fromJson(response.toString(), new TypeToken<List<Toko>>() {
                }.getType());
                mAdapterFavoritToko = new FavoritTokoAdapter(tokoList);
                mRecyclerViewFavoritToko.setAdapter(mAdapterFavoritToko);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", UserToken.getToken(getContext()));
                params.put("Accept", "application/json");
                return params;
            }
        };
        queue.add(feedRequest);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
