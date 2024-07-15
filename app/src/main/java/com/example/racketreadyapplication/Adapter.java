package com.example.racketreadyapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {


    private List<Users> usersList;

    private Context context;

    public Adapter() {
    }

    public Adapter(List<Users> usersList, Context context) {
        this.usersList = usersList;
        this.context = context;
    }



//    private String[] testing = {"Jonny", "Joe", "Hendry"};
//
//    String testing2 = "match level";


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardmatches, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

         Users users = usersList.get(position);

         String fullname = users.getFullname();
         String skillLevel = users.getSkillLevel();


        viewHolder.textview.setText(fullname);
        viewHolder.textview2.setText(skillLevel);

//        Glide.with(context)
//                .load(users.getProfilePic())
//                .into(viewHolder.imageView);

        viewHolder.imageView.setImageResource(R.drawable.racketreadylogo);
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textview;
        TextView textview2;

        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textview = itemView.findViewById(R.id.matchName);
            textview2 = itemView.findViewById(R.id.matchSkillLevel);
            imageView = itemView.findViewById(R.id.imageView3);
        }
    }
}
