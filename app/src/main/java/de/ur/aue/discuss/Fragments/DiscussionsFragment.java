package de.ur.aue.discuss.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.ur.aue.discuss.Adapter.DiscussionsRecyclerViewAdapter;
import de.ur.aue.discuss.Global.DiscussApplication;
import de.ur.aue.discuss.Models.DiscussionItemElement;
import de.ur.aue.discuss.Models.DiscussionItemElement.DiscussionItem;
import de.ur.aue.discuss.R;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class DiscussionsFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private RecyclerView recyclerView;
    private Context mContext;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public DiscussionsFragment() {
    }

    @SuppressWarnings("unused")
    public static DiscussionsFragment newInstance(int columnCount) {
        DiscussionsFragment fragment = new DiscussionsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        String url = "http://discuss-001-site1.ftempurl.com/api/Discussions";
        GetDiscussionsFromServer(url);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_discussions_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new DiscussionsRecyclerViewAdapter(mListener));
        }
        return view;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }

        mContext = context;

        String url = "http://discuss-001-site1.ftempurl.com/api/Discussions";

        GetDiscussionsFromServer(url);

        Log.w("INTERNET", "HTTP requests sent");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(DiscussionItem item);
    }

    public void GetDiscussionsFromServer(String url){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        Log.w("INTERNET", response.toString());

                        ArrayList<DiscussionItem> discussionsFromServer = new ArrayList<>();
                        for(int i = 0; i < response.length(); i++)
                        {
                            try {
                                JSONObject json = response.getJSONObject(i);
                                int id = json.getInt("Id");
                                int catId = json.getInt("Category_Id");
                                String title = json.getString("Name");
                                ArrayList<Integer> categories = new ArrayList<>();

                                // Gesundheit
                                if(catId == 1)
                                    categories.add(R.drawable.gesundheit);
                                // Wirtschaft
                                if(catId == 2)
                                    categories.add(R.drawable.wirtschaft);
                                // Verbrechen
                                if(catId == 3)
                                    categories.add(R.drawable.verbrechen);
                                // Umwelt
                                if(catId == 4)
                                    categories.add(R.drawable.umwelt);
                                // Arbeit
                                if(catId == 5)
                                    categories.add(R.drawable.arbeit);
                                // Gesellschaft
                                if(catId == 6)
                                    categories.add(R.drawable.gesellschaft);
                                // Infrastruuktur
                                if(catId == 7)
                                    categories.add(R.drawable.infrastruktur);

                                DiscussionItem discussion = new DiscussionItem(id, title, R.drawable.deutschland, categories);
                                discussionsFromServer.add(discussion);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        DiscussionsRecyclerViewAdapter adapter = (DiscussionsRecyclerViewAdapter) recyclerView.getAdapter();
                        adapter.setDiscussionItems(discussionsFromServer);
                        adapter.getFilter().filter(null);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.w("INTERNET", "HTTP Response error");
                        Log.w("INTERNET", error);
                    }
                }) {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                //params.put("Id","1");
                //params.put("Name", "Umwelt");

                return params;
            }
        };

        DiscussApplication app = (DiscussApplication) getActivity().getApplication();
        app.getVolleyRequestQueue().add(jsonArrayRequest);
    }
}
