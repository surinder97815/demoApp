package com.enest.pc_68.pathfinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLngBounds;

public class CustomPlaceAutoCompleteFragment extends PlaceAutocompleteFragment {

    private EditText editSearch;
    private View zzbnl;
    private View zzbnm;
    private boolean zzbno;
    @Nullable
    private LatLngBounds zzbnp;
    @Nullable
    private AutocompleteFilter zzbnq;
    @Nullable
    private PlaceSelectionListener zzbnr;

    public CustomPlaceAutoCompleteFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_custom_place_auto_complete, container, false);
        editSearch = (EditText)view.findViewById(R.id.editWorkLocation);
        editSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zzJk();
            }
        });
        return view;
    }

    public void onDestroyView() {
        this.zzbnl = null;
        this.zzbnm = null;
        this.editSearch = null;
        super.onDestroyView();
    }

    public void setBoundsBias(@Nullable LatLngBounds var1) {
        this.zzbnp = var1;
    }

    public void setFilter(@Nullable AutocompleteFilter var1) {
        this.zzbnq = var1;
    }

    public void setText(CharSequence var1) {
        this.editSearch.setText(var1);
        this.zzJj();
    }

    public void setHint(CharSequence var1) {
        this.editSearch.setHint(var1);
        this.zzbnl.setContentDescription(var1);
    }

    public void setOnPlaceSelectedListener(PlaceSelectionListener var1) {
        this.zzbnr = var1;
    }

    private void zzJj() {
        boolean var1 = !this.editSearch.getText().toString().isEmpty();
        //this.zzbnm.setVisibility(var1?0:8);
    }




    private void zzJk() {
        int var1 = -1;

        try {
            Intent var2 = (new PlaceAutocomplete.IntentBuilder(2)).setBoundsBias(this.zzbnp).setFilter(this.zzbnq).zzeZ(this.editSearch.getText().toString()).zzlc(1).build(this.getActivity());
            this.zzbno = true;
            this.startActivityForResult(var2, 30421);
        } catch (GooglePlayServicesRepairableException var3) {
            var1 = var3.getConnectionStatusCode();
            Log.e("Places", "Could not open autocomplete activity", var3);
        } catch (GooglePlayServicesNotAvailableException var4) {
            var1 = var4.errorCode;
            Log.e("Places", "Could not open autocomplete activity", var4);
        }

        if(var1 != -1) {
            GoogleApiAvailability var5 = GoogleApiAvailability.getInstance();
            var5.showErrorDialogFragment(this.getActivity(), var1, 30422);
        }

    }

    public void onActivityResult(int var1, int var2, Intent var3) {
        this.zzbno = false;
        if(var1 == 30421) {
            if(var2 == -1) {
                Place var4 = PlaceAutocomplete.getPlace(this.getActivity(), var3);
                if(this.zzbnr != null) {
                    this.zzbnr.onPlaceSelected(var4);
                }

                this.setText(var4.getName().toString());
            } else if(var2 == 2) {
                Status var5 = PlaceAutocomplete.getStatus(this.getActivity(), var3);
                if(this.zzbnr != null) {
                    this.zzbnr.onError(var5);
                }
            }
        }

        super.onActivityResult(var1, var2, var3);
    }

}
