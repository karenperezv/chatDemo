package my.chat.chatsocketio;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class chat_adapter extends RecyclerView.Adapter<chat_adapter.ViewHolder> {
    ArrayList<String> chat_data = new ArrayList<>();
    ArrayList<String> penguirin = new ArrayList<>();

    public  chat_adapter(ArrayList<String> chat_data, ArrayList<String> penguirin){
    this.chat_data=chat_data;
    this.penguirin=penguirin;
    }

    @NonNull
    @Override
    public chat_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.other_message_chat,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull chat_adapter.ViewHolder holder, int i) {
        if (penguirin.get(i) == "me") {
            holder.getOthersms().setText(chat_data.get(i));
            holder.getUser().setText(penguirin.get(i));
            holder.getLayout().setGravity(Gravity.RIGHT);
        }else{
            holder.getOthersms().setText(chat_data.get(i));
            holder.getUser().setText(penguirin.get(i));
            holder.getLayout().setGravity(Gravity.RIGHT);
        }
    }

    @Override
    public int getItemCount() {
        return chat_data.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        TextView othersms,user;
        LinearLayout layout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            othersms=(TextView)itemView.findViewById(R.id.txtotherSMS);
            user=(TextView)itemView.findViewById(R.id.txtothername);
            layout=(LinearLayout)itemView.findViewById(R.id.otherUserLiner);
        }

        public TextView getOthersms() {
            return othersms;
        }

        public TextView getUser() {
            return user;
        }

        public LinearLayout getLayout() {
            return layout;
        }
    }
}
