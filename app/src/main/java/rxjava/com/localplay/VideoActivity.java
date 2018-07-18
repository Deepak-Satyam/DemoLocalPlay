package rxjava.com.localplay;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoActivity extends AppCompatActivity {
    VideoView video;
    ProgressDialog pd;
    MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_activity);

        video = (VideoView)findViewById(R.id.video);
        mediaController = new
                MediaController(this);

        pd = new ProgressDialog(VideoActivity.this);
        pd.setMessage("Buffering...");
        pd.show();
        String video_url = getIntent().getStringExtra("VIDEO_URL");
        Uri uri = Uri.parse(video_url);
        video.setVideoURI(uri);
        video.setMediaController(mediaController);
        video.start();

        video.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                //close the progress dialog when buffering is done
                pd.dismiss();
                //mediaController.show();
            }
        });
    }
}
