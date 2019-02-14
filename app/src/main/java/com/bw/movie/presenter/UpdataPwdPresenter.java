package com.bw.movie.presenter;

import com.bw.movie.core.DataCall;
import com.bw.movie.core.IRequest;
import com.bw.movie.http.NetworkManager;

import io.reactivex.Observable;

public class UpdataPwdPresenter extends BasePresenter{
    public UpdataPwdPresenter(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    protected Observable observable(Object... args) {
        IRequest iRequest = NetworkManager.instance().create(IRequest.class);
        return iRequest.updatepwd((int)args[0],(String)args[1],(String)args[2],(String)args[3],(String)args[4]);
    }
}
