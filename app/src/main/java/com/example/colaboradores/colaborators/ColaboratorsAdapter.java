package com.example.colaboradores.colaborators;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.colaboradores.R;
import com.example.colaboradores.model.Colaborator;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ColaboratorsAdapter extends RecyclerView.Adapter<ColaboratorsAdapter.ColaboratorViewHolder> {

    private Context context;
    private List<Colaborator> colaboratorList;
    private ColaboratorsCallback colaboratorsCallback;

    public ColaboratorsAdapter(Context context,ColaboratorsCallback colaboratorsCallback, List<Colaborator> colaboratorList1) {
        this.context = context;
        this.colaboratorsCallback = colaboratorsCallback;
        this.colaboratorList = colaboratorList1;
    }


    @NonNull
    @Override
    public ColaboratorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_colaborator, parent, false);
        return new ColaboratorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ColaboratorViewHolder holder, int position) {
        Colaborator colaborator = colaboratorList.get(position);
        if (colaborator != null){
            holder.setColaborator(colaborator);
            holder.tvColaboratorName.setText(colaborator.getName());
        }
    }


    @Override
    public int getItemCount() {
        return colaboratorList.size();
    }


    public class ColaboratorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.item_colaborator_name)
        TextView tvColaboratorName;
        @BindView(R.id.item_colaborator_card)
        CardView crvColaborator;

        Colaborator colaborator;

        public Colaborator getColaborator() {
            return colaborator;
        }

        public void setColaborator(Colaborator colaborator) {
            this.colaborator = colaborator;
        }

        private View.OnClickListener onColaboratorClickListener = new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                colaboratorsCallback.onItemClick(getColaborator());

            }
        };



        public ColaboratorViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            crvColaborator.setOnClickListener(onColaboratorClickListener);

            //btnProgrammedPayment.setOnClickListener(this);

        }


        @Override
        public void onClick(View view) {
            switch (view.getId()){
//                case R.id.btnProgrammedPayment:
//                    colaboratorsCallback.programmedPayment(getValuation());
//                    break;

            }
        }
    }
}