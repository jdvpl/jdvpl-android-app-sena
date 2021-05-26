package app.juanda.jdvpl;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.miViewHolder> {
    Context mContext;
    List<Post> mData;

    public PostAdapter(Context mContext, List<Post> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }



    @NonNull
    @Override
    public miViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row= LayoutInflater.from(mContext).inflate(R.layout.row_post_item,parent, false);
        return new miViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull miViewHolder holder, int position) {
        holder.tvTitle.setText(mData.get(position).getTitle());
        Glide.with(mContext).load(mData.get(position).getPicture()).into(holder.imgPost);
        Glide.with(mContext).load(mData.get(position).getUserPhoto()).into(holder.imgPostProfile);

    }
    public void setfilter(List<Post> listitem){
        mData=new ArrayList<>();
        mData.addAll(listitem);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class miViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        ImageView imgPost;
        ImageView imgPostProfile;
        TextView telefono;

        TextView cr;
        TextView numero;
        TextView direcc;
        TextView ciudad;
        TextView departamento;
        TextView pais;

        public miViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.row_post_title);
            imgPost=itemView.findViewById(R.id.row_post_img);
            imgPostProfile=itemView.findViewById(R.id.row_post_profile_img);
            telefono=itemView.findViewById(R.id.popup_telefono);
            //mapas
            cr=itemView.findViewById(R.id.popup_cr);
            numero=itemView.findViewById(R.id.popup_numero);
            direcc=itemView.findViewById(R.id.popup_direcc);
            ciudad=itemView.findViewById(R.id.popup_ciudad);
            departamento=itemView.findViewById(R.id.popup_depart);
            pais=itemView.findViewById(R.id.popup_pais);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent postDetailActivity = new Intent(mContext,PostDetailActivity.class);
                    int position = getAdapterPosition();

                    postDetailActivity.putExtra("title",mData.get(position).getTitle());
                    postDetailActivity.putExtra("postImage",mData.get(position).getPicture());
                    postDetailActivity.putExtra("description",mData.get(position).getDescription());
                    postDetailActivity.putExtra("telefono",mData.get(position).getTelefono());
                    postDetailActivity.putExtra("postKey",mData.get(position).getPostKey());
                    postDetailActivity.putExtra("userId",mData.get(position).getUserId());
                    //mapas
                    postDetailActivity.putExtra("cr",mData.get(position).getCr());
                    postDetailActivity.putExtra("numero",mData.get(position).getNumero());
                    postDetailActivity.putExtra("direcc",mData.get(position).getDirecc());
                    postDetailActivity.putExtra("ciudad",mData.get(position).getCiudad());
                    postDetailActivity.putExtra("departamento",mData.get(position).getDepartamento());
                    postDetailActivity.putExtra("pais",mData.get(position).getPais());
                    //finish mapas
                    postDetailActivity.putExtra("userPhoto",mData.get(position).getUserPhoto());
                    // will fix this later i forgot to add user name to post object
                    //postDetailActivity.putExtra("userName",mData.get(position).getUsername);
                    long timestamp  = (long) mData.get(position).getTimeStamp();
                    postDetailActivity.putExtra("postDate",timestamp);
                    postDetailActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(postDetailActivity);

                }
            });


        }
    }
}
