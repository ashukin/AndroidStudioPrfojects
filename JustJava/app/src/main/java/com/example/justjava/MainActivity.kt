//package com.example.justjava
//
//
////import android.support.v7.app.AppCompatActivity
//
////import android.R
//import android.os.Bundle
//import android.view.View
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//import java.text.NumberFormat
//
////import com.example.justjava.R
///**
// * This app displays an order form to order coffee.
// */
//class MainActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//    }
//
//    /**
//     * This method is called when the order button is clicked.
//     */
//    fun submitOrder(view: View?) {
//        display(2)
//        displayPrice(2*5)
//    }
//
//    /**
//     * This method displays the given quantity value on the screen.
//     */
//    private fun display(number: Int) {
//        val quantityTextView =
//            findViewById<View>(R.id.quantity_text_view) as TextView
//        quantityTextView.text = "" + number
//    }
//    /**
//     * This method displays the given price on the screen.
//     */
//    private fun displayPrice(number: Int) {
//        val priceTextView = findViewById(R.id.price_text_view) as TextView
//        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number))
//    }
//}


package com.example.justjava;



import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        display(1);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}