package com.android.myapplication.coldpod.ui.details;

import android.util.Log;
import android.view.View;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.android.myapplication.coldpod.network.Channel;
import com.android.myapplication.coldpod.network.RssFeed;
import com.android.myapplication.coldpod.repository.PodCastDetailRepository;
import com.android.myapplication.coldpod.utils.AbsentLiveData;
import com.android.myapplication.coldpod.utils.Constants;
import com.android.myapplication.coldpod.utils.Resource;


import javax.inject.Inject;


public class PodCastDetailViewModel extends ViewModel {
    private static final String TAG = "PodCastDetailViewModel";
    private final PodCastDetailRepository repository;

    private final MutableLiveData<String> _podcastId = new MutableLiveData<>();

    private MediatorLiveData<Integer> progress = new MediatorLiveData<>();
    public LiveData<Integer> getProgress(){
        return progress;
    }

    private final LiveData<Resource<String>> feedURL = Transformations.switchMap(_podcastId, new Function<String, LiveData<Resource<String>>>() {
        @Override
        public LiveData<Resource<String>> apply(String input) {
            Log.d(TAG, "apply: " + input);
            return repository.getLookUp(Constants.I_TUNES_LOOKUP, input);
        }
    });

    public final LiveData<Resource<Channel>> mResourceChannel = Transformations.switchMap(feedURL, new Function<Resource<String>, LiveData<Resource<Channel>>>() {
        @Override
        public LiveData<Resource<Channel>> apply(Resource<String> input) {
            if (input != null && input.status == Resource.Status.SUCCESS && input.data != null) {
                Log.d(TAG, "apply: " + input.data);
                return repository.getRssfeed(input.data);
            }
            return AbsentLiveData.create();
        }
    });
    public LiveData<Channel> mChannel = Transformations.map(mResourceChannel, new Function<Resource<Channel>, Channel>() {
        @Override
        public Channel apply(Resource<Channel> input) {
            if (input != null) {
                return input.data;
            }
            return null;
        }
    });
/*    public LiveData<Integer> progress = Transformations.map(mResourceChannel, new Function<Resource<Channel>, Integer>() {
        @Override
        public Integer apply(Resource<Channel> input) {
            if(input!=null) {
                if (input.status == Resource.Status.LOADING) {
                    return View.VISIBLE;
                }else {
                    return View.GONE;
                }
            }
            return View.VISIBLE;
        }
    });*/




    public void registerMediatorToFeedUrlRequest(){
        progress.addSource(feedURL, new Observer<Resource<String>>() {
            @Override
            public void onChanged(Resource<String> resource) {
                if(resource!=null){
                    progress.setValue(View.VISIBLE);
                    if(resource.status!= Resource.Status.LOADING){
                        progress.removeSource(feedURL);
                        registerMediatorToChannelRequest();
                    }
                }else{
                    progress.removeSource(feedURL);
                    progress.setValue(View.GONE);
                }
            }
        });
    }


    public void registerMediatorToChannelRequest(){
        progress.addSource(mResourceChannel, new Observer<Resource<Channel>>() {
            @Override
            public void onChanged(Resource<Channel> channelResource) {
               if(channelResource!=null){
                   progress.setValue(View.VISIBLE);
                   if(channelResource.status!= Resource.Status.LOADING){
                       progress.removeSource(mResourceChannel);
                       progress.setValue(View.GONE);
                   }
               }else{
                   progress.removeSource(mResourceChannel);
                   progress.setValue(View.GONE);
               }
            }
        });
    }


    @Inject
    public PodCastDetailViewModel(PodCastDetailRepository repository) {
        this.repository = repository;
    }


    public void setPodCastId(String podCastId) {
        Log.d(TAG, "setPodCastId: " + podCastId);
        _podcastId.setValue(podCastId);
        registerMediatorToFeedUrlRequest();
    }
}
