package com.example.racketreadyapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.bumptech.glide.Glide;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {


    private List<User> usersList;

    private Context context;

    public Adapter() {
    }

    public Adapter(List<User> usersList, Context context) {
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

        User users = usersList.get(position);
        String fullname = users.getName();
        String skillLevel = users.getSkillLevel();
        String match_email = users.getEmail();
        String match_password = users.getPassword();
    //    int match_type = users.getMatchTypeID();
        String location = users.getLocation();

        viewHolder.textview.setText(fullname);
        viewHolder.textview2.setText(skillLevel);
        Log.d("fullname", "the name is " + fullname);
        Log.d("fullname", "the Skill Level is " + skillLevel);

        Log.d("Adapter", "Binding data for position " + position + ": " + fullname + ", " + skillLevel);

//        Glide.with(context)
//                .load(users.getProfilePic())
//                .into(viewHolder.imageView);

        viewHolder.imageView.setImageResource(R.drawable.racketreadylogo);

        viewHolder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MatchUserDetails.class);
            intent.putExtra("user-email", match_email);
            intent.putExtra("user-password", match_password);
            intent.putExtra("user-fullname", fullname);
            intent.putExtra("user-skillLevel", skillLevel);
            intent.putExtra("user-location", location);
        //    intent.putExtra("user_matchType", match_type);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        Log.d("user-size", "in the adapter the size is " + usersList.size());
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
