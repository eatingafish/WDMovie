package com.bw.movie.presenter.cinema;

import com.bw.movie.core.DataCall;
import com.bw.movie.core.IRequest;
import com.bw.movie.http.NetworkManager;
import com.bw.movie.presenter.BasePresenter;

import io.reactivex.Observable;

public class CinemaPresenter extends BasePresenter {
    public CinemaPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetworkManager.instance().create(IRequest.class);
        return iRequest.yingpian((int) args[0],(int) args[1]);
    }
}
