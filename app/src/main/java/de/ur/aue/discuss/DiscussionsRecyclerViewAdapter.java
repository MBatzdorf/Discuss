package de.ur.aue.discuss;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.ur.aue.discuss.DiscussionsFragment.OnListFragmentInteractionListener;
import de.ur.aue.discuss.dummy.DummyContent.DummyItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class DiscussionsRecyclerViewAdapter extends RecyclerView.Adapter<DiscussionsRecyclerViewAdapter.ViewHolder> {

    private final List<DummyItem> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context mContext;

    public DiscussionsRecyclerViewAdapter(List<DummyItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_discussions, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mDiscussionTitle.setText(mValues.get(position).title);
        holder.mRegionImage.setImageResource(mValues.get(position).region);

        final float scale = mContext.getResources().getDisplayMetrics().density;
        int dps = (int) (25 * scale + 0.5f);

        LinearLayout.LayoutParams params = new LinearLayout
                .LayoutParams(dps, dps);
        params.gravity = Gravity.CENTER_VERTICAL;

        for (int catId: mValues.get(position).categories) {
            ImageView catImage =new ImageView(mContext);
            catImage.setImageResource(catId);
            catImage.setLayoutParams(params);
            holder.mCategoriesLayout.addView(catImage);
        }
        holder.mCategoriesLayout.requestLayout();

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mDiscussionTitle;
        public final ImageView mRegionImage;
        public final LinearLayout mCategoriesLayout;
        public DummyItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mDiscussionTitle = (TextView) view.findViewById(R.id.discussionTitle);
            mRegionImage = (ImageView) view.findViewById(R.id.imageRegion);
            mCategoriesLayout = (LinearLayout) view.findViewById(R.id.layoutCategories);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mDiscussionTitle.getText() + "'";
        }
    }
}
