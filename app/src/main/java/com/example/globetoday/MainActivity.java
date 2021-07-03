package com.example.globetoday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryRVAdapter.CategorClickInterface{



    private RecyclerView newsRV, categoryRV;
    private ProgressBar loadingPB;
    private ArrayList<Articles> articlesArrayList;
    private ArrayList<CategoryRVModaL> categoryRVModaLArrayList;
    private CategoryRVAdapter categoryRVAdapter;
    private NewsRVModalAdapter newsRVModalAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsRV = findViewById(R.id.news);
        categoryRV = findViewById(R.id.categories);
        loadingPB = findViewById(R.id.loading);
        articlesArrayList = new ArrayList<>();
        categoryRVModaLArrayList = new ArrayList<>();
        newsRVModalAdapter = new NewsRVModalAdapter(articlesArrayList, this);
        categoryRVAdapter = new CategoryRVAdapter(categoryRVModaLArrayList, this,this::onCategoryClick);
        newsRV.setLayoutManager(new LinearLayoutManager(this));
        newsRV.setAdapter(newsRVModalAdapter);
        categoryRV.setAdapter(categoryRVAdapter);
        getCategories();
        getNews("All");
        newsRVModalAdapter.notifyDataSetChanged();

    }

    private void getCategories(){
        categoryRVModaLArrayList.add(new CategoryRVModaL("All", "https://images.unsplash.com/photo-1576185850227-1f72b7f8d483?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1420&q=80"));
        categoryRVModaLArrayList.add(new CategoryRVModaL("Technology", "https://images.unsplash.com/photo-1618410320928-25228d811631?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80"));
        categoryRVModaLArrayList.add(new CategoryRVModaL("Science", "https://images.unsplash.com/photo-1581093588401-fbb62a02f120?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80"));
        categoryRVModaLArrayList.add(new CategoryRVModaL("Sports", "https://images.unsplash.com/photo-1594470117722-de4b9a02ebed?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1574&q=80"));
        categoryRVModaLArrayList.add(new CategoryRVModaL("General", "https://images.unsplash.com/photo-1489533119213-66a5cd877091?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1351&q=80"));
        categoryRVModaLArrayList.add(new CategoryRVModaL("Business", "https://images.unsplash.com/photo-1444653614773-995cb1ef9efa?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1355&q=80"));
        categoryRVModaLArrayList.add(new CategoryRVModaL("Entertainment", "https://images.unsplash.com/photo-1603739903239-8b6e64c3b185?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1351&q=80"));
        categoryRVModaLArrayList.add(new CategoryRVModaL("Health", "https://images.unsplash.com/photo-1494390248081-4e521a5940db?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=995&q=80"));
        categoryRVAdapter.notifyDataSetChanged();
    }

    private void getNews(String category){
        loadingPB.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String categoryURL = "https://newsapi.org/v2/top-headlines?country=in&category=" + category + "&apiKey=e1b5c4bdce264decba6d9da844c74b3b";
        String url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=e1b5c4bdce264decba6d9da844c74b3b";
        String BASE_URL = "https://newsapi.org/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitApi retrofitApi = retrofit.create(RetrofitApi.class);
        Call<NewsModal> call;
        if(category.equals("All")){
            call = retrofitApi.getALlNews(url);
        }else{
            call = retrofitApi.getNewsByCategory(categoryURL);
        }

        call.enqueue(new Callback<NewsModal>() {
            @Override
            public void onResponse(Call<NewsModal> call, Response<NewsModal> response) {
                NewsModal  newsModal = response.body();
                loadingPB.setVisibility(View.GONE);
                ArrayList<Articles> articles = newsModal.getArticles();
                for(int i=0; i<articles.size(); i++){
                    articlesArrayList.add(new Articles(articles.get(i).getTitle(),articles.get(i).getDescription(),articles.get(i).getUrlToImage(),
                    articles.get(i).getUrl(), articles.get(i).getContent()));
                }
                newsRVModalAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewsModal> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Fail to get news", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onCategoryClick(int position) {
        String category = categoryRVModaLArrayList.get(position).getCategory();
        getNews(category);

    }
}