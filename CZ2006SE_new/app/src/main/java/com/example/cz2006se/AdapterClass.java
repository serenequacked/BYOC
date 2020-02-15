package com.example.cz2006se;

import android.content.Intent;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.MyGymHolder> implements Filterable {
    ArrayList<Gym> list;
    ArrayList<Gym> listFull;

    @Override
    public Filter getFilter() {
        return listFilter;
    }

    private Filter listFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Gym> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(listFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (Gym item : listFull) {
                    if (item.getAddress().toLowerCase().contains(filterPattern.toLowerCase()) || item.getName().toLowerCase().contains(filterPattern.toLowerCase())) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            list.clear();
            list.addAll((ArrayList) filterResults.values);
            notifyDataSetChanged();
        }
    };



    //use interface inside a viewholder class
    //use interface to detect the click

    public interface OnItemClickListener {
        void onItemClick(int position);
    }


    //listener variable
    private OnItemClickListener mOnItemClickListener;
//    private final OnItemClickListener listener;

    public AdapterClass(ArrayList<Gym> list, OnItemClickListener onItemClickListener)
    {

        this.list = list;
        listFull = new ArrayList<Gym>();
        this.mOnItemClickListener = onItemClickListener;
    }
    @NonNull
    @Override
    public MyGymHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.gym_holder,viewGroup,false);

        return new MyGymHolder(view, mOnItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyGymHolder myGymHolder, int i) {
        myGymHolder.id.setText(list.get(i).getName());
        myGymHolder.address.setText(list.get(i).getAddress());
        myGymHolder.openingHour.setText(list.get(i).getOpeningHour());
        myGymHolder.contact.setText("Contact: " + list.get(i).getContact());
        myGymHolder.latitude.setText(list.get(i).getLatitude());
        myGymHolder.longitude.setText(list.get(i).getLongitude());
        //url image link
        myGymHolder.image.setText(list.get(i).getImage());
        Picasso.get()
                .load(list.get(i).getImage()).into(myGymHolder.gym_image);
        Gym item = list.get(i);
        myGymHolder.bind(item,i);





    }

    @Override
    public int getItemCount() {

        int a ;

        if(list != null && !list.isEmpty()) {

            a = list.size();
        }
        else {

            a = 0;

        }

        return a;
    }


    class MyGymHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView id,address,openingHour,contact,latitude,longitude,image;
        ImageView gym_image;
        private Gym currentItem;
        private int j;

        OnItemClickListener onItemClickListener;

        public MyGymHolder(@NonNull View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            id = itemView.findViewById(R.id.gymId);
            address = itemView.findViewById(R.id.gymAddress);
            openingHour = itemView.findViewById(R.id.gymOpeningHours);
            contact = itemView.findViewById(R.id.gymContact);
            gym_image = itemView.findViewById(R.id.gymImage);
            latitude = itemView.findViewById(R.id.gymLatitude);
            longitude = itemView.findViewById(R.id.gymLongitude);
            image = itemView.findViewById(R.id.url);

            this.onItemClickListener = onItemClickListener;
            itemView.setOnClickListener(this);

        }
        void bind(Gym item,int currentIndex) {  //<--bind method allows the ViewHolder to bind to the data it is displaying
            currentItem = item;
            j = currentIndex;

//            currentItemName = currentItem.getName();
//            Log.d("CurrentitemName",currentItemName);//<-- keep a reference to the current item
        }

        @Override
        public void onClick(View view) {
            //call onItemListener
            int position = 0 ;
            Log.d("Current item Name,", (currentItem.getName()));
            Log.d("Current item Address,", (currentItem.getAddress()));
            Log.d("Current item Index,", String.valueOf(j));
            switch(currentItem.getName())
            {
                case "Anytime Fitness Jurong West":
                    position = 0;
                    break;

                case "Fitness Workz Gym":
                    position = 1;
                    break;

                case "Anytime Fitness Marine Parade":
                    position = 2;
                    break;

                case "Fitness First - Changi (UE Biz Hub)":
                    position = 3;
                    break;
                case "Anytime Fitness Pasir Ris E!Hub":
                    position = 4;
                    break;
                case "Contours Express Women's Gym Tampines":
                    position = 5;
                    break;
                case "Anytime Fitness Eastpoint Mall":
                    position = 6;
                    break;
                case "Anytime Fitness Northpoint City":
                    position = 7;
                    break;
                case "Anytime Fitness Woodlands":
                    position = 8;
                    break;
                case "Yishun ActiveSG Gym":
                    position = 9;
                    break;
                case "Fitness First - Junction 10":
                    position = 10;
                    break;
                case "Dennis Gym Jurong":
                    position = 11;
                    break;
                case "Choa Chu Kang ActiveSG Sports Centre":
                    position = 12;
                    break;
                case "Anytime Fitness, Telok Blangah":
                    position = 13;
                    break;
                case "Gym n Tonic":
                    position = 14;
                    break;
                case "Ritual Gym Robinson Road":
                    position = 15;
                    break;
                case "Fitness First - One Raffles Quay":
                    position = 16;
                    break;
                case "Virgin Active Tanjong Pagar":
                    position = 17;
                    break;
                case "Jurong East ActiveSG Gym":
                    position = 18;
                    break;
                case "Fitness First - Westgate":
                    position = 19;
                    break;
                case "Contours Express Women's Gym Jurong East":
                    position = 20;
                    break;
                case "SAFRA EnergyOne":
                    position = 21;
                    break;
                case "Anytime Fitness Taman Jurong":
                    position = 22;
                    break;
                case "Jurong West ActiveSG Sports Centre":
                    position = 23;
                    break;
                case "Anytime Fitness West Coast":
                    position = 24;
                    break;

            }


            onItemClickListener.onItemClick(position);

        }


    }
}