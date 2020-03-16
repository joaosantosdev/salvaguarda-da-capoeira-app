package br.com.jovemdeveloper.salvaguardadacapoeira.view;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import br.com.jovemdeveloper.salvaguardadacapoeira.R;
import br.com.jovemdeveloper.salvaguardadacapoeira.controller.OperacaoRoda;

import java.io.IOException;
import java.util.List;

public class Mapa extends SupportMapFragment implements OnMapReadyCallback {

    public static GoogleMap mMap;
    public OperacaoRoda or;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        or = new OperacaoRoda(getContext());
        getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {

            mMap = googleMap;
            mMap.getUiSettings().setZoomControlsEnabled(true);


            CameraPosition cp = new CameraPosition.Builder().zoom(7).target(new LatLng(-5.262458, -39.539827)).build();


            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cp));
            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    TextView txtView = new TextView(getContext());
                    txtView.setBackgroundResource(R.color.centerColor);
                    txtView.setText(Html.fromHtml("<h3>" + marker.getTitle() + "</h3>"));

                    txtView.setHeight(50);
                    txtView.setTextColor(Color.WHITE);
                    return txtView;
                }

                @Override
                public View getInfoContents(Marker marker) {

                    return null;
                }
            });

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    return false;
                }
            });
            or.listar(mMap, null);

        } catch (Exception e) {

        }
    }

    public void searchLocation(String location, int d) {

        Geocoder gc = new Geocoder(getActivity());

        try {

            List<Address> address = gc.getFromLocationName(location, 1);
            if (address.size() > 0) {
                CameraPosition cp = new CameraPosition.Builder().zoom(d).target(new LatLng(address.get(0).getLatitude(), address.get(0).getLongitude())).build();
                mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cp));
            } else {
                Toast.makeText(getContext(), "Roda não encontrada", Toast.LENGTH_SHORT).show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Roda não encontrada", Toast.LENGTH_SHORT).show();
        }


    }

    public static void verNoMapa(LatLng latLng) {


        CameraPosition cp = new CameraPosition.Builder().zoom(16).target(latLng).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cp));


    }


}