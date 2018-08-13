package org.unibl.etf.sni.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.unibl.etf.sni.authenticator.R;
import org.unibl.etf.sni.model.Document;
import org.unibl.etf.sni.model.DrivingLicenceCategoryDto;
import org.unibl.etf.sni.model.DrivingLicenceDto;
import org.unibl.etf.sni.model.IdentityCardDto;
import org.unibl.etf.sni.model.PassportDto;
import org.unibl.etf.sni.rest.beans.DocumentsBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DocumentAdapter extends RecyclerView.Adapter<DocumentAdapter.ViewHolder> {
    private List<Document> documents =new ArrayList<>();
    private DocumentsBean documentBean;
    private Activity activity;
    public DocumentAdapter(DocumentsBean documentBean, Activity activity) {
        this.documentBean=documentBean;
        for(DrivingLicenceDto dl:documentBean.getDrivingLicences()){
            documents.add(dl);
        }
        for(PassportDto pt:documentBean.getPassports()){
            documents.add(pt);
        }
        for(IdentityCardDto id:documentBean.getIdentityCards()){
            documents.add(id);
        }
        this.activity=activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v = inflater.inflate(R.layout.card_document, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    private static final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Document item = documents.get(position);

        if(item instanceof PassportDto){
            holder.type.setText("PASSPORT");

        }else if(item instanceof  IdentityCardDto){
            holder.type.setText("IDENTITY CARD");
        }else{
            holder.type.setText("DRIVING LICENCE");
        }
        holder.validUntil.setText("Valid until: "+sdf.format(item.getValidUntil()));
        holder.validFrom.setText("Valid from: "+sdf.format(item.getValidFrom()));


        if(!(item instanceof DrivingLicenceDto)){
            holder.a.setVisibility(View.GONE);
            holder.a1.setVisibility(View.GONE);
            holder.b.setVisibility(View.GONE);
            holder.b1.setVisibility(View.GONE);
            holder.c.setVisibility(View.GONE);
            holder.d.setVisibility(View.GONE);
            holder.d1.setVisibility(View.GONE);
            holder.be.setVisibility(View.GONE);
            holder.c1e.setVisibility(View.GONE);
            holder.ce.setVisibility(View.GONE);
            holder.de.setVisibility(View.GONE);
            holder.c1.setVisibility(View.GONE);
        }else{
            holder.a.setVisibility(View.VISIBLE);
            holder.a1.setVisibility(View.VISIBLE);
            holder.b.setVisibility(View.VISIBLE);
            holder.b1.setVisibility(View.VISIBLE);
            holder.c.setVisibility(View.VISIBLE);
            holder.d.setVisibility(View.VISIBLE);
            holder.d1.setVisibility(View.VISIBLE);
            holder.be.setVisibility(View.VISIBLE);
            holder.c1e.setVisibility(View.VISIBLE);
            holder.ce.setVisibility(View.VISIBLE);
            holder.de.setVisibility(View.VISIBLE);
            holder.c1.setVisibility(View.VISIBLE);
            DrivingLicenceDto dld=(DrivingLicenceDto)item;
                for(DrivingLicenceCategoryDto category:dld.getCategories()) {
                    if ("A".equals(category.getCategory().getCategory())) {
                        holder.a.setText("A: " + sdf.format(category.getValidFrom()));
                    } else if ("B".equals(category.getCategory().getCategory())) {
                        holder.b.setText("B: " + sdf.format(category.getValidFrom()));
                    } else if ("C".equals(category.getCategory().getCategory())) {
                        holder.c.setText("C: " + sdf.format(category.getValidFrom()));
                    } else if ("A1".equals(category.getCategory().getCategory())) {
                        holder.a1.setText("A1: " + sdf.format(category.getValidFrom()));
                    } else if ("B1".equals(category.getCategory().getCategory())) {
                        holder.b1.setText("B1: " + sdf.format(category.getValidFrom()));
                    } else if ("C1".equals(category.getCategory().getCategory())) {
                        holder.c1.setText("C1: " + sdf.format(category.getValidFrom()));
                    } else if ("D1".equals(category.getCategory().getCategory())) {
                        holder.d1.setText("D1: " + sdf.format(category.getValidFrom()));
                    } else if ("D".equals(category.getCategory().getCategory())) {
                        holder.d.setText("D: " + sdf.format(category.getValidFrom()));
                    } else if ("BE".equals(category.getCategory().getCategory())) {
                        holder.be.setText("BE: " + sdf.format(category.getValidFrom()));
                    } else if ("C1E".equals(category.getCategory().getCategory())) {
                        holder.c1e.setText("C1E: " + sdf.format(category.getValidFrom()));
                    } else if ("CE".equals(category.getCategory().getCategory())) {
                        holder.ce.setText("CE: " + sdf.format(category.getValidFrom()));
                    } else {
                        holder.de.setText("DE: " + sdf.format(category.getValidFrom()));
                    }
                }
        }
        holder.serial.setText("Serial :"+item.getSerial());
        holder.name.setText("Name:"+item.getUser().getName());
        holder.surname.setText("Surname:"+item.getUser().getSurname());
        holder.residence.setText("Residence:"+item.getUser().getResidence());
        holder.birthplace.setText("Birth place:"+item.getUser().getPlaceOfBirth());
        holder.birthdate.setText("Birth date:"+sdf.format(item.getUser().getDateOfBirth()));
        holder.sex.setText("Sex :"+(item.getUser().getSex()?"Male":"Female"));
        holder.pid.setText("Person ID:"+item.getUser().getPid());


    }

    public void add(int position, Document item) {
        documents.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        documents.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return documents.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView serial;
        public TextView type;
        public TextView name;
        public TextView surname;
        public TextView pid;
        public TextView birthdate;
        public TextView birthplace;
        public TextView residence;
        public TextView validFrom;
        public TextView validUntil;
        public TextView sex;
        public TextView a;
        public TextView b;
        public TextView c;
        public TextView d;
        public TextView a1;
        public TextView b1;
        public TextView c1;
        public TextView d1;
        public TextView be;
        public TextView c1e;
        public TextView ce;
        public TextView de;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            serial=v.findViewById(R.id.serial);
            type=v.findViewById(R.id.type);
            name=v.findViewById(R.id.name);
            surname=v.findViewById(R.id.surname);
            pid=v.findViewById(R.id.pid);
            birthdate=v.findViewById(R.id.birthDate);
            birthplace=v.findViewById(R.id.birthPlace);
            residence=v.findViewById(R.id.residence);
            sex=v.findViewById(R.id.sex);
            a=v.findViewById(R.id.a);
            b=v.findViewById(R.id.b);
            c=v.findViewById(R.id.c);
            b1=v.findViewById(R.id.b1);
            d=v.findViewById(R.id.d);
            a1=v.findViewById(R.id.a1);
            validFrom=v.findViewById(R.id.validFrom);
            validUntil=v.findViewById(R.id.validUntil);
            be=v.findViewById(R.id.be);
            d1=v.findViewById(R.id.d1);
            c1=v.findViewById(R.id.c1);
            c1e=v.findViewById(R.id.c1e);
            de=v.findViewById(R.id.de);
            ce=v.findViewById(R.id.ce);
        }



    }
}
