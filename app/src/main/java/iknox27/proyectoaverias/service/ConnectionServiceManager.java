package iknox27.proyectoaverias.service;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectionServiceManager{

    private static FailureService singleton;

    public static FailureService obtenerServicio(){

        if(singleton == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://jsonplaceholder.typicode.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            singleton = retrofit.create(FailureService.class);
        }

        return singleton;
    }
}
