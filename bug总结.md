# 问题总结
## 1-加载地图闪退

未配置Application

```
public class MApplication extends Application {

    /**
     * 上下文对象
     */
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(this.getApplicationContext());
    }

}
```

## 2-成功加载百度地图控件，但是只有网格线，没有地图展示

问题原因：百度地图SDK `key`配置错误。检查`key`

如果更换过AndroidStudio的  `.Android`文件夹 请重新生成`SHA1`码，并修改百度地图开发者中心应用的信息。

## 3-设置对话框窗口闪退
问题原因，整个`Application`应用的是 `style.xml`中自定义的`AppTheme`主题
查看`AppTheme`如下,他的父类主题是`AppCompat`
```
<style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
        <!-- Customize your theme here. -->
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
</style>
```
解决方法:修改`AndroidMainifest.xml`如下,`AboutActivity`为要设置为对话框式的`Activity`
默认状态下:`android:theme="@android:style/Theme.Dialog" />`
应修改为:`android:theme="@style/Theme.AppCompat.Dialog" />`
```
<activity android:name=".AboutActivity"
            android:label="@string/about"
            android:theme="@style/Theme.AppCompat.Dialog" />
```
