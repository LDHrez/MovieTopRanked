package com.hitsstest.movietoprated.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hitsstest.movietoprated.DetailsActivity;
import com.hitsstest.movietoprated.R;
import com.hitsstest.movietoprated.PojoClasses.Result;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class AdapterPosterView extends RecyclerView.Adapter<AdapterPosterView.ViewHolder> {

    private static final  String IMAGE_BASE_PATH = "https://image.tmdb.org/t/p/w500/";
    private List<Result> resultsList;
    private  Context mContext;
    private int posX;
    private int posY;

    public AdapterPosterView(List<Result> resultsList, Context context) {
        this.resultsList = resultsList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_poster,parent,false);
        view.setFocusable(true);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Picasso.get().load(IMAGE_BASE_PATH + resultsList.get(position).getPosterPath()).into(holder.posterImage);
        holder.titleText.setText(resultsList.get(position).getTitle());
        holder.rankText.setText(resultsList.get(position).getVoteAverage().toString());
        holder.topText.setText("Top #"+String.valueOf(position+1));

        try {
            holder.yearText.setText(GetDateFormat(resultsList.get(position).getReleaseDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.descriptionText.setText(resultsList.get(position).getOverview());
        holder.showInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, DetailsActivity.class);
                Bundle bundle= new Bundle();
                bundle.putSerializable("result", (Serializable) resultsList.get(position));
                intent.putExtras(bundle);
                mContext.startActivity(intent);
                ShowAnimation(false,holder);
                holder.descriptionLayout.setVisibility(View.GONE);
            }
        });

        holder.posterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.descriptionLayout.getVisibility() == View.GONE)
                {
                    ShowAnimation(true,holder);
                    holder.descriptionLayout.setVisibility(View.VISIBLE);
                }
                else if (holder.descriptionLayout.getVisibility() == View.VISIBLE)
                {
                    ShowAnimation(false, holder);
                    holder.descriptionLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return resultsList.size();
    }
    public void setX(int posX) {
        this.posX = posX;
    }

    public void setY(int posY) {
        this.posY = posY;
    }


    private String GetDateFormat(String dateString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        Date myDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        return dateFormat.format(myDate);
    }



    private void ShowAnimation(boolean isApper, ViewHolder holder)
    {
        AnimationSet set = new AnimationSet(true);
        Animation animation = null;
        if (isApper)
        {
            animation = new TranslateAnimation(
                    Animation.ABSOLUTE, 0.0f,
                    Animation.ABSOLUTE, 0.0f,
                    Animation.RELATIVE_TO_PARENT, 1.0f,
                    Animation.RELATIVE_TO_PARENT, 0.0f);
        }
        else
        {
            animation = new TranslateAnimation(
                    Animation.ABSOLUTE, 0.0f,
                    Animation.ABSOLUTE, 0.0f,
                    Animation.RELATIVE_TO_PARENT, 0.0f,
                    Animation.RELATIVE_TO_PARENT, 1.0f);
        }
        animation.setDuration(700);
        set.addAnimation(animation);
        LayoutAnimationController controller = new LayoutAnimationController(set, 0.25f);

        holder.descriptionLayout.setLayoutAnimation(controller);
        holder.descriptionLayout.startAnimation(animation);
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout posterLayout;
        private LinearLayout descriptionLayout;

        private TextView titleText;
        private TextView yearText;
        private TextView rankText;
        private TextView topText;
        private ImageView posterImage;


        private TextView descriptionText;
        private Button showInfoButton;

        public ViewHolder(View itemView){
            super(itemView);
            posterLayout = (LinearLayout) itemView.findViewById(R.id.layoutPoster);
            descriptionLayout = (LinearLayout) itemView.findViewById(R.id.layoutDesc);

            titleText = (TextView) itemView.findViewById(R.id.textMovieTitle);
            yearText = (TextView) itemView.findViewById(R.id.textMovieYear);
            rankText = (TextView) itemView.findViewById(R.id.textMovieRank);
            topText = (TextView) itemView.findViewById(R.id.textTop);
            posterImage = (ImageView) itemView.findViewById(R.id.imageViewPoster);
            descriptionText = (TextView) itemView.findViewById(R.id.textMovieDescription);
            showInfoButton = (Button) itemView.findViewById(R.id.buttonMovieMoreInfo);


        }
    }

}
