package ditu.bawai.com.gaodemap;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MyLocationStyle;

public class MainActivity extends AppCompatActivity {
    MapView mMapView = null;
    AMap aMap;
    MyLocationStyle myLocationStyle;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;
    private Button btntao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map);
        btntao = (Button) findViewById(R.id.btntiao);
        btntao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,GPSNaviActivity.class);
                startActivity(intent);
            }
        });
        //初始化地图控制器对象

        if (aMap == null) {
            aMap =  mMapView .getMap();
        }

        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
//aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
          AMapLocations();


    }
      public void AMapLocations(){
          //初始化定位
          mLocationClient = new AMapLocationClient(getApplicationContext());

     //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
          mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        //该方法默认为false。
          mLocationOption.setOnceLocation(true);

//获取最近3s内精度最高的一次定位结果：
//设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
          mLocationOption.setOnceLocationLatest(true);
          //给定位客户端对象设置定位参数
          mLocationClient.setLocationOption(mLocationOption);
//启动定位
          //声明定位回调监听器
       AMapLocationListener mLocationListener = new AMapLocationListener() {
           @Override
           public void onLocationChanged(AMapLocation aMapLocation) {
               if (aMapLocation != null) {
                   if (aMapLocation.getErrorCode() == 0) {
                       //可在其中解析amapLocation获取相应内容。
                       Log.d("tag", aMapLocation.getCountry() + aMapLocation.getProvince() + aMapLocation.getCity()
                       +aMapLocation.getDistrict()+ aMapLocation.getStreet());
                       System.out.print(aMapLocation.getCountry() + aMapLocation.getProvince() + aMapLocation.getCity()
                               +aMapLocation.getDistrict()+ aMapLocation.getStreet());
                       Toast.makeText(MainActivity.this,aMapLocation.getCountry() + aMapLocation.getProvince() + aMapLocation.getCity()
                               +aMapLocation.getDistrict()+ aMapLocation.getStreet(),Toast.LENGTH_SHORT).show();
                   } else {
                       //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                       Log.e("AmapError", "location Error, ErrCode:"
                               + aMapLocation.getErrorCode() + ", errInfo:"
                               + aMapLocation.getErrorInfo());
                   }
               }
           }



       };
          //设置定位回调监听
          mLocationClient.setLocationListener(mLocationListener);
          mLocationClient.startLocation();
      }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }
}

