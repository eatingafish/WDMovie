package com.bw.movie.presenter.cinema;

import com.bw.movie.core.DataCall;
import com.bw.movie.core.IRequest;
import com.bw.movie.http.NetworkManager;
import com.bw.movie.presenter.BasePresenter;

import io.reactivex.Observable;

public class CinemaMoviePaiqi extends BasePresenter {
    public CinemaMoviePaiqi(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetworkManager.instance().create(IRequest.class);
        return iRequest.dypaiqi((int) args[0], (String) args[1],(int) args[2],(int) args[3]);
    }
}
