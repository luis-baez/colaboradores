package com.example.colaboradores.main;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.colaboradores.utils.Constants;
import com.example.colaboradores.ws.ApiClient;
import com.example.colaboradores.ws.EndPoints;
import com.example.colaboradores.ws.response.ColaboratorsResponse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import butterknife.internal.Utils;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter {

    private Context context;
    private MainCallback mainCallback;

    public MainPresenter(Context context, MainCallback mainCallback) {
        this.context = context;
        this.mainCallback = mainCallback;
    }

    public void dbExist(){
        File file = new File(Constants.FILE_PATH + Constants.FILE_NAME_COMPLETE );
        mainCallback.onDbExist(file.exists());
    }

    public void getColaborators(){
        Call<ColaboratorsResponse> callDownloadCall = ApiClient.getRetrofitInstance().create(EndPoints.class).getColaborators();
        callDownloadCall.enqueue(new Callback<ColaboratorsResponse>() {
            @Override
            public void onResponse(Call<ColaboratorsResponse> call, Response<ColaboratorsResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getData() != null && response.body().getData().getFile() != null
                        && !response.body().getData().getFile().isEmpty()) {
                    downloadJson(response.body().getData().getFile());

                } else {
                    mainCallback.onErrorGetColaboratorst();
                }
            }

            @Override
            public void onFailure(Call<ColaboratorsResponse> call, Throwable t) {
                mainCallback.onErrorGetColaboratorst();
            }
        });
    }

    private void downloadJson(String url){
            Call<ResponseBody> callDownloadCall = ApiClient.getRetrofitInstanceDownload().create(EndPoints.class).downloadJSON(url);
            callDownloadCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        new AsyncTask<Void, Void, Void>() {
                            @Override
                            protected Void doInBackground(Void... voids) {
                                boolean writtenToDisk = writeResponseBodyToDisk(response.body());
                                if (writtenToDisk) {
                                    try {
                                        unzip(Constants.FILE_PATH + Constants.FILE_NAME + ".zip", Constants.FILE_PATH);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        Log.d("ZIP", e.getLocalizedMessage());
                                    }
                                } else {
                                    mainCallback.onErrorGetColaboratorst();
                                }
                                return null;
                            }
                        }.execute();

                    } else {
                        mainCallback.onErrorGetColaboratorst();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    mainCallback.onErrorGetColaboratorst();
                }
            });
    }

    public static boolean writeResponseBodyToDisk(ResponseBody body) {
        try {

            File folder = new File(Environment.getExternalStorageDirectory(), "colaborators");
            if(!folder.exists())
                folder.mkdirs();

            File futureStudioIconFile = new File(Constants.FILE_PATH + Constants.FILE_NAME + ".zip");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

//                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    public static void unzip(String zipFile, String location) throws IOException {
        try {
            File f = new File(location);
            if (!f.isDirectory()) {
                f.mkdirs();
            }

            try {

                net.lingala.zip4j.core.ZipFile zipFileD = new net.lingala.zip4j.core.ZipFile(zipFile);


                zipFileD.extractFile(zipFileD.getFileHeader(Constants.FILE_NAME_COMPLETE), location, null, Constants.FILE_NAME_COMPLETE);

            } finally {
                File zFile = new File(zipFile);
                if (zFile.exists()){
                    zFile.delete();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
