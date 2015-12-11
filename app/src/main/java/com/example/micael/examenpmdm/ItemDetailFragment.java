package com.example.micael.examenpmdm;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.micael.examenpmdm.dummy.DummyContent;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.content);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);

        // Show the dummy content as text in a TextView.
        //Dentro de este if esta el codigo realcionado con la llamada al boton
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.item_detail)).setText(mItem.details);
            //llamada al boton
            Button bBorrar = (Button) rootView.findViewById(R.id.borrar);
            //añadiendo metodo onClick al boton
            bBorrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //llamando a este fragmente y comprobando con el if puedo saber si tengo en pantalla el fragment con la lista y de esta manera decidir si vuelvo al activity o si borro el contenido del detalle
                    ItemListFragment fragment = (ItemListFragment) getFragmentManager().findFragmentById(R.id.item_list);
                    if (fragment == null || !fragment.isInLayout()) {

                        //intent para volver al activity principal
                        Intent intentResultado = new Intent();
                        getActivity().setResult(Activity.RESULT_OK, intentResultado);
                        getActivity().finish();
                    } else {
                        //aqui llamo al meto para borrar el detalle
                        erase();
                        //toast que te indica si has borrado el detalle
                        Toast.makeText(getActivity(), "OK Has Borrado", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        return rootView;
    }
    //aqui creo el metodo con el que borrare el detalle
    public void erase() {
        TextView view = (TextView) getView().findViewById(R.id.item_detail);
        view.setText("");

    }
}
