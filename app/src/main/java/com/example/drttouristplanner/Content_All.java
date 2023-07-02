

package com.example.drttouristplanner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Content_All extends AppCompatActivity implements RecyclerViewInterface, Serializable {

    ContentAdapter adapter;
    SearchView searchView;


    ArrayList<ContentModel> caveContent = new ArrayList<>();
    ArrayList<ContentModel> hillContent = new ArrayList<>();
    ArrayList<ContentModel> fallContent = new ArrayList<>();
    ArrayList<ContentModel> mountainContent = new ArrayList<>();
    ArrayList<ContentModel> infrastructureContent = new ArrayList<>();
    ArrayList<ContentModel> riverContent = new ArrayList<>();
    ArrayList<ContentModel> lakeContent = new ArrayList<>();
    ArrayList<ContentModel> springContent = new ArrayList<>();
    ArrayList<ContentModel> defaultContent = new ArrayList<>();
    ArrayList<ContentModel> campsiteContent = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycleview2);
        searchView = findViewById(R.id.searchView2);
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });




        caveContent.add(new ContentModel(
                "VvLL7YkuTZqcgIQ1WLKT",
                "14.956091357068864, 121.08980264086861",
                "bayabas",
                "cave",
                "Batong Palaka",
                "located in South. Brgy. Silid When viewed from atop Bayabas, Batong Palaka, the rock formation looks like a frog.",
                "Show Tourist Spot Information",
                "5.0",
                new int[]{R.drawable.batongpalaka, R.drawable.batongpalaka2}
        ));
        caveContent.add(new ContentModel(
                "m5tzfo2Z0K7aRZKsNRFD",
                "14.962362984827053, 121.09206889673732",
                "bayabas",
                "cave",
                "Puning Cave",
                "In addition to being spectacular and well-preserved, the cave features a spring that flows from a mountain.",
                "Show Tourist Spot Information",
                "5.0",
                new int[]{R.drawable.puningcave1, R.drawable.puningcave2, R.drawable.puningcave3}
        ));

        fallContent.add(new ContentModel(
                "bCAWDI2FRZwYONZuCY6P",
                "14.986410229194332, 121.08390061357753",
                "camachile",
                "fall",
                "Tung-tung Falls",
                "Tungtong Falls is a family-owned Resort with mesmerizing rock-formation fresh flowing water.",
                "Show Tourist Spot Information",
                "5.0",
                new int[]{R.drawable.tungtungfalls, R.drawable.tungtungfalls2, R.drawable.tungtungfalls3}
        ));

        infrastructureContent.add(new ContentModel(
                "cKyLiW6rNlKfFEr9rMGe",
                "14.99153691755392, 121.0660989259427",
                "camachile",
                "infrastructure",
                "Simbahang Bato",
                "is a rock formation resembling a church.",
                "Show Tourist Spot Information",
                "5.0",
                new int[]{R.drawable.simbahangbato, R.drawable.simbahangbato2, R.drawable.simbahangbato3}
        ));

        mountainContent.add(new ContentModel(
                "BpDdTBHYDXCN0U6T0ogW",
                "15.148461449894073, 120.97790017151091",
                "camachile",
                "mountan",
                "Mt. Puting Bato",
                "is a rock formation resembling a church.",
                "Show Tourist Spot Information",
                "5.0",new int[]{R.drawable.putingbato1, R.drawable.putingbato2, R.drawable.putingbato3}
        ));

        springContent.add(new ContentModel(
                "9szjXyp3rcOjz76ngH82",
                "15.005329299497376, 121.0602965205604",
                "camachile",
                "spring",
                "Malangaan Spring",
                "Malangaan Spring and Cave upon the creation of the Municipality of DRT in 1977, both tourist destinations became part of Barangay Camachile. Visitors can still enjoy the refreshing spring water, even during the summer.",
                "Show Tourist Spot Information",
                "5.0",
                new int[]{R.drawable.malangaanspring}
        ));

        fallContent.add(new ContentModel(
                "byZPLhQ9KWPWoI7f9fTw",
                "15.092696155312364, 121.13292650233358",
                "camachin",
                "fall",
                "Adarna Falls",
                "formerly known as 13 falls, Adarna got its name for it is once known to be the home of birds known by the locals as adarna.",
                "Show Tourist Spot Information",
                "5.0",
                new int[]{R.drawable.adarnafalls, R.drawable.adarnafalls2, R.drawable.adarnafalls3}
        ));
        fallContent.add(new ContentModel(
                "osTATY3gSlbywzsw7Qp9",
                "14.985858759377104, 121.09569085137673",
                "camachin",
                "fall",
                "Secret Falls",
                ", Secret Falls serves as one of the pioneer nature-based tourist destinations. Visitors enjoy the clear running water during September and February.",
                "Show Tourist Spot Information",
                "5.0",
                new int[]{R.drawable.secretfalls, R.drawable.secretfalls2, R.drawable.secretfalls3}
        ));

        infrastructureContent.add(new ContentModel(
                "EZnpXaGHu8nUaIrXFmtA",
                "15.06331948295018, 121.10681230579127",
                "camachin",
                "infrastructure",
                "Candle Monument",
                ". At the peak of Mt. Lumot, a candle-like monument was created to symbolize light.",
                "Show Tourist Spot Information",
                "5.0",
                new int[]{R.drawable.candlemonument}
        ));

        caveContent.add(new ContentModel(
                "0VCr9Rlh56ggnQccibPl",
                "14.962333017067388, 121.09206877642356",
                "kalawakan",
                "cave",
                "Patingan Cave",
                ". At the peak of Mt. Lumot, a candle-like monument was created to symbolize light.",
                "Show Tourist Spot Information",
                "5.0",new int[]{R.drawable.pantingancave, R.drawable.pantingancave2, R.drawable.pantingancave3}
        ));
        fallContent.add(new ContentModel(
                "HeHcFOe3wDGMRdTh4gQu",
                "15.261644846199083, 121.1605586741556",
                "kalawakan",
                "fall",
                "Talon ni Lukab",
                "considered the highest waterfall in DTR. Tourists can best appreciate tourists from September to late January..",
                "Show Tourist Spot Information",
                "5.0",
                new int[]{R.drawable.talonlukab}
        ));

        fallContent.add(new ContentModel(
                "O7jt3QfDtoRQZ0FVHGia",
                "15.184310197590628, 121.15540127539418",
                "kalawakan",
                "fall",
                "Talon ni Eba",
                "At the peak of Mt. Lumot, a candle-like monument was created to symbolize light.",
                "Show Tourist Spot Information",
                "5.0",
                new int[]{R.drawable.talonnieba}
        ));


        fallContent.add(new ContentModel(
                "KxWrpKmfMAXHMckF1tdF",
                "15.248525729195443, 121.13231385378181",
                "kalawakan",
                "fall",
                "Talon ni Pedro",
                " the DRT's second-highest waterfall. Its cold waters are ideal for a refreshing dip. The water drops into a deep basin at the foot of Talon in Eva before flowing downstream.",
                "Show Tourist Spot Information",
                "5.0",
                new int[]{R.drawable.talonnipedro}
        ));

        fallContent.add(new ContentModel(
                "cgO1FerWMorj26usJsgb",
                "15.177709148644746, 121.12590934545862",
                "kalawakan",
                "fall",
                "Talon ni Zamora",
                "an ideal destination for swimming and picnic activities. From October to February, tourists can enjoy the area at its best.",
                "Show Tourist Spot Information",
                "5.0",
                new int[]{R.drawable.talonnizamora}
        ));

        fallContent.add(new ContentModel(
                "o7MTnBB6ZtkCsKHdLV1x",
                "15.1566588217533, 121.11249678844672",
                "kalawakan",
                "fall",
                "Talon ni Pari",
                "has a natural body of water and has been a tourist destination because of its natural charm. Residents named the location after an older man caught with a woman in the area by his wife.",
                "Show Tourist Spot Information",
                "5.0",
                new int[]{R.drawable.talonpari, R.drawable.talonpari2}
        ));

        hillContent.add(new ContentModel(
                "gkkQNVGU2r4TxjxbvtVg",
                "15.122568409601364, 121.11353585852191",
                "kalawakan",
                "hill",
                "Tila Pilon Hills",
                ". At the peak of Mt. Lumot, a candle-like monument was created to symbolize light.",
                "Show Tourist Spot Information",
                "5.0",
                new int[]{R.drawable.tilapilonhills, R.drawable.tilapilonhills2}
        ));

        mountainContent.add(new ContentModel(
                "bJjCuu3kUR9nBSOzTxE8",
                "15.131772490837788, 121.09622728347215",
                "kalawakan",
                "mountain",
                "Mt.Balistada",
                "It is reputed to be the highest point in the area from which to view the stunning Sierra Madre Mountain Ranges and other picturesque locations.",
                "Show Tourist Spot Information",
                "5.0",
                new int[]{R.drawable.balistada, R.drawable.balistada2, R.drawable.balistada3}
        ));

        mountainContent.add(new ContentModel(
                "3sAvCb9r5m0omcDDDpYp",
                "15.162748055645585, 121.09103874992071",
                "kalawakan",
                "mountain",
                "Mt.Manalmon",
                " There are several tunnels and beautiful, green plants atop Mount Manalmon. Despite being shorter than most mountains, it has a challenging path and a stunning vista at the summit.",
                "Show Tourist Spot Information",
                "5.0",
                new int[]{R.drawable.mtmanalmon, R.drawable.mtmanalmon2}
        ));

        riverContent.add(new ContentModel(
                "b3ZXBuNZCekNi2oF4o9t",
                "14.874469979261416, 121.14461443168526",
                "kalawakan",
                "river",
                "Tangke River",
                ". At the peak of Mt. Lumot, a candle-like monument was created to symbolize light.",
                "Show Tourist Spot Information",
                "5.0",
                new int[]{R.drawable.tangke, R.drawable.tangke2, R.drawable.tangke3}
        ));

        lakeContent.add(new ContentModel(
                "mEPnKlmFqYZSXwj29tJR",
                "15.09938181902129, 121.11316564046777",
                "talbak",
                "lake",
                "Palanguyan",
                "At the peak of Mt. Lumot, a candle-like monument was created to symbolize light.",
                "Show Tourist Spot Information",
                "5.0",
                new int[]{R.drawable.palanguyan, R.drawable.palanguyan2, R.drawable.palanguyan3, R.drawable.palanguyan4}
        ));
        fallContent.add(new ContentModel(
                "zKffkLDDRaPxt2pAQq9p",
                "15.09938181902129, 121.11316564046777",
                "talbak",
                "fall",
                "Verdibia Falls",
                "A hidden beauty with a serene splendour.",
                "Show Tourist Spot Information",
                "5.0",
                new int[]{R.drawable.verdibia}
        ));

        mountainContent.add(new ContentModel(
                "bJjCuu3kUR9nBSOzTxE8",
                "15.131772490837788, 121.09622728347215",
                "talbak",
                "mountain",
                "Mt.Balistada",
                "It is reputed to be the highest point in the area from which to view the stunning Sierra Madre Mountain Ranges and other picturesque locations.",
                "Show Tourist Spot Information",
                "5.0",
                new int[]{R.drawable.balistada, R.drawable.balistada2, R.drawable.balistada3}
        ));

        mountainContent.add(new ContentModel(
                "lUoHXM30Ew3fQHVTQIg6",
                "15.113837846615471, 121.09686924789591",
                "talbak",
                "mountain",
                "Mt.Mavio",
                ". At the peak of Mt. Lumot, a candle-like monument was created to symbolize light.",
                "Show Tourist Spot Information",
                "5.0",
                new int[]{R.drawable.mtmavio, R.drawable.mtmavio2, R.drawable.mtmavio3}
        ));
        campsiteContent.add(new ContentModel(
                "0",
                "",
                "",
                "0",
                "OOPS",
                "This page is empty",
                "Show Tourist Spot Information",
                "5.0",
                new int[]{R.drawable.talonnieba}
        ));

        // DEFAULT
        defaultContent.add(new ContentModel(
                "0",
                "",
                "",
                "0",
                "OOPS",
                "This page is empty",
                "Show Tourist Spot Information",
                "5.0",
                new int[]{R.drawable.talonlukab}
        ));
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            RecyclerView rvContacts = (RecyclerView) this.findViewById(R.id.rvContent2);
            ContentAdapter adapter;
            String data = extras.getString("target_data");
            switch (data) {
                case ("cave"):
                    adapter = new ContentAdapter(caveContent, this);
                    break;
                case ("fall"):
                    adapter = new ContentAdapter(fallContent, this);
                    break;
                case ("campsite"):
                    adapter = new ContentAdapter(campsiteContent, this);
                    break;
                case ("hill"):
                    adapter = new ContentAdapter(hillContent, this);
                    break;
                case ("mountain"):
                    adapter = new ContentAdapter(mountainContent, this);
                    break;
                case ("river"):
                    adapter = new ContentAdapter(riverContent, this);
                    break;
                case ("spring"):
                    adapter = new ContentAdapter(springContent, this);
                    break;
                case ("lake"):
                    adapter = new ContentAdapter(lakeContent, this);
                    break;
                case ("infrastructure"):
                    adapter = new ContentAdapter(infrastructureContent, this);
                    break;
                default:
                    //Default
                    adapter = new ContentAdapter(defaultContent, this);
            }
            rvContacts.setAdapter(adapter);
            rvContacts.setLayoutManager(new LinearLayoutManager(this));
        }

    }


    private void filterList(String newText) {
        List<ContentModel> filteredList = new ArrayList<>();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            RecyclerView rvContacts = (RecyclerView) this.findViewById(R.id.rvContent2);
            ContentAdapter adapter;
            String data = extras.getString("target_data");
            switch (data) {
                case ("cave"):
                    adapter = new ContentAdapter(caveContent, this);
                    for (ContentModel item : caveContent) {
                        if (item.getTitle().toLowerCase().contains(newText.toLowerCase())) {
                            filteredList.add(item);
                        }
                    }
                    if (filteredList.isEmpty()) {
                        Toast.makeText(Content_All.this, "No result found", Toast.LENGTH_SHORT).show();
                    } else {
                        adapter.setFilteredList(filteredList);
                    }

                    break;
                case ("spring"):
                    adapter = new ContentAdapter(springContent, this);
                    for (ContentModel item : springContent) {
                        if (item.getTitle().toLowerCase().contains(newText.toLowerCase())) {
                            filteredList.add(item);
                        }
                    }
                    if (filteredList.isEmpty()) {
                        Toast.makeText(Content_All.this, "No result found", Toast.LENGTH_SHORT).show();
                    } else {
                        adapter.setFilteredList(filteredList);
                    }
                    break;
                case ("lake"):
                    adapter = new ContentAdapter(lakeContent, this);
                    for (ContentModel item : lakeContent) {
                        if (item.getTitle().toLowerCase().contains(newText.toLowerCase())) {
                            filteredList.add(item);
                        }
                    }
                    if (filteredList.isEmpty()) {
                        Toast.makeText(Content_All.this, "No result found", Toast.LENGTH_SHORT).show();
                    } else {
                        adapter.setFilteredList(filteredList);
                    }
                    break;
                case ("infrastructure"):
                    adapter = new ContentAdapter(infrastructureContent, this);
                    for (ContentModel item : infrastructureContent) {
                        if (item.getTitle().toLowerCase().contains(newText.toLowerCase())) {
                            filteredList.add(item);
                        }
                    }
                    if (filteredList.isEmpty()) {
                        Toast.makeText(Content_All.this, "No result found", Toast.LENGTH_SHORT).show();
                    } else {
                        adapter.setFilteredList(filteredList);
                    }
                    break;
                case ("fall"):
                    adapter = new ContentAdapter(fallContent, this);
                    for (ContentModel item : fallContent) {
                        if (item.getTitle().toLowerCase().contains(newText.toLowerCase())) {
                            filteredList.add(item);
                        }
                    }
                    if (filteredList.isEmpty()) {
                        Toast.makeText(Content_All.this, "No result found", Toast.LENGTH_SHORT).show();
                    } else {
                        adapter.setFilteredList(filteredList);
                    }
                    break;
                case ("hill"):
                    adapter = new ContentAdapter(hillContent, this);
                    for (ContentModel item : hillContent) {
                        if (item.getTitle().toLowerCase().contains(newText.toLowerCase())) {
                            filteredList.add(item);
                        }
                    }
                    if (filteredList.isEmpty()) {
                        Toast.makeText(Content_All.this, "No result found", Toast.LENGTH_SHORT).show();
                    } else {
                        adapter.setFilteredList(filteredList);
                    }
                    break;
                case ("mountain"):
                    adapter = new ContentAdapter(mountainContent, this);
                    for (ContentModel item : mountainContent) {
                        if (item.getTitle().toLowerCase().contains(newText.toLowerCase())) {
                            filteredList.add(item);
                        }
                    }
                    if (filteredList.isEmpty()) {
                        Toast.makeText(Content_All.this, "No result found", Toast.LENGTH_SHORT).show();
                    } else {
                        adapter.setFilteredList(filteredList);
                    }
                    break;
                case ("river"):
                    adapter = new ContentAdapter(riverContent, this);
                    for (ContentModel item : riverContent) {
                        if (item.getTitle().toLowerCase().contains(newText.toLowerCase())) {
                            filteredList.add(item);
                        }
                    }
                    if (filteredList.isEmpty()) {
                        Toast.makeText(Content_All.this, "No result found", Toast.LENGTH_SHORT).show();
                    } else {
                        adapter.setFilteredList(filteredList);
                    }
                    break;
                case ("campsite"):
                    adapter = new ContentAdapter(campsiteContent, this);
                    for (ContentModel item : campsiteContent) {
                        if (item.getTitle().toLowerCase().contains(newText.toLowerCase())) {
                            filteredList.add(item);
                        }
                    }
                    if (filteredList.isEmpty()) {
                        Toast.makeText(Content_All.this, "No result found", Toast.LENGTH_SHORT).show();
                    } else {
                        adapter.setFilteredList(filteredList);
                    }
                    break;
                default:
                    //Default
                    adapter = new ContentAdapter(defaultContent, this);

            }

            if (filteredList.isEmpty()) {
                Toast.makeText(Content_All.this, "No result found", Toast.LENGTH_SHORT).show();
            } else {
                adapter.setFilteredList(filteredList);
            }

            rvContacts.setAdapter(adapter);
            rvContacts.setLayoutManager(new LinearLayoutManager(this));

        }
    }

    @Override
    public void onItemClick(int position) {

        Intent intent = new Intent(Content_All.this, CreateTrip.class);

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
                case ("infrastructure"):
                    intent.putExtra("Title", infrastructureContent.get(position).getTitle());
                    startActivity(intent);
                    break;
                case ("river"):
                    intent.putExtra("Title", riverContent.get(position).getTitle());
                    startActivity(intent);
                    break;
                case ("lake"):
                    intent.putExtra("Title", lakeContent.get(position).getTitle());
                    startActivity(intent);
                    break;
                case ("spring"):
                    intent.putExtra("Title", springContent.get(position).getTitle());
                    startActivity(intent);
                    break;

/*
        intent.putExtra("Image", caveContent.get(position).getImageAsInt());
        intent.putExtra("Image", fallContent.get(position).getImageAsInt());
        intent.putExtra("Image", hillContent.get(position).getImageAsInt());
        intent.putExtra("Image", mountainContent.get(position).getImageAsInt());
        intent.putExtra("Image", infrastructureContent.get(position).getImageAsInt());
        intent.putExtra("Image", riverContent.get(position).getImageAsInt());
        intent.putExtra("Image", lakeContent.get(position).getImageAsInt());
        intent.putExtra("Image", springContent.get(position).getImageAsInt());*/

            }
        }
    }
}

