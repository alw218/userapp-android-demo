package com.example.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.os.AsyncTask;

public class ArticleHelper {
	
	public static void loadArticles(String token, ArticleHelper.Callback callback) {
		new ArticlesAsyncTask().execute(token, callback);
	}
	
	public static interface Callback {
		public void call(List<Article> articles);
	}
	
	private static class ArticlesAsyncTask extends AsyncTask<Object, Integer, String>{
		private ArticleHelper.Callback callback;
		
		@Override
		protected String doInBackground(Object... params) {
			this.callback = (ArticleHelper.Callback) params[1];
			return getData((String) params[0]);
		}
		
		protected void onPostExecute(String result) {
			Type listType = new TypeToken<List<Article>>() {}.getType();
			callback.call((List<Article>) new Gson().fromJson(result, listType));
		}
	}
	
	private static String getData(String token) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpContext ctx = new BasicHttpContext();
		HttpGet httpget = new HttpGet("http://10.0.2.2:3000/articles");
		
		try {
			httpget.addHeader("Cookie", "ua_session_token=" + token + ";");
			HttpResponse response = httpclient.execute(httpget);
			return getStringFromInputStream(response.getEntity().getContent());
		} catch (ClientProtocolException e) {
			
		} catch (IOException e) {
		
		}
		
		return "";
	}

	/** Convert InputStream to String */
	private static String getStringFromInputStream(InputStream is) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();

		String line;
		try {
			br = new BufferedReader(new InputStreamReader(is));
			
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return sb.toString();
	}
	
}