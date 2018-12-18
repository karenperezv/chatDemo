package my.chat.chatsocketio;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterChatRead extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final int ITEM1 = 1;
    private final int ITEM2 = 2;
    private final int ITEM3 = 3;//myImage
    private final int ITEM4 = 4;//Other Image


    private List<ItemExistentMessage> items = new ArrayList<>();

    public AdapterChatRead(List<ItemExistentMessage> items) {
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder;
        switch (viewType){
            //mensajes leidos
            case ITEM1: viewHolder = new AdapterChatRead.Item1Holder(inflater.inflate(R.layout.recycler_chat_read,parent,false));
                break;
                //mensajes por leer
            case ITEM2: viewHolder = new AdapterChatRead.Item2Holder(inflater.inflate(R.layout.recycler_chat_message,parent,false));
                break;
                //imagenes vistas
            case ITEM3: viewHolder = new AdapterChatRead.Item3Holder(inflater.inflate(R.layout.recycler_chat_img_read,parent,false));
                break;
                //imagenes por ver
            case ITEM4: viewHolder = new AdapterChatRead.Item4Holder(inflater.inflate(R.layout.recycler_chat_img,parent,false));
                break;
            default: viewHolder = new AdapterChatRead.Item1Holder(inflater.inflate(R.layout.my_message_chat,parent));
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case ITEM1:
                DataMessageRead item1 = (DataMessageRead) items.get(position);
                AdapterChatRead.Item1Holder item1Holder = (AdapterChatRead.Item1Holder)holder;
                item1Holder.tvMsg.setText(item1.getMessage());
                item1Holder.tvNombre.setText(item1.getUser2());
                break;
            case ITEM2:
                DataMessageNotRead item2 = (DataMessageNotRead) items.get(position);
                AdapterChatRead.Item2Holder item2Holder = (AdapterChatRead.Item2Holder)holder;
                item2Holder.tvNombre.setText(item2.getUser2());
                item2Holder.tvMsg.setText(item2.getMensaje());
                break;
            case ITEM3:
                DataChatImageRead myImage = (DataChatImageRead) items.get(position);
                AdapterChatRead.Item3Holder item3Holder = (AdapterChatRead.Item3Holder) holder;
                // item2Holder.imgPhoto.setImageResource(item2.getFoto());
                Picasso.get().load(myImage.getUrlImg()).into(item3Holder.myImage);
                item3Holder.myImageDate.setText(myImage.getUser2());
                break;
            case ITEM4:
                DataChatImage otherImage = (DataChatImage) items.get(position);
                AdapterChatRead.Item4Holder item4Holder = (AdapterChatRead.Item4Holder)holder;
                // item2Holder.imgPhoto.setImageResource(item2.getFoto());
                Picasso.get().load(otherImage.getUrlImg()).into(item4Holder.otherImage);
                item4Holder.otherImageDate.setText(otherImage.getUser2());
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
        TextView tvMsg;
        TextView tvNombre;
        public Item1Holder(final View itemView) {
            super(itemView);
            tvNombre = (TextView) itemView.findViewById(R.id.txtUserLeido);
            tvMsg = (TextView) itemView.findViewById(R.id.txtChatLeido);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(itemView.getContext(), ChatRoomWithUserActivity.class);
                    intent.putExtra("name2",tvNombre.getText());
                    itemView.getContext().startActivity(intent);
                }
            });

        }
    }

    class Item2Holder extends RecyclerView.ViewHolder{

        TextView tvMsg;
        TextView tvNombre;
        public Item2Holder(final View itemView) {
            super(itemView);
            tvNombre = (TextView) itemView.findViewById(R.id.txtUser2ChatExistente);
            tvMsg=(TextView) itemView.findViewById(R.id.txtMessageChatExistente);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(itemView.getContext(), ChatRoomWithUserActivity.class);
                    intent.putExtra("name2",tvNombre.getText());
                    itemView.getContext().startActivity(intent);
                }
            });

        }
    }



    class Item3Holder extends RecyclerView.ViewHolder{
        ImageView myImage;
        TextView myImageDate;
        public Item3Holder(final View itemView) {
            super(itemView);
            myImage = (ImageView) itemView.findViewById(R.id.imgread);
            myImageDate =(TextView) itemView.findViewById(R.id.txtuser2imgread);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(itemView.getContext(), ChatRoomWithUserActivity.class);
                    intent.putExtra("name2",myImageDate.getText());
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
    class Item4Holder extends RecyclerView.ViewHolder{
        ImageView otherImage;
        TextView otherImageDate;
        public Item4Holder(final View itemView) {
            super(itemView);
            otherImage =(ImageView) itemView.findViewById(R.id.imgnotread);
            otherImageDate = (TextView) itemView.findViewById(R.id.user2notread);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(itemView.getContext(), ChatRoomWithUserActivity.class);
                    intent.putExtra("name2",otherImageDate.getText());
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }

}
