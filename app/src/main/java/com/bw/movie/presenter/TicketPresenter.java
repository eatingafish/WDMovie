package com.bw.movie.presenter;

import com.bw.movie.core.DataCall;
import com.bw.movie.core.IRequest;
import com.bw.movie.http.NetworkManager;


import io.reactivex.Observable;

public class TicketPresenter extends BasePresenter {
    public TicketPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetworkManager.instance().create(IRequest.class);
        return iRequest.findTicket((int)args[0],(String)args[1],(int)args[2],(int)args[3],(int)args[4]);
    }
}
