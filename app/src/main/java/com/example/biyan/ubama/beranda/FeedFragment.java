package com.example.biyan.ubama.beranda;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.biyan.ubama.R;
import com.example.biyan.ubama.UrlUbama;
import com.example.biyan.ubama.UserToken;
import com.example.biyan.ubama.models.Feed;
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
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.empty)
    TextView empty;
    Unbinder unbinder;

    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    List<Feed> feedList;
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
        layoutManager = new GridLayoutManager(getActivity(), 1);
        recycler.setLayoutManager(layoutManager);
        recycler.setAdapter(adapter);
        getFeed();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        getFeed();
    }

    public void getFeed() {
        String url = UrlUbama.USER_FEED;
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                feedList = new Gson().fromJson(response.toString(), new TypeToken<List<Feed>>() {
                }.getType());
                adapter = new FeedTokoAdapter(feedList);
                recycler.setAdapter(adapter);
                if (!(feedList.size() > 0)) {
                    empty.setVisibility(View.VISIBLE);
                    recycler.setVisibility(View.GONE);
                }
                else{
                    recycler.setVisibility(View.VISIBLE);
                    empty.setVisibility(View.GONE);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error Volley", error.toString());
                return;
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
        request.setShouldCache(false);
        queue.add(request);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
