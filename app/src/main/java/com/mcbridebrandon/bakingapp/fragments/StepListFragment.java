package com.mcbridebrandon.bakingapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mcbridebrandon.bakingapp.R;
import com.mcbridebrandon.bakingapp.StepDetailActivity;
import com.mcbridebrandon.bakingapp.adapters.StepAdapter;
import com.mcbridebrandon.bakingapp.model.Step;

import java.util.List;

public class StepListFragment extends Fragment implements StepAdapter.ItemClickListener{

    private View rootView;
    private StepAdapter mStepAdapter;
    private RecyclerView mStepRecyclerView;
    private List<Step> stepList;

    //mandatory constructor for instantiating the fragment
    public StepListFragment(){

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        //inflate the ingredient list layout
        View rootView = inflater.inflate(R.layout.fragment_step_list,container,false);

        //get a reference to the recyclerview
        mStepRecyclerView = rootView.findViewById(R.id.rv_step_list);

        //set the layout manager
        mStepRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //setup ingredient adapter
        mStepAdapter = new StepAdapter(getContext(), stepList, this);

        //set the adapter
        mStepRecyclerView.setAdapter(mStepAdapter);


        return rootView;
    }

    public void setStepList(List<Step> stepList) {
        this.stepList = stepList;
    }

    @Override
    public void onItemClick(int position) {
        //handled in detail activity
        Step stepToSend;
        stepToSend = stepList.get(position);
        Bundle bundle = new Bundle();
        bundle.putParcelable("step", stepToSend);
        Intent intent = new Intent(getContext(), StepDetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
