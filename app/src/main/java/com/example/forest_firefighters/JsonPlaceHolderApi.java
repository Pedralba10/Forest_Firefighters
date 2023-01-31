package com.example.forest_firefighters;

import java.util.List;

import com.example.forest_firefighters.Weather.Model200;
import Modelo.PrediccionMunicipio;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface JsonPlaceHolderApi {

    //Método para obtener la información de la API, propio de retrofit, devuelve un response
    //Indica la parte de la url de dónde va a coger los datos
    //@GET("opendata/api/prediccion/especifica/municipio/diaria/36039/?api_key=eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZHJpODZ2aWdvQGdtYWlsLmNvbSIsImp0aSI6IjJkNzgzYjhhLTFjMTUtNGJjNS04ZmJkLTMwZmY4NWM2NWUyNSIsImlzcyI6IkFFTUVUIiwiaWF0IjoxNjAzMDE5NTg0LCJ1c2VySWQiOiIyZDc4M2I4YS0xYzE1LTRiYzUtOGZiZC0zMGZmODVjNjVlMjUiLCJyb2xlIjoiIn0.a1IIOmDUM1FI6neNmgLeT728iLAKa26mxia-Oe5sOWs")
    //@GET("opendata/sh/89315117")
    @GET
    Call<Model200> getModel200 (@Url String url);

    @GET
    Call<List<PrediccionMunicipio>> getPrediccion(@Url String url);
}
