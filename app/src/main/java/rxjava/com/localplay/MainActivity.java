package rxjava.com.localplay;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView)findViewById(R.id.videoList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getData();
    }
    private void getData() {
        pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("Loading...");
        pd.show();

        String url = VideoAPI.url;
        url = url+"videos";

        final Call<VideoList> postList = VideoAPI.getService().getPostList(url);
        postList.enqueue(new Callback<VideoList>() {
            @Override
            public void onResponse(Call<VideoList> call, Response<VideoList> response) {
                VideoList list = response.body();
                recyclerView.setAdapter(new VideoAdapter(MainActivity.this,list.getVideos()));
                pd.dismiss();
            }

            @Override
            public void onFailure(Call<VideoList> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(MainActivity.this, "Error Occured", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
