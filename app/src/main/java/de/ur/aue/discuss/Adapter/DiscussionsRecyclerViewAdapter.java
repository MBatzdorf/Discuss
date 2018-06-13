package de.ur.aue.discuss.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import de.ur.aue.discuss.Fragments.DiscussionsFragment.OnListFragmentInteractionListener;
import de.ur.aue.discuss.Models.DiscussionItemElement.DiscussionItem;
import de.ur.aue.discuss.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DiscussionItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class DiscussionsRecyclerViewAdapter extends RecyclerView.Adapter<DiscussionsRecyclerViewAdapter.ViewHolder> implements Filterable {

    private List<DiscussionItem> mDiscussionValues;
    private List<DiscussionItem> mFilteredDiscussionValues;
    private final OnListFragmentInteractionListener mListener;
    private Context mContext;
    private DiscussionFilter mFilter = new DiscussionFilter();

    private HashMap<String, Boolean> RegionsMap;
    private HashMap<String, Boolean> CategoriesMap;

    private LinearLayout mLayoutNoDiscussions;

    public DiscussionsRecyclerViewAdapter(List<DiscussionItem> items, OnListFragmentInteractionListener listener) {
        mDiscussionValues = items;
        mFilteredDiscussionValues = new ArrayList<>();
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

        holder.mItem = mFilteredDiscussionValues.get(position);
        holder.mDiscussionTitle.setText(mFilteredDiscussionValues.get(position).title);
        holder.mRegionImage.setImageResource(mFilteredDiscussionValues.get(position).region);

        final float scale = mContext.getResources().getDisplayMetrics().density;
        int dps = (int) (25 * scale + 0.5f);

        LinearLayout.LayoutParams params = new LinearLayout
                .LayoutParams(dps, dps);
        params.gravity = Gravity.CENTER_VERTICAL;

        holder.mCategoriesLayout.removeAllViews();
        for (int catId: mFilteredDiscussionValues.get(position).categories) {
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
        if(mFilteredDiscussionValues.size() <= 0) {
            mLayoutNoDiscussions.setVisibility(View.VISIBLE);
        }else{
            mLayoutNoDiscussions.setVisibility(View.GONE);
        }
        return mFilteredDiscussionValues.size();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    public void setEmptyDiscussionsLayout(LinearLayout inLayout)
    {
        mLayoutNoDiscussions = inLayout;
    }

    public void setFilterMaps(HashMap<String, Boolean> inRegionsMap, HashMap<String, Boolean> inCategoriesMap)
    {
        RegionsMap = inRegionsMap;
        CategoriesMap = inCategoriesMap;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mDiscussionTitle;
        public final ImageView mRegionImage;
        public final LinearLayout mCategoriesLayout;
        public DiscussionItem mItem;

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

    private class DiscussionFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();
            mFilteredDiscussionValues.clear();
            final ArrayList<DiscussionItem> nlist = new ArrayList<>();

            for(DiscussionItem Item : mDiscussionValues) {
                ArrayList<DiscussionItem> regionSortedList = new ArrayList<>();
                switch (Item.region) {
                    case R.drawable.landkreis:
                        if (RegionsMap.get("Landkreis"))
                            regionSortedList.add(Item);
                        break;
                    case R.drawable.deutschland:
                        if (RegionsMap.get("Deutschland"))
                            regionSortedList.add(Item);
                        break;
                    case R.drawable.eu:
                        if (RegionsMap.get("Europa"))
                            regionSortedList.add(Item);
                        break;
                    case R.drawable.welt:
                        if (RegionsMap.get("Welt"))
                            regionSortedList.add(Item);
                        break;
                }
                for (DiscussionItem SortedItem : regionSortedList) {
                    for(int categoryInt : SortedItem.categories)
                    {
                        if(categoryInt == R.drawable.gesundheit && CategoriesMap.get("Gesundheit")){
                            nlist.add(SortedItem);
                            break;
                        }
                        if(categoryInt == R.drawable.verbrechen && CategoriesMap.get("Verbrechen")){
                            nlist.add(SortedItem);
                            break;
                        }
                        if(categoryInt == R.drawable.wirtschaft && CategoriesMap.get("Wirtschaft")){
                            nlist.add(SortedItem);
                            break;
                        }
                        if(categoryInt == R.drawable.umwelt && CategoriesMap.get("Umwelt")){
                            nlist.add(SortedItem);
                            break;
                        }
                        if(categoryInt == R.drawable.infrastruktur && CategoriesMap.get("Infrastruktur")){
                            nlist.add(SortedItem);
                            break;
                        }
                        if(categoryInt == R.drawable.arbeit && CategoriesMap.get("Arbeit")){
                            nlist.add(SortedItem);
                            break;
                        }
                        if(categoryInt == R.drawable.gesellschaft && CategoriesMap.get("Gesellschaft")){
                            nlist.add(SortedItem);
                            break;
                        }
                    }
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mFilteredDiscussionValues = (ArrayList<DiscussionItem>) results.values;
            notifyDataSetChanged();
        }

    }
}
