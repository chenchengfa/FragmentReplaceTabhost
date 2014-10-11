package com.example.fragmentreplacetabhost;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	// 用于展示消息的Fragment
	private MessageFragment messageFragment;
	// 用于展示联系人的Fragment
	private ContactsFragment contactsFragment;
	// 消息界面布局
	private View messageLayout;
	// 联系人界面布局
	private View contactsLayout;
	// Tab布局上消息的图标控件
	private ImageView messageImage;
	// Tab布局上联系人的图标控件
	private ImageView contactsImage;
	// 消息图标下的文本
	private TextView messageText;
	// 联系人图标下的文本
	private TextView contactsText;
	// 用于对Fragment进行管理
	private FragmentManager fragmentManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initViews();
	}

	// 初始化控件
	@SuppressLint("NewApi")
	private void initViews() {
		messageLayout = findViewById(R.id.message_layout);
		contactsLayout = findViewById(R.id.contacts_layout);
		messageImage = (ImageView) findViewById(R.id.message_image);
		contactsImage = (ImageView) findViewById(R.id.contacts_image);
		messageText = (TextView) findViewById(R.id.message_text);
		contactsText = (TextView) findViewById(R.id.contacts_text);
		fragmentManager = getFragmentManager();
		setTabselection(0);
		// 给两个布局添加监听事件
		messageLayout.setOnClickListener(this);
		contactsLayout.setOnClickListener(this);
	}

	// 布局的监听事件
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.message_layout:
			setTabselection(0);
			break;
		case R.id.contacts_layout:
			setTabselection(1);
			break;
		}
	}

	/**
	 * 根据传入的index参数来设置选中tab页
	 * 
	 * @param index
	 *            每个tab页对应的下标，0代表消息，1代表联系人
	 */
	@SuppressLint("NewApi")
	private void setTabselection(int index) {
		// 每次选中之前清除掉上次的选中状态
		clearSelection();
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上
		hideFragments(transaction);
		switch (index) {
		case 0:// 此时点击的是Message
			messageText.setTextColor(Color.WHITE);
			if (messageFragment == null) {
				// 如果MessageFragment为空，则创建一个并添加到界面上
				messageFragment = new MessageFragment();
				// 第一个参数为添加到哪个布局的id，第二个参数是被添加的Fragment
				transaction.add(R.id.content, messageFragment);
			} else {
				transaction.show(messageFragment);
			}
			break;
		case 1:// 点击的时Contacts
			contactsText.setTextColor(Color.WHITE);
			if (contactsFragment == null) {
				contactsFragment = new ContactsFragment();
				transaction.add(R.id.content, contactsFragment);
			} else {
				transaction.show(contactsFragment);
			}

			break;

		}
		transaction.commit();
	}

	// 清除掉所有的选中状态
	private void clearSelection() {
		messageText.setTextColor(Color.parseColor("#82858b"));
		contactsText.setTextColor(Color.parseColor("#82858b"));
	}

	/**
	 * 将所有的Fragment都设置为隐藏状态
	 * 
	 * @param transaction
	 *            用于对Fragment执行操作的事物
	 */
	@SuppressLint("NewApi")
	private void hideFragments(FragmentTransaction transaction) {
		if (messageFragment != null) {
			transaction.hide(messageFragment);
		}
		if (contactsFragment != null) {
			transaction.hide(contactsFragment);
		}
	}
}
