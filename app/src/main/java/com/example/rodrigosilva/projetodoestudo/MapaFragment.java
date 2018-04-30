package com.example.rodrigosilva.projetodoestudo;

import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;

import com.example.rodrigosilva.projetodoestudo.Data.PessoasDao;
import com.example.rodrigosilva.projetodoestudo.Model.Pessoas;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback {
    @Override
    public void onCreate(Bundle savedIntenceState) {
        super.onCreate(savedIntenceState);

        getMapAsync(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(GoogleMap googleMap) {

        LatLng MinhaPosicao = pegaCoordenada("Rua Anthêmios de Tralles,21,Vila Nova Mazzei,São Paulo");

        if (MinhaPosicao != null) {
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(MinhaPosicao, 17);
            googleMap.moveCamera(update);
        }
        PessoasDao dao = new PessoasDao(getContext());

        for(Pessoas lavaRapido : dao.buscaPessoa()){
            LatLng coordenada  = pegaCoordenada(lavaRapido.getEndereco());
            if(coordenada!=null){
                MarkerOptions marcador = new MarkerOptions();
                marcador.position(coordenada);
                marcador.title(lavaRapido.getNome());
                marcador.snippet(lavaRapido.getNota().toString());
                googleMap.addMarker(marcador);
            }
        }
        dao.close();
    }


    private LatLng pegaCoordenada(String endereco) {
        try {
            Geocoder geocoder = new Geocoder(getContext());

            List<Address> resultado = geocoder.getFromLocationName(endereco, 1);

            if (!resultado.isEmpty()) {
                LatLng posicao = new LatLng(resultado.get(0).getLatitude(), resultado.get(0).getLongitude());
                return posicao;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
