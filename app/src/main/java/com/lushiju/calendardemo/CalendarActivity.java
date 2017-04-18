package com.lushiju.calendardemo;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lushiju.calendardemo.utils.DateUtils;
import com.lushiju.calendardemo.view.MonthDateView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class CalendarActivity extends Activity implements View.OnClickListener {

    private Activity mActivity;
    private Button btn_commit;
    private ImageView iv_cal_left,iv_cal_right;
    private TextView tv_cal_date;
    private MonthDateView monthDateView;
    private String title="";
    private List<Integer> list;
    private List<String> dates;
    private StringBuffer str;
    private int year,month,day;
    private int currYear;
    private int currMonth;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_calendar);
        mActivity = this;
        initView();
    }

    private void initView() {
        String title = getIntent().getStringExtra("title");
        TextView tv_title = (TextView) findViewById(R.id.title);
        tv_title.setText(title);
        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        btn_commit = (Button) findViewById(R.id.btn_commit);
        btn_commit.setOnClickListener(this);
        iv_cal_left = (ImageView) findViewById(R.id.iv_cal_back);
        iv_cal_left.setOnClickListener(this);
        iv_cal_right = (ImageView) findViewById(R.id.iv_cal_forward);
        iv_cal_right.setOnClickListener(this);
        tv_cal_date = (TextView) findViewById(R.id.tv_cal_date);
        monthDateView = (MonthDateView) findViewById(R.id.monthDateView);
        list = new ArrayList<Integer>();
        dates = new ArrayList<String>();
        str = new StringBuffer();
        monthDateView.setText(title);
        long currentTimeMillis = System.currentTimeMillis();
        currYear= (Calendar.getInstance()).get(Calendar.YEAR);
        currMonth= (Calendar.getInstance()).get(Calendar.MONTH) + 1;
        Date nowDate = new Date(currentTimeMillis); //获取当前date格式时间
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String str =sdf.format(nowDate);
        final long currtimes = DateUtils.getTimes(str);
        monthDateView.setTextView(tv_cal_date);
        monthDateView.setDateClick(new MonthDateView.DateClick() {

            @Override
            public void onClickOnDate() {
                day = monthDateView.getmSelDay();
                month = monthDateView.getmSelMonth();
                year = monthDateView.getmSelYear();
                long times = DateUtils.getTimes(year + "-" + (month + 1) + "-" + day);
                if (times>=currtimes){
                    if (list.size()!=0&&list!=null){
                        if (list.contains(day)){
                            for (int i = 0; i <list.size() ; i++) {
                                if (list.get(i)==day){
                                    list.remove(list.get(i));
                                    dates.remove(dates.get(i));
                                }
                            }
                        }else {
                            list.add(day);
                            dates.add(year+"-"+getMonth(month+1)+"-"+getDay(day));
                        }
                    }else {
                        list.add(day);
                        dates.add(year+"-"+getMonth(month+1)+"-"+getDay(day));
                    }
                    monthDateView.setDaysHasThingList(list);
                }else {
                    monthDateView.setDaysHasThingList(list);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                this.finish();
            case R.id.btn_commit:
                sendData();
                break;
            case R.id.iv_cal_back:
                if (list!=null&&list.size()!=0){
                    list.clear();
                }
                if (dates!=null&&dates.size()!=0){
                    dates.clear();
                }
                if (str!=null&&!str.equals("")){
                    str = new StringBuffer("");
                }
                monthDateView.setDaysHasThingList(list);
                monthDateView.onLeftClick();
                currYear = getCurrYear();
                currMonth = getCurrMonth();
                break;
            case R.id.iv_cal_forward:
                if (list!=null&&list.size()!=0){
                    list.clear();
                }
                if (dates!=null&&dates.size()!=0){
                    dates.clear();
                }
                if (str!=null&&!str.equals("")){
                    str = new StringBuffer("");
                }
                monthDateView.setDaysHasThingList(list);
                monthDateView.onRightClick();
                currYear = getCurrYear();
                currMonth = getCurrMonth();
                break;
        }
    }
    private int getCurrYear(){
        String selectYearMonth = monthDateView.getSelectYearMonth();
        String[] str = selectYearMonth.split(",");
        int currYear = Integer.parseInt(str[0]);
        return currYear;
    }
    private int getCurrMonth(){
        String selectYearMonth = monthDateView.getSelectYearMonth();
        String[] str = selectYearMonth.split(",");
        int currMonth = Integer.parseInt(str[1]);
        return currMonth+1;
    }
    private void sendData() {
        if (str!=null&&!str.equals("")){
            str = new StringBuffer("");
        }
        dates = removeDuplicate(dates);
        if (dates.size()!=0&&dates!=null){
            for (int i = 0; i < dates.size(); i++) {
               if (i==0){
                   str.append(dates.get(i));
               }else{
                   str.append(","+dates.get(i));
               }
            }
        }
        Log.e("CalendarActivity","str="+str.toString());
        this.finish();
    }

    //  删除ArrayList中重复元素
    private List<String> removeDuplicate(List<String> list)  {
        HashSet h  =   new HashSet(list);
        list.clear();
        list.addAll(h);
        return list;
    }

    private String getMonth(int month){
        String Month="";
        if (month<10&&month>0){
            Month = "0"+(month);
        }else {
            Month = ""+(month);
        }
        return Month;
    }
    private String getDay(int day){
        String Day = "";
        if (day<10&&day>0){
            Day = "0"+day;
        }else {
            Day = ""+day;
        }
        return Day;
    }
    private void setDataResult(String houseFull, String closedShop) {
        if (title.equals(getString(R.string.guest_full))){
            //客满
            if (!TextUtils.isEmpty(houseFull)){
                string2Array(houseFull);
            }
        }else {
            //不营业
            if (!TextUtils.isEmpty(closedShop)){
                string2Array(closedShop);
            }
        }
    }

    private void string2Array(String str) {
        if (str.contains(",")){
            String[] datas = str.split(",");
            for (String date:datas) {
                String s = date.substring(date.length() - 2);
                String month = date.substring(5, 7);
                String year = str.substring(0, 4);
                int i = Integer.parseInt(s);
                int mm = Integer.parseInt(month);
                int yyyy = Integer.parseInt(year);
                boolean monthDay = getMonthDay(currYear,currMonth,yyyy,mm);
                if (monthDay){
                    list.add(i);
                    dates.add(date);
                }
            }
        }else {
            String s = str.substring(str.length() - 2);
            String month = str.substring(5, 7);
            String year = str.substring(0, 4);
            int i = Integer.parseInt(s);
            int mm = Integer.parseInt(month);
            int yyyy = Integer.parseInt(year);
            boolean monthDay = getMonthDay(currYear,currMonth,yyyy,mm);
            if (monthDay){
                list.add(i);
                dates.add(str);
            }
        }
        monthDateView.setDaysHasThingList(list);
    }
    private boolean getMonthDay(int year,int month,int selectYear,int selectMonth){
        if (selectYear==year&&selectMonth==month){
            return true;
        }
        return false;
    }
}
