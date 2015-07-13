package com.jing.du.common.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.jing.du.Main.Activity.MainActivity;
import com.jing.du.Main.Model.Category;
import com.jing.du.Main.Model.Tag;
import com.jing.du.Main.R;
import com.jing.du.common.utils.StringUtils;
import org.litepal.crud.DataSupport;

/**
 * Created by charle-chen on 15/6/26.
 */
public class InputDialog {

    public static final int ADD_TAG = 1;
    public static final int EDIT_CATEGORY = 2;
    public static final int EDIT_TAG = 3;

    int id;
    private int layoutId;
    private Context context;
    private int operatorId;

    public InputDialog(Context context, int layoutId, int id, int operatorId) {
        this.layoutId = layoutId;
        this.context = context;
        this.id = id;
        this.operatorId = operatorId;
    }

    public void initView() {
        LayoutInflater li = LayoutInflater.from(context);
        final View promptsView = li.inflate(layoutId, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setView(promptsView);

        alertDialogBuilder.setCancelable(false).setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        switch (operatorId) {
                            case ADD_TAG:
                                addTag(promptsView);
                                break;
                            case EDIT_CATEGORY:
                                editCategory(promptsView);
                                break;
                            case EDIT_TAG:
                                editTag(promptsView);
                                break;
                        }
                    }
                });
        alertDialogBuilder.setCancelable(false).setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    private void addTag(View view) {
        BootstrapEditText userInput = (BootstrapEditText) view.findViewById(R.id.et_tag);
        final String textString = userInput.getText().toString();
        if (!StringUtils.isEmpty(textString)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Category category = DataSupport.find(Category.class, id);
                    Tag tag = new Tag();
                    tag.setName(textString);
                    tag.setCategory(category);
                    tag.save();
                }
            }).start();

        }
    }

    private void editCategory(View view) {
        BootstrapEditText userInput = (BootstrapEditText) view.findViewById(R.id.et_tag);
        final String textString = userInput.getText().toString();
        if (!StringUtils.isEmpty(textString)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Category category = new Category();
                    category.setName(textString);
                    category.update(id);
                }
            }).start();

        }
    }

    private void editTag(View view) {
        BootstrapEditText userInput = (BootstrapEditText) view.findViewById(R.id.et_tag);
        final String textString = userInput.getText().toString();
        if (!StringUtils.isEmpty(textString)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Tag tag = new Tag();
                    tag.setName(textString);
                    tag.update(id);
                }
            }).start();
        }
    }
}
