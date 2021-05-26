package app.juanda.jdvpl;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



public class myRecycleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView txt_commentarios;

    ItemClickListened itemClickListened;

    public void setItemClickListened(ItemClickListened itemClickListened) {
        this.itemClickListened = itemClickListened;
    }

    public myRecycleViewHolder(@NonNull View itemView) {
        super(itemView);

        txt_commentarios=(TextView)itemView.findViewById(R.id.text_comemn);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        itemClickListened.onClick(view,getAdapterPosition());
    }
}
