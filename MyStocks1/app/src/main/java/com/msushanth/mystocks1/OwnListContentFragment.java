/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.msushanth.mystocks1;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Provides UI for the view with List.
 */
public class OwnListContentFragment extends Fragment {


    static String[] stocksToAddArr;
    static String[] stocksToAddCurrValueArr;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        MainActivity mainActivity = (MainActivity) getActivity();
        stocksToAddArr = mainActivity.getStocksToAddArr();
        stocksToAddCurrValueArr = mainActivity.getStocksToAddCurrValueArr();

        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycler_view, container, false);
        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView avator;
        public TextView name;
        public TextView description;
        final String API_KEY = "EEQXBJ2E2FCFANHP";
        String URL_NAME="https://www.alphavantage.co/query?function=TIME_SERIES_WEEKLY&symbol=SYMBOL_NAME&outputsize=full&apikey=" + API_KEY;
        String[] stockData;


        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_own_list_content, parent, false));
            avator = (ImageView) itemView.findViewById(R.id.list_avatar);
            name = (TextView) itemView.findViewById(R.id.list_title);
            description = (TextView) itemView.findViewById(R.id.list_desc);

            // TODO: Copy paste this into the other Fragments
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    downloadGraphData dGD = new downloadGraphData();
                    dGD.setView(v);
                    dGD.execute();
                }
            });
        }

        class downloadGraphData extends AsyncTask<Object, Object, Void> {
            View view = null;
            ProgressDialog progressDialog = null;

            protected void setView(View v) {
                view = v;
            }

            @Override
            protected void onPreExecute() {
                progressDialog = new ProgressDialog(view.getContext(), AlertDialog.THEME_DEVICE_DEFAULT_DARK);
                progressDialog.setTitle("Loading Information");
                progressDialog.setMessage("Please wait");
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            protected Void doInBackground(Object... v) {
                try {
                    Document document = Jsoup
                            .connect(URL_NAME.replace("SYMBOL_NAME", stocksToAddArr[getAdapterPosition()]))
                            .ignoreContentType(true)
                            .get();
                    String words = document.text();

                    String[] splitterString = words.split("\"");
                    ArrayList<String> values = new ArrayList<>();

                    for (int i = 1; i < splitterString.length; i += 2) {
                        values.add(splitterString[i]);
                    }

                    stockData = values.toArray(new String[values.size()]);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void voids) {
                Context context = view.getContext();
                Intent intent = new Intent(context, DisplayGraph.class);
                intent.putExtra("Stock Name", stocksToAddArr[getAdapterPosition()]);
                intent.putExtra("Current Value", stocksToAddCurrValueArr[getAdapterPosition()]);
                intent.putExtra("Stock Data", stockData);

                progressDialog.dismiss();

                System.out.println("$#%#$% Position: " + getAdapterPosition());
                context.startActivity(intent);
            }
        }
    }




    /**
     * Adapter to display recycler view.
     */
    public class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {
        // Set numbers of List in RecyclerView.
        private final int LENGTH = stocksToAddArr.length;
        private final String[] mPlaces;
        private final String[] mPlaceDesc;
        private final Drawable[] mPlaceAvators;

        public ContentAdapter(Context context) {
            Resources resources = context.getResources();
            mPlaces = resources.getStringArray(R.array.places);
            mPlaceDesc = resources.getStringArray(R.array.place_desc);
            TypedArray a = resources.obtainTypedArray(R.array.gain_loss);
            mPlaceAvators = new Drawable[a.length()];
            for (int i = 0; i < mPlaceAvators.length; i++) {
                mPlaceAvators[i] = a.getDrawable(i);
            }
            a.recycle();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.avator.setImageDrawable(mPlaceAvators[position % mPlaceAvators.length]);
            holder.name.setText(stocksToAddArr[position]);
            holder.description.setText(stocksToAddCurrValueArr[position]);
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }
}
