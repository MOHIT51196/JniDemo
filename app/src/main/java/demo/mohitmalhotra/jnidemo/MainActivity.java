package demo.mohitmalhotra.jnidemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import demo.mohitmalhotra.jnidemo.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'jnidemo' library on application startup.
    static {
        System.loadLibrary("jnidemo");
    }

    private ActivityMainBinding bind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        bind.btnPerform.setOnClickListener(v -> {
                try{
                    int num = Integer.parseInt(bind.etInput.getText().toString(), 10);
                    bind.tvMessage.setText("Result : " + getSquareRoot(num));
                } catch(Exception e) {
                    Log.e(this.getClass().getName(), "Error performing native operation due to " + e.getMessage());
                }
        });

        bind.btnPerformAsync.setOnClickListener(v -> {
            try{
                setSquareRootCallback(result -> {
                    Log.i(this.getClass().getName(), "Fetched the result in the callback successfully");
                    bind.tvMessage.setText("Result : " + result);
                });
                int num = Integer.parseInt(bind.etInput.getText().toString(), 10);
                getSquareRootAsync(num);
            } catch(Exception e) {
                Log.e(this.getClass().getName(), "Error performing native operation due to " + e.getMessage());
            }
        });
    }

    native private double getSquareRoot(int value);
    native private void getSquareRootAsync(int value);
    native private void setSquareRootCallback(SquareRootCallback callback);


    interface SquareRootCallback {
        void getResult(double result);
    }
}