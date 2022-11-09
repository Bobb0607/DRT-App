package com.example.drttouristplanner;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Content_Kalawakan extends AppCompatActivity implements RecyclerViewInterface{

    ArrayList<ContentModel> caveContent = new ArrayList<>();
    ArrayList<ContentModel> hillContent = new ArrayList<>();
    ArrayList<ContentModel> fallContent = new ArrayList<>();
    ArrayList<ContentModel> mountainContent = new ArrayList<>();
    ArrayList<ContentModel> riverContent = new ArrayList<>();
    ArrayList<ContentModel> defaultContent = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content);

        // =================================
        // =========== Dito yung data
        caveContent.add( new ContentModel(
                "Patingan Cave",
                ". At the peak of Mt. Lumot, a candle-like monument was created to symbolize light.",
                new int[]{ R.drawable.pantingancave, R.drawable.pantingancave2, R.drawable.pantingancave3}
        ));
        fallContent.add( new ContentModel(
                "Talon ni Lukab",
                "considered the highest waterfall in DTR. Tourists can best appreciate tourists from September to late January..",
                new int[]{ R.drawable.talonlukab}
        ));

        fallContent.add( new ContentModel(
                "Talon ni Eba",
                "At the peak of Mt. Lumot, a candle-like monument was created to symbolize light.",
                new int[]{ R.drawable.talonnieba}
        ));


        fallContent.add( new ContentModel(
                "Talon ni Pedro",
                " the DRT's second-highest waterfall. Its cold waters are ideal for a refreshing dip. The water drops into a deep basin at the foot of Talon in Eva before flowing downstream.",
                new int[]{ R.drawable.talonnipedro}
        ));

        fallContent.add( new ContentModel(
                "Talon ni Zamora",
                "an ideal destination for swimming and picnic activities. From October to February, tourists can enjoy the area at its best.",
                new int[]{ R.drawable.talonnizamora}
        ));

        fallContent.add( new ContentModel(
                "Talon ni Pari",
                "has a natural body of water and has been a tourist destination because of its natural charm. Residents named the location after an older man caught with a woman in the area by his wife.",
                new int[]{ R.drawable.talonpari,R.drawable.talonpari2}
        ));

        hillContent.add( new ContentModel(
                "Tila Pilon Hills",
                ". At the peak of Mt. Lumot, a candle-like monument was created to symbolize light.",
                new int[]{ R.drawable.tilapilonhills,R.drawable.tilapilonhills2}
        ));

        mountainContent.add( new ContentModel(
                "Mt.Balistada",
                "It is reputed to be the highest point in the area from which to view the stunning Sierra Madre Mountain Ranges and other picturesque locations.",
                new int[]{ R.drawable.balistada, R.drawable.balistada2, R.drawable.balistada3}
        ));

        mountainContent.add( new ContentModel(
                "Mt.Manalmon",
                " There are several tunnels and beautiful, green plants atop Mount Manalmon. Despite being shorter than most mountains, it has a challenging path and a stunning vista at the summit.",
                new int[]{ R.drawable.mtmanalmon, R.drawable.mtmanalmon2}
        ));

       riverContent.add( new ContentModel(
                "Tangke River",
                ". At the peak of Mt. Lumot, a candle-like monument was created to symbolize light.",
                new int[]{ R.drawable.tangke,R.drawable.tangke2,R.drawable.tangke3}
        ));

        // DEFAULT
        defaultContent.add( new ContentModel(
                "OOPS",
                "This page is empty",
                new int[]{ R.drawable.talonlukab }
        ));




        // =================================
        // =========== Eto yung recycler view
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            RecyclerView rvContacts = (RecyclerView) this.findViewById(R.id.rvContent);
            ContentAdapter adapter;
            String data = extras.getString("target_data");
            switch (data) {
                case("cave"):
                    adapter = new ContentAdapter(caveContent,this);
                    break;
                case("fall"):
                    adapter = new ContentAdapter(fallContent,this);
                    break;
                case("hill"):
                    adapter = new ContentAdapter(hillContent,this);
                    break;
                case("mountain"):
                    adapter = new ContentAdapter(mountainContent,this);
                    break;
                case("river"):
                    adapter = new ContentAdapter(riverContent,this);
                    break;
                default:
                    //Default
                    adapter = new ContentAdapter(defaultContent,this);
            }
            rvContacts.setAdapter(adapter);
            rvContacts.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(Content_Kalawakan.this, CreateTrip.class);

        Bundle extras = getIntent().getExtras();
        String data = extras.getString("target_data");
        if (extras != null) {
            switch (data) {
                case ("cave"):
                    intent.putExtra("Title", caveContent.get(position).getTitle());
                    intent.putExtra("Image", caveContent.get(position).getImageAsInt());
                    startActivity(intent);
                    break;
                case("fall"):
                    intent.putExtra("Title", fallContent.get(position).getTitle());
                    startActivity(intent);
                    break;
                case ("hill"):
                    intent.putExtra("Title", hillContent.get(position).getTitle());
                    startActivity(intent);
                    break;
                case ("mountain"):
                    intent.putExtra("Title", mountainContent.get(position).getTitle());
                    startActivity(intent);
                    break;
                case ("river"):
                    intent.putExtra("Title", riverContent.get(position).getTitle());
                    startActivity(intent);
                    break;
            }
        }
    }
}