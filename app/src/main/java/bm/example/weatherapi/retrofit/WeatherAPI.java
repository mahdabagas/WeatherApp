package bm.example.weatherapi.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WeatherAPI {
    @GET("forecast?latitude=-7.98&longitude=112.63&daily=weathercode&current_weather=true&timezone=auto")
    Call<RecyclerData> getData();
}
