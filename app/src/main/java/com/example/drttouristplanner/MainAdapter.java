package com.example.drttouristplanner;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel,MainAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull MainModel model) {

        holder.trip_spot.setText(model.getTrip_spot());
        holder.trip_name.setText(model.getTrip_name());
        holder.trip_start.setText(model.getTrip_start());
        holder.trip_end.setText(model.getTrip_end());
        holder.trip_desc.setText(model.getTrip_desc());

        //img
        Glide.with(holder.img.getContext())
                .load(model.getTrip_desc())
                .placeholder(R.drawable.mountainbg)
                .circleCrop()
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .into(holder.img);


        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true,1700)
                        .create();

                //dialogPlus.show();

                View view1 = dialogPlus.getHolderView();

                EditText trip_spot = view1.findViewById(R.id.txtTripspot);
                EditText trip_name = view1.findViewById(R.id.txtTripname);
                EditText trip_start = view1.findViewById(R.id.txtTripstart);
                EditText trip_end = view1.findViewById(R.id.txtTripend);
                EditText trip_desc = view1.findViewById(R.id.txtTripdesc);

                Button btnUpdate = view1.findViewById(R.id.btnUpdate);


                trip_spot.setText(model.getTrip_spot());
                trip_name.setText(model.getTrip_name());
                trip_start.setText(model.getTrip_start());
                trip_end.setText(model.getTrip_end());
                trip_desc.setText(model.getTrip_desc());

                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Map<String,Object> map = new HashMap<>();
                        map.put("trip_spot", trip_spot.getText().toString());
                        map.put("trip_name", trip_name.getText().toString());
                        map.put("trip_start", trip_start.getText().toString());
                        map.put("trip_end", trip_end.getText().toString());
                        map.put("trip_desc", trip_desc.getText().toString());


                        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        FirebaseDatabase.getInstance().getReference(currentuser).child("trips")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.trip_name.getContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure( Exception e) {

                                        Toast.makeText(holder.trip_name.getContext(), "Error while updating", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });

                        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(holder.trip_name.getContext());
                                builder.setTitle("Are you sure?");
                                builder.setMessage("Deleted data will be pernanently gone.");

                                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        FirebaseDatabase.getInstance().getReference(currentuser).child("trips")
                                                .child(getRef(position).getKey()).removeValue();

                                    }
                                });
                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(holder.trip_name.getContext(), "Cancelled.", Toast.LENGTH_SHORT).show();

                                    }
                                });
                                builder.show();
                            }
                        });

                    }
                });


            }
        });



    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        CircleImageView img;
        TextView trip_spot, trip_name, trip_start, trip_end, trip_desc;

        Button btnEdit, btnDelete;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            img = (CircleImageView)itemView.findViewById(R.id.img1);
            trip_spot = (TextView)itemView.findViewById(R.id.tripspottext);
            trip_name = (TextView)itemView.findViewById(R.id.tripnametext);
            trip_start = (TextView)itemView.findViewById(R.id.tripstarttext);
            trip_end = (TextView)itemView.findViewById(R.id.tripendtext);
            trip_desc = (TextView)itemView.findViewById(R.id.tripdesctext);

            btnEdit = (Button) itemView.findViewById(R.id.btnEdit);
            btnDelete = (Button) itemView.findViewById(R.id.btnDelete);

        }
    }
}
