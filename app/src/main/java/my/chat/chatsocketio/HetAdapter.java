package my.chat.chatsocketio;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HetAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private final int ITEM1 = 1;
    private final int ITEM2 = 2;

    private List<Item> items = new ArrayList<>();

    public HetAdapter(List<Item> items) {
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder;
        switch (viewType){
            case ITEM1: viewHolder = new Item1Holder(inflater.inflate(R.layout.my_message_chat,parent,false));
                break;
            case ITEM2: viewHolder = new Item2Holder(inflater.inflate(R.layout.other_message_chat,parent,false));
                break;
            default: viewHolder = new Item1Holder(inflater.inflate(R.layout.my_message_chat,parent));
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case ITEM1:
                Item1 item1 = (Item1) items.get(position);
                Item1Holder item1Holder = (Item1Holder)holder;
                //item1Holder.imgPhoto.setImageResource(item1.getPhoto());
                Picasso.get().load(item1.getPhoto()).into(item1Holder.imgPhoto);
                item1Holder.tvNombre.setText(item1.getMensaje());
                item1Holder.tvHora.setText(item1.getHora());
                break;
            case ITEM2:
                Item2 item2 = (Item2) items.get(position);
                Item2Holder item2Holder = (Item2Holder)holder;
               // item2Holder.imgPhoto.setImageResource(item2.getFoto());
                Picasso.get().load(item2.getFoto()).into(item2Holder.imgPhoto);
                item2Holder.tvNombre.setText(item2.getUsername2());
                item2Holder.tvHora.setText(item2.getHora());
                item2Holder.tvMsg.setText(item2.getMsg());
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class Item1Holder extends RecyclerView.ViewHolder{
        ImageView imgPhoto;
        TextView tvNombre;
        TextView tvHora;
        public Item1Holder(View itemView) {
            super(itemView);
            imgPhoto = (ImageView) itemView.findViewById(R.id.v);
            tvNombre = (TextView) itemView.findViewById(R.id.txtMyMessage);
            tvHora = (TextView) itemView.findViewById(R.id.txtTime);
        }
    }

    class Item2Holder extends RecyclerView.ViewHolder{
        ImageView imgPhoto;
        TextView tvMsg;
        TextView tvHora;
        TextView tvNombre;
        public Item2Holder(View itemView) {
            super(itemView);
            imgPhoto = (ImageView) itemView.findViewById(R.id.viewHoter);
            tvNombre = (TextView) itemView.findViewById(R.id.txtothername);
            tvHora = (TextView) itemView.findViewById(R.id.txtotherTime);
            tvMsg=(TextView) itemView.findViewById(R.id.txtotherSMS);
        }
    }
}