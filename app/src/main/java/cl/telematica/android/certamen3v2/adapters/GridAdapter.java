package cl.telematica.android.certamen3v2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

import cl.telematica.android.certamen3v2.R;
import cl.telematica.android.certamen3v2.interfaces.CardSelectedListener;
import cl.telematica.android.certamen3v2.models.CardModel;

/**
 * Created by franciscocabezas on 11/24/16.
 */

public class GridAdapter extends ArrayAdapter<CardModel> {

    CardSelectedListener listener;
    LayoutInflater mInflater;
    int mCount;

    public GridAdapter(Context context, int textViewResourceId,
                       List<CardModel> objects, CardSelectedListener listener) {
        super(context, textViewResourceId, objects);
        this.listener = listener;
        mInflater = LayoutInflater.from(context);
        mCount = objects.size();
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;

        final CardModel model = (CardModel) getItem(position);

        if(convertView == null){
            convertView = mInflater.inflate(R.layout.card_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ImageView cardImage = holder.getCardImage();

        if(!model.isSelected())
            cardImage.setImageResource(model.getDefBg());
        else
            cardImage.setImageResource(model.getCard());

        convertView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                model.setSelected(true);
                cardImage.setImageResource(model.getCard());
                if(listener != null)
                    listener.onCardSelected(model, position);
            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return mCount;
    }

    private class ViewHolder {
        private View mRow;
        private ImageView cardImage = null;

        public ViewHolder(View row) {
            mRow = row;
        }

        public ImageView getCardImage(){
            if(cardImage == null){
                cardImage = (ImageView) mRow.findViewById(R.id.galleryImage);
            }
            return cardImage;
        }

    }

}
