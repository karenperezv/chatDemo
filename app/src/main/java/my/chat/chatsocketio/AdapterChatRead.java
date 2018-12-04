package my.chat.chatsocketio;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class AdapterChatRead extends RecyclerView.Adapter<AdapterChatRead.ViewHolder>{

    private ArrayList<DataChatRead> data;

    public  AdapterChatRead(ArrayList<DataChatRead> data) {
        this.data = data;
    }


    @NonNull
    @Override
    public AdapterChatRead.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new
                ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_chat_read, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        DataChatRead obj = data.get(i);
        viewHolder.user2.setText(obj.getUser2());
        viewHolder.msg.setText(obj.getMessage());
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
            user2 = (TextView) itemView.findViewById(R.id.txtUserLeido);
            msg = (TextView) itemView.findViewById(R.id.txtChatLeido);

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
