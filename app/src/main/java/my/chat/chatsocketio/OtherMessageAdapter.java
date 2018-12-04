package my.chat.chatsocketio;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class OtherMessageAdapter extends RecyclerView.Adapter<OtherMessageAdapter.ViewHolder>{

    private ArrayList<otherSMS> data;

    public OtherMessageAdapter(ArrayList<otherSMS> data) {
        this.data = data;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new
         ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.other_message_chat, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        otherSMS obj = data.get(i);
       /* viewHolder.imgPhoto.setImageResource(obj.getFoto());
        viewHolder.tvNombre.setText(obj.getUsername2());
        viewHolder.tvHora.setText(obj.getHora());
        viewHolder.tvMsg.setText(obj.getMsg());*/
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgPhoto;
        TextView tvMsg;
        TextView tvHora;
        TextView tvNombre;

        public ViewHolder(View itemView) {
            super(itemView);
            imgPhoto = (ImageView) itemView.findViewById(R.id.viewHoter);
            tvNombre = (TextView) itemView.findViewById(R.id.txtothername);
            tvHora = (TextView) itemView.findViewById(R.id.txtotherTime);
            tvMsg=(TextView) itemView.findViewById(R.id.txtotherSMS);
        }
    }


}
