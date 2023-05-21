package bm.example.weatherapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import bm.example.weatherapi.retrofit.WeatherAPI;
import bm.example.weatherapi.retrofit.RecyclerData;
import retrofit2.Call;
import retrofit2.Callback;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import bm.example.weatherapi.databinding.ActivityMainBinding;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener {

    private RequestQueue requestQueue;

    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Set OnClick listener Button
        binding.btnVolley.setOnClickListener(this);
        binding.btnRetrofit.setOnClickListener(this);
        binding.reset.setOnClickListener(this);

        // Membuat Request Antrian
        this.requestQueue = Volley.newRequestQueue(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == binding.btnVolley.getId()){
            getWeatherVolley();
        } else if (view.getId() == binding.reset.getId()){
            reset();
        } else if(view.getId() == binding.btnRetrofit.getId()){
            getWeatherRetrofit();
        }
    }

    public void getWeatherVolley() {
        JsonObjectRequest sr = new JsonObjectRequest(
                "https://api.open-meteo.com/v1/forecast?latitude=-7.98&longitude=112.63&daily=weathercode&current_weather=true&timezone=auto",
                this,
                this
        );
        this.requestQueue.add(sr);
    }

    @Override
    public void onResponse(JSONObject response) {
        try{
            // Mengambil data apabila berbentuk object dan array
            JSONObject current_weather = response.getJSONObject("current_weather");
            JSONObject daily = response.getJSONObject("daily");
            JSONArray time = daily.getJSONArray("time");
            JSONArray weathercode = daily.getJSONArray("weathercode");

            // Set temperature, windspeed, dan koordinat
            String derajatTemp = current_weather.getString("temperature") + "°";
            binding.temperature.setText(derajatTemp);
            binding.windspeed.setText(current_weather.getString("windspeed"));
            String koordinat = response.getString("latitude") + " ,"+ response.getString("longitude") ;
            binding.koordinat.setText(koordinat);

            // Set Text Date 7 hari kedepan
            binding.date1.setText(time.getString(0));
            binding.date2.setText(time.getString(1));
            binding.date3.setText(time.getString(2));
            binding.date4.setText(time.getString(3));
            binding.date5.setText(time.getString(4));
            binding.date6.setText(time.getString(5));
            binding.date7.setText(time.getString(6));

            // Set Gambar Sesuai dengan
            int ubahGambar;
            ubahGambar = setGambar(weathercode.getString(0));
            binding.imgDate1.setImageResource(ubahGambar);
            binding.imgWeather.setImageResource(ubahGambar);
            ubahGambar = setGambar(weathercode.getString(1));
            binding.imgDate2.setImageResource(ubahGambar);
            ubahGambar = setGambar(weathercode.getString(2));
            binding.imgDate3.setImageResource(ubahGambar);
            ubahGambar = setGambar(weathercode.getString(3));
            binding.imgDate4.setImageResource(ubahGambar);
            ubahGambar = setGambar(weathercode.getString(4));
            binding.imgDate5.setImageResource(ubahGambar);
            ubahGambar = setGambar(weathercode.getString(5));
            binding.imgDate6.setImageResource(ubahGambar);
            ubahGambar = setGambar(weathercode.getString(6));
            binding.imgDate7.setImageResource(ubahGambar);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    private void getWeatherRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.open-meteo.com/v1/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        WeatherAPI weatherAPI = retrofit.create(WeatherAPI.class);
        Call<RecyclerData> call = weatherAPI.getData();
        call.enqueue(new Callback<RecyclerData>() {
            @Override
            public void onResponse(Call<RecyclerData> call, retrofit2.Response<RecyclerData> response) {
                if (response.isSuccessful()){
                    RecyclerData data = response.body();

                    // Data temperature
                    String derajatTemp = String.valueOf(data.getCurrent_weather().getTemperature()) + "°";
                    binding.temperature.setText(derajatTemp);
                    binding.windspeed.setText(String.valueOf(data.getCurrent_weather().getWindspeed()));
                    String koordinat = data.getLatitude() + ", " + data.getLongitude();
                    binding.koordinat.setText(koordinat);

                    // Data Date 7 Hari kedepan
                    binding.date1.setText(data.getDaily().getTime().get(0));
                    binding.date2.setText(data.getDaily().getTime().get(1));
                    binding.date3.setText(data.getDaily().getTime().get(2));
                    binding.date4.setText(data.getDaily().getTime().get(3));
                    binding.date5.setText(data.getDaily().getTime().get(4));
                    binding.date6.setText(data.getDaily().getTime().get(5));
                    binding.date7.setText(data.getDaily().getTime().get(6));

                    // Set Gambar
                    int ubahGambar;
                    ubahGambar = setGambar(data.getDaily().getWeathercode().get(0).toString());
                    System.out.println(ubahGambar);
                    binding.imgDate1.setImageResource(ubahGambar);
                    binding.imgWeather.setImageResource(ubahGambar);
                    ubahGambar = setGambar(String.valueOf(data.getDaily().getWeathercode().get(1)));
                    binding.imgDate2.setImageResource(ubahGambar);
                    ubahGambar = setGambar(String.valueOf(data.getDaily().getWeathercode().get(2)));
                    binding.imgDate3.setImageResource(ubahGambar);
                    ubahGambar = setGambar(String.valueOf(data.getDaily().getWeathercode().get(3)));
                    binding.imgDate4.setImageResource(ubahGambar);
                    ubahGambar = setGambar(String.valueOf(data.getDaily().getWeathercode().get(4)));
                    binding.imgDate5.setImageResource(ubahGambar);
                    ubahGambar = setGambar(String.valueOf(data.getDaily().getWeathercode().get(5)));
                    binding.imgDate6.setImageResource(ubahGambar);
                    ubahGambar = setGambar(String.valueOf(data.getDaily().getWeathercode().get(6)));
                    binding.imgDate7.setImageResource(ubahGambar);
                }
            }

            @Override
            public void onFailure(Call<RecyclerData> call, Throwable t) {

            }
        });
    }
    public int setGambar (String value) {
        switch (value) {
            case "0":
                return R.drawable.weather0;
            case "1":
                return R.drawable.weather1;
            case "2":
                return R.drawable.weather2;
            case "3":
                return R.drawable.weather3;
            case "45":
            case "48":
                return R.drawable.weather45;
            case "51":
            case "53":
            case "55":
            case "56":
            case "57":
                return R.drawable.weather51;
            case "61":
                return R.drawable.weather61;
            case "63":
                return R.drawable.weather63;
            case "65":
            case "66":
            case "67":
                return R.drawable.weather65;
            case "95":
                return R.drawable.weather95;
        }
        return R.drawable.weather95;
    }

    public void reset() {
        binding.temperature.setText("0°");
        binding.windspeed.setText("TextView");
        binding.koordinat.setText("TextView");
        binding.date1.setText("TextView");
        binding.date2.setText("TextView");
        binding.date3.setText("TextView");
        binding.date4.setText("TextView");
        binding.date5.setText("TextView");
        binding.date6.setText("TextView");
        binding.date7.setText("TextView");
        binding.imgDate1.setImageResource(R.drawable.ic_launcher_background);
        binding.imgWeather.setImageResource(R.drawable.ic_launcher_background);
        binding.imgDate2.setImageResource(R.drawable.ic_launcher_background);
        binding.imgDate3.setImageResource(R.drawable.ic_launcher_background);
        binding.imgDate4.setImageResource(R.drawable.ic_launcher_background);
        binding.imgDate5.setImageResource(R.drawable.ic_launcher_background);
        binding.imgDate6.setImageResource(R.drawable.ic_launcher_background);
        binding.imgDate7.setImageResource(R.drawable.ic_launcher_background);
    }
}