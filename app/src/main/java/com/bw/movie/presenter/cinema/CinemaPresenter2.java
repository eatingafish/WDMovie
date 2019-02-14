package com.bw.movie.presenter.cinema;

import com.bw.movie.core.DataCall;
import com.bw.movie.core.IRequest;
import com.bw.movie.http.NetworkManager;
import com.bw.movie.presenter.BasePresenter;

import io.reactivex.Observable;

public class CinemaPresenter2 extends BasePresenter {
    public CinemaPresenter2(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetworkManager.instance().create(IRequest.class);
        return iRequest.near((int)args[0], (String)args[1],(String) args[2],(String) args[3],(int) args[4],(int)args[5]);
    }
}
