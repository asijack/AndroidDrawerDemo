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
		//�Ƿ���ʾ���밴ť
		ab.setDisplayHomeAsUpEnabled(true);
		//���밴ť�Ƿ���Ч
		ab.setHomeButtonEnabled(true);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.navdrawer);
		//�����ͷ
		drawerArrow = new DrawerArrowDrawable(this) {
			@Override
			public boolean isLayoutRtl() {
				return false;
			}
		};
		//���뿪��
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				drawerArrow, R.string.drawer_open, R.string.drawer_close) {
			//������Ϸ���
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				//��ѡ��˵���Ч ��Ҫ���ִ���
				invalidateOptionsMenu();
			}

			//����򿪷���
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				invalidateOptionsMenu();
			}
		};
		//�Գ������ü���
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		//ͬ��״̬
		mDrawerToggle.syncState();

		String[] values = new String[] { "Stop Animation (Back icon)",
				"Stop Animation (Home icon)", "Start Animation",
				"Change Color"};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, values);
		mDrawerList.setAdapter(adapter);
		//���û�ѡ���˳����б������һ��Itemʱ, ϵͳ����onItemClickListener�ϵ�onItemClick(), 
		//��setOnItemClickListener().
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

	//��һ���ɱ����Ŀ��ѡ��ʱ��ϵͳ�������ض�����Ŀѡ�񷽷����� ��onOptionsItemSelected
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

	//���Activityʵ���ǵ�һ���������򲻵��ã��Ժ��ÿ�����������������
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onConfigurationChanged(android.content.res.Configuration)
	 * �ڸı���Ļ���򡢵�������̺����������ʱ �����AndroidManifest.xml ������android:onConfigurationChanged����
	 * �򲻻�ִ��oncreate������ִ������ķ�����
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
}
