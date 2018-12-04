package my.chat.chatsocketio;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder>{
    String username2;
    private ArrayList<DataList> data;

    public ListAdapter(ArrayList<DataList> data) {
        this.data = data;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new
         ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_user_list, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        DataList obj = data.get(i);
      //  viewHolder.imgPhoto.setImageResource(obj.getPhoto());
        Picasso.get().load(obj.getPhoto()).into(viewHolder.imgPhoto);
        viewHolder.tvUser2.setText(obj.getUsername2());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imgPhoto;
        TextView tvUser2;

        public ViewHolder(final View itemView) {
            super(itemView);
            imgPhoto = (ImageView) itemView.findViewById(R.id.phothoNickname);
            tvUser2 = (TextView) itemView.findViewById(R.id.txtnickNameUser);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();
                    Intent intent= new Intent(itemView.getContext(), ChatRoomWithUserActivity.class);
                    intent.putExtra("name2",tvUser2.getText());
                    itemView.getContext().startActivity(intent);

                }
            });

            //Foto de Nickname

            imgPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent= new Intent(itemView.getContext(), Activity_profilephoto.class);
                    intent.putExtra("name2",tvUser2.getText());
                    itemView.getContext().startActivity(intent);
                }
            });

        }
    }


}
