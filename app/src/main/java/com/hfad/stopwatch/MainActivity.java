package com.hfad.stopwatch;

import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.os.Bundle;
import android.widget.TextView;
import java.util.Locale;

//В переменных seconds, running и wasRunning хранится соответственно количество прошедших секунд,
// флаг отсчета времени и флаг отсчета времени до приостановки активности.
public class MainActivity extends Activity {
    //Количество секунд на секундомере
    private int seconds = 0;
    //Проверка работоспособности секундомера
    private boolean running;
    private boolean wasRunning;

    // Получить предыдущее состояние секундомера, если активность была уничтожена и будет создана заново
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState !=null) {
            seconds=savedInstanceState.getInt("seconds");
            running=savedInstanceState.getBoolean("running");
            wasRunning=savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
    }

    // Сохранить состояние секудномера, если он готовится к уничтожению
    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }
    //Если активность останавливается- ОСТАНОВИТЬ ОТСЧЕТ ВРЕМЕНИ
    @Override
    protected void onPause() {
        super.onPause();
        wasRunning=running;
        running=false;
    }
    //Если активность возобновляется- запустить отсчет, если он был запущен РАНЕЕ
    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running=true; }
    }

    public void onClickStart(View view) {
        running = true;
    }

    public void onClickStop(View view) {
        running = false;
    }

    public void onClickReset(View view) {
        running = false;
        seconds = 0;
    }

    //Обновление показателя счетчика
    //Метод runTimer() использует объект Handler для увеличения числа секунд и обновления надписи.
    private void runTimer() {
        final TextView timeView = (TextView) findViewById(R.id.просмотр_времени);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int минуты = (seconds / 3600) % 60;
                int секунды = seconds % 60;
                String время = String.format(Locale.getDefault(),
                        "%02d:%02d",минуты,секунды);
                timeView.setText(время);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }

        });
    }
    }