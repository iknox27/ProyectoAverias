package iknox27.proyectoaverias.service;

import java.util.List;

import iknox27.proyectoaverias.entities.Failure;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface FailureService {
    public static final String API_KEY = "x-api-key: rabArf10E86thWRQ5u4MH3pFXVpiQiXv8jg1c4hO";

    @Headers({"Accept: application/json", API_KEY})
    @GET("averias")
    Call<List<Failure>> obtenerListaDeAverias();

    @Headers({"Accept: application/json", API_KEY})
    @GET("averias/{id}")
    Call<Failure> obtenerDetallesDePost(@Path("id") String id);


    @Headers({"Accept: application/json", API_KEY})
    @POST("averias")
    Call<Failure> crearAveria(@Body Failure post);


    @Headers({"Accept: application/json", API_KEY})
    @POST("averias/{id}")
    Call<Failure> editarAveriaPorId(@Path("id") String id, @Body Failure post);

    @Headers({"Accept: application/json", API_KEY})
    @DELETE("averias/{id}")
    Call<Failure> eliminarAveriaPorId(@Path("id") String id);
}
