package com.asijack.ldrawer.sample;
import com.asijack.ldrawer.R;
import com.asijack.ldrawer.lib.ActionBarDrawerToggle;
import com.asijack.ldrawer.lib.DrawerArrowDrawable;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private DrawerArrowDrawable drawerArrow;
	private boolean drawerArrowColor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActionBar ab = getActionBar();
		//是否显示抽屉按钮
		ab.setDisplayHomeAsUpEnabled(true);
		//抽屉按钮是否有效
		ab.setHomeButtonEnabled(true);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.navdrawer);
		//抽屉箭头
		drawerArrow = new DrawerArrowDrawable(this) {
			@Override
			public boolean isLayoutRtl() {
				return false;
			}
		};
		//抽屉开关
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				drawerArrow, R.string.drawer_open, R.string.drawer_close) {
			//抽屉关上方法
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				//是选项菜单无效 需要重现创建
				invalidateOptionsMenu();
			}

			//抽屉打开方法
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				invalidateOptionsMenu();
			}
		};
		//对抽屉设置监听
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		//同步状态
		mDrawerToggle.syncState();

		String[] values = new String[] { "Stop Animation (Back icon)",
				"Stop Animation (Home icon)", "Start Animation",
				"Change Color"};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, values);
		mDrawerList.setAdapter(adapter);
		//当用户选择了抽屉列表里面的一个Item时, 系统调用onItemClickListener上的onItemClick(), 
		//给setOnItemClickListener().
		mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@SuppressLint("ResourceAsColor")
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						switch (position) {
						case 0:
							mDrawerToggle.setAnimateEnabled(false);
							drawerArrow.setProgress(1f);
							break;
						case 1:
							mDrawerToggle.setAnimateEnabled(false);
							drawerArrow.setProgress(0f);
							break;
						case 2:
							mDrawerToggle.setAnimateEnabled(true);
							mDrawerToggle.syncState();
							break;
						case 3:
							if (drawerArrowColor) {
								drawerArrowColor = false;
								drawerArrow.setColor(R.color.ldrawer_color);
							} else {
								drawerArrowColor = true;
								drawerArrow.setColor(R.color.drawer_arrow_second_color);
							}
							mDrawerToggle.syncState();
							break;
						
						}

					}
				});
	}

	//当一个可标记项目被选中时，系统将调用特定的项目选择方法比如 ：onOptionsItemSelected
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
				mDrawerLayout.closeDrawer(mDrawerList);
			} else {
				mDrawerLayout.openDrawer(mDrawerList);
			}
		}
		return super.onOptionsItemSelected(item);
	}

	//如果Activity实例是第一次启动，则不调用，以后的每次重新启动都会调用
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onConfigurationChanged(android.content.res.Configuration)
	 * 在改变屏幕方向、弹出软件盘和隐藏软键盘时 如果在AndroidManifest.xml 设置了android:onConfigurationChanged属性
	 * 则不会执行oncreate方法会执行下面的方法。
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
}
