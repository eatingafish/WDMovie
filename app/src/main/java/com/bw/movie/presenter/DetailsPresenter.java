package com.bw.movie.presenter;

import com.bw.movie.core.DataCall;
import com.bw.movie.core.IRequest;
import com.bw.movie.http.NetworkManager;

import io.reactivex.Observable;

public class DetailsPresenter extends BasePresenter {
    public DetailsPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    public Observable observable(Object... args) {
        IRequest iRequests = NetworkManager.instance().create(IRequest.class);
        return iRequests.getDetails((int)args[0],(String) args[1],(int)args[2]);
    }
}
