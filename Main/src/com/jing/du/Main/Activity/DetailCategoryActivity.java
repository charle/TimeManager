package com.jing.du.Main.Activity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.jing.du.Main.Adapter.TagAdapter;
import com.jing.du.Main.Model.Category;
import com.jing.du.Main.Model.Tag;
import com.jing.du.Main.R;
import com.jing.du.Main.ViewHolder.TagViewHolder;
import com.jing.du.common.constant.CommonConstant;
import com.jing.du.common.utils.StringUtils;
import com.jing.du.common.utils.Toast;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by charle-chen on 15/7/14.
 */
public class DetailCategoryActivity extends BaseActivity {

    @InjectView(R.id.lv_tag)
    ListView lvTag;
    @InjectView(R.id.et_tag)
    BootstrapEditText etTag;
    @InjectView(R.id.bt_add_tag)
    BootstrapButton btAddTag;

    private int categoryId;
    private Category category;
    private List<Tag> tagList;
    private TagAdapter tagAdapter;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    if (StringUtils.isListEmpty(tagList)) {
                        tagList = new ArrayList<Tag>();
                        category.setTagList(tagList);
                    }
                    TagViewHolder tagViewHolder = new TagViewHolder();
                    tagAdapter = new TagAdapter(DetailCategoryActivity.this, tagList, tagViewHolder, R.layout.tag_item);
                    lvTag.setAdapter(tagAdapter);
                    break;
                case 2:
                    tagAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_category_detail);
        ButterKnife.inject(this);
        categoryId = getIntent().getIntExtra("category_id", 0);
        afterView();
        initData();
    }

    private void afterView() {
        ActionBar actionBar = getActionBar();
        actionBar.show();

        btAddTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tagString = etTag.getText().toString();

                if (StringUtils.isEmpty(tagString)) {
                    Toast.show(DetailCategoryActivity.this, getString(R.string.empty_warning), 1000);
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Tag tag = new Tag();
                            tag.setName(tagString);
                            tag.setDefaultType(CommonConstant.TAG_ANOTHER);
                            tag.setCategory(DataSupport.find(Category.class, categoryId));
                            tag.save();
                            tagList.add(tag);
                            mHandler.sendEmptyMessage(2);
                        }
                    }).start();
                }
                etTag.setText("");
            }
        });
    }


    @Override
    public void initData() {
        super.initData();
        new Thread(new Runnable() {
            @Override
            public void run() {
                category = DataSupport.find(Category.class, categoryId, true);
                tagList = category.getTagList();
                mHandler.sendEmptyMessage(1);
            }
        }).start();
    }

    @Override
    public void initEvent() {
        super.initEvent();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (category.getDefaultType() == CommonConstant.CATEGORY_ANOTHER) {
            getMenuInflater().inflate(R.menu.category_detail, menu);
        } else {
            getMenuInflater().inflate(R.menu.category_detail_default, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete:
                dialog();
                break;
            case R.id.action_save:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                break;
            case R.id.action_edit:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("category", category);
                intent.putExtras(bundle);
                intent.setClass(this, EditCategoryActivity.class);
                startActivityForResult(intent, CommonConstant.GOTO_EDIT_CATEGORY);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确认删除分类吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                DataSupport.delete(Category.class, categoryId);
                Intent intent = DetailCategoryActivity.this.getIntent();
                DetailCategoryActivity.this.setResult(CommonConstant.GOTO_TAG_FRAGMENT, intent);
                DetailCategoryActivity.this.finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CommonConstant.GOTO_EDIT_CATEGORY:
                if (resultCode == CommonConstant.GOTO_CATEGORY_DETAIL) {
                    category = (Category) data.getSerializableExtra("category");
                    tagList = category.getTagList();
                    mHandler.sendEmptyMessage(1);
                }
        }
    }
}
