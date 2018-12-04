package my.chat.chatsocketio;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{

    private ArrayList<chatExistente> data;

    public MessageAdapter(ArrayList<chatExistente> data) {
        this.data = data;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new
         ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_chat_message, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        chatExistente obj = data.get(i);
        viewHolder.user2.setText(obj.getUser2());
        viewHolder.msg.setText(obj.getMensaje());
    }

    @Override
    public int getItemCount() {
       return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView user2;
        TextView msg;

        public ViewHolder(final View itemView) {
            super(itemView);
            user2 = (TextView) itemView.findViewById(R.id.txtUser2ChatExistente);
            msg = (TextView) itemView.findViewById(R.id.txtMessageChatExistente);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent intent= new Intent(itemView.getContext(), ChatRoomWithUserActivity.class);
                    intent.putExtra("name2",user2.getText());
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }


}
