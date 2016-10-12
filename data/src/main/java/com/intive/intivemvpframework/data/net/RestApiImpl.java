package com.intive.intivemvpframework.data.net;

import com.intive.intivemvpframework.data.entity.UserEntity;
import com.intive.intivemvpframework.data.exception.NetworkConnectionException;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * {@link RestApi} implementation for retrieving data from the network using Retrofit 2.
 */
public class RestApiImpl implements RestApi {

    private static final int READ_TIMEOUT = 10000;

    private static final int CONNECT_TIMEOUT = 15000;

    private final Context mContext;

    private final ApiService mApiService;

    /**
     * Constructor of the class
     *
     * @param context {@link Context}.
     */
    public RestApiImpl(final Context context) {
        if (context == null) {
            throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
        }

        mContext = context.getApplicationContext();
        mApiService = buildRetrofit().create(ApiService.class);
    }

    @Override
    public Observable<List<UserEntity>> getUserEntityList() {

        if (isThereInternetConnection()) {
            return mApiService.getUsers();
        }

        return Observable.error(new NetworkConnectionException());
    }

    @Override
    public Observable<UserEntity> getUserEntityById(final int userId) {

        if (isThereInternetConnection()) {
            return mApiService.getUser(userId);
        }

        return Observable.error(new NetworkConnectionException());
    }

    private Retrofit buildRetrofit() {
        return new Retrofit.Builder()
                .client(buildClient())
                .baseUrl(RestApi.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    private OkHttpClient buildClient() {
        return new OkHttpClient.Builder().
                readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .build();
    }

    /**
     * Checks if the device has any active internet connection.
     *
     * @return true device with internet connection, otherwise false.
     */
    private boolean isThereInternetConnection() {
        boolean isConnected;

        final ConnectivityManager connectivityManager =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }
}
