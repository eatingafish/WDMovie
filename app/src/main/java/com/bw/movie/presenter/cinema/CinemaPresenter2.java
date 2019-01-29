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
        return iRequest.near((int)args[0], (String)args[1],(int) args[2],(int) args[3],(String) args[4],(String)args[5]);
    }
}
