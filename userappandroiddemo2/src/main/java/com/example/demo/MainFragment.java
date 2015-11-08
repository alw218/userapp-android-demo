package com.example.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.userapp.client.android.UserApp;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainFragment extends Fragment {
	UserApp.Session session;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_main, container, false);
        
		// Listen for the login event so we could load the articles
        session = new UserApp.Session(this.getActivity(), new UserApp.Session.StatusCallback() {
		    @Override
		    public void call(Boolean authenticated, Exception exception) {
		        if (authenticated) {
		        	ArticleHelper.loadArticles(session.token, new ArticleHelper.Callback() {
					    @Override
					    public void call(List<Article> articles) {
					        // Print articles
							ListView listview = (ListView) view.findViewById(R.id.articleList);
							
							final ArrayList<String> list = new ArrayList<String>();
						    for (Article article : articles) {
						    	list.add(article.title);
						    }
							
							final StableArrayAdapter adapter = new StableArrayAdapter(view.getContext(), android.R.layout.simple_list_item_1, list);
							listview.setAdapter(adapter);
					    }
					});
		        }
		    }
		});
        
		return view;
    }

	@Override
	public void onResume() {
	    super.onResume();
	    session.onResume();
	}

	@Override
	public void onPause() {
	    super.onPause();
	    session.onPause();
	}
	
}
