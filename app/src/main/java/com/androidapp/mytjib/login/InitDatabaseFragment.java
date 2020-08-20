package com.androidapp.mytjib.login;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.androidapp.mytjib.R;
import com.androidapp.mytjib.admin_panel.Admin;
import com.androidapp.mytjib.admin_panel.venues.Venue;
import com.androidapp.mytjib.customer.Customer;
import com.androidapp.mytjib.customer.ShippingDetails;
import com.androidapp.mytjib.events.Event;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Fragment for database initialization screen
 * Used ONCE by admin before first launch of the app
 */

public class InitDatabaseFragment extends Fragment {

    private LoginViewModel mViewModel;
    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) { // set menu and inflate layout
        setHasOptionsMenu(false);
        return inflater.inflate(R.layout.init_db_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // set fields
        view = getView();
        mViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        mViewModel.createRepository();

        // set text to appear on screen
        TextView text = view.findViewById(R.id.db_init_text);
        text.setText("Please enter a password to initialize the database.\n**WARNING!**\n Do not initialize more than once.");

        // initialize the database when button is pressed
        Button button = view.findViewById(R.id.db_init_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authorizeInit();
            }
        });
    }

    public void authorizeInit() { // check if initialization is authorized
        // get the password entered to edit text
        EditText password = view.findViewById(R.id.db_init_password);
        String pass = password.getText().toString();
        // password is incorrect
        if(!(pass.equals("Tjib4711"))) Toast.makeText(getContext(), "Wrong password", Toast.LENGTH_SHORT).show();
        else { // password is correct
            initDb(); // start database initialization
            // create a dialog box which asks to wait for initialization to process
            final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
            alertDialog.setTitle("Alert");
            alertDialog.setMessage("");
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            alertDialog.show();
            new CountDownTimer(60000, 1000) { // one minute timer
                public void onTick(long millisUntilFinished) {
                    alertDialog.setMessage("Please allow the database to initialize.\nTime Remaining: " + millisUntilFinished / 1000);
                }
                public void onFinish() {
                    alertDialog.setMessage("done!");
                }
            }.start();
            Navigation.findNavController(view).navigate(R.id.loginFragment); // go back to login fragment
        }
    }

    public void initDb() { // set default data for initialization
        // arrays to hold the data
        List<Venue> venues = new ArrayList<>();
        List<Event> events = new ArrayList<>();
        List<String[]> seats = new ArrayList<>();
        List<Admin> admins = new ArrayList<>();
        List<Customer> customers = new ArrayList<>();
        List<ShippingDetails> shipping = new ArrayList<>();

        // set venues
        Venue venue1 = new Venue("Online", "Online", 1000000);
        Venue venue2 = new Venue("SK Olympic Handball Gymnasium", "Seoul", 5003);
        Venue venue3 = new Venue("Jamsil Olympic Stadium", "Seoul", 69950);
        Venue venue4 = new Venue("Jangchung Arena", "Seoul", 4507);
        Venue venue5 = new Venue("Universal Arts Center", "Seoul", 1200);
        Venue venue6 = new Venue("Busan Asiad Auxiliary Stadium", "Busan", 4549);
        Venue venue7 = new Venue("Namdong Gymnasium", "Incheon", 8828);

        venues.add(venue1);
        venues.add(venue2);
        venues.add(venue3);
        venues.add(venue4);
        venues.add(venue5);
        venues.add(venue6);
        venues.add(venue7);

        // set admins
        Admin admin1 = new Admin("interpark", "fixglobalsite2020", "tickethelp@interpark.com");
        Admin admin2 = new Admin("Melon", "thanks4mmm", "info@focuslive.kr");
        Admin admin3 = new Admin("Yes24", "stopyamails", "yesticket@yes24.com");

        admins.add(admin1);
        admins.add(admin2);
        admins.add(admin3);

        // set events
        Event event1 = new Event("MAMAMOO - 4 Seasons 4 Colors", "Live Concert", "https://i.ibb.co/vX5k3LB/event1-mamamoo.jpg",
                "SK Olympic Handball Gymnasium", "2020-10-21 ; 19:00-21:00");
        Event event2 = new Event("BTS - Love Yourself Tour", "Live Concert", "https://i.ibb.co/gF3xWNz/bts-love-yourself-seoul.jpg",
                "Jamsil Olympic Stadium", "2020-10-21 ; 18:00-20:30");
        Event event3 = new Event("DEAN - Sorry Not Sorry", "Fan Meeting", "https://www.ksoulmag.com/wp-content/uploads/2016/05/Dean.png",
                "Universal Arts Center", "2021-04-11 ; 17:00-19:00");
        Event event4 = new Event("ZICO - King of the Zungle", "Live Concert", "https://pbs.twimg.com/media/DrOjMd5WsAAqgwd.jpg",
                "SK Olympic Handball Gymnasium", "2021-02-09 ; 20:00-22:00");
        Event event5 = new Event("Show Me The Money - The Concert", "Live Concert", "https://i.pinimg.com/originals/87/34/37/873437cf9aa72dd9c1afacf55624dce4.jpg",
                "Busan Asiad Auxiliary Stadium", "2020-09-10 ; 18:00-21:30");
        Event event6 = new Event("Moonbyul - 1st Ontact Live", "Online Concert", "https://pbs.twimg.com/media/EX5__PRU0AAWRLa.jpg",
                "Online", "2020-09-15 ; 18:00-20:00");
        Event event7 = new Event("(G)I-DLE I-Land: Who Am I", "Online Concert", "https://image.kpopmap.com/2020/06/gidle-concert-poster-scaled.jpeg",
                "Online", "2020-09-15 ; 17:00-19:00");
        Event event8 = new Event("NCT127 - Neo City: The Origin", "Live Concert", "https://vignette.wikia.nocookie.net/kboy-group/images/d/d2/NEO_CITY_Seoul-The_Origin.jpg",
                "Jamsil Olympic Stadium", "2020-10-02 ; 18:00-20:30");
        Event event9 = new Event("HELLO! MOOMOO - MMM Asia FM", "Fan Meeting", "https://i0.wp.com/live.staticflickr.com/65535/32751175947_a824edab30_z.jpg",
                "Jangchung Arena", "2020-10-31 ; 17:00-19:00");
        Event event10 = new Event("SUNMI - WARNING! Fan Meeting", "Fan Meeting", "https://i.redd.it/bdvz0k9oapa21.jpg",
                "Universal Arts Center", "2020-10-12 ; 18:00-19:30");
        Event event11 = new Event("BTS - BangBang Con: The Live", "Online Concert", "https://vignette.wikia.nocookie.net/the-bangtan-boys/images/d/d5/Bang_Bang_Con_The_Live.jpg",
                "Online", "2020-09-15 ; 18:00-21:00");
        Event event12 = new Event("BTS - Burn The Stage: The ARMY Meeting", "Fan Meeting", "https://www.kpopmusic.co.uk/Images/2018/12/181206055703_29.jpg",
                "Jangchung Arena", "2020-11-15 ; 18:00-20:30");
        Event event13 = new Event("ATEEZ - The Expedition Tour", "Live Concert", "https://www.hellokpop.com/wp-content/uploads/2019/06/IMG_20190608_150424-724x1024.jpg",
                "Namdong Gymnasium", "2020-09-09 ; 15:30-17:30");
        Event event14 = new Event("SuperM - Beyond the Future", "Online Concert", "https://i.imgur.com/yTU8L6G.jpg",
                "Online", "2020-09-15 ; 18:30-21:00");
        Event event15 = new Event("GRAND * K-pop Festival", "Live Concert", "https://i.pinimg.com/originals/3a/31/8f/3a318fbeab96c20944c283f3df3e7235.jpg",
                "Busan Asiad Auxiliary Stadium", "2020-11-08 ; 17:00-21:00");
        Event event16 = new Event("Jay Park - 4EVA", "Fan Meeting", "https://loveshouldgoon.files.wordpress.com/2012/12/a9ga097cqaafdxi.jpg",
                "Jangchung Arena", "2020-12-21 ; 18:00-20:00");
        Event event17 = new Event("iKON - Showtime Tour", "Live Concert", "https://i.pinimg.com/originals/c7/fd/71/c7fd71b072d9abe634a08806513ea67d.jpg",
                "SK Olympic Handball Gymnasium", "2020-10-10 ; 18:00-20:00");
        Event event18 = new Event("WINNER - Everywhere Tour", "Online Concert", "https://static.wixstatic.com/media/584047_6b041afbb7c24ca881271ca79d3dfe17~mv2.jpg/v1/fill/w_630,h_931,al_c,q_85/sfx66011.webp",
                "Online", "2021-01-10 ; 18:30-20:30");
        Event event19 = new Event("BIGBANG - MADE Tour [Final]", "Live Concert", "https://kpoptown.com/shop298397/official%20goods/bigbang/bigbang_choker-1.jpg",
                "Jamsil Olympic Stadium", "2021-01-01 ; 18:00-21:00");
        Event event20 = new Event("Stray Kids - UNVEIL TOUR 'i am...'", "Live Concert", "https://pm1.narvii.com/7120/14f0730fa7dc3ab49539e7904cb1034fc07b7379r1-1202-1700v2_hq.jpg",
                "Namdong Gymnasium", "2020-10-02 ; 18:30-20:30");

        events.add(event1);
        events.add(event2);
        events.add(event3);
        events.add(event4);
        events.add(event5);
        events.add(event6);
        events.add(event7);
        events.add(event8);
        events.add(event9);
        events.add(event10);
        events.add(event11);
        events.add(event12);
        events.add(event13);
        events.add(event14);
        events.add(event15);
        events.add(event16);
        events.add(event17);
        events.add(event18);
        events.add(event19);
        events.add(event20);

        // set seats (tickets available for each event)
        // [event id] [number of tickets] [section] [price]
        seats.add(new String[]{"1", "81", "SITTING", "30000"});
        seats.add(new String[]{"1", "20", "STANDING", "25000"});
        seats.add(new String[]{"1", "10", "VIP", "40000"});
        seats.add(new String[]{"2", "100", "SITTING", "33000"});
        seats.add(new String[]{"2", "30", "STANDING", "28000"});
        seats.add(new String[]{"2", "15", "VIP", "43000"});
        seats.add(new String[]{"3", "49", "SITTING", "35000"});
        seats.add(new String[]{"4", "72", "SITTING", "30000"});
        seats.add(new String[]{"4", "15", "STANDING", "25000"});
        seats.add(new String[]{"5", "100", "STANDING", "19000"});
        seats.add(new String[]{"6", "40", "VIP", "15000"});
        seats.add(new String[]{"7", "40", "VIP", "17000"});
        seats.add(new String[]{"8", "64", "SITTING", "30000"});
        seats.add(new String[]{"8", "10", "STANDING", "25000"});
        seats.add(new String[]{"8", "5", "VIP", "40000"});
        seats.add(new String[]{"9", "64", "SITTING", "38000"});
        seats.add(new String[]{"10", "49", "SITTING", "33000"});
        seats.add(new String[]{"11", "90", "VIP", "22000"});
        seats.add(new String[]{"12", "81", "SITTING", "49000"});
        seats.add(new String[]{"13", "72", "SITTING", "30000"});
        seats.add(new String[]{"13", "15", "STANDING", "25000"});
        seats.add(new String[]{"13", "5", "VIP", "37000"});
        seats.add(new String[]{"14", "90", "VIP", "19000"});
        seats.add(new String[]{"15", "100", "SITTING", "10000"});
        seats.add(new String[]{"15", "30", "STANDING", "10000"});
        seats.add(new String[]{"16", "64", "SITTING", "39000"});
        seats.add(new String[]{"16", "10", "VIP", "49000"});
        seats.add(new String[]{"17", "81", "SITTING", "30000"});
        seats.add(new String[]{"17", "30", "STANDING", "27000"});
        seats.add(new String[]{"18", "75", "VIP", "15000"});
        seats.add(new String[]{"19", "100", "SITTING", "33000"});
        seats.add(new String[]{"19", "35", "STANDING", "28000"});
        seats.add(new String[]{"19", "15", "VIP", "43000"});
        seats.add(new String[]{"20", "72", "SITTING", "30000"});
        seats.add(new String[]{"20", "18", "STANDING", "25000"});

        // set customers
        Customer customer1 = new Customer("Serendipity", "jimminie@mochi.kr", "caughtinalie");
        Customer customer2 = new Customer("NayNay", "naya@rivera.star", "santanalopez");
        Customer customer3 = new Customer("Gdragon", "big@bang.yg", "oneofakind1");

        customers.add(customer1);
        customers.add(customer2);
        customers.add(customer3);

        // set shipping details for each customer
        ShippingDetails shipping1 = new ShippingDetails("Jimin", "Park", "50564736587", 1025, new ArrayList<Integer>());
        ShippingDetails shipping2 = new ShippingDetails("Naya", "Rivera", "5867749044", 1122, new ArrayList<Integer>());
        ShippingDetails shipping3 = new ShippingDetails("Ji-Yong", "Kwon", "53494674978", 1221, new ArrayList<Integer>());

        shipping.add(new ShippingDetails(shipping1, new ArrayList<>(Arrays.asList(4,5,6,161,162,192,193,260,355,356,357,392,400,587,588))));
        shipping.add(new ShippingDetails(shipping1, new ArrayList<>(Arrays.asList(655,666,667,717,1020,1021,854,855,856))));
        shipping.add(new ShippingDetails(shipping2, new ArrayList<>(Arrays.asList(45,46,170,262,263,404,405,915,916,944,945))));
        shipping.add(new ShippingDetails(shipping2, new ArrayList<>(Arrays.asList(1008,1028,1120,1267,1268,1333,1488,1492,1530))));
        shipping.add(new ShippingDetails(shipping3, new ArrayList<>(Arrays.asList(79,80,81,1035,680,1335,1336,131,132,900))));
        shipping.add(new ShippingDetails(shipping3, new ArrayList<>(Arrays.asList(1012,1540,1528,1481,862,1500,940))));
        shipping.add(new ShippingDetails(shipping3, new ArrayList<>(Arrays.asList(120,121,1423,363,402,403,721))));

        //  send the data to view model for initialization via repository
        mViewModel.initDatabase(venues, events, seats, admins, customers, shipping, getContext());
    }
}
