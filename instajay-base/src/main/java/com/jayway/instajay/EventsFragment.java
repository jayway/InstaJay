package com.jayway.instajay;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class EventsFragment extends Fragment {

    private static final String TAG = EventsFragment.class.getSimpleName();

    private RecyclerView recyclerView;

    public EventsFragment() {
    }

    public static EventsFragment newInstance() {
        EventsFragment fragment = new EventsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_events, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new EventsFragment.EventsAdapter(EventStore.getInstance(getContext()).getEvents()));
        return rootView;
    }

    private class EventsAdapter extends RecyclerView.Adapter<EventsFragment.EventsAdapter.ViewHolder> {

        private List<Event> events;

        public class ViewHolder extends RecyclerView.ViewHolder {

            private TextView dateAndTime;
            private TextView title;
            private TextView text;
            private TextView location;
            private ImageView image;

            public ViewHolder(View itemView) {
                super(itemView);
                title = itemView.findViewById(R.id.title);
                dateAndTime = itemView.findViewById(R.id.date_time);
                location = itemView.findViewById(R.id.location);
                text = itemView.findViewById(R.id.text);
                image = itemView.findViewById(R.id.image);
            }
        }

        public EventsAdapter(List<Event> events) {
            this.events = events;
        }

        @Override
        public EventsFragment.EventsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.event, parent, false);
            return new EventsFragment.EventsAdapter.ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(EventsFragment.EventsAdapter.ViewHolder holder, int position) {
            final Event event = events.get(position);

            holder.title.setText(event.getTitle());
            holder.dateAndTime.setText(event.getDate() + " " + event.getTime());
            holder.location.setText(event.getLocation());
            holder.text.setText(Html.fromHtml(event.getText()).toString());
            if (!TextUtils.isEmpty(event.getImageUrl())) {
                Picasso.with(getContext()).load(event.getImageUrl()).error(R.drawable.unknown_author).into(holder.image);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(TAG, "Launching " + event.getUrl());
                    NavigationUtil.startAppLink(getContext(), event.getUrl());
                }
            });
        }

        @Override
        public int getItemCount() {
            return events.size();
        }
    }
}