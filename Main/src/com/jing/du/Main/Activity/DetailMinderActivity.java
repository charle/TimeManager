package com.jing.du.Main.Activity;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.jing.du.Main.Model.Minder;
import com.jing.du.Main.R;
import com.jing.du.common.constant.CommonConstant;
import org.litepal.crud.DataSupport;

/**
 * Created by charle-chen on 15/7/12.
 */
public class DetailMinderActivity extends BaseActivity {

    @InjectView(R.id.tv_minder_title)
    TextView tvMinderTitle;
    @InjectView(R.id.tv_minder_content)
    TextView tvMinderContent;
    @InjectView(R.id.iv_minder_type)
    ImageView ivMinderType;
    @InjectView(R.id.tv_minder_type)
    TextView tvMinderType;

    private int minderId;
    private Minder mindder;
    private int position;
    private boolean isDataChanged = false;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    tvMinderTitle.setText(mindder.getTitle());
                    tvMinderContent.setText(mindder.getContent());
                    ivMinderType.setImageResource(CommonConstant.MINDER_RESOUCE[mindder.getMinderType()]);
                    tvMinderType.setText(CommonConstant.MINDER_STATE[mindder.getMinderType()]);
                    tvMinderType.setTextColor(CommonConstant.MINDER_COLOR[mindder.getMinderType()]);
                    break;
                case 2:
                    DetailMinderActivity.this.finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_minder_detail);
        ButterKnife.inject(this);
        minderId = getIntent().getIntExtra("minder_id", 0);
        position = getIntent().getIntExtra("position", 0);
        ActionBar actionBar = getActionBar();
        actionBar.show();
        initData();
    }


    @Override
    public void initData() {
        super.initData();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mindder = DataSupport.find(Minder.class, minderId);
                mHandler.sendEmptyMessage(1);
            }
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.minder_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("minder_id", minderId);
                intent.putExtras(bundle);
                intent.setClass(this, CreateMinderActivity.class);
                startActivityForResult(intent, CommonConstant.GOTO_CREATE_MINDER);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            case R.id.action_delete:
                dialog();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("确认删除事件吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                DataSupport.delete(Minder.class, minderId);
                Intent intent = DetailMinderActivity.this.getIntent();
                DetailMinderActivity.this.setResult(CommonConstant.GOTO_MINDER_FLAGMENT, intent);
                DetailMinderActivity.this.finish();
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
            case CommonConstant.GOTO_CREATE_MINDER:
                if (resultCode == CommonConstant.GOTO_MINDER_FLAGMENT) {
                    mindder = (Minder) data.getSerializableExtra("minder");
                    isDataChanged = true;
                    mHandler.sendEmptyMessage(1);
                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (isDataChanged) {
                    Intent intent = DetailMinderActivity.this.getIntent();
                    intent.putExtra("minder", mindder);
                    DetailMinderActivity.this.setResult(CommonConstant.GOTO_MINDER_FLAGMENT_CHANGE, intent);
                    DetailMinderActivity.this.finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                } else {
                    DetailMinderActivity.this.finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
                break;
            default:
                break;
        }
        return true;
    }
}

