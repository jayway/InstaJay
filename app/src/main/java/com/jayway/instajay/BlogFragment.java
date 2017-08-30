package com.jayway.instajay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class BlogFragment extends Fragment {

    RecyclerView recyclerView;

    public BlogFragment() {
    }

    public static BlogFragment newInstance() {
        BlogFragment fragment = new BlogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_blog, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new BlogPostsAdapter(BlogPostStore.getInstance(getContext()).getBlogPosts()));
        return rootView;
    }

    private class BlogPostsAdapter extends RecyclerView.Adapter<BlogPostsAdapter.ViewHolder> {

        private List<BlogPost> blogPosts;

        public class ViewHolder extends RecyclerView.ViewHolder {

            private TextView published;
            private TextView title;
            private TextView description;
            private ImageView authorPicture;

            public ViewHolder(View itemView) {
                super(itemView);
                published = itemView.findViewById(R.id.published);
                title = itemView.findViewById(R.id.title);
                description = itemView.findViewById(R.id.description);
                authorPicture = itemView.findViewById(R.id.authorPicture);
            }
        }

        public BlogPostsAdapter(List<BlogPost> blogPosts) {
            this.blogPosts = blogPosts;
        }

        @Override
        public BlogPostsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_post, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final BlogPost blogPost = blogPosts.get(position);

            holder.published.setText(String.format(getString(R.string.published_by), blogPost.getPublished(), blogPost.getAuthor()));
            holder.title.setText(blogPost.getTitle());
            holder.description.setText(Html.fromHtml(blogPost.getText()).toString());
            if (!TextUtils.isEmpty(blogPost.getAuthorPictureUrl())) {
                Picasso.with(getContext()).load(blogPost.getAuthorPictureUrl()).error(R.drawable.unknown_author).into(holder.authorPicture);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), BlogPostDetailActivity.class);
                    intent.putExtra("url", blogPost.getUrl());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return blogPosts.size();
        }
    }
}