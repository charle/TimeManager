package com.jing.du.Main.Activity;

import android.app.ActionBar;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.jing.du.Main.Adapter.CategoryEditAdapter;
import com.jing.du.Main.Model.Category;
import com.jing.du.Main.Model.Tag;
import com.jing.du.Main.R;
import com.jing.du.Main.ViewHolder.CategoryEditViewHolder;
import com.jing.du.common.adapter.MyListEditAdapter;
import com.jing.du.common.constant.CommonConstant;
import com.jing.du.common.utils.StringUtils;
import com.jing.du.common.utils.Toast;
import org.litepal.crud.DataSupport;

/**
 * Created by charle-chen on 15/7/14.
 */
public class EditCategoryActivity extends BaseActivity {

    @InjectView(R.id.et_category)
    EditText etCategory;
    @InjectView(R.id.bt_edit_category)
    BootstrapButton btEditCategory;
    @InjectView(R.id.lv_tag)
    ListView lvTag;
    private boolean isSaved = true;
    private Category category;
    private CategoryEditAdapter categoryEditAdapter;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    categoryEditAdapter.notifyDataSetChanged();
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_edit_category);
        ButterKnife.inject(this);
        category = (Category) getIntent().getSerializableExtra("category");
        afterView();
        initData();
        initEvent();
    }

    private void afterView() {
        if (!StringUtils.isObjectEmpty(category)) {
            etCategory.setText(category.getName());
        }
        ActionBar actionBar = getActionBar();
        actionBar.show();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initData() {
        super.initData();
        if (!StringUtils.isObjectEmpty(category)) {
            CategoryEditViewHolder categoryEditViewHolder = new CategoryEditViewHolder();
            categoryEditAdapter = new CategoryEditAdapter(this, category.getTagList());
            lvTag.setAdapter(categoryEditAdapter);
        }
    }

    @Override
    public void initEvent() {
        super.initEvent();
        categoryEditAdapter.setOnEditItemClickListener(new MyListEditAdapter.onEditItemClickListener() {
            @Override
            public void onEditItemClick(View v, int position) {
                if (category.getTagList().get(position).getDefaultType() == CommonConstant.TAG_ANOTHER) {
                    Intent intent = new Intent();
                    intent.putExtra("tag_id", category.getTagList().get(position).getId());
                    intent.putExtra("position", position);
                    intent.setClass(EditCategoryActivity.this, EditTagActivity.class);
                    startActivityForResult(intent, CommonConstant.GOTO_EDIT_TAG_ITEM);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });

        categoryEditAdapter.setOnDeleteItemClickListener(new MyListEditAdapter.onDeleteItemClickListener() {
            @Override
            public void onDeleteItemClick(View v, final int position) {
                if (category.getTagList().get(position).getDefaultType() == CommonConstant.TAG_ANOTHER) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            DataSupport.delete(Tag.class, category.getTagList().get(position).getId());
                            category.getTagList().remove(position);
                            mHandler.sendEmptyMessage(1);
                        }
                    }).start();

                }
            }
        });
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                String str = etCategory.getText().toString();
                if (StringUtils.isEmpty(str)) {
                    Toast.show(this, getString(R.string.empty_warning), 1000);
                } else {
                    ContentValues values = new ContentValues();
                    values.put("name", str);
                    DataSupport.update(Category.class, values, category.getId());
                    Intent intent = getIntent();
                    category.setName(str);
                    intent.putExtra("category", category);
                    EditCategoryActivity.this.setResult(CommonConstant.GOTO_CATEGORY_DETAIL, intent);
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
                break;
            case android.R.id.home:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_category, menu);
        return true;
    }

    @OnClick({R.id.bt_edit_category})
    public void clickEvent(View v) {
        switch (v.getId()) {
            case R.id.bt_edit_category:
                if (category.getDefaultType() == CommonConstant.CATEGORY_ANOTHER) {
                    if (isSaved) {
                        etCategory.setEnabled(true);
                        btEditCategory.setText(getString(R.string.save));
                        isSaved = false;
                    } else {
                        etCategory.setEnabled(false);
                        btEditCategory.setText(getString(R.string.edit));
                        isSaved = true;
                    }
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CommonConstant.GOTO_EDIT_TAG_ITEM:
                if (resultCode == CommonConstant.GOTO_EDIT_CATEGORY) {
                    Tag tag = (Tag) data.getSerializableExtra("tag");
                    int position = data.getIntExtra("position", 0);
                    category.getTagList().remove(position);
                    category.getTagList().add(position, tag);
                    mHandler.sendEmptyMessage(1);
                }
        }
    }
}