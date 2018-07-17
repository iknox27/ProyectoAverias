package iknox27.proyectoaverias.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectionImgurManager {
    private static ImgurService singleton;

    public static ImgurService obtenerServicio(){

        if(singleton == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.imgur.com/3/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            singleton = retrofit.create(ImgurService.class);
        }

        return singleton;
    }
}
