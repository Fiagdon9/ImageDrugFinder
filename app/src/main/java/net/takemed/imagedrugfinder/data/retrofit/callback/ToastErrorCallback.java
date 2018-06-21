package net.takemed.imagedrugfinder.data.retrofit.callback;

import net.takemed.imagedrugfinder.ui.activity.BaseActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ToastErrorCallback<T> implements Callback<T> {

    private final String failedMessage;
    private BaseActivity base;
    private OnSuccessCallback<T> onSuccess;

    public ToastErrorCallback(BaseActivity base,
                              OnSuccessCallback<T> onSuccess,
                              String failedMessage) {
        this.base = base;
        this.onSuccess = onSuccess;
        this.failedMessage = failedMessage;
    }

    public ToastErrorCallback(BaseActivity base,
                              OnSuccessCallback<T> onSuccess) {
        this.base = base;
        this.onSuccess = onSuccess;
        this.failedMessage = "Error, motherfucker!";
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful() &&
                response.body() != null) {
            //ok
            onSuccess.onSuccess(response.body());
        }
        //has no data or not successful
        else {
            onFailure(call, null);
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        base.showToast(failedMessage);
    }


    public interface OnSuccessCallback<T> {

        void onSuccess(T data);

    }

}