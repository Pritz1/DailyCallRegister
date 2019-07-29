package com.eis.dailycallregister.Fragment;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.eis.dailycallregister.Activity.CapNUpVstCard;
import com.eis.dailycallregister.Activity.TestDetail;
import com.eis.dailycallregister.Api.RetrofitClient;
import com.eis.dailycallregister.Others.Global;
import com.eis.dailycallregister.Others.ViewDialog;
import com.eis.dailycallregister.Pojo.TestListItem;
import com.eis.dailycallregister.Pojo.TestResponse;
import com.eis.dailycallregister.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Elearn extends Fragment {

View view;

    ViewDialog progressDialoge;
    RecyclerView test;
    List<TestListItem> testList = new ArrayList<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("TEST MASTER");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_elearn, container, false);
        test=view.findViewById(R.id.test);
        progressDialoge = new ViewDialog(getActivity()); //loader
        setAdapter();
        callApi();
        return view;
    }

    private void setAdapter() {
        test.setNestedScrollingEnabled(false);
        test.setLayoutManager(new LinearLayoutManager(getActivity()));
        test.setAdapter(new RecyclerView.Adapter() {
            class Holder extends RecyclerView.ViewHolder{ //changes
                TextView tname,sdate,edate,time,quest;
                public Holder(@NonNull View itemView) {
                    super(itemView);
                    tname=itemView.findViewById(R.id.tname);
                    sdate=itemView.findViewById(R.id.sdate);
                    edate=itemView.findViewById(R.id.edate);
                    time=itemView.findViewById(R.id.time);
                    quest=itemView.findViewById(R.id.quest);
                }
            }

            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View v=LayoutInflater.from(getActivity()).inflate(R.layout.testmasteradapter,viewGroup,false);
                Holder holder=new Holder(v);
                return holder;
            }

            @Override //add below 2 methods to avoid data loss on scroll of recycler view
            public int getItemViewType(int position) {
                return super.getItemViewType(position);
            }

            @Override
            public long getItemId(int position) {
                return super.getItemId(position);
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final Holder holder= (Holder) viewHolder;
                final TestListItem modal=testList.get(i);
                holder.tname.setText(modal.getTestName());
                holder.sdate.setText(modal.getActiveDateFrom());
                holder.edate.setText(modal.getActiveDateTo());
                holder.time.setText(modal.getTotalTime());
                holder.quest.setText(modal.getNoOfQuestions());
                holder.itemView.setTag(i);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), TestDetail.class);
                        intent.putExtra("tname", modal.getTestName());
                        intent.putExtra("sdate", modal.getActiveDateFrom());
                        intent.putExtra("edate", modal.getActiveDateTo());
                        intent.putExtra("time", modal.getTotalTime());
                        intent.putExtra("quest", modal.getNoOfQuestions());
                        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.trans_left_in, R.anim.trans_left_out).toBundle();
                        startActivity(intent, bndlanimation);
                    }
                });
            }

            @Override
            public int getItemCount() {
                return testList.size();
            }
        });
    }

    private void callApi() {
        progressDialoge.show();
        retrofit2.Call<TestResponse> call= RetrofitClient.getInstance().getApi().getTestMaster("3", Global.dbprefix);
        call.enqueue(new Callback<TestResponse>() {
            @Override
            public void onResponse(Call<TestResponse> call, Response<TestResponse> response) {
                progressDialoge.dismiss();
                TestResponse res=response.body();
                testList=res.getTestList();
                test.getAdapter().notifyDataSetChanged();
                Log.d("emfijewnfib : ",Integer.toString(testList.size()));
            }

            @Override
            public void onFailure(Call<TestResponse> call, Throwable t) {
                progressDialoge.dismiss();
                Toast.makeText(getActivity(), "Failed To Fetch Data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
