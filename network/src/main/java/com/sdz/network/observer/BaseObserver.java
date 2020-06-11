package com.sdz.network.observer;

import android.util.Log;


import com.sdz.base.model.SuperBaseModel;
import com.sdz.network.errorhandler.ExceptionHandle;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseObserver<T> implements Observer<T> {
    SuperBaseModel baseModel;
    public BaseObserver(SuperBaseModel baseModel) {
        this.baseModel = baseModel;
    }
    @Override
    public void onError(Throwable e) {
        Log.e("lvr", e.getMessage());
        // todo error somthing

        if(e instanceof ExceptionHandle.ResponseThrowable){
            onError((ExceptionHandle.ResponseThrowable)e);
        } else {
            onError(new ExceptionHandle.ResponseThrowable(e, ExceptionHandle.ERROR.UNKNOWN));
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        if(baseModel != null){
            baseModel.addDisposable(d);
        }
    }


    @Override
    public void onComplete() {
    }


    public abstract void onError(ExceptionHandle.ResponseThrowable e);

}
