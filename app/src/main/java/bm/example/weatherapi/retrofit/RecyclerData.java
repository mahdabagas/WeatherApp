package bm.example.weatherapi.retrofit;

import java.util.List;

public class RecyclerData {
    private float latitude;
    private float longitude;
    private CurrentWeather current_weather;
    private Daily daily;
    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public CurrentWeather getCurrent_weather() {
        return current_weather;
    }

    public void setCurrent_weather(CurrentWeather current_weather) {
        this.current_weather = current_weather;
    }

    public Daily getDaily() {
        return daily;
    }

    public void setDaily(Daily daily) {
        this.daily = daily;
    }

    public class CurrentWeather {
        private float temperature;
        private float windspeed;

        public float getTemperature() {
            return temperature;
        }

        public void setTemperature(float temperature) {
            this.temperature = temperature;
        }

        public float getWindspeed() {
            return windspeed;
        }

        public void setWindspeed(float windspeed) {
            this.windspeed = windspeed;
        }

    }
    
    public class Daily{
        private List<String> time;
        private List<Integer> weathercode;

        public List<String> getTime() {
            return time;
        }

        public void setTime(List<String> time) {
            this.time = time;
        }

        public List<Integer> getWeathercode() {
            return weathercode;
        }

        public void setWeathercode(List<Integer> weathercode) {
            this.weathercode = weathercode;
        }

    }
}
