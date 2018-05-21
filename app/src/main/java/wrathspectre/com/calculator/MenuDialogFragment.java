package wrathspectre.com.calculator;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MenuDialogFragment extends BottomSheetDialogFragment {

    private MenuDialogListener listener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.menu_dialog_layout, container, false);

        FloatingActionButton basicButton = view.findViewById(R.id.basic_button);
        FloatingActionButton scientificButton = view.findViewById(R.id.scientific_button);
        FloatingActionButton baseNButton = view.findViewById(R.id.base_n_button);

        basicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonClick("basic");
                dismiss();
            }
        });

        scientificButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonClick("scientific");
                dismiss();
            }
        });

        baseNButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onButtonClick("base_n");
                dismiss();
            }
        });

        return view;
    }

    public interface MenuDialogListener {
        void onButtonClick(String layout);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (MenuDialogListener) context;
        } catch(ClassCastException e) {
            e.printStackTrace();
        }
    }
}
