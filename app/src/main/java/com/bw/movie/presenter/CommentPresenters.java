package com.bw.movie.presenter;

import com.bw.movie.core.DataCall;
import com.bw.movie.core.IRequest;
import com.bw.movie.http.NetworkManager;

import io.reactivex.Observable;

/**
 * Created by 申雨轩
 */

public class CommentPresenters extends BasePresenter {
    public CommentPresenters(DataCall dataCall) {
        super(dataCall);
    }

    @Override
    public Observable observable(Object... args) {
        IRequest iRequests = NetworkManager.instance().create(IRequest.class);
        return iRequests.getComment((int)args[0],(String) args[1],(int)args[2],(int)args[3],(int)args[4]);
    }
}
