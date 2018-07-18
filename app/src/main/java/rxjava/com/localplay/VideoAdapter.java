package rxjava.com.localplay;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.PostViewHolder>{

    private Context context;
    private List<Video> items;

    public VideoAdapter(Context context, List<Video> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.video_item,parent,false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PostViewHolder holder, int position) {
        final Video item = items.get(position);
        holder.postTitle.setText(item.getTitle());
        Glide.with(context).load(item.getThumbnailUrl()).into(holder.postImage);
        holder.videoImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, VideoActivity.class);
                intent.putExtra("VIDEO_URL", item.getVideoUrl());
                context.startActivity(intent);
            }
        });
        holder.whatsAppImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                whatsappIntent.setType("text/plain");
                whatsappIntent.setPackage("com.whatsapp");
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, item.getVideoUrl());
                try {
                    context.startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(context, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        holder.likeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               MediaPlayer m = new MediaPlayer();
                try{
                    //Play MP3
                    AssetFileDescriptor descriptor = context.getAssets().openFd("likesound.mp3");
                    m.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength() );
                    descriptor.close();
                    m.prepare();
                    m.setLooping(false);
                    m.start();
                    //Play GIF
                    Glide.with(context)
                            .load(R.drawable.likeanimation)
                           .into(holder.likeImage);
                    } catch(Exception e){
                    // handle error here..
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        ImageView postImage;
        TextView postTitle;
        ImageView whatsAppImage;
        ImageView likeImage;
        ImageView videoImg;
        public PostViewHolder(View itemView) {
            super(itemView);
            postImage = (ImageView) itemView.findViewById(R.id.postImage);
            whatsAppImage = (ImageView) itemView.findViewById(R.id.whatsAppImg);
            likeImage = (ImageView) itemView.findViewById(R.id.likeImg);
            videoImg = (ImageView) itemView.findViewById(R.id.videoImg);
            postTitle = (TextView) itemView.findViewById(R.id.postTitle);
        }
    }
}
