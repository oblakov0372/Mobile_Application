package com.example.studytracker;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment {

    private class LocationInfo {
        int buttonId;     // ID кнопки
        String geoCoords; // Координаты

        public LocationInfo(int buttonId, String geoCoords) {
            this.buttonId = buttonId;
            this.geoCoords = geoCoords;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Массив с информацией о кнопках и координатах
        LocationInfo[] locations = new LocationInfo[] {
                new LocationInfo(R.id.novaSgradaButton, "42.15941028222238,24.71348542804354"),
                new LocationInfo(R.id.rectoratButton, "42.13839745581086,24.75111983163681"),
                new LocationInfo(R.id.buttonBiologicalFaculty, "42.14733603634424, 24.750789046217253"),
                new LocationInfo(R.id.buttonTechnologyCenter, "42.13672791185112, 24.750034626984565"),
                new LocationInfo(R.id.buttonMolecularBiologyCenter, "42.1433639550902, 24.76814772883522"),
                new LocationInfo(R.id.buttonTrainingHall, "42.1566367453607, 24.715214915343616"),
                new LocationInfo(R.id.buttonSportsComplex, "42.12804439831305, 24.766018576718103")
        };

        // Установка обработчиков для всех кнопок
        for (LocationInfo location : locations) {
            Button button = view.findViewById(location.buttonId);
            button.setOnClickListener(v -> openMap(location.geoCoords));
        }

        return view;
    }

    private void openMap(String geoLocation) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/dir/?api=1&destination=" + geoLocation + "&travelmode=driving"));
        startActivity(intent);
    }
}
