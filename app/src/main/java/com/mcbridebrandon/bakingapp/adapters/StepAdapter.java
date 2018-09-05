package com.mcbridebrandon.bakingapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mcbridebrandon.bakingapp.R;
import com.mcbridebrandon.bakingapp.model.Step;

import java.util.List;


public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder>{

    private static final String TAG = "STEP ADAPTER";
    private List<Step> mStepList;
    private final ItemClickListener mClickListener;


    public StepAdapter(Context context, List<Step> itemList, ItemClickListener clickListener) {
        this.mStepList = itemList;
        this.mClickListener = clickListener;
    }


    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.step_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);

        return new StepViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        Log.d(TAG, "#" + position + mStepList.get(position).getVideoURL());

        int id;
        String shortDescription;
        String description;
        String videoUrl;
        String thumbUrl;

        if (mStepList != null) {
            //holder.id.setText(String.valueOf(mStepList.get(position).getId()) );
            holder.shortDescription.setText(mStepList.get(position).getId() + ". " + mStepList.get(position).getShortDescription());
           // holder.description.setText(mStepList.get(position).getDescription());
            //holder.videoUrl.setText(mStepList.get(position).getVideoURL());

            //holder.thumbUrl.setText(mStepList.get(position).getThumbnailURL());


        }
    }

    @Override
    public int getItemCount() {
        if(mStepList != null) {
            return this.mStepList.size();
        }else{
            return 1;
        }
    }

    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView id;
        final TextView shortDescription;
        TextView description;
        TextView videoUrl;
        TextView thumbUrl;


        StepViewHolder(View itemView) {
            super(itemView);
            //recipeImageView = itemView.findViewById();
            //id = itemView.findViewById(R.id.tv_step_id);
            shortDescription = itemView.findViewById(R.id.tv_step_short_description);
           // description = itemView.findViewById(R.id.tv_step_description);
           // videoUrl = itemView.findViewById(R.id.tv_video_url);
            //thumbUrl = itemView.findViewById(R.id.tv_thumb_url);


            //set onclick
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickListener.onItemClick(getAdapterPosition());
        }
    }

    //click listener for recipe
    public interface ItemClickListener {
        void onItemClick(int position);

    }



    public void updateAdapter(List<Step> itemList){
        this.mStepList = itemList;
        notifyDataSetChanged();
    }

}
