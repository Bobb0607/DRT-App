package com.example.drttouristplanner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton10);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(Content_Kalawakan.this);
                builder.setMessage("1.Tap on View Route to show location in maps \n" +
                                "2.Tap Create Trip to proceed to create trip for the tourist spot.")
                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });
                // Create the AlertDialog object and return it
                androidx.appcompat.app.AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        caveContent.add( new ContentModel(
                "0VCr9Rlh56ggnQccibPl",
                "14.962333017067388, 121.09206877642356",
                "kalawakan",
                "cave",
                "Patingan Cave",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vestibulum sollicitudin est ex, a faucibus tellus maximus eget. Vivamus elementum tortor quis commodo posuere.",
                "Show Tourist Spot Information",
                "5.0",
                new int[]{ R.drawable.pantingancave, R.drawable.pantingancave2, R.drawable.pantingancave3}
        ));
        fallContent.add( new ContentModel(
                "HeHcFOe3wDGMRdTh4gQu",
                "15.261644846199083, 121.1605586741556",
                "kalawakan",
                "fall",
                "Talon ni Lukab",
                "Considered the highest waterfall in DTR. Tourists can best appreciate tourists from September to late January..",
                "Show Tourist Spot Information",
                "4.0",
                new int[]{ R.drawable.talonlukab}
        ));

        fallContent.add( new ContentModel(
                "O7jt3QfDtoRQZ0FVHGia",
                "15.184310197590628, 121.15540127539418",
                "kalawakan",
                "fall",
                "Talon ni Eba",
                "Talon ni Eva is the second highest waterfall in Dona Remedios Trinidad. Its water cascades at a height of 30 meters into a deep basin. Contrary to usual perception, the name Eva is the shortened version of the male name Evaristo.",
                "Show Tourist Spot Information",
                "4.5",
                new int[]{ R.drawable.talonnieba}
        ));


        fallContent.add( new ContentModel(
                "KxWrpKmfMAXHMckF1tdF",
                "15.248525729195443, 121.13231385378181",
                "kalawakan",
                "fall",
                "Talon ni Pedro",
                "Its cold waters are ideal for a refreshing dip. The water drops into a deep basin at the foot of Talon in Eva before flowing downstream.",
                "Show Tourist Spot Information",
                "5.0",
                new int[]{ R.drawable.talonnipedro}
        ));

        fallContent.add( new ContentModel(
                "cgO1FerWMorj26usJsgb",
                "15.177709148644746, 121.12590934545862",
                "kalawakan",
                "fall",
                "Talon ni Zamora",
                "An ideal destination for swimming and picnic activities. From October to February, tourists can enjoy the area at its best.",
                "Show Tourist Spot Information",
                "4.8",
                new int[]{ R.drawable.talonnizamora}
        ));

        fallContent.add( new ContentModel(
                "o7MTnBB6ZtkCsKHdLV1x",
                "15.1566588217533, 121.11249678844672",
                "kalawakan",
                "AhQSqN1ibzSAEXBBtr90",
                "Talon ni Pari",
                "Has a natural body of water and has been a tourist destination because of its natural charm. Residents named the location after an older man caught with a woman in the area by his wife.",
                "Show Tourist Spot Information",
                "5.0",
                new int[]{ R.drawable.talonpari,R.drawable.talonpari2}
        ));

        hillContent.add( new ContentModel(
                "gkkQNVGU2r4TxjxbvtVg",
                "15.122568409601364, 121.11353585852191",
                "kalawakan",
                "hill",
                "Tila Pilon Hills",
                "Tila Pilon Hills is a 180-meter-high mountain with a 360-degree view of lush forests and the Sierra Madre mountain range. It’s a truly breathtaking view.",
                "Show Tourist Spot Information",
                "5.0",
                new int[]{ R.drawable.tila1,R.drawable.tila2,R.drawable.tila3}
        ));


        mountainContent.add( new ContentModel(
                "3sAvCb9r5m0omcDDDpYp",
                "15.162748055645585, 121.09103874992071",
                "kalawakan",
                "mountain",
                "Mt.Manalmon",
                " There are several tunnels and beautiful, green plants atop Mount Manalmon. Despite being shorter than most mountains, it has a challenging path and a stunning vista at the summit.",
                "Show Tourist Spot Information",
                "5.0",
                new int[]{ R.drawable.mtmanalmon, R.drawable.mtmanalmon2}
        ));

       riverContent.add( new ContentModel(
               "b3ZXBuNZCekNi2oF4o9t",
               "15.163611, 121.106667",
               "kalawakan",
               "river",
                "Tangke River",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed lectus dui, rhoncus eu blandit in, sollicitudin sit amet eros. Aenean vitae neque leo. Interdum et malesuada fames ac ante ipsum primis in faucibus.",
               "Show Tourist Spot Information",
               "5.0",
               new int[]{ R.drawable.tangke,R.drawable.tangke2,R.drawable.tangke4}
        ));

        // DEFAULT
        defaultContent.add( new ContentModel(
                "0",
                "",
                "",
                "0",
                "OOPS",
                "This page is empty",
                "Show Tourist Spot Information",
                "5.0",
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
                    intent.putExtra("place_id", caveContent.get(position).getPlaceID());
                    startActivity(intent);
                    break;
                case("fall"):
                    intent.putExtra("Title", fallContent.get(position).getTitle());
                    intent.putExtra("place_id", fallContent.get(position).getPlaceID());
                    startActivity(intent);
                    break;
                case ("hill"):
                    intent.putExtra("Title", hillContent.get(position).getTitle());
                    intent.putExtra("place_id", hillContent.get(position).getPlaceID());
                    startActivity(intent);
                    break;
                case ("mountain"):
                    intent.putExtra("Title", mountainContent.get(position).getTitle());
                    intent.putExtra("place_id",mountainContent.get(position).getPlaceID());
                    startActivity(intent);
                    break;
                case ("river"):
                    intent.putExtra("Title", riverContent.get(position).getTitle());
                    intent.putExtra("place_id", riverContent.get(position).getPlaceID());
                    startActivity(intent);
                    break;
            }
        }
    }
}