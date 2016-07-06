package com.app.example.baidutranslateapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	
	Spinner languageSpinner;
	Button translation;
	ImageButton clearText;
	ListView list;
	EditText languagecontent;
	TextView yiwen,moveyiwen;
	RelativeLayout id_yiwen;
	RelativeLayout id_top;
	LinearLayout id_bottom;
	String curName="目标语言：英语";
	List<String> listViewDataList;
	ArrayAdapter<String> adapter1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();
	}

	private void initViews() {
		id_yiwen=(RelativeLayout) findViewById(R.id.id_yiwen);
		languageSpinner=(Spinner) findViewById(R.id.id_languagetranslation);
		translation=(Button) findViewById(R.id.id_translation);
		clearText=(ImageButton) findViewById(R.id.id_clearText);
		list=(ListView) findViewById(R.id.id_list);
		languagecontent=(EditText) findViewById(R.id.id_translatecontent);
		yiwen=(TextView) findViewById(R.id.id_yiwen2);
		moveyiwen=(TextView) findViewById(R.id.id_movetext);
		translation.setOnClickListener(this);
		clearText.setOnClickListener(this);
		List<String> dataList=new ArrayList<String>();
		dataList.add("目标语言：英语");
		dataList.add("目标语言：中文");
		dataList.add("目标语言：粤语");
		dataList.add("目标语言：繁体中文");
		dataList.add("目标语言：西班牙语");
		dataList.add("目标语言：俄语");
		dataList.add("目标语言：葡萄牙语");
		dataList.add("目标语言：日语");
		dataList.add("目标语言：韩语");
		dataList.add("目标语言：法语");
		dataList.add("目标语言：阿拉伯语");
		dataList.add("目标语言：德语");
		dataList.add("目标语言：意大利语");
		ArrayAdapter<String> adapter=new ArrayAdapter(this, android.R.layout.simple_list_item_1,dataList );
		languageSpinner.setAdapter(adapter);
		languageSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				curName=(String) languageSpinner.getSelectedItem();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				
			}
		});
		listViewDataList=new ArrayList<String>();
		adapter1=new ArrayAdapter(this, android.R.layout.simple_list_item_1,listViewDataList );
		list.setAdapter(adapter1);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String ls=listViewDataList.get(position);
				String[] lsarr=ls.split("\n");
				languagecontent.setText(lsarr[0]);
				yiwen.setText(lsarr[1]);
				list.setVisibility(View.GONE);
				id_yiwen.setVisibility(View.VISIBLE);
			}
		});
	}

	public String targetLanguage(String toLanguage)
	{
		String target=null;
		if(toLanguage==null)
		{
			target="en";
		}
		else
		{
			if(toLanguage.equals("目标语言：英语"))
			{
				target="en";
			}
			else if(toLanguage.equals("目标语言：中文"))
			{
				target="zh";
			}
			else if(toLanguage.equals("目标语言：粤语"))
			{
				target="yue";
			}
			else if(toLanguage.equals("目标语言：繁体中文"))
			{
				target="cht";
			}
			else if(toLanguage.equals("目标语言：西班牙语"))
			{
				target="spa";
			}
			else if(toLanguage.equals("目标语言：俄语"))
			{
				target="ru";
			}
			else if(toLanguage.equals("目标语言：葡萄牙语"))
			{
				target="pt";
			}
			else if(toLanguage.equals("目标语言：日语"))
			{
				target="jp";
			}
			else if(toLanguage.equals("目标语言：韩语"))
			{
				target="kor";
			}
			else if(toLanguage.equals("目标语言：法语"))
			{
				target="fra";
			}
			else if(toLanguage.equals("目标语言：阿拉伯语"))
			{
				target="ara";
			}
			else if(toLanguage.equals("目标语言：德语"))
			{
				target="de";
			}
			else if(toLanguage.equals("目标语言：意大利语"))
			{
				target="it";
			}
			else
			{
				target="en";
			}
		}
		
		return target;
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.id_translation:
			String re=languagecontent.getText().toString();
			if(re.isEmpty())
			{
				Toast.makeText(this, "请输入有效的翻译内容", Toast.LENGTH_SHORT).show();
			}
			else
			{
				TranslationUtils tu=new TranslationUtils();
				String result=tu.translation(re, targetLanguage(curName));
				if(result!=null)
				{
					yiwen.setText(result);
					list.setVisibility(View.GONE);
					id_yiwen.setVisibility(View.VISIBLE);
					listViewDataList.add(re+"\n"+result);
					adapter1.notifyDataSetChanged();
				}
				else
				{
					Toast.makeText(this, "发生错误，请重试", Toast.LENGTH_SHORT).show();
				}
			}
			break;
		case R.id.id_clearText:
			languagecontent.setText("");
			list.setVisibility(View.VISIBLE);
			id_yiwen.setVisibility(View.GONE);
			break;
		}
	}
	
}
